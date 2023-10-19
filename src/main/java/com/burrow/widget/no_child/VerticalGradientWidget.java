package com.burrow.widget.no_child;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.auxiliary.BurrowAux;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.canvas.BCanvas;
import com.burrow.widget.single_child.canvas.stroke.VerticalGradientStroke;

@SuppressWarnings("rawtypes")
public final class VerticalGradientWidget extends Widget {
    protected BCanvas canvas;
    protected VerticalGradientWidgetState state;
    
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
        state = new VerticalGradientWidgetState(this);
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
        data.canvas.addStroke(new VerticalGradientStroke(
            (int)data.x,
            (int)data.y,
            (int)data.width,
            (int)data.height,
            state.topColor,
            state.bottomColor
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


    public VerticalGradientWidget(int topColor, int bottomColor) {
        state.topColor = topColor;
        state.bottomColor = bottomColor;
    }
    

    public class VerticalGradientWidgetState extends State<VerticalGradientWidget> {
        public BoxFrame frame;
        public int topColor;
        public int bottomColor;

        public VerticalGradientWidgetState(VerticalGradientWidget widget) {
            super(widget);
        }
    }
}
