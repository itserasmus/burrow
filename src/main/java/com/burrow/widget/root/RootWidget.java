package com.burrow.widget.root;

import com.burrow.auxiliary.ScreenData;
import com.burrow.base.BPanel;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.canvas.CanvasWidget;

public abstract class RootWidget extends CanvasWidget {
    public BPanel panel;

    public abstract void init(BPanel panel, ScreenData screen);
    public abstract void layout(int width, int height);
    public abstract void paint();

    public abstract void onResize(int width, int height);
        
    protected RootWidget(Widget child) {
        super(child);
    }
}
