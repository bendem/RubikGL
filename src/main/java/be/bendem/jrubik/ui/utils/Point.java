package be.bendem.jrubik.ui.utils;

public class Point {

    public static Point of(float x, float y, float z) {
        return new Point(x, y, z);
    }

    public static Point of(float xyz) {
        return new Point(xyz, xyz, xyz);
    }

    public final float x;
    public final float y;
    public final float z;

    private Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }
}
