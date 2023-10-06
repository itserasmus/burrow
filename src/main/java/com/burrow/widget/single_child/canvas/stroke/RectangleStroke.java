package com.burrow.widget.single_child.canvas.stroke;

public final class RectangleStroke implements BStroke {
    public final int x, y, width, height, color;
    public final double[] hitbox;

    @Override
    public void rasterizeToCArr(int[][] arr) {
        if(arr.length == 0) {return;}
        final int maxHeight = Math.min(height + y, arr[0].length);
        final int maxWidth = Math.min(width + x, arr.length);

        for(int i = y<0?0:y; i < maxHeight; i++) {
            for(int j = x<0?0:x; j < maxWidth; j++) {
                arr[j][i] = color;
            }
        }
    }

    @Override
    public int pixelColor(double x, double y) {
        if(this.x <= x && this.y <= y && this.x + this.width >= x && this.y + this.height >= y) {
            return this.color;
        }
        return 0;
    }

    @Override
    public double[] getHitbox() {
        return hitbox;
    }

    public RectangleStroke(int x, int y, int width, int height, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hitbox = new double[]{x, y, width, height};
    }
}
