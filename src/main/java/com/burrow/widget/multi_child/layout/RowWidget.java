package com.burrow.widget.multi_child.layout;

import java.util.ArrayList;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.LayoutData;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.multi_child.MultiChildWidget;

@SuppressWarnings("rawtypes")
public class RowWidget extends MultiChildWidget {
    protected RowWidgetState state;

    @Override
    public void layout(LayoutData data) {
        double minHeight = data.minHeight;
        double maxHeight = data.maxHeight;
        for(Widget child : children) {
            child.layout(data.set(
                0, minHeight,
                Integer.MAX_VALUE, maxHeight
            ));
        }
        double currX = 0;
        double maxH = 0;
        for(Widget child : children) {
            child.getBoxFrame().setPos(currX, 0);
            currX += child.getBoxFrame().width;
            maxH = Math.max(maxH, child.getBoxFrame().height);
        }
        state.frame.setSize(currX, maxH);
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
        state = new RowWidgetState(this);
    }

    public RowWidget(Widget[] children) {
        super(children);
    }
    public RowWidget(ArrayList<Widget> children) {
        super(children);
    }

    public class RowWidgetState extends State<RowWidget> {
        public BoxFrame frame;

        public RowWidgetState(RowWidget widget) {
            super(widget);
        }
    }
}
