package com.burrow;

import com.burrow.base.BFrame;
import com.burrow.base.BPanel;
import com.burrow.widget.Widget;
import com.burrow.widget.multi_child.layout.ColumnWidget;
import com.burrow.widget.multi_child.layout.RowWidget;
import com.burrow.widget.no_child.RectangleWidget;
import com.burrow.widget.no_child.VerticalGradientWidget;
import com.burrow.widget.root.BasicRootWidget;
import com.burrow.widget.root.RootWidget;
import com.burrow.widget.single_child.ScrollableWidget;
import com.burrow.widget.single_child.layout.FixedSizeWidget;

public class Main {
    public static void main(String[] args) {
        runBurrow(
            new BasicRootWidget(
                new ColumnWidget(
                    new Widget[]{
                        new RowWidget(
                            new Widget[]{
                                new ColumnWidget(
                                    new Widget[]{
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFFFF0000),
                                            200, 100
                                        ),
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFF00FF00),
                                            250, 150
                                        ),
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFF0000FF),
                                            200, 200
                                        )
                                    }
                                ),
                                new ColumnWidget(
                                    new Widget[]{
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFF00FFFF),
                                            300, 200
                                        ),
                                        new FixedSizeWidget(
                                            new ScrollableWidget(
                                                new FixedSizeWidget(
                                                    new VerticalGradientWidget(0xFFFF0000, 0xFF00FF00),
                                                    200, 600
                                                )
                                            ),
                                            200, 200
                                        ),
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFFFFFF00),
                                            250, 100
                                        )
                                    }
                                ),
                                new ColumnWidget(
                                    new Widget[]{
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFFFF00FF),
                                            200, 400
                                        ),
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFF00FF00),
                                            250, 150
                                        ),
                                        new FixedSizeWidget(
                                            new RectangleWidget(0xFF0000FF),
                                            200, 200
                                        )
                                    }
                                )
                            }
                        ),
                        new RectangleWidget(0xFF888888)
                    }
                )
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
