package be.bendem.jrubik.utils;

import java.util.Arrays;

public interface IntArrayBaked extends ArrayBaked<int[]> {

    @Override
    default int[] array() {
        int[] array = weakArray();
        return Arrays.copyOf(array, array.length);
    }

    @Override
    default int[] array(int[] ints, int offset) {
        return copy(weakArray(), ints, offset);
    }

    static int[] copy(int[] from, int[] to, int offset) {
        System.arraycopy(from, 0, to, offset, from.length);
        return to;
    }

}
