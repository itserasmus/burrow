package com.burrow.widget.root;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

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
    @Override
    public void layout(LayoutData data) {
        state.frame.setSize(data.maxWidth, data.maxHeight);
        child.layout(data);

        if(state.cache != null) {
            if(
                (state.cache.getWidth() == 0 && state.frame.width != 0) ||
                state.cache.getWidth() != state.frame.width ||
                state.cache.getHeight() != state.frame.height
            ) {
                resetCache();
                caches.clear();
            }
        }
    }

    @Override
    public void layout(int width, int height) {
        state.frame.setSize(width, height);
        child.layout(new LayoutData(
            0, 0,
            width, height,
            width, height
        ));

        if(state.cache != null) {
            if(
                (state.cache.getWidth() == 0 && width != 0) ||
                state.cache.getWidth() != width ||
                state.cache.getHeight() != height
            ) {
                resetCache();
                caches.clear();
            }
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
        return state.frame;
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

        state.frame.setSize(Math.min(state.frame.width, screen.width), Math.min(state.frame.height, screen.height));

        panel.setPreferredSize(new Dimension((int)state.frame.width, (int)state.frame.height));
        panel.frame.pack();
        panel.frame.setLocationRelativeTo(null);
        if(state.frame.width >= screen.width && state.frame.height >= screen.height) {
            panel.frame.setSize((int)screen.width/2, (int)screen.height/2);
            panel.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        state.strokes = new ArrayList<BStroke>();
        root = this;

        child.init(new InitData(this, screen));
    }
    
    public BasicRootWidget(Widget child) {
        super(child);
        state.frame = new BoxFrame().setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    public BasicRootWidget(Widget child, int width, int height) {
        super(child);
        state.frame = new BoxFrame().setSize(width, height);
    }

    public class BasicRootWidgetState extends SingleChildCanvasWidgetState {
        public BasicRootWidgetState(BasicRootWidget widget) {
            super(widget);
        }
    }
}
