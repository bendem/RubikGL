package be.bendem.jrubik.ui;

import be.bendem.jrubik.core.Rubik;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengles.GLES;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengles.GLES20.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengles.GLES20.GL_CULL_FACE;
import static org.lwjgl.opengles.GLES20.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengles.GLES20.GL_DEPTH_TEST;
import static org.lwjgl.opengles.GLES20.GL_TRUE;
import static org.lwjgl.opengles.GLES20.glClear;
import static org.lwjgl.opengles.GLES20.glClearColor;
import static org.lwjgl.opengles.GLES20.glEnable;
import static org.lwjgl.opengles.GLES30.glBindVertexArray;
import static org.lwjgl.opengles.GLES30.glDeleteVertexArrays;
import static org.lwjgl.opengles.GLES30.glGenVertexArrays;

public class UI {

    static final int WIDTH = 700;
    static final int HEIGHT = 600;

    // The window handle
    private long window;
    private final Rubik rubik;
    private final Keyboard keyboard;
    private final State state;

    public UI() {
        init();
        rubik = new Rubik();
        keyboard = new Keyboard(window);
        state = new State(rubik, keyboard, window);
    }

    public void run() {
        try {
            loop();

            // Free the window callbacks and destroy the window
            Callbacks.glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } catch (Throwable t) {
            System.err.println("----");
            t.printStackTrace();
            System.err.println("----");
        } finally {
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        //glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "YOLO!", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the resolution of the primary monitor
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (vidMode.width() - WIDTH) / 2,
            (vidMode.height() - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        //glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        GLES.createCapabilities();

        // bg
        glClearColor(0.2f, 0.2f, 0.2f, 0.0f);

        // VAO, don't ask
        int vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        state.init();

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            state.tick();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            state.render();

            // TODO Draw fps?
            glfwSwapBuffers(window);

            // TODO FPS cap?
        }

        state.close();
        glDeleteVertexArrays(vertexArray);
    }

    public static void main(String[] args) {
        new UI().run();
    }
}
