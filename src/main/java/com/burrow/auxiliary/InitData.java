package com.burrow.auxiliary;

import java.util.ArrayList;

import com.burrow.widget.Widget;
import com.burrow.widget.root.RootWidget;
import com.burrow.widget.single_child.canvas.CanvasWidget;

public class InitData {
    public ScreenData screen;
    public RootWidget root;
    public CanvasWidget canvas;
    public ArrayList<Widget> ancestors;

    public InitData setRoot(RootWidget root) {
        this.root = root;
        return this;
    }

    public InitData setCanvas(CanvasWidget canvas) {
        this.canvas = canvas;
        return this;
    }

    public InitData pop() {
        if(ancestors.size() == 0) {
            return this;
        }
        ancestors.remove(ancestors.size()-1);
        return this;
    }

    public InitData push(Widget w) {
        ancestors.add(w);
        return this;
    }

    public InitData(RootWidget root, ScreenData screen) {
        this.screen = screen;
        this.root = root;
        this.canvas = root;
        this.ancestors = new ArrayList<Widget>();
        this.ancestors.add(root);
    }
}
