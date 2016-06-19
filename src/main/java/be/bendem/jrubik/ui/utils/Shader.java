package be.bendem.jrubik.ui.utils;

import be.bendem.jrubik.utils.AutoCloseable;

import static org.lwjgl.opengles.GLES20.glDeleteShader;

public class Shader implements GLWrapper, AutoCloseable {

    private final int shader;

    public Shader(int shader) {
        this.shader = shader;
    }

    @Override
    public int getId() {
        return shader;
    }

    @Override
    public void close() {
        glDeleteShader(shader);
    }

}
