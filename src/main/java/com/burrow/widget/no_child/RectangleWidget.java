package com.burrow.widget.no_child;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.canvas.CanvasWidget;
import com.burrow.widget.single_child.canvas.stroke.RectangleStroke;

@SuppressWarnings("rawtypes")
public final class RectangleWidget extends Widget {
    protected CanvasWidget canvas;
    protected BoxFrame frame;
    protected RectangleWidgetState state;
    
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
        state = new RectangleWidgetState(this);
    }

    @Override
    public void layout(LayoutData data) {
        if(aux.wasResizedMax(data, frame)) {
            canvas.requestRepaint();
        }
        frame.setSize(data.maxWidth, data.maxHeight);
    }

    @Override
    public void paint(PaintData data) {
        data.canvas.addStroke(new RectangleStroke(
            (int)data.x,
            (int)data.y,
            (int)data.width,
            (int)data.height,
            state.color
        ));
    }

    @Override
    public void init(InitData data) {
        canvas = data.canvas;
        frame = new BoxFrame();
    }

    @Override
    public void onRelocate(TreeData data) {
        canvas = data.canvas;
    }

    @Override
    public void dispose() {}


    public RectangleWidget(int color) {
        state.color = color;
    }
    

    public class RectangleWidgetState extends State<RectangleWidget> {
        public int color;

        public RectangleWidgetState(RectangleWidget widget) {
            super(widget);
        }
    }
}
