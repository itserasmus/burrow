package com.burrow;

import com.burrow.base.BFrame;
import com.burrow.base.BPanel;
import com.burrow.widget.no_child.RectangleWidget;
import com.burrow.widget.root.BasicRootWidget;
import com.burrow.widget.root.RootWidget;
import com.burrow.widget.single_child.layout.FixedSizeWidget;

public class Main {
    public static void main(String[] args) {
        runBurrow(
            new BasicRootWidget(
                new FixedSizeWidget(
                    new RectangleWidget(0xFF000000),
                    100,
                    200
                ),
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
