package be.bendem.jrubik.ui.utils;

import be.bendem.jrubik.core.Plane;
import org.joml.Matrix3f;

public class Matrices {

    public static Matrix3f rotation(Plane plane, float angle, Matrix3f out) {
        return plane.get(
            () -> rotationZ(angle, out),
            () -> rotationY(angle, out),
            () -> rotationX(angle, out)
        );
    }

    public static Matrix3f rotationX(float angle, Matrix3f out) {
        return out.set(
            1,                       0,                        0,
            0, (float) Math.cos(angle), -(float) Math.sin(angle),
            0, (float) Math.sin(angle),  (float) Math.cos(angle)
        );
    }

    public static Matrix3f rotationY(float angle, Matrix3f out) {
        return out.set(
             (float) Math.cos(angle), 0, (float) Math.sin(angle),
                                  0,  1,                       0,
            -(float) Math.sin(angle), 0, (float) Math.cos(angle)
        );
    }

    public static Matrix3f rotationZ(float angle, Matrix3f out) {
        return out.set(
            (float) Math.cos(angle), -(float) Math.sin(angle), 0,
            (float) Math.sin(angle),  (float) Math.cos(angle), 0,
                                  0,                        0, 1
        );
    }

}
