package com.burrow.widget.single_child.canvas.stroke;

import java.awt.Color;

import com.burrow.auxiliary.BufferedImageRasterizeData;
import com.burrow.auxiliary.BurrowAux;
import com.burrow.auxiliary.Graphics2DRasterizeData;

public final class VerticalGradientStroke implements BStroke {
    public final int x, y, width, height, topColor, bottomColor;
    public int color;
    public final double[] hitbox;

    @Override
    public int pixelColor(double x, double y, BRenderFilter filter) {
        if(this.x <= x && this.y <= y && this.x + this.width >= x && this.y + this.height >= y) {
            return BurrowAux.lerpColor(topColor, bottomColor, (y-this.y)/height);
        }
        return 0;
    }

    @Override
    public boolean drawStroke(double x, double y, double[] hitbox) {
        return BurrowAux.pointInHitbox(x, y, this.hitbox) &&
            BurrowAux.pointInHitbox(x, y, hitbox);
    }

    public VerticalGradientStroke(
        int x, int y,
        int width, int height,
        int topColor, int bottomColor
    ) {
        this.x = x;
        this.y = y;
        this.width = width > Integer.MAX_VALUE - x ? Integer.MAX_VALUE - x: width;
        this.height = height > Integer.MAX_VALUE - y ? Integer.MAX_VALUE - y : height;
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        this.hitbox = new double[]{x, y, width, height};
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y + ", width:" + width + ", height:" + height;
    }
    public String toShortString() {
        return x + ", " + y + ", " + width + ", " + height;
    }

    @Override
    public void rasterizeToBufferedImage(BufferedImageRasterizeData data) {
        final int maxY = Math.min(
            (int)(data.filter.getCropBounds()[1]+data.filter.getCropBounds()[3]),
            Math.min(height + y, data.image.getHeight())
        );
        final int maxX = Math.min(
            (int)(data.filter.getCropBounds()[0]+data.filter.getCropBounds()[2]),
            Math.min(width + x, data.image.getWidth())
        );
        final int startX = Math.max((int)data.filter.getCropBounds()[0], Math.max(0, x));

        for(int i = Math.max((int)data.filter.getCropBounds()[1], Math.max(0, y)); i < maxY; i++) {
            color = BurrowAux.lerpColor(topColor, bottomColor, (i-y)*1.0/height);
            for(int j = startX; j < maxX; j++) {
                data.image.setRGB(j, i, color);
            }
        }
    }

    @Override
    public void rasterizeToGraphics2D(Graphics2DRasterizeData data) {
        final int maxWidth = Math.min(
            (int)data.filter.getCropBounds()[2],
            width
        );
        for(int i = Math.max((int)data.filter.getCropBounds()[1], Math.max(0, y)); i < Math.min((int)(data.filter.getCropBounds()[1]+data.filter.getCropBounds()[3]), height + y); i++) {
            data.g.setColor(new Color(BurrowAux.lerpColor(topColor, bottomColor, (i-y)*1.0/height)));
            data.g.fillRect(
                Math.max((int)data.filter.getCropBounds()[0], Math.max(0, x)),
                i,
                maxWidth,
                1
            );
        }
    }
}
