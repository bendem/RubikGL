package be.bendem.jrubik.utils;

public class Floats {

    public static float clamp(float min, float value, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

}
