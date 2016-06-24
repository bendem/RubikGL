package be.bendem.jrubik.core;

public class Position {

    private final int x;
    private final int y;
    private final int z;

    public Position(int x, int y, int z) {
        assert x >= -1 && x <= 1;
        assert y >= -1 && y <= 1;
        assert z >= -1 && z <= 1;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

}
