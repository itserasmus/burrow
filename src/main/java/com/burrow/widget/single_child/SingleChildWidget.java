package com.burrow.widget.single_child;

import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.auxiliary.BurrowAux;
import com.burrow.event_handling.event.BMouseEvent;
import com.burrow.event_handling.event.BScrollEvent;
import com.burrow.event_handling.listener.BMouseListener;
import com.burrow.event_handling.listener.BScrollListener;
import com.burrow.widget.Widget;

public abstract class SingleChildWidget extends Widget implements BMouseListener, BScrollListener {
    protected Widget child;
    
    @Override
    public void layout(LayoutData data) {
        getBoxFrame().setSize(data.maxWidth, data.maxHeight);
        child.layout(data);
    }
    @Override
    public void paint(PaintData data) {
        child.paint(data.setAccum(data.x, data.y, child.getBoxFrame()));
    }
    @Override
    public void init(InitData data) {
        child.init(data.push(this));
        data.pop();
    }
    @Override
    public void onRelocate(TreeData data) {
        child.onRelocate(data.push(this));
        data.pop();
    }
    @Override
    public void dispose() {
        getState().dispose();
        child.dispose();
    }

    @Override
    public boolean onMouseUp(BMouseEvent e) {
        return child instanceof BMouseListener &&
                BurrowAux.pointInHitbox(e.x, e.y, child.getBoxFrame()) &&
                ((BMouseListener) child).onMouseUp(e.subtract(child.getBoxFrame()));
    }

    @Override
    public boolean onMouseDown(BMouseEvent e) {
        return child instanceof BMouseListener &&
                BurrowAux.pointInHitbox(e.x, e.y, child.getBoxFrame()) &&
                ((BMouseListener) child).onMouseDown(e.subtract(child.getBoxFrame()));
    }

    @Override
    public boolean onScroll(BScrollEvent e) {
        return child instanceof BScrollListener &&
                BurrowAux.pointInHitbox(e.x, e.y, child.getBoxFrame()) &&
                ((BScrollListener) child).onScroll(e.subtract(child.getBoxFrame()));
    }

    protected SingleChildWidget(Widget child) {
        super();
        this.child = child;
    }
}
