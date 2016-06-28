package be.bendem.jrubik.core;

import be.bendem.jrubik.utils.IntArrayBaked;

public class Position implements IntArrayBaked {

    private final int[] coords;

    public Position(int x, int y, int z) {
        assert x >= -1 && x <= 1;
        assert y >= -1 && y <= 1;
        assert z >= -1 && z <= 1;

        coords = new int[] { x, y, z };
    }

    public int x() {
        return coords[0];
    }

    public int y() {
        return coords[1];
    }

    public int z() {
        return coords[2];
    }

    public Position x(int x) {
        coords[0] = x;
        return this;
    }

    public Position y(int y) {
        coords[1] = y;
        return this;
    }

    public Position z(int z) {
        coords[2] = z;
        return this;
    }


    @Override
    public int[] weakArray() {
        return coords;
    }
}
