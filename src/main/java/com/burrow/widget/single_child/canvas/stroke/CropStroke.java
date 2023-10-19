package com.burrow.widget.single_child.canvas.stroke;

import com.burrow.auxiliary.BufferedImageRasterizeData;
import com.burrow.auxiliary.BurrowConstants;
import com.burrow.auxiliary.Graphics2DRasterizeData;
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
    public void rasterizeToBufferedImage(BufferedImageRasterizeData data) {
        data.filter.pushCropBounds(hitbox);
    }

    @Override
    public void rasterizeToGraphics2D(Graphics2DRasterizeData data) {
        data.filter.pushCropBounds(hitbox);
        data.g.setClip(
            (int)data.filter.getCropBounds()[0],
            (int)data.filter.getCropBounds()[1],
            (int)data.filter.getCropBounds()[2],
            (int)data.filter.getCropBounds()[3]
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
        public void rasterizeToBufferedImage(BufferedImageRasterizeData data) {
            data.filter.popCropBounds();
        }

        @Override
        public void rasterizeToGraphics2D(Graphics2DRasterizeData data) {
            data.filter.popCropBounds();
            data.g.setClip(
                (int)data.filter.getCropBounds()[0],
                (int)data.filter.getCropBounds()[1],
                (int)data.filter.getCropBounds()[2],
                (int)data.filter.getCropBounds()[3]
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
