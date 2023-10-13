package com.burrow.widget.single_child.canvas.stroke;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.burrow.auxiliary.BurrowConstants;
import com.burrow.auxiliary.PaintData;

public final class CropStroke implements BStroke {
    public final double[] hitbox;

    @Override
    public int pixelColor(double x, double y, BRenderFilter filter) {
        filter.pushCropBounds(hitbox);
        return 0x00000000;
    }

    @Override
    public boolean drawStroke(double x, double y, double[] hitbox) {
        return true;
    }
    
    public CropStroke(double[] hitbox) {
        this.hitbox = hitbox;
    }

    @Override
    public void rasterizeToBufferedImage(BufferedImage image, BRenderFilter filter) {
        filter.pushCropBounds(hitbox);
    }

    @Override
    public void rasterizeToGraphics2D(Graphics2D g, BRenderFilter filter) {
        filter.pushCropBounds(hitbox);
        g.setClip(
            (int)filter.getCropBounds()[0],
            (int)filter.getCropBounds()[1],
            (int)filter.getCropBounds()[2],
            (int)filter.getCropBounds()[3]
        );
    }
    
    public CropStroke(PaintData data) {
        this.hitbox = new double[]{
            data.x,
            data.y,
            data.width,
            data.height
        };
    }

    public static final class UncropStroke implements BStroke {
        @Override
        public int pixelColor(double x, double y, BRenderFilter filter) {
            filter.popCropBounds();
            return 0x00000000;
        }
    
        @Override
        public boolean drawStroke(double x, double y, double[] hitbox) {
            return true;
        }

        @Override
        public void rasterizeToBufferedImage(BufferedImage image, BRenderFilter filter) {
            filter.popCropBounds();
        }

        @Override
        public void rasterizeToGraphics2D(Graphics2D g, BRenderFilter filter) {
            filter.popCropBounds();
            g.setClip(
                (int)filter.getCropBounds()[0],
                (int)filter.getCropBounds()[1],
                (int)filter.getCropBounds()[2],
                (int)filter.getCropBounds()[3]
            );
        }
        
        public UncropStroke() {}
    }
    
    public static final BStroke firstComplementary(double[] hitbox) {
        return BurrowConstants.paintDirection ?
            new CropStroke(hitbox) :
            new UncropStroke();
    }
    
    public static final BStroke secondComplementary(double[] hitbox) {
        return BurrowConstants.paintDirection ?
            new UncropStroke() :
            new CropStroke(hitbox);
    }
}
