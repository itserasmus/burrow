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

    public LayoutData setMin(double minWidth, double minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        return this;
    }

    public LayoutData set(double maxWidth, double maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        return this;
    }

    public LayoutData setMinWidth(double minWidth) {
        this.minWidth = minWidth;
        return this;
    }
    public LayoutData setMinHeight(double minHeight) {
        this.minHeight = minHeight;
        return this;
    }
    public LayoutData setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }
    public LayoutData setMaxHeight(double maxHeight) {
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
