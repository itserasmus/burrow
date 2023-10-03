package com.burrow.widget.single_child.canvas.stroke;

public interface BStroke {
    public void rasterizeToCArr(int[][] arr);
    public int pixelColor(int x, int y);
    public int[] getHitbox();
}
