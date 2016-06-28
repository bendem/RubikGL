package be.bendem.jrubik.ui.renderer;

import be.bendem.jrubik.ui.State;
import be.bendem.jrubik.utils.AutoCloseable;

public interface Renderer<T> extends AutoCloseable {

    void init();

    float[] prepare(State state, T t);

    void render(State state, float[] vertices);

    default void cleanup() {}

}
