package com.burrow.event_handling.listener;

import com.burrow.event_handling.event.BMouseEvent;

public interface BMouseListener {
    public boolean onMouseDown(BMouseEvent e);
    public boolean onMouseUp(BMouseEvent e);
}
