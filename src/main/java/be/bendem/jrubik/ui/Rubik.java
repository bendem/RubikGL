package be.bendem.jrubik.ui;

import be.bendem.jrubik.ui.utils.Buffers;
import be.bendem.jrubik.ui.utils.Point;
import be.bendem.jrubik.ui.utils.Shader;
import be.bendem.jrubik.ui.utils.Shaders;
import be.bendem.jrubik.ui.utils.Shapes;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengles.GLES;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengles.GLES20.*;
import static org.lwjgl.opengles.GLES30.glBindVertexArray;
import static org.lwjgl.opengles.GLES30.glDeleteVertexArrays;
import static org.lwjgl.opengles.GLES30.glGenVertexArrays;

public class Rubik {

    private static float clamp(float min, float value, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    private static float[] concat(float[] array, float[]... arrays) {
        int newLength = array.length;
        for (float[] values : arrays) {
            newLength += values.length;
        }

        float[] newArray = new float[newLength];

        System.arraycopy(array, 0, newArray, 0, array.length);
        int current = array.length;
        for (float[] values : arrays) {
            System.arraycopy(values, 0, newArray, current, values.length);
            current += values.length;
        }

        return newArray;
    }

    private static final float[] colors;
    static {
        colors = new float[6 * 2 * 3 * 3 * 8];
        for (int i = 0; i < colors.length; i += 9) {
            colors[i  ] = colors[i+3] = colors[i+6] = clamp(0.4f, (float) Math.random(), 0.9f);
            colors[i+1] = colors[i+4] = colors[i+7] = clamp(0.4f, (float) Math.random(), 0.9f);
            colors[i+2] = colors[i+5] = colors[i+8] = clamp(0.4f, (float) Math.random(), 0.9f);
        }
    }

    // The window handle
    private long window;
    private final State state = new State();

    public void run() {
        try {
            init();
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

        int WIDTH = 800;
        int HEIGHT = 800;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "YOLO!", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
            switch (action) {
            case GLFW_PRESS:
                state.keyDown(key);
                break;
            case GLFW_RELEASE:
                if (key == GLFW_KEY_ESCAPE || key == GLFW_KEY_Q || key == GLFW_KEY_A) { // Fuck qwerty
                    glfwSetWindowShouldClose(window, true);
                } else {
                    state.keyUp(key);
                }
                break;
            }
        });

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
        glClearColor(0.0f, 0.0f, 0.4f, 0.0f);

        // VAO, don't ask
        int vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        int program;
        try (Shader vertex = Shaders.fromFile(GL_VERTEX_SHADER, "vertex.glsl");
             Shader fragment = Shaders.fromFile(GL_FRAGMENT_SHADER, "fragment.glsl")) {
            program = Shaders.compile(vertex, fragment);
        }

        int matrix = glGetUniformLocation(program, "mvp");

        float[] vertices = concat(
            Shapes.cube(Point.of( -1,  -1,  -1), 0.8f),
            Shapes.cube(Point.of(.2f,  -1,  -1), 0.8f),
            Shapes.cube(Point.of( -1, .2f,  -1), 0.8f),
            Shapes.cube(Point.of(.2f, .2f,  -1), 0.8f),
            Shapes.cube(Point.of( -1,  -1, .2f), 0.8f),
            Shapes.cube(Point.of(.2f,  -1, .2f), 0.8f),
            Shapes.cube(Point.of( -1, .2f, .2f), 0.8f),
            Shapes.cube(Point.of(.2f, .2f, .2f), 0.8f)
        );

        int vertexBuffer = Buffers.f(vertices);
        int colorBuffer = Buffers.f(colors);

        glEnable(GL_CULL_FACE);

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            state.pulse();

            glClear(GL_COLOR_BUFFER_BIT/* | GL_DEPTH_BUFFER_BIT*/);
            draw(program, matrix, vertexBuffer, vertices, colorBuffer);
            // TODO Draw fps?
            glfwSwapBuffers(window);

            // TODO FPS cap?
        }

        glDeleteBuffers(vertexBuffer);
        glDeleteBuffers(colorBuffer);
        glDeleteProgram(program);
        glDeleteVertexArrays(vertexArray);
    }

    private void draw(int program, int matrix, int vertexBuffer, float[] vertices, int colorBuffer) {
        glUseProgram(program);

        glUniformMatrix4fv(matrix, false, state.computeMatrix());

        // ?
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glVertexAttribPointer(
            0,                      // attribute 0. No particular reason for 0, but must match the layout in the shader.
            3,                      // size
            GL_FLOAT,               // type
            false,                  // normalized?
            0,                      // stride
            MemoryUtil.NULL         // array buffer offset
        );

        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);
        glVertexAttribPointer(
            1,                      // attribute 0. No particular reason for 0, but must match the layout in the shader.
            3,                      // size
            GL_FLOAT,               // type
            false,                  // normalized?
            0,                      // stride
            MemoryUtil.NULL         // array buffer offset
        );

        // Draw the triangle
        glDrawArrays(GL_TRIANGLES, 0, vertices.length / 3);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    public static void main(String[] args) {
        new Rubik().run();
    }
}
