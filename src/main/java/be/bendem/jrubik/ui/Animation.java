package be.bendem.jrubik.ui;

import be.bendem.jrubik.core.Cube;
import be.bendem.jrubik.core.Rubik;
import be.bendem.jrubik.core.Slice;

import java.util.Set;
import java.util.stream.Collectors;

public class Animation {

    private final Rubik rubik;
    private final Slice slice;
    private final Action done;
    private float tick = 0;

    public interface Action {
        void whenDone();
    }

    public Animation(Rubik rubik, Slice slice, Action done) {
        this.rubik = rubik;
        this.slice = slice;
        this.done = done;
    }

    public void tick() {
        tick += 0.05;

        if (tick > 1) {
            done.whenDone();
        }
    }

    public boolean done() {
        return tick > 1;
    }

    public void rotations(float[] out) {
        float angle = (float) (Math.PI / 2 * tick);

        int index = slice.plane().get(() -> 2, () -> 1, () -> 0);
        out[index] = angle;
    }

    public Set<Cube> cubes() {
        return rubik.forSlice(slice).collect(Collectors.toSet());
    }

}
