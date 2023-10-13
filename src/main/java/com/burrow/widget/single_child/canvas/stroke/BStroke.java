package com.burrow.widget.single_child.canvas.stroke;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public interface BStroke {
    public int pixelColor(double x, double y, BRenderFilter filter);
    public void rasterizeToBufferedImage(BufferedImage image, BRenderFilter filter);
    public void rasterizeToGraphics2D(Graphics2D g, BRenderFilter filter);
    public boolean drawStroke(double x, double y, double[] hitbox);
}
