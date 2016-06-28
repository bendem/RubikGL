package be.bendem.jrubik.core;

import java.util.function.Supplier;

public enum Plane {

    XY(0, 0, 1), XZ(0, 1, 0), YZ(1, 0, 0);

    public final float x;
    public final float y;
    public final float z;

    Plane(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public <T> T get(Supplier<T> xy, Supplier<T> xz, Supplier<T> yz) {
        switch (this) {
        case XY: return xy.get();
        case XZ: return xz.get();
        case YZ: return yz.get();
        }
        throw new AssertionError();
    }

}
