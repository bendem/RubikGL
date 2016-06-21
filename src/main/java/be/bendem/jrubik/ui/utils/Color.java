package be.bendem.jrubik.ui.utils;

import java.util.Arrays;

public class Color {

    private final float[] color;

    public Color(float red, float green, float blue) {
        color = new float[3];
        color[0] = red;
        color[1] = green;
        color[2] = blue;
    }

    public float[] array() {
        return Arrays.copyOf(color, 3);
    }

    public float red() {
        return color[0];
    }

    public float green() {
        return color[1];
    }

    public float blue() {
        return color[2];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color1 = (Color) o;
        return Arrays.equals(color, color1.color);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(color);
    }
}
