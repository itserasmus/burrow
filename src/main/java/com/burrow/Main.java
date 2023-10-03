package com.burrow;

import com.burrow.base.BFrame;
import com.burrow.base.BPanel;
import com.burrow.widget.no_child.RectangleWidget;
import com.burrow.widget.root.BasicRootWidget;
import com.burrow.widget.root.RootWidget;

public class Main {
    public static void main(String[] args) {
        runBurrow(
            new BasicRootWidget(
                new RectangleWidget(20, 20, 100, 100, 0xFF000000),
                300,
                200
            )
        );
    }

    public static BFrame runBurrow(RootWidget root) {
        BFrame frame = new BFrame();
        BPanel panel = new BPanel();
        frame.initialize(panel);
        panel.initialize(frame, root);

        return frame;
    }
}
