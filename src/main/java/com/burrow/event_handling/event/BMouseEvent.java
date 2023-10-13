package com.burrow.event_handling.event;

import java.awt.event.MouseEvent;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.widget.root.RootWidget;

public class BMouseEvent extends BEvent {
    public double x, y;
    public final MouseEvent event;

    public final BMouseEvent set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public final BMouseEvent add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public final BMouseEvent subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }


    public final BMouseEvent add(BoxFrame frame) {
        this.x += frame.relX;
        this.y += frame.relY;
        return this;
    }

    public final BMouseEvent subtract(BoxFrame frame) {
        this.x -= frame.relX;
        this.y -= frame.relY;
        return this;
    }
    public BMouseEvent(double x, double y, RootWidget root, MouseEvent event) {
        super(root);
        this.x = x;
        this.y = y;
        this.event = event;
    }
}
