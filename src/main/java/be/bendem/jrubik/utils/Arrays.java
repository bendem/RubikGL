package be.bendem.jrubik.utils;

public class Arrays {

    public static float[] concat(float[] array, float[]... arrays) {
        int newLength = array.length;
        for (float[] values : arrays) {
            newLength += values.length;
        }

        float[] newArray = new float[newLength];

        System.arraycopy(array, 0, newArray, 0, array.length);
        int current = array.length;
        for (float[] values : arrays) {
            System.arraycopy(values, 0, newArray, current, values.length);
            current += values.length;
        }

        return newArray;
    }

}
