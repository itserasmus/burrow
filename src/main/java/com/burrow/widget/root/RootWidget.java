package com.burrow.widget.root;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.burrow.auxiliary.ScreenData;
import com.burrow.base.BPanel;
import com.burrow.event_handling.event.BMouseEvent;
import com.burrow.event_handling.event.BScrollEvent;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.canvas.SingleChildCanvasWidget;

public abstract class RootWidget extends SingleChildCanvasWidget implements MouseListener, MouseWheelListener {
    public BPanel panel;
    public LinkedList<BufferedImage> caches = new LinkedList<BufferedImage>();

    public abstract void init(BPanel panel, ScreenData screen);
    public abstract void layout(int width, int height);
    public abstract void paint();

    public abstract void onResize(int width, int height);

    @Override
    public void mousePressed(MouseEvent e) {
        onMouseDown(new BMouseEvent(
            e.getX(), e.getY(),
            this, e
        ));
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        onMouseUp(new BMouseEvent(
            e.getX(), e.getY(),
            this, e
        ));
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        onScroll(
            new BScrollEvent(
                e.getX(), e.getY(),
                e.getScrollAmount() * e.getWheelRotation(),
                e.isShiftDown(),
                this, e
            ));
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
        
    protected RootWidget(Widget child) {
        super(child);
    }
}
