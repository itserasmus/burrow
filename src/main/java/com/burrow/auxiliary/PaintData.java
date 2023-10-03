package com.burrow.auxiliary;

import com.burrow.widget.single_child.canvas.CanvasWidget;

public class PaintData {
    public double x, y;
    public double width, height;
    public CanvasWidget canvas;

    public PaintData setPos(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public PaintData setSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public PaintData setCanvas(CanvasWidget canvas) {
        this.canvas = canvas;
        return this;
    }

    public PaintData set(BoxFrame frame) {
        this.x = frame.relX;
        this.y = frame.relY;
        this.width = frame.width;
        this.height = frame.height;
        return this;
    }

    public PaintData set(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public PaintData(double x, double y, double width, double height, CanvasWidget canvas) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.canvas = canvas;
    }
}
