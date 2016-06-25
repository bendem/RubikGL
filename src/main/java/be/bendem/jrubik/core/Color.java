package be.bendem.jrubik.core;

import java.util.Arrays;

public class Color {

    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE = new Color(0, 0, 1);
    public static final Color ORANGE = new Color(1, 0.549f, 0);
    public static final Color YELLOW = new Color(1, 0.918f, 0);

    private final float[] color;

    public Color(float red, float green, float blue) {
        color = new float[] { red, green, blue };
    }

    public float[] array() {
        return Arrays.copyOf(color, 3);
    }

    public float[] array(float[] out) {
        return array(out, 0);
    }

    public float[] array(float[] out, int offset) {
        System.arraycopy(color, 0, out, offset, 3);
        return out;
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

    @Override
    public String toString() {
        return "Color{" +
            "color=" + Arrays.toString(color) +
            '}';
    }
}
