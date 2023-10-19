package com.burrow.widget.no_child;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.auxiliary.BurrowAux;
import com.burrow.event_handling.event.BMouseEvent;
import com.burrow.event_handling.listener.BMouseListener;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.canvas.BCanvas;
import com.burrow.widget.single_child.canvas.stroke.RectangleStroke;

@SuppressWarnings("rawtypes")
public final class RectangleWidget extends Widget implements BMouseListener {
    protected BCanvas canvas;
    protected RectangleWidgetState state;
    
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
        state = new RectangleWidgetState(this);
    }

    @Override
    public void layout(LayoutData data) {
        if(BurrowAux.wasResizedMax(data, state.frame)) {
            canvas.requestRepaint();
        }
        state.frame.setSize(data.maxWidth, data.maxHeight);
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
    }

    @Override
    public void onRelocate(TreeData data) {
        canvas = data.canvas;
    }

    @Override
    public void dispose() {}


    @Override
    public boolean onMouseDown(BMouseEvent e) {
        System.out.println("mouse down on " + state.color);
        return true;
    }

    @Override
    public boolean onMouseUp(BMouseEvent e) {
        System.out.println("mouse down on " + state.color);
        return true;
    }


    public RectangleWidget(int color) {
        state.color = color;
    }
    

    public class RectangleWidgetState extends State<RectangleWidget> {
        public BoxFrame frame;
        public int color;

        public RectangleWidgetState(RectangleWidget widget) {
            super(widget);
        }
    }
}
