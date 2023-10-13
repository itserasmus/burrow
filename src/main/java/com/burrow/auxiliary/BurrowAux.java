package com.burrow.auxiliary;

public final class BurrowAux {
    public static boolean wasResizedMax(LayoutData data, BoxFrame frame) {
         return !(data.maxWidth == frame.width &&
            data.maxHeight == frame.height);
    }

    public static boolean wasResizedMin(LayoutData data, BoxFrame frame) {
         return !(data.minWidth == frame.width &&
            data.minHeight == frame.height);
    }

    public static boolean pointInHitbox(double x, double y, BoxFrame frame) {
        return frame.relX <= x && frame.relY <= y &&
            x <= frame.width + frame.relX && y <= frame.height + frame.relY;
    }

    public static boolean pointInHitbox(double x, double y, double[] hitbox) {
        return hitbox[0] <= x && hitbox[1] <= y &&
            x <= hitbox[2] + hitbox[0] && y <= hitbox[3] + hitbox[1];
    }

    public static boolean pointInHitbox(double x, double y, int[] hitbox) {
        return hitbox[0] <= x && hitbox[1] <= y &&
            x <= hitbox[2] + hitbox[0] && y <= hitbox[3] + hitbox[1];
    }

    public static boolean doHitboxIntersect(int[] h1, int[] h2) {
        return
            h1[0] + h1[2] > h2[0] &&
            h1[0] < h2[0] + h2[2] &&
            h1[1] + h1[3] > h2[1] &&
            h1[1] < h2[1] + h2[3];
    }

    public static boolean doHitboxIntersect(double[] h1, double[] h2) {
        return
            h1[0] + h1[2] > h2[0] &&
            h1[0] < h2[0] + h2[2] &&
            h1[1] + h1[3] > h2[1] &&
            h1[1] < h2[1] + h2[3];
    }

    public static double[] hitboxIntersection(int[] h1, int[] h2) {
        return new double[]{
            Math.max(h1[0], h2[0]),
            Math.max(h1[1], h2[1]),
            Math.min(h1[0] + h1[2], h2[0] + h2[2]) - Math.max(h1[0], h2[0]),
            Math.min(h1[1] + h1[3], h2[1] + h2[3]) - Math.max(h1[1], h2[1])
        };
    }

    public static void hitboxIntersection(int[] h1, int[] h2, int[] dest) {
        dest[0] = Math.max(h1[0], h2[0]);
        dest[1] = Math.max(h1[1], h2[1]);
        dest[2] = Math.min(h1[0] + h1[2], h2[0] + h2[2]) - dest[0];
        dest[3] = Math.min(h1[1] + h1[3], h2[1] + h2[3]) - dest[1];
    }

    public static int lerpColor(int c1, int c2, double phase) {
        return
            (int)((c1>>>24&0xFF)-(c1>>>24&0xFF)*phase + (c2>>>24&0xFF)*phase) << 24 |
            (int)((c1>>>16&0xFF)-(c1>>>16&0xFF)*phase + (c2>>>16&0xFF)*phase) << 16 |
            (int)((c1>>>8 &0xFF)-(c1>>>8 &0xFF)*phase + (c2>>>8 &0xFF)*phase) << 8  |
            (int)((c1     &0xFF)-(c1     &0xFF)*phase + (c2     &0xFF)*phase);
    }
}
