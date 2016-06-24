package be.bendem.jrubik.ui.renderer;

import be.bendem.jrubik.ui.State;
import be.bendem.jrubik.utils.AutoCloseable;

public interface Renderer<T> extends AutoCloseable {

    void init();

    void render(State state, T t);

}
