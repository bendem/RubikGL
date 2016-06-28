package be.bendem.jrubik.core;

public class Slice {

    private final Plane plane;
    private final int offset;

    public Slice(Plane plane, int offset) {
        assert offset >= -1 && offset <= 1;

        this.plane = plane;
        this.offset = offset;
    }

    public Plane plane() {
        return plane;
    }

    public int offset() {
        return offset;
    }

    public boolean matches(Position position) {
        return plane.get(position::z, position::y, position::x) == offset;
    }

    @Override
    public String toString() {
        return "Slice{" +
            "plane=" + plane +
            ", offset=" + offset +
            '}';
    }
}
