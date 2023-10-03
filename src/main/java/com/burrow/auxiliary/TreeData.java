package com.burrow.auxiliary;

import java.util.ArrayList;

import com.burrow.widget.Widget;
import com.burrow.widget.root.RootWidget;
import com.burrow.widget.single_child.canvas.CanvasWidget;

public class TreeData {
    public RootWidget root;
    public CanvasWidget canvas;
    public ArrayList<Widget> ancestors;

    public TreeData setRoot(RootWidget root) {
        this.root = root;
        return this;
    }

    public TreeData setCanvas(CanvasWidget canvas) {
        this.canvas = canvas;
        return this;
    }

    public TreeData pop() {
        if(ancestors.size() == 0) {
            return this;
        }
        ancestors.remove(ancestors.size()-1);
        return this;
    }

    public TreeData push(Widget w) {
        ancestors.add(w);
        return this;
    }

    public TreeData(RootWidget root) {
        this.root = root;
        this.canvas = root;
        this.ancestors = new ArrayList<Widget>();
        this.ancestors.add(root);
    }
}
