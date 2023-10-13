package com.burrow.widget.single_child.canvas;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.base.BPanel;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.SingleChildWidget;
import com.burrow.widget.single_child.canvas.stroke.BRenderFilter;
import com.burrow.widget.single_child.canvas.stroke.BStroke;

@SuppressWarnings("rawtypes")
public class CanvasWidget extends SingleChildWidget implements BStroke {
    protected double dpr;
    protected CanvasState state;

    protected CanvasWidget parentCanvas;

    protected boolean needsRepaint = true;

    public static final int repaintsUntilCache = 3;
    protected int remainingRepaintsUntilCache = repaintsUntilCache;

    @Override
    public void layout(LayoutData data) {
        state.frame.setSize(data.maxWidth, data.maxHeight);
        child.layout(data);

        if(
            (state.image.getWidth() == 0 && data.windowWidth != 0) ||
            state.image.getWidth() != data.windowWidth ||
            state.image.getHeight() != data.windowHeight
        ) {
            state.image = new BufferedImage(Math.max(1, data.windowWidth), Math.max(1, data.windowHeight), BufferedImage.TYPE_INT_ARGB);
            resetCache();
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
        this.state = new CanvasState(this);
    }
    
    @Override
    public void init(InitData data) {
        state.strokes = new ArrayList<BStroke>();
        parentCanvas = data.canvas;

        dpr = data.screen.dpr; 

        child.init(data.push(this).setCanvas(this));
        data.pop().setCanvas(parentCanvas);

        state.image = new BufferedImage(Math.max(1, data.root.panel.getWidth()), Math.max(1, data.root.panel.getHeight()), BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void onRelocate(TreeData data) {
        parentCanvas = data.canvas;

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
        BRenderFilter filter = new BRenderFilter(panel, new double[]{0, 0, width, height});
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

    // @Override
    // public void rasterizeToCArr(int[][] cArr, BRenderFilter filter) {
    //     if(remainingRepaintsUntilCache > 0) {
    //         for(int i = state.strokes.size() - 1; i >= 0; i--) {
    //             state.strokes.get(i).rasterizeToCArr(cArr, filter);
    //         }
    //         remainingRepaintsUntilCache--;
    //         return;
    //     }
    //     if(remainingRepaintsUntilCache == 0) {
    //         for(int i = state.strokes.size() - 1; i >= 0; i--) {
    //             state.strokes.get(i).rasterizeToCArr(state.cArr, filter);
    //         }
    //         remainingRepaintsUntilCache--;
    //     }
    //     final int width = Math.min(state.cArr.length, cArr.length);
    //     if(width == 0) {return;}
    //     final int height = Math.min(state.cArr[0].length, cArr[0].length);
    //     for(int i = 0; i < width; i++) {
    //         for(int j = 0; j < height; j++) {
    //             cArr[i][j] = state.cArr[i][j];
    //         }
    //     }
    // }

    @Override
    public void rasterizeToBufferedImage(BufferedImage image, BRenderFilter filter) {
        if(remainingRepaintsUntilCache > 0) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToBufferedImage(image, filter);
            }
            remainingRepaintsUntilCache--;
            return;
        }
        if(remainingRepaintsUntilCache == 0) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToBufferedImage(state.image, filter);
            }
            remainingRepaintsUntilCache--;
        }
        for(int i = 0; i < image.getWidth(); i++) {
            for(int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, state.image.getRGB(i, j));
            }
        }
    }

    @Override
    public void rasterizeToGraphics2D(Graphics2D g, BRenderFilter filter) {
        if(remainingRepaintsUntilCache > 0) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToGraphics2D(g, filter);
            }
            remainingRepaintsUntilCache--;
            return;
        }
        if(remainingRepaintsUntilCache == 0) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToBufferedImage(state.image, filter);
            }
            remainingRepaintsUntilCache--;
        }
        g.drawImage(state.image, 0, 0, null);
    }

    @Override
    public boolean drawStroke(double x, double y, double[] hitbox) {
        return true;
    }

    public void addStroke(BStroke stroke) {
        state.strokes.add(stroke);
    }

    public void requestRepaint() {
        needsRepaint = true;
        resetCache();
    }

    public void resetCache() {
        remainingRepaintsUntilCache = repaintsUntilCache;
        if(parentCanvas != null) {
            parentCanvas.resetCache();
        }
    }

    public ArrayList<BStroke> getStrokes() {
        return state.strokes;
    }

    public CanvasWidget(Widget child) {
        super(child);
    }
    
    public class CanvasState extends State<CanvasWidget> {
        public BoxFrame frame;
        public BufferedImage image;
        public ArrayList<BStroke> strokes;

        public CanvasState(CanvasWidget self) {
            super(self);
        }
    }
}
