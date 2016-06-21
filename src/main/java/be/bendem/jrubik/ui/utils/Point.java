package be.bendem.jrubik.ui.utils;

import java.util.Arrays;

public class Point {

    public static Point of(float x, float y, float z) {
        return new Point(x, y, z);
    }

    public static Point of(float xyz) {
        return new Point(xyz, xyz, xyz);
    }

    private final float[] coords;

    private Point(float x, float y, float z) {
        coords = new float[3];
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
    }

    public float x() {
        return coords[0];
    }

    public float y() {
        return coords[1];
    }

    public float z() {
        return coords[2];
    }

    public float[] array() {
        return Arrays.copyOf(coords, coords.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Arrays.equals(coords, point.coords);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coords);
    }
}
