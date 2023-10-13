package com.burrow.widget;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.TreeData;

@SuppressWarnings("rawtypes")
public abstract class Widget {  
    public abstract BoxFrame getBoxFrame();
    public abstract State getState();
    public abstract void createBoxFrame();
    public abstract void createState();

    public abstract void layout(LayoutData data);
    public abstract void paint(PaintData data);

    public abstract void init(InitData data);
    public abstract void onRelocate(TreeData data);

    public abstract void dispose();

    protected Widget() {
        createState();
        createBoxFrame();
    }
}