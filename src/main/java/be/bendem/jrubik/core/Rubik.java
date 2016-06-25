package be.bendem.jrubik.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public Rubik rotate(Face face, Direction direction) {
        // TODO

        return this;
    }

}
