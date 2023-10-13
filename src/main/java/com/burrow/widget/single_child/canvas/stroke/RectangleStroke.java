package com.burrow.widget.single_child.canvas.stroke;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.burrow.auxiliary.BurrowAux;

public final class RectangleStroke implements BStroke {
    public final int x, y, width, height, color;
    public final double[] hitbox;

    @Override
    public int pixelColor(double x, double y, BRenderFilter filter) {
        if(this.x <= x && this.y <= y && this.x + this.width >= x && this.y + this.height >= y) {
            return this.color;
        }
        return 0;
    }

    // @Override
    // public void rasterizeToCArr(int[][] cArr, BRenderFilter filter) {
    //     if(cArr.length == 0) {return;}
    //     final int maxY = Math.min(
    //         (int)(filter.getCropBounds()[1]+filter.getCropBounds()[3]),
    //         Math.min(height + y, cArr[0].length)
    //     );
    //     final int maxX = Math.min(
    //         (int)(filter.getCropBounds()[0]+filter.getCropBounds()[2]),
    //         Math.min(width + x, cArr.length)
    //     );
    //     final int startX = Math.max((int)filter.getCropBounds()[0], Math.max(0, x));

    //     for(int i = Math.max((int)filter.getCropBounds()[1], Math.max(0, y)); i < maxY; i++) {
    //         for(int j = startX; j < maxX; j++) {
    //             cArr[j][i] = color;
    //         }
    //     }
    // }

    @Override
    public boolean drawStroke(double x, double y, double[] hitbox) {
        return BurrowAux.pointInHitbox(x, y, this.hitbox) && BurrowAux.pointInHitbox(x, y, hitbox);
    }

    public RectangleStroke(int x, int y, int width, int height, int color) {
        this.x = x;
        this.y = y;
        this.width = width > Integer.MAX_VALUE - x ? Integer.MAX_VALUE - x: width;
        this.height = height > Integer.MAX_VALUE - y ? Integer.MAX_VALUE - y : height;
        this.color = color;
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
    public void rasterizeToBufferedImage(BufferedImage image, BRenderFilter filter) {
        final int maxY = Math.min(
            (int)(filter.getCropBounds()[1]+filter.getCropBounds()[3]),
            Math.min(height + y, image.getHeight())
        );
        final int maxX = Math.min(
            (int)(filter.getCropBounds()[0]+filter.getCropBounds()[2]),
            Math.min(width + x, image.getWidth())
        );
        final int startX = Math.max((int)filter.getCropBounds()[0], Math.max(0, x));

        for(int i = Math.max((int)filter.getCropBounds()[1], Math.max(0, y)); i < maxY; i++) {
            for(int j = startX; j < maxX; j++) {
                image.setRGB(j, i, color);
            }
        }
    }

    @Override
    public void rasterizeToGraphics2D(Graphics2D g, BRenderFilter filter) {
        g.setColor(new Color(color));
        g.fillRect(
            Math.max((int)filter.getCropBounds()[0], Math.max(0, x)),
            Math.max((int)filter.getCropBounds()[1], Math.max(0, y)),
            Math.min(
                (int)filter.getCropBounds()[2],
                width
            ),
            Math.min(
                (int)filter.getCropBounds()[3],
                height
            )
        );
    }
}
