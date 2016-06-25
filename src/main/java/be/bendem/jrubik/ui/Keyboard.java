package be.bendem.jrubik.ui;

import java.util.*;

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

    public enum Event {
        KEY_PRESSED, KEY_RELEASED, KEY_HELD
    }

    public class HandlerRegistration {
        private final Collection<Handler> handlers;

        public HandlerRegistration(Collection<Handler> handlers) {
            this.handlers = handlers;
        }

        public HandlerRegistration clear() {
            handlers.clear();
            return this;
        }

        public HandlerRegistration add(Handler handler) {
            handlers.add(handler);
            return this;
        }
    }

    private final Set<Integer> keysDown;
    private final Map<Event, Map<Integer, Collection<Handler>>> handlers;

    public Keyboard(long window) {
        keysDown = new HashSet<>();
        handlers = new EnumMap<>(Event.class);

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

        on(Event.KEY_PRESSED, GLFW_KEY_ESCAPE).add(() -> glfwSetWindowShouldClose(window, true));
    }

    private void keyDown(int key) {
        keysDown.add(key);
        handlers
            .getOrDefault(Event.KEY_PRESSED, Collections.emptyMap())
            .getOrDefault(key, Collections.emptySet())
            .forEach(Handler::handle);
    }

    private void keyUp(int key) {
        keysDown.remove(key);
        handlers
            .getOrDefault(Event.KEY_RELEASED, Collections.emptyMap())
            .getOrDefault(key, Collections.emptySet())
            .forEach(Handler::handle);
    }

    public boolean isDown(int key) {
        return keysDown.contains(key);
    }

    public HandlerRegistration on(Event event, int key) {
        return new HandlerRegistration(
            handlers
                .computeIfAbsent(event, e -> new HashMap<>())
                .computeIfAbsent(key, k -> new HashSet<>()));
    }

    public void pulse() {
        Map<Integer, Collection<Handler>> onHold = handlers
            .getOrDefault(Event.KEY_HELD, Collections.emptyMap());

        onHold.forEach((key, handlers) -> {
            if (keysDown.contains(key)) {
                handlers.forEach(Handler::handle);
            }
        });
    }

}
