package com.burrow.widget.single_child.canvas.stroke;

import java.util.Arrays;

import com.burrow.base.BPanel;

public class BRenderFilter {
    public BPanel panel;
    protected double[][] cropBounds;
    protected int cropBoundsLength = 1;

    public double[][] trans;
    public int transLength = 1;

    public final double[] initialTrans;
    public final double[] initialCropBounds;


    

    public BRenderFilter reset() {
        cropBoundsLength = 1;
        transLength = 1;
        return this;
    }
    
    
    public double[] getCropBounds() {
        return cropBounds[cropBoundsLength - 1];
    }
    public double[] popCropBounds() {
        if(cropBoundsLength < 2) {
            return cropBounds[cropBoundsLength - 1];
        }
        if(cropBoundsLength << 3 > cropBounds.length) {
            halfCropBoundsLength();
        }
        return cropBounds[--cropBoundsLength];
    }
    public BRenderFilter pushCropBounds(double[] cropBounds) {
        if(cropBoundsLength >= cropBounds.length) {
            doubleCropBoundsLength();
        }
        this.cropBounds[cropBoundsLength++] = cropBounds;
        return this;
    }

    protected void doubleCropBoundsLength() {
        double[][] newCropBounds = new double[cropBounds.length << 1][4];
        int minLength = cropBoundsLength < newCropBounds.length ? cropBoundsLength : newCropBounds.length;
        for(int i = 0; i < minLength; i++) {
            newCropBounds[i] = cropBounds[i];
        }
        cropBounds = newCropBounds;
    }
    protected void halfCropBoundsLength() {
        double[][] newCropBounds = new double[cropBounds.length >> 1][4];
        int minLength = cropBoundsLength < newCropBounds.length ? cropBoundsLength : newCropBounds.length;
        for(int i = 0; i < minLength; i++) {
            newCropBounds[i] = cropBounds[i];
        }
        cropBounds = newCropBounds;
    }
    
    
    public double[] getTrans() {
        return trans[transLength - 1];
    }
    public double[] popTrans() {
        if(transLength < 2) {
            return trans[transLength - 1];
        }
        if(transLength << 3 > trans.length) {
            halfTransLength();
        }
        return trans[--transLength];
    }
    public BRenderFilter pushTrans(double[] trans) {
        if(transLength >= trans.length) {
            doubleTransLength();
        }
        this.trans[transLength++] = trans;
        return this;
    }

    protected void doubleTransLength() {
        double[][] newTrans = new double[trans.length << 1][4];
        int minLength = transLength < newTrans.length ? transLength : newTrans.length;
        for(int i = 0; i < minLength; i++) {
            newTrans[i] = trans[i];
        }
        trans = newTrans;
    }
    protected void halfTransLength() {
        double[][] newTrans = new double[trans.length >> 1][4];
        int minLength = transLength < newTrans.length ? transLength : newTrans.length;
        for(int i = 0; i < minLength; i++) {
            newTrans[i] = trans[i];
        }
        trans = newTrans;
    }





    public long getUID() {
        final int prime = 31;
        long result = 1;

        result = prime * result + Arrays.deepHashCode(this.cropBounds);
        result = prime * result + Arrays.deepHashCode(this.trans);
        result = prime * result + this.cropBoundsLength;

        return result;
    }

    public long getLooseUID() {
        final int prime = 31;
        long result = 1;

        result = prime * result + this.cropBounds[cropBoundsLength-1].hashCode();
        result = prime * result + this.trans[transLength-1].hashCode();
        result = prime * result + this.cropBoundsLength;

        return result;
    }

    public BRenderFilter(BPanel panel, double[] cropBounds, double[] trans) {
        this.panel = panel;

        this.cropBounds = new double[4][16];
        this.cropBounds[0] =cropBounds;
        this.initialCropBounds = cropBounds;

        this.trans = new double[2][16];
        this.trans[0] = trans;
        this.initialTrans = trans;
    }

    protected BRenderFilter(double[] cropBounds, double[] trans) {
        this.initialCropBounds = cropBounds;
        this.initialTrans = trans;
    }
}
