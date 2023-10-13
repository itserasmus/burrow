package com.burrow.widget.single_child.canvas.stroke;

import com.burrow.base.BPanel;

public class BRenderFilter {
    public BPanel panel;
    protected double[][] cropBounds;
    protected int length = 1;
    public final double[] initialCropBounds;

    public double[] getCropBounds() {
        return cropBounds[length - 1];
    }
    public double[] popCropBounds() {
        if(length < 2) {
            return cropBounds[length - 1];
        }
        if(length << 3 > cropBounds.length) {
            halfLength();
        }
        return cropBounds[--length];
    }
    public BRenderFilter pushCropBounds(double[] cropBounds) {
        if(length >= cropBounds.length) {
            doubleLength();
        }
        this.cropBounds[length++] = cropBounds;
        return this;
    }

    public BRenderFilter reset() {
        length = 1;
        return this;
    }

    protected void doubleLength() {
        double[][] newCropBounds = new double[4][cropBounds.length << 1];
        int minLength = length < newCropBounds.length ? length : newCropBounds.length;
        for(int i = 0; i < minLength; i++) {
            newCropBounds[i] = cropBounds[i];
        }
        cropBounds = newCropBounds;
    }
    protected void halfLength() {
        double[][] newCropBounds = new double[4][cropBounds.length >> 1];
        int minLength = length < newCropBounds.length ? length : newCropBounds.length;
        for(int i = 0; i < minLength; i++) {
            newCropBounds[i] = cropBounds[i];
        }
        cropBounds = newCropBounds;
    }

    public BRenderFilter(BPanel panel, double[] cropBounds) {
        this.panel = panel;
        this.cropBounds = new double[4][16];
        this.cropBounds[0] =cropBounds;
        this.initialCropBounds = cropBounds;
    }

    protected BRenderFilter(double[] cropBounds) {
        this.initialCropBounds = cropBounds;
    }
}
