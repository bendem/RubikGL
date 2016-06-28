package be.bendem.jrubik.core;

import be.bendem.jrubik.ui.utils.Matrices;
import org.joml.Matrix3f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static be.bendem.jrubik.core.Color.BLUE;
import static be.bendem.jrubik.core.Color.GREEN;
import static be.bendem.jrubik.core.Color.ORANGE;
import static be.bendem.jrubik.core.Color.RED;
import static be.bendem.jrubik.core.Color.WHITE;
import static be.bendem.jrubik.core.Color.YELLOW;

public class Rubik {

    private final List<Cube> cubes;

    public Rubik() {
        cubes = new ArrayList<>(27);
        reset();
    }

    public void reset() {
        cubes.clear();
        cubes.add(Cube.builder().x( 1).y( 1).z(-1).right(GREEN).top(WHITE).back(RED).build());
        cubes.add(Cube.builder().x( 0).y( 1).z(-1).top(WHITE).back(RED).build());
        cubes.add(Cube.builder().x(-1).y( 1).z(-1).left(BLUE).top(WHITE).back(RED).build());

        cubes.add(Cube.builder().x( 1).y( 0).z(-1).right(GREEN).back(RED).build());
        cubes.add(Cube.builder().x( 0).y( 0).z(-1).back(RED).build());
        cubes.add(Cube.builder().x(-1).y( 0).z(-1).left(BLUE).back(RED).build());

        cubes.add(Cube.builder().x( 1).y(-1).z(-1).right(GREEN).bottom(YELLOW).back(RED).build());
        cubes.add(Cube.builder().x( 0).y(-1).z(-1).bottom(YELLOW).back(RED).build());
        cubes.add(Cube.builder().x(-1).y(-1).z(-1).left(BLUE).bottom(YELLOW).back(RED).build());

        cubes.add(Cube.builder().x( 1).y( 1).z( 0).right(GREEN).top(WHITE).build());
        cubes.add(Cube.builder().x( 0).y( 1).z( 0).top(WHITE).build());
        cubes.add(Cube.builder().x(-1).y( 1).z( 0).left(BLUE).top(WHITE).build());

        cubes.add(Cube.builder().x( 1).y( 0).z( 0).right(GREEN).build());
        cubes.add(Cube.builder().x(-1).y( 0).z( 0).left(BLUE).build());

        cubes.add(Cube.builder().x( 1).y(-1).z( 0).right(GREEN).bottom(YELLOW).build());
        cubes.add(Cube.builder().x( 0).y(-1).z( 0).bottom(YELLOW).build());
        cubes.add(Cube.builder().x(-1).y(-1).z( 0).left(BLUE).bottom(YELLOW).build());

        cubes.add(Cube.builder().x( 1).y( 1).z( 1).right(GREEN).top(WHITE).front(ORANGE).build());
        cubes.add(Cube.builder().x( 0).y( 1).z( 1).top(WHITE).front(ORANGE).build());
        cubes.add(Cube.builder().x(-1).y( 1).z( 1).left(BLUE).top(WHITE).front(ORANGE).build());

        cubes.add(Cube.builder().x( 1).y( 0).z( 1).right(GREEN).front(ORANGE).build());
        cubes.add(Cube.builder().x( 0).y( 0).z( 1).front(ORANGE).build());
        cubes.add(Cube.builder().x(-1).y( 0).z( 1).left(BLUE).front(ORANGE).build());

        cubes.add(Cube.builder().x( 1).y(-1).z( 1).right(GREEN).bottom(YELLOW).front(ORANGE).build());
        cubes.add(Cube.builder().x( 0).y(-1).z( 1).bottom(YELLOW).front(ORANGE).build());
        cubes.add(Cube.builder().x(-1).y(-1).z( 1).left(BLUE).bottom(YELLOW).front(ORANGE).build());
    }

    public List<Cube> cubes() {
        return Collections.unmodifiableList(cubes);
    }

    public Stream<Cube> forSlice(Slice slice) {
        return cubes().stream()
            .filter(c -> slice.matches(c.position()));
    }

    public Rubik rotate(Slice slice, Direction direction) {
        System.out.printf("rotation: %s%n", slice);

        Matrix3f rotation = Matrices.rotation(slice.plane(), (float) (Math.PI * .5), new Matrix3f());
        forSlice(slice)
            .forEach(c -> {
                Position position = c.position();
                Vector3f rotated = new Vector3f(
                    position.x(), position.y(), position.z())
                    .mul(rotation);

                position
                    .x(Math.round(rotated.x))
                    .y(Math.round(rotated.y))
                    .z(Math.round(rotated.z));

                c.rotation().rotate(slice.plane());
            });

        return this;
    }

}
