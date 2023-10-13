package com.burrow.event_handling.event;

import java.awt.event.MouseWheelEvent;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.widget.root.RootWidget;

public class BScrollEvent extends BEvent {
    public double x, y;
    public final int scroll;
    public final boolean isShiftDown;
    public final MouseWheelEvent event;

    public final BScrollEvent set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public final BScrollEvent add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public final BScrollEvent subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }


    public final BScrollEvent add(BoxFrame frame) {
        this.x += frame.relX;
        this.y += frame.relY;
        return this;
    }

    public final BScrollEvent subtract(BoxFrame frame) {
        this.x -= frame.relX;
        this.y -= frame.relY;
        return this;
    }
    
    public BScrollEvent(
        double x, double y,
        int scroll, boolean isShiftDown,
        RootWidget root, MouseWheelEvent event
    ) {
        super(root);
        this.x = x;
        this.y = y;
        this.scroll = scroll;
        this.isShiftDown = isShiftDown;
        this.event = event;
    }
}
