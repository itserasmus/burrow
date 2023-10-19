package com.burrow.widget.single_child.canvas;

import com.burrow.auxiliary.BufferedImageRasterizeData;
import com.burrow.widget.single_child.canvas.stroke.BStroke;

public interface BCanvas extends BStroke {
    public void resetCache();
    public void requestRepaint();
    public void addStroke(BStroke stroke);
    public void createCache(BufferedImageRasterizeData data);
    public void disposeCache();
}
