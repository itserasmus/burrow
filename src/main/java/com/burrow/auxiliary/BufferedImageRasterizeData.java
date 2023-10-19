package com.burrow.auxiliary;

import java.awt.image.BufferedImage;

import com.burrow.widget.single_child.canvas.stroke.BRenderFilter;

public class BufferedImageRasterizeData {
    public BufferedImage image;
    public BRenderFilter filter;
    public int remainingCaches;
    public boolean isCallerCaching = false;

    public BufferedImageRasterizeData decremetRemainingCaches() {
        remainingCaches--;
        return this;
    }

    public BufferedImageRasterizeData setImage(BufferedImage image) {
        this.image = image;
        return this;
    }

    public BufferedImageRasterizeData setIsCallerCaching(boolean isCallerCaching) {
        this.isCallerCaching = isCallerCaching;
        return this;
    }

    public BufferedImageRasterizeData(BufferedImage image, BRenderFilter filter, int remainingCaches) {
        this.image = image;
        this.filter = filter;
        this.remainingCaches = remainingCaches;
    }
}
