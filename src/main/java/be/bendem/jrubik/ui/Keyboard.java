package be.bendem.jrubik.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Keyboard {

    @FunctionalInterface
    public interface Handler {
        void handle();
    }

    private final Set<Integer> keysDown;
    private final Map<Integer, Set<Handler>> onHold;
    private final Map<Integer, Set<Handler>> onPress;
    private final Map<Integer, Set<Handler>> onRelease;

    public Keyboard(long window) {
        keysDown = new HashSet<>();
        onHold = new HashMap<>();
        onPress = new HashMap<>();
        onRelease = new HashMap<>();

        glfwSetKeyCallback(window, (win, key, scanCode, action, mods) -> {
            switch (action) {
            case GLFW_PRESS:
                keyDown(key);
                break;
            case GLFW_RELEASE:
                keyUp(key);
                break;
            }
        });

        onRelease(GLFW_KEY_ESCAPE, () -> glfwSetWindowShouldClose(window, true));
    }

    private void keyDown(int key) {
        keysDown.add(key);
        onPress
            .getOrDefault(key, Collections.emptySet())
            .forEach(Handler::handle);
    }

    private void keyUp(int key) {
        keysDown.remove(key);
        onRelease
            .getOrDefault(key, Collections.emptySet())
            .forEach(Handler::handle);
    }

    public boolean isDown(int key) {
        return keysDown.contains(key);
    }

    public void onHold(int key, Handler handler) {
        onHold.computeIfAbsent(key, k -> new HashSet<>()).add(handler);
    }

    public void onPress(int key, Handler handler) {
        onPress.computeIfAbsent(key, k -> new HashSet<>()).add(handler);
    }

    public void onRelease(int key, Handler handler) {
        onRelease.computeIfAbsent(key, k -> new HashSet<>()).add(handler);
    }

    public void pulse() {
        for (Integer key : keysDown) {
            onHold
                .getOrDefault(key, Collections.emptySet())
                .forEach(Handler::handle);
        }
    }

}
