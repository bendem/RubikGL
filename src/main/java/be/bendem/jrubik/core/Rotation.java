package be.bendem.jrubik.core;

import java.util.ArrayList;
import java.util.List;

public class Rotation {

    private enum Axis { X, Y, Z }

    // This, is a leak.
    private final List<Axis> rotations;

    public Rotation() {
        rotations = new ArrayList<>();
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
        rotations.add(Axis.X);
        return this;
    }

    public Rotation rotateY() {
        rotations.add(Axis.Y);
        return this;
    }

    public Rotation rotateZ() {
        rotations.add(Axis.Z);
        return this;
    }

    public Face apply(Face face) {
        for (int i = 0; i < rotations.size(); i++) {
            switch (rotations.get(i)) {
            case X: face = face.nextYZ(); break;
            case Y: face = face.nextXZ(); break;
            case Z: face = face.nextXY(); break;
            }
        }
        return face;
    }

    @Override
    public String toString() {
        return "Rotation{" +
            "rotations=" + rotations +
            '}';
    }
}
