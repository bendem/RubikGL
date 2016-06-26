package be.bendem.jrubik.utils;

public interface ArrayBaked {

    float[] array();

    float[] array(float[] array, int offset);

    default float[] array(float[] array) {
        return array(array, 0);
    }

    static float[] copy(float[] from, float[] to, int offset) {
        System.arraycopy(from, 0, to, offset, from.length);
        return to;
    }

}
