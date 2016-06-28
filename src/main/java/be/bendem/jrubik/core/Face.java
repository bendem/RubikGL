package be.bendem.jrubik.core;

public enum Face {

    LEFT, RIGHT, TOP, BOTTOM, FRONT, BACK;

    public Face next(Plane plane) {
        return plane.get(
            this::nextXY,
            this::nextXZ,
            this::nextYZ
        );
    }

    public Face nextXY() {
        switch (this) {
        case LEFT: return TOP;
        case RIGHT: return BOTTOM;
        case TOP: return RIGHT;
        case BOTTOM: return LEFT;
        case FRONT: return FRONT;
        case BACK: return BACK;
        }
        throw new AssertionError();
    }

    public Face nextXZ() {
        switch (this) {
        case LEFT: return BACK;
        case RIGHT: return FRONT;
        case TOP: return TOP;
        case BOTTOM: return BOTTOM;
        case FRONT: return LEFT;
        case BACK: return RIGHT;
        }
        throw new AssertionError();
    }

    public Face nextYZ() {
        switch (this) {
        case LEFT: return LEFT;
        case RIGHT: return RIGHT;
        case TOP: return BACK;
        case BOTTOM: return FRONT;
        case FRONT: return TOP;
        case BACK: return BOTTOM;
        }
        throw new AssertionError();
    }

}
