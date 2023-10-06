package com.burrow.widget.single_child.canvas;

import java.util.ArrayList;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.SingleChildWidget;
import com.burrow.widget.single_child.canvas.stroke.BStroke;

@SuppressWarnings("rawtypes")
public class CanvasWidget extends SingleChildWidget implements BStroke {
    protected double dpr;
    protected BoxFrame frame;
    protected CanvasState state;

    protected CanvasWidget parentCanvas;

    protected boolean needsRepaint = true;

    public static final int repaintsUntilCache = 3;
    protected int remainingRepaintsUntilCache = repaintsUntilCache;

    @Override
    public void layout(LayoutData data) {
        frame.setSize(data.maxWidth, data.maxHeight);
        child.layout(data);

        if(
            (state.cArr.length == 0 && data.windowWidth != 0) ||
            state.cArr.length != data.windowWidth ||
            state.cArr[0].length != data.windowHeight
        ) {
            state.cArr = new int[data.windowWidth][data.windowHeight];
            resetCache();
        }
    }

    @Override
    public void paint(PaintData data) {
        if(needsRepaint) {
            needsRepaint = false;
            state.strokes.clear();
            child.paint(data.set(child.getBoxFrame()));
        }
    }

    @Override
    public BoxFrame getBoxFrame() {
        return frame;
    }
    @Override
    public State getState() {
        return state;
    }
    @Override
    public void createState() {
        this.state = new CanvasState(this);
    }
    @Override
    public void init(InitData data) {
        state.strokes = new ArrayList<BStroke>();
        frame = new BoxFrame();
        parentCanvas = data.canvas;

        dpr = data.screen.dpr; 

        child.init(data.push(this).setCanvas(this));
        state.cArr = new int[data.root.panel.getWidth()][data.root.panel.getHeight()];
    }

    @Override
    public void onRelocate(TreeData data) {
        parentCanvas = data.canvas;

        child.onRelocate(data.push(this).setCanvas(this));
    }

    @Override
    public void rasterizeToCArr(int[][] cArr) {
        if(remainingRepaintsUntilCache > 0) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToCArr(cArr);
            }
            remainingRepaintsUntilCache--;
            return;
        }
        if(remainingRepaintsUntilCache == 0) {
            for(int i = state.strokes.size() - 1; i >= 0; i--) {
                state.strokes.get(i).rasterizeToCArr(state.cArr);
            }
            remainingRepaintsUntilCache--;
        }
        final int width = Math.min(state.cArr.length, cArr.length);
        if(width == 0) {return;}
        final int height = Math.min(state.cArr[0].length, cArr[0].length);
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                cArr[i][j] = state.cArr[i][j];
            }
        }
    }
    @Override
    public int pixelColor(double x, double y) {
        return state.cArr[(int)(x*dpr)][(int)(y*dpr)];
    }
    @Override
    public double[] getHitbox() {
        return null;
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
        public int[][] cArr;
        public ArrayList<BStroke> strokes;

        public CanvasState(CanvasWidget self) {
            super(self);
        }
    }
}
