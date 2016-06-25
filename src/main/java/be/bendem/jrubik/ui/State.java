package be.bendem.jrubik.ui;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static be.bendem.jrubik.ui.Keyboard.Event.KEY_HELD;
import static be.bendem.jrubik.ui.Keyboard.Event.KEY_PRESSED;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class State {

    private final FloatBuffer mvpCache;
    private boolean dirty = true;
    private float xRot = 0;
    private float yRot = 0;

    public State(Keyboard keyboard) {
        mvpCache = BufferUtils.createFloatBuffer(16);

        keyboard.on(KEY_HELD, GLFW_KEY_DOWN).add(() -> xRot += getMovement(keyboard));
        keyboard.on(KEY_HELD, GLFW_KEY_UP).add(() -> xRot -= getMovement(keyboard));
        keyboard.on(KEY_HELD, GLFW_KEY_RIGHT).add(() -> yRot += getMovement(keyboard));
        keyboard.on(KEY_HELD, GLFW_KEY_LEFT).add(() -> yRot -= getMovement(keyboard));
        keyboard.on(KEY_PRESSED, GLFW_KEY_R).add(() -> {
            xRot = yRot = 0;
            dirty = true;
        });
    }

    private float getMovement(Keyboard keyboard) {
        dirty = true;
        return keyboard.isDown(GLFW_KEY_LEFT_CONTROL)
            || keyboard.isDown(GLFW_KEY_RIGHT_CONTROL) ? .1f : .02f;
    }

    public FloatBuffer computeMvpMatrix() {
        if (dirty) {
            mvpCache.clear();

            new Matrix4f().perspective((float) Math.toRadians(45.0), 1, 0.1f, 100.0f)
                .lookAt(
                    1, 1, 5,  // Camera position in World Space
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
}
