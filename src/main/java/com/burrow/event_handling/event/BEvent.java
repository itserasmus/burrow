package com.burrow.event_handling.event;

import com.burrow.widget.root.RootWidget;

public abstract class BEvent {
    public final RootWidget root;

    protected BEvent(RootWidget root) {
        this.root = root;
    }
}
