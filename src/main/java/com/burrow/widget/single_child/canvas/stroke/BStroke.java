package com.burrow.widget.single_child.canvas.stroke;

import com.burrow.auxiliary.BufferedImageRasterizeData;
import com.burrow.auxiliary.Graphics2DRasterizeData;

public interface BStroke {
    public int pixelColor(double x, double y, BRenderFilter filter);
    public void rasterizeToBufferedImage(BufferedImageRasterizeData data);
    public void rasterizeToGraphics2D(Graphics2DRasterizeData data);
    public boolean drawStroke(double x, double y, double[] hitbox);
}
