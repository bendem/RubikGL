package be.bendem.jrubik.ui;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class State {

    private final Set<Integer> keysDown;
    private float xRot = 0;
    private float yRot = 0;
    private boolean dirty = true;
    private final FloatBuffer mvpCache;

    public State() {
        keysDown = new HashSet<>();
        mvpCache = BufferUtils.createFloatBuffer(16);
    }

    public FloatBuffer computeMatrix() {
        if (dirty) {
            mvpCache.clear();

            new Matrix4f().perspective((float) Math.toRadians(45.0), 1, 0.1f, 100.0f)
                .lookAt(
                    4, 2, 2,  // Camera position in World Space
                    0, 0, 0,  // Looks at
                    0, 1, 0   // Head is at
                )
                //.mul(new Matrix4f().identity())
                .rotate(xRot, 1, 0, 0)
                .rotate(yRot, 0, 1, 0)
                .get(mvpCache);

            dirty = false;
        }

        return mvpCache;
    }

    public State keyDown(int key) {
        keysDown.add(key);
        return this;
    }

    public State keyUp(int key) {
        keysDown.remove(key);
        return this;
    }

    public void pulse() {
        final float MOVEMENT;
        if (keysDown.contains(GLFW_KEY_LEFT_CONTROL)
                || keysDown.contains(GLFW_KEY_RIGHT_CONTROL)) {
            MOVEMENT = .1f;
        } else {
            MOVEMENT = .01f;
        }

        for (int key : keysDown) {
            switch (key) {
            case GLFW_KEY_RIGHT:
                yRot += MOVEMENT;
                dirty = true;
                break;
            case GLFW_KEY_LEFT:
                yRot -= MOVEMENT;
                dirty = true;
                break;
            case GLFW_KEY_UP:
                xRot += MOVEMENT;
                dirty = true;
                break;
            case GLFW_KEY_DOWN:
                xRot -= MOVEMENT;
                dirty = true;
                break;
            }
        }
    }

}
