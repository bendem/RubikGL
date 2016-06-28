package be.bendem.jrubik.utils;

import java.util.Arrays;

public interface FloatArrayBaked extends ArrayBaked<float[]> {

    @Override
    default float[] array() {
        float[] array = weakArray();
        return Arrays.copyOf(array, array.length);
    }

    @Override
    default float[] array(float[] ints, int offset) {
        return copy(weakArray(), ints, offset);
    }

    static float[] copy(float[] from, float[] to, int offset) {
        System.arraycopy(from, 0, to, offset, from.length);
        return to;
    }

}
