package com.burrow.widget.single_child.canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.BufferedImageRasterizeData;
import com.burrow.auxiliary.Graphics2DRasterizeData;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.base.BPanel;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.root.RootWidget;
import com.burrow.widget.single_child.SingleChildWidget;
import com.burrow.widget.single_child.canvas.stroke.BRenderFilter;
import com.burrow.widget.single_child.canvas.stroke.BStroke;

@SuppressWarnings("rawtypes")
public class SingleChildCanvasWidget extends SingleChildWidget implements BCanvas {
    protected double dpr;
    protected SingleChildCanvasWidgetState state;

    protected BCanvas parentCanvas;
    protected RootWidget root;

    protected boolean needsRepaint = true;
    protected long filterUID = 0L;

    public static final int repaintsUntilCache = 3;
    protected int remainingRepaintsUntilCache = repaintsUntilCache;

    @Override
    public void layout(LayoutData data) {
        state.frame.setSize(data.maxWidth, data.maxHeight);
        child.layout(data);

        if(state.cache != null) {
            if(
                (state.cache.getWidth() == 0 && state.frame.width != 0) ||
                state.cache.getWidth() != state.frame.width ||
                state.cache.getHeight() != state.frame.height
            ) {
                resetCache();
            }
        }
    }

    @Override
    public void paint(PaintData data) {
        if(needsRepaint) {
            needsRepaint = false;
            state.strokes.clear();
            child.paint(data.set(child.getBoxFrame()).setCanvas(this));
            data.setCanvas(parentCanvas);
        }
    }

    @Override
    public BoxFrame getBoxFrame() {
        return state.frame;
    }
    @Override
    public State getState() {
        return state;
    }
    @Override
    public void createBoxFrame() {
        state.frame = new BoxFrame();
    }
    @Override
    public void createState() {
        this.state = new SingleChildCanvasWidgetState(this);
    }
    
    @Override
    public void init(InitData data) {
        state.strokes = new ArrayList<BStroke>();
        parentCanvas = data.canvas;

        dpr = data.screen.dpr;
        root = data.root;

        child.init(data.push(this).setCanvas(this));
        data.pop().setCanvas(parentCanvas);
    }

    @Override
    public void onRelocate(TreeData data) {
        parentCanvas = data.canvas;
        root = data.root;

        child.onRelocate(data.push(this).setCanvas(this));
        data.pop().setCanvas(parentCanvas);
    }
    
    @Override
    public int pixelColor(double x, double y, BRenderFilter filter) {
        int color = 0x00000000;
        for(int i = state.strokes.size() - 1; i > -1; i--) {
            if(state.strokes.get(i).drawStroke(x, y, filter.getCropBounds())) {
                color = state.strokes.get(i).pixelColor(x, y, filter);
                if(color >>> 24 == 0xFF) { // TODO: deal with this later
                    return color;
                }
            }
        }
        return color;
    }
    public int pixelColor(double x, double y, BPanel panel, double width, double height) {
        BRenderFilter filter = new BRenderFilter(
            panel,
            new double[]{0, 0, width, height},
            new double[]{0, 0}
        );
        int color = 0x00000000;
        for(int i = state.strokes.size() - 1; i > -1; i--) {
            if(state.strokes.get(i).drawStroke(x, y, filter.getCropBounds())) {
                color = state.strokes.get(i).pixelColor(x, y, filter);
                if(color >>> 24 == 0xFF) { // TODO: deal with this later
                    return color;
                }
            }
        }
        return color;
    }

    @Override
    public void rasterizeToBufferedImage(BufferedImageRasterizeData data) {
        data.filter.pushTrans(state.frame.toPositionDoubleArray());
        long uid = data.filter.getUID();
        if(filterUID != uid) {
            filterUID = uid;
            resetSelfCache();
        }
        if(remainingRepaintsUntilCache > 0 || data.remainingCaches <= 0 || !data.isCallerCaching) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToBufferedImage(data);
            }
            remainingRepaintsUntilCache = Math.max(0, remainingRepaintsUntilCache-1);
        } else {
            if(remainingRepaintsUntilCache == 0) {
                createCache(data.decremetRemainingCaches().setImage(state.cache));
            }
            for(int i = 0; i < data.image.getWidth(); i++) {
                for(int j = 0; j < data.image.getHeight(); j++) {
                    data.image.setRGB(i, j, state.cache.getRGB(i, j));
                }
            }
        }
        data.filter.popCropBounds();
    }

    @Override
    public void rasterizeToGraphics2D(Graphics2DRasterizeData data) {
        data.filter.pushTrans(state.frame.toPositionDoubleArray());
        long uid = data.filter.getUID();
        if(filterUID != uid) {
            filterUID = uid;
            resetSelfCache();
        }
        if(remainingRepaintsUntilCache > 0 || data.remainingCaches <= 0 || !data.isCallerCaching) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToGraphics2D(data);
            }
            remainingRepaintsUntilCache = Math.max(0, remainingRepaintsUntilCache-1);
            
        } else {
            if(remainingRepaintsUntilCache == 0) {
                createCache(new BufferedImageRasterizeData(state.cache, data.filter, data.decremetRemainingCaches().remainingCaches));
            }
            data.g.drawImage(state.cache, 0, 0, null);
        }
        data.filter.popCropBounds();
    }

    public void resolveCache(BufferedImageRasterizeData data) {
        int i = 0;
        for(BufferedImage cache : root.caches) {
            if(cache.getWidth() == (int)state.frame.width && cache.getHeight() == (int)state.frame.height) {
                state.cache = root.caches.remove(i);
                return;
            }
            i++;
        }
        state.cache = new BufferedImage(
            Math.max(1, (int)state.frame.width),
            Math.max(1, (int)state.frame.height),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = state.cache.createGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, state.cache.getWidth(), state.cache.getHeight());
    }

    @Override
    public void createCache(BufferedImageRasterizeData data) {
        resolveCache(data);
        for(int i = state.strokes.size() - 1; i >= 0; i--) {
            state.strokes.get(i).rasterizeToBufferedImage(data);
        }
        remainingRepaintsUntilCache = -1;
    }

    @Override
    public void disposeCache() {
        root.caches.add(state.cache);
        state.cache = null;
    }

    @Override
    public boolean drawStroke(double x, double y, double[] hitbox) {
        return true;
    }

    @Override
    public void addStroke(BStroke stroke) {
        state.strokes.add(stroke);
    }

    @Override
    public void requestRepaint() {
        needsRepaint = true;
        resetCache();
    }

    @Override
    public void resetCache() {
        remainingRepaintsUntilCache = repaintsUntilCache;
        if(parentCanvas != null) {
            parentCanvas.resetCache();
        }
    }

    protected void resetSelfCache() {
        needsRepaint = true;
        remainingRepaintsUntilCache = repaintsUntilCache;
    }

    public ArrayList<BStroke> getStrokes() {
        return state.strokes;
    }

    public SingleChildCanvasWidget(Widget child) {
        super(child);
    }
    
    public class SingleChildCanvasWidgetState extends State<SingleChildCanvasWidget> {
        public BoxFrame frame;
        public BufferedImage cache;
        public ArrayList<BStroke> strokes;

        public SingleChildCanvasWidgetState(SingleChildCanvasWidget self) {
            super(self);
        }
    }
}
