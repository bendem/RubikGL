package be.bendem.jrubik.core;

import java.util.EnumMap;
import java.util.Map;

public class Cube {

    public static class CubeBuilder {
        private Color top = Color.BLACK;
        private Color front = Color.BLACK;
        private Color left = Color.BLACK;
        private Color back = Color.BLACK;
        private Color right = Color.BLACK;
        private Color bottom = Color.BLACK;
        private int x = 0;
        private int y = 0;
        private int z = 0;
        private Orientation orientation = Orientation.FRONT;

        private CubeBuilder() {}

        public CubeBuilder top(Color top) {
            this.top = top;
            return this;
        }

        public CubeBuilder front(Color front) {
            this.front = front;
            return this;
        }

        public CubeBuilder left(Color left) {
            this.left = left;
            return this;
        }

        public CubeBuilder back(Color back) {
            this.back = back;
            return this;
        }

        public CubeBuilder right(Color right) {
            this.right = right;
            return this;
        }

        public CubeBuilder bottom(Color bottom) {
            this.bottom = bottom;
            return this;
        }

        public CubeBuilder x(int x) {
            this.x = x;
            return this;
        }

        public CubeBuilder y(int y) {
            this.y = y;
            return this;
        }

        public CubeBuilder z(int z) {
            this.z = z;
            return this;
        }

        public CubeBuilder orientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public Cube build() {
            EnumMap<Face, Color> colors = new EnumMap<>(Face.class);
            colors.put(Face.TOP, top);
            colors.put(Face.FRONT, front);
            colors.put(Face.LEFT, left);
            colors.put(Face.BACK, back);
            colors.put(Face.RIGHT, right);
            colors.put(Face.BOTTOM, bottom);
            return new Cube(colors, new Position(x, y, z), orientation);
        }
    }

    public static CubeBuilder builder() {
        return new CubeBuilder();
    }

    private Map<Face, Color> colors;
    private Position position;
    private Orientation orientation;

    private Cube(Map<Face, Color> colors, Position position, Orientation orientation) {
        this.colors = colors;
        this.position = position;
        this.orientation = orientation;
    }

    public Map<Face, Color> getColors() {
        return colors;
    }

    public Position getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Cube setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

}
