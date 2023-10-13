package com.burrow.auxiliary;

public class BoxFrame {
    public double relX, relY;
    public double width, height;

    public BoxFrame setPos(double relX, double relY) {
        this.relX = relX;
        this.relY = relY;
        return this;
    }

    public BoxFrame setSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public BoxFrame constrain(double width, double height) {
        this.width = Math.min(width, this.width);
        this.height = Math.min(height, this.height);
        return this;
    }

    public BoxFrame set(double relX, double relY, double width, double height) {
        this.relX = relX;
        this.relY = relY;
        this.width = width;
        this.height = height;
        return this;
    }

    public BoxFrame setIntersection(BoxFrame other) {
        relX = Math.max(relX, other.relX);
        relY = Math.max(relY, other.relY);
        width = Math.min(relX + width, other.relX + other.width) - relX;
        height = Math.min(relY + height, other.relY + other.height) - relY;

        return this;
    }

    public BoxFrame(double relX, double relY, double width, double height) {
        this.relX = relX;
        this.relY = relY;
        this.width = width;
        this.height = height;
    }
    public BoxFrame() {
        this.relX = 0;
        this.relY = 0;
        this.width = 0;
        this.height = 0;
    }

    @Override
    public String toString() {
        return "relX: " + relX + ", relY: " + relY + ", width:" + width + ", height:" + height;
    }
    public String toShortString() {
        return relX + ", " + relY + ", " + width + ", " + height;
    }
}
