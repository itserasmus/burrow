package com.burrow.widget.multi_child.layout;

import java.util.ArrayList;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.LayoutData;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.multi_child.MultiChildWidget;

@SuppressWarnings("rawtypes")
public class ColumnWidget extends MultiChildWidget {
    protected ColumnWidgetState state;

    @Override
    public void layout(LayoutData data) {
        double minWidth = data.minWidth;
        double maxWidth = data.maxWidth;
        for(Widget child : children) {
            child.layout(data.set(
                minWidth, 0,
                maxWidth, Integer.MAX_VALUE
            ));
        }
        double currY = 0;
        double maxW = 0;
        for(Widget child : children) {
            child.getBoxFrame().setPos(0, currY);
            currY += child.getBoxFrame().height;
            maxW = Math.max(maxW, child.getBoxFrame().width);
        }
        state.frame.setSize(maxW, currY);
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
        state = new ColumnWidgetState(this);
    }

    public ColumnWidget(Widget[] children) {
        super(children);
    }
    public ColumnWidget(ArrayList<Widget> children) {
        super(children);
    }

    public class ColumnWidgetState extends State<ColumnWidget> {
        public BoxFrame frame;

        public ColumnWidgetState(ColumnWidget widget) {
            super(widget);
        }
    }
}
