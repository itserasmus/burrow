package com.burrow.auxiliary;

public class LayoutData {
    public double minWidth, minHeight;
    public double maxWidth, maxHeight;
    public final int windowWidth, windowHeight;

    public LayoutData set(double minWidth, double minHeight, double maxWidth, double maxHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        return this;
    }

    public LayoutData(
        double minWidth, double minHeight,
        double maxWidth, double maxHeight,
        int windowWidth, int windowHeight
    ) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
}
