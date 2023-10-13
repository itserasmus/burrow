package com.burrow.widget.single_child.layout;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.LayoutData;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.SingleChildWidget;

@SuppressWarnings("rawtypes")
public class FixedSizeWidget extends SingleChildWidget {
    protected FixedSizeWidgetState state;

    @Override
    public void layout(LayoutData data) {
        state.frame.setSize(
            Math.min(state.width, data.maxWidth),
            Math.min(state.height, data.maxHeight)
        );
        child.layout(data.set(
            state.frame.width,
            state.frame.height,
            state.frame.width,
            state.frame.height
        ));
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
        state = new FixedSizeWidgetState(this);
    }
    

    public FixedSizeWidget(Widget child, double width, double height) {
        super(child);
        state.width = width;
        state.height = height;
    }


    public class FixedSizeWidgetState extends State<FixedSizeWidget> {
        public BoxFrame frame;
        public double width, height;

        public FixedSizeWidgetState(FixedSizeWidget widget) {
            super(widget);
        }

    }
}
