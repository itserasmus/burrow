package com.burrow.widget.single_child;

import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;
import com.burrow.widget.Widget;

public abstract class SingleChildWidget extends Widget {
    protected Widget child;
    
    @Override
    public void layout(LayoutData data) {
        child.layout(data);
    }
    @Override
    public void paint(PaintData data) {
        child.paint(data.set(child.getBoxFrame()));
    }
    @Override
    public void init(TreeData data) {
        child.init(data.push(this));
    }
    @Override
    public void onRelocate(TreeData data) {
        
        child.onRelocate(data.push(this));
    }
    @Override
    public void dispose() {
        getState().dispose();
        child.dispose();
    }

    protected SingleChildWidget(Widget child) {
        super();
        this.child = child;
    }
}
