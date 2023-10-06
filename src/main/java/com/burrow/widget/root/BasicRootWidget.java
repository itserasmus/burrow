package com.burrow.widget.root;

import java.awt.Dimension;
import java.util.ArrayList;

import com.burrow.auxiliary.BoxFrame;
import com.burrow.auxiliary.InitData;
import com.burrow.auxiliary.LayoutData;
import com.burrow.auxiliary.PaintData;
import com.burrow.auxiliary.ScreenData;
import com.burrow.auxiliary.TreeData;
import com.burrow.base.BPanel;
import com.burrow.widget.State;
import com.burrow.widget.Widget;
import com.burrow.widget.single_child.canvas.stroke.BStroke;

@SuppressWarnings("rawtypes")
public final class BasicRootWidget extends RootWidget {
    protected BoxFrame frame;

    @Override
    public void layout(LayoutData data) {
        frame.setSize(data.maxWidth, data.maxHeight);
        child.layout(data);

        if(
            (state.cArr.length == 0 && data.windowWidth != 0) ||
            state.cArr.length != data.windowWidth ||
            state.cArr[0].length != data.windowHeight
        ) {
            state.cArr = new int[data.windowWidth][data.windowHeight];
            resetCache();
        }
    }

    @Override
    public void layout(int width, int height) {
        frame.setSize(width, height);
        child.layout(new LayoutData(0, 0, width, height, width, height));

        if(
            (state.cArr.length == 0 && width != 0) ||
            state.cArr.length != width ||
            state.cArr[0].length != height
        ) {
            state.cArr = new int[width][height];
            resetCache();
        }
    }

    @Override
    public void paint() {
        if(needsRepaint) {
            needsRepaint = false;
            state.strokes.clear();
            child.paint(new PaintData(0, 0, 0, 0, this).set(child.getBoxFrame()));
        }
    }

    @Override
    public BoxFrame getBoxFrame() {
        return frame;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void createState() {
        this.state = new BasicRootWidgetState(this);
    }

    @Override
    public void onResize(int width, int height) {
        resetCache();
    }

    @Override
    public void onRelocate(TreeData data) {}

    @Override
    public void init(BPanel panel, ScreenData screen) {
        this.panel = panel;

        panel.setSize((int)frame.width, (int)frame.height);
        panel.frame.setPreferredSize(new Dimension((int)frame.width, (int)frame.height));
        panel.frame.pack();

        state.strokes = new ArrayList<BStroke>();
        frame = new BoxFrame();

        child.init(new InitData(this, screen));
        
        state.cArr = new int[(int)frame.width][(int)frame.height];
    }
    
    public BasicRootWidget(Widget child, int width, int height) {
        super(child);
        frame = new BoxFrame().setSize(width, height);
    }

    public class BasicRootWidgetState extends CanvasState {
        public BasicRootWidgetState(BasicRootWidget widget) {
            super(widget);
        }
    }
}
