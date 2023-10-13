package com.burrow.widget.single_child;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.BurrowAux;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.event_handling.event.BScrollEvent;
import com.burrow.event_handling.listener.BScrollListener;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.canvas.CanvasWidget;
import com.burrow.widget.single_child.canvas.stroke.CropStroke;

@SuppressWarnings("rawtypes")
public final class ScrollableWidget extends SingleChildWidget {
    protected ScrollableWidgetState state;
    protected CanvasWidget parentCanvas;
    protected BoxFrame frame;

    @Override
    public void layout(LayoutData data) {
        frame.setSize(data.maxWidth, data.maxHeight);
        child.layout(data.setMaxHeight(Double.MAX_VALUE));
        child.getBoxFrame().setPos(0, state.scroll);
    }
    @Override
    public void paint(PaintData data) {
        double[] cropBounds = new double[]{data.x, data.y, data.width, data.height};
        data.canvas.addStroke(CropStroke.firstComplementary(cropBounds));
        child.paint(data.setAccum(data.x, data.y, child.getBoxFrame()));
        data.canvas.addStroke(CropStroke.secondComplementary(cropBounds));
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
    public void createBoxFrame() {
        frame = new BoxFrame();
    }

    @Override
    public void createState() {
        state = new ScrollableWidgetState(this);
    }

    @Override
    public boolean onScroll(BScrollEvent e) {
        if(
            child instanceof BScrollListener &&
            BurrowAux.pointInHitbox(e.x, e.y, child.getBoxFrame()) &&
            ((BScrollListener) child).onScroll(e.subtract(child.getBoxFrame()))
        ) {
            return true; 
        }
        // if(
        //     (state.scroll > 0 && e.scroll > 0) ||
        //     (state.scroll < child.getBoxFrame().height - frame.height && e.scroll < 0)
        // ) {
            System.out.println(child.getBoxFrame().height);
            state.scroll = Math.max(0, Math.min(state.scroll - e.scroll, child.getBoxFrame().height - frame.height));
            parentCanvas.requestRepaint();
            return true;
        // }
        // return false;
    }

    @Override
    public void init(InitData data) {
        parentCanvas = data.canvas;
        child.init(data.push(this));
        data.pop();
    }
    @Override
    public void onRelocate(TreeData data) {
        parentCanvas = data.canvas;
        child.onRelocate(data.push(this));
        data.pop();
    }

    public ScrollableWidget(Widget child) {
        super(child);
    }

    public class ScrollableWidgetState extends State<ScrollableWidget> {
        protected double scroll;

        public ScrollableWidgetState(ScrollableWidget widget) {
            super(widget);
        }
    }
}
