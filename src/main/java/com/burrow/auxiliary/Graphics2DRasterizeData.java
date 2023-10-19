package com.burrow.auxiliary;

import java.awt.Graphics2D;

import com.burrow.widget.single_child.canvas.stroke.BRenderFilter;

public class Graphics2DRasterizeData {
    public Graphics2D g;
    public BRenderFilter filter;
    public int remainingCaches;
    public boolean isCallerCaching = false;

    public Graphics2DRasterizeData decremetRemainingCaches() {
        remainingCaches--;
        return this;
    }

    public Graphics2DRasterizeData setGraphics(Graphics2D g) {
        this.g = g;
        return this;
    }

    public Graphics2DRasterizeData setIsCallerCaching(boolean isCallerCaching) {
        this.isCallerCaching = isCallerCaching;
        return this;
    }

    public Graphics2DRasterizeData(Graphics2D g, BRenderFilter filter, int remainingCaches) {
        this.g = g;
        this.filter = filter;
        this.remainingCaches = remainingCaches;
    }
}
