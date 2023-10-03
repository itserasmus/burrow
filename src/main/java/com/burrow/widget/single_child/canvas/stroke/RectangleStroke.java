package com.burrow.widget.single_child.canvas.stroke;

public final class RectangleStroke implements BStroke {
    public final int x, y, width, height, color;

    @Override
    public void rasterizeToCArr(int[][] arr) {
        if(arr.length == 0) {return;}
        final int maxHeight = Math.min(height + y, arr.length);
        final int maxWidth = Math.min(width + x, arr[0].length);

        for(int i = y<0?0:y; i < maxHeight; i++) {
            for(int j = x<0?0:x; j < maxWidth; j++) {
                arr[i][j] = color;
            }
        }
    }

    @Override
    public int pixelColor(int x, int y) {
        if(this.x <= x && this.y <= y && this.x + this.width >= x && this.y + this.height >= y) {
            return this.color;
        }
        return 0;
    }

    @Override
    public int[] getHitbox() {
        return new int[]{x, y, width, height};
    }

    public RectangleStroke(int x, int y, int width, int height, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
