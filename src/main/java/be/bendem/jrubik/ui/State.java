package be.bendem.jrubik.ui;

import be.bendem.jrubik.core.Cube;
import be.bendem.jrubik.core.Plane;
import be.bendem.jrubik.core.Rubik;
import be.bendem.jrubik.core.Slice;
import be.bendem.jrubik.ui.renderer.CubeRenderer;
import be.bendem.jrubik.ui.renderer.Renderer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengles.GLES20;

import java.nio.FloatBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import static be.bendem.jrubik.ui.Keyboard.Event.KEY_HELD;
import static be.bendem.jrubik.ui.Keyboard.Event.KEY_PRESSED;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;

public class State {

    private final Rubik rubik;
    private final Keyboard keyboard;
    private final Deque<Animation> animations;
    private final Renderer<Cube> cubeRenderer;
    private final FloatBuffer mvpCache;
    private boolean dirty = true;
    private float xRot = 0;
    private float yRot = 0;
    private int width = UI.WIDTH;
    private int height = UI.HEIGHT;
    private Animation currentAnimation;

    public State(Rubik rubik, Keyboard keyboard, long window) {
        this.rubik = rubik;
        this.keyboard = keyboard;
        animations = new ArrayDeque<>();
        cubeRenderer = new CubeRenderer();
        mvpCache = BufferUtils.createFloatBuffer(16);

        glfwSetWindowSizeCallback(window, (w, width, height) -> {
            System.out.printf("Window %d got resized to %d:%d%n", w, width, height);
            this.width = width;
            this.height = height;
            GLES20.glViewport(0, 0, width, height);
            dirty = true;
        });

        keyboard.on(KEY_HELD, GLFW_KEY_DOWN).add(() -> xRot += getMovement(keyboard));
        keyboard.on(KEY_HELD, GLFW_KEY_UP).add(() -> xRot -= getMovement(keyboard));
        keyboard.on(KEY_HELD, GLFW_KEY_RIGHT).add(() -> yRot += getMovement(keyboard));
        keyboard.on(KEY_HELD, GLFW_KEY_LEFT).add(() -> yRot -= getMovement(keyboard));
        keyboard.on(KEY_PRESSED, GLFW_KEY_R).add(() -> {
            rubik.reset();
            animations.clear();
            currentAnimation = null;
            xRot = yRot = 0;
            dirty = true;
        });
        keyboard.on(KEY_PRESSED, GLFW_KEY_SPACE).add(this::rotate);
    }

    private final Random random = new Random(1);
    private void rotate() {
        Slice slice = new Slice(Plane.values()[random.nextInt(3)], random.nextInt(2) - 1);
        animations.add(new Animation(rubik, slice, () -> rubik.rotate(slice, null)));
    }

    private float getMovement(Keyboard keyboard) {
        dirty = true;
        return keyboard.isDown(GLFW_KEY_LEFT_CONTROL)
            || keyboard.isDown(GLFW_KEY_RIGHT_CONTROL) ? .1f : .02f;
    }

    public FloatBuffer computeMvpMatrix() {
        if (dirty) {
            mvpCache.clear();

            new Matrix4f()
                .perspective(
                    (float) Math.toRadians(45.0),
                    (float) width / height,
                    0.1f, 100.0f
                )
                .lookAt(
                    1, 1, 3,  // Camera position in World Space
                    0, 0, 0,  // Looks at
                    0, 1, 0   // up direction
                )
                .rotate(xRot, 1, 0, 0)
                .rotate(yRot, 0, 1, 0)
                .get(mvpCache);

            dirty = false;
        }

        return mvpCache;
    }

    public void render() {
        List<Cube> cubes = rubik.cubes();
        for (int i = 0; i < cubes.size(); i++) {
            render(cubeRenderer, cubes.get(i));
        }
    }

    private <T> void render(Renderer<T> renderer, T element) {
        float[] vertices = renderer.prepare(this, element);
        cubeRenderer.render(this, vertices);
        cubeRenderer.cleanup();
    }

    public void tick() {
        keyboard.tick();

        if (currentAnimation() != null) {
            currentAnimation.tick();
            if (currentAnimation.done()) {
                currentAnimation = null;
            }
        }
    }

    public Animation currentAnimation() {
        if (currentAnimation != null) {
            return currentAnimation;
        }
        return currentAnimation = animations.pollLast();
    }


    public void init() {
        cubeRenderer.init();
    }

    public void close() {
        cubeRenderer.close();
    }
}
