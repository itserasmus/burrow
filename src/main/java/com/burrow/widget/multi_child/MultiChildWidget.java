package com.burrow.widget.multi_child;

import java.util.ArrayList;
import java.util.Arrays;

import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.auxiliary.BurrowAux;
import com.burrow.event_handling.event.BMouseEvent;
import com.burrow.event_handling.event.BScrollEvent;
import com.burrow.event_handling.listener.BMouseListener;
import com.burrow.event_handling.listener.BScrollListener;
import com.burrow.widget.Widget;

public abstract class MultiChildWidget extends Widget implements BMouseListener, BScrollListener {
    protected ArrayList<Widget> children;

    @Override
    public void paint(PaintData data) {
        double x = data.x, y = data.y;
        for(Widget child : children) {
            child.paint(data.setAccum(x, y, child.getBoxFrame()));
        }
    }
    @Override
    public void init(InitData data) {
        data.push(this);
        for(Widget child : children) {
            child.init(data);
        }
        data.pop();
    }
    @Override
    public void onRelocate(TreeData data) {
        data.push(this);
        for(Widget child : children) {
            child.onRelocate(data);
        }
        data.pop();
    }
    @Override
    public void dispose() {
        getState().dispose();
        for(Widget child : children) {
            child.dispose();
        }
    }

    @Override
    public boolean onMouseUp(BMouseEvent e) {
        for(Widget child : children) {
            if(
                child instanceof BMouseListener &&
                BurrowAux.pointInHitbox(e.x, e.y, child.getBoxFrame()) &&
                ((BMouseListener) child).onMouseUp(e.subtract(child.getBoxFrame()))
            ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onMouseDown(BMouseEvent e) {
        for(Widget child : children) {
            if(
                child instanceof BMouseListener &&
                BurrowAux.pointInHitbox(e.x, e.y, child.getBoxFrame()) &&
                ((BMouseListener) child).onMouseDown(e.subtract(child.getBoxFrame()))
            ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onScroll(BScrollEvent e) {
        for(Widget child : children) {
            if(
                child instanceof BScrollListener &&
                BurrowAux.pointInHitbox(e.x, e.y, child.getBoxFrame()) &&
                ((BScrollListener) child).onScroll(e.subtract(child.getBoxFrame()))
            ) {
                return true;
            }
        }
        return false;
    }

    protected MultiChildWidget(Widget[] children) {
        super();
        this.children = new ArrayList<Widget>(Arrays.asList(children));
    }

    protected MultiChildWidget(ArrayList<Widget> children) {
        super();
        this.children = children;
    }
}
