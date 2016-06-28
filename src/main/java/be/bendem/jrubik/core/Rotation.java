package be.bendem.jrubik.core;

import be.bendem.jrubik.utils.IntArrayBaked;

import java.util.Arrays;

public class Rotation implements IntArrayBaked {

    private final int[] vector;

    public Rotation() {
        this(0, 0, 0);
    }

    public Rotation(int x, int y, int z) {
        assert x >= 0 && x < 4;
        assert y >= 0 && y < 4;
        assert z >= 0 && z < 4;

        vector = new int[] { x, y, z };
    }

    public int x() {
        return vector[0];
    }

    public int y() {
        return vector[1];
    }

    public int z() {
        return vector[2];
    }

    public Rotation rotate(Plane plane) {
        switch (plane) {
        case XY: return rotateZ();
        case XZ: return rotateY();
        case YZ: return rotateX();
        }
        throw new AssertionError();
    }

    public Rotation rotateX() {
        vector[0] = vector[0] + 1 & 0b11;
        return this;
    }

    public Rotation rotateY() {
        vector[1] = vector[1] + 1 & 0b11;
        return this;
    }

    public Rotation rotateZ() {
        vector[2] = vector[2] + 1 & 0b11;
        return this;
    }

    public Face apply(Face face) {
        for (int i = 0; i < x(); i++) {
            face = face.nextYZ();
        }
        for (int i = 0; i < y(); i++) {
            face = face.nextXZ();
        }
        for (int i = 0; i < z(); i++) {
            face = face.nextXY();
        }
        return face;
    }

    @Override
    public int[] weakArray() {
        return vector;
    }

    @Override
    public String toString() {
        return "Rotation{" +
            "vector=" + Arrays.toString(vector) +
            '}';
    }
}
