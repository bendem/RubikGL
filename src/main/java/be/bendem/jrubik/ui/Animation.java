package be.bendem.jrubik.ui;

import be.bendem.jrubik.core.Cube;
import be.bendem.jrubik.core.Slice;
import be.bendem.jrubik.ui.renderer.CubeRenderer;
import be.bendem.jrubik.ui.utils.Matrices;
import org.joml.Matrix3f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.Set;

public class Animation extends CubeRenderer {

    private final Set<Cube> cubes;
    private final Slice slice;
    private final Action done;
    private float tick = 0;

    public interface Action {
        void whenDone();
    }

    public Animation(Set<Cube> cubes, Slice slice, Action done) {
        this.cubes = cubes;
        this.slice = slice;
        this.done = done;
    }

    public void tick() {
        tick += 0.02;

        if (tick > 1) {
            done.whenDone();
        }
    }

    public boolean done() {
        return tick > 1;
    }

    @Override
    public float[] prepare(State state, Cube cube) {
        float[] vertices = super.prepare(state, cube);

        // Write a shader instead?

        float angle = (float) (Math.PI / 2 * tick);
        FloatBuffer buffer = FloatBuffer.wrap(vertices);
        Vector3f point = new Vector3f();
        Matrix3f rotation = Matrices.rotation(slice.plane(), angle, new Matrix3f());

        for (int i = 0; i < vertices.length; i += 3) {
            point
                .set(i, buffer)
                .mul(rotation)
                .get(i, buffer);
        }

        return vertices;
    }

    public Set<Cube> cubes() {
        return Collections.unmodifiableSet(cubes);
    }

}
