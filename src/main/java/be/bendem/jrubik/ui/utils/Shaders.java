package be.bendem.jrubik.ui.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Objects;

import static org.lwjgl.opengles.GLES20.*;

public class Shaders {

    public static Shader fromString(int type, CharSequence... strings) {
        int shader = glCreateShader(type);
        glShaderSource(shader, strings);
        glCompileShader(shader);

        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (status == GL_TRUE) {
            return new Shader(shader);
        }

        throw new RuntimeException(glGetShaderInfoLog(shader));
    }

    private static CharSequence[] load(String file) {
        try (InputStream is = Objects.requireNonNull(Shaders.class.getClassLoader().getResourceAsStream(file));
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines()
                .map(s -> s + "\n")
                .toArray(CharSequence[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Shader fromFile(int type, String file) {
        return fromString(type, load(file));
    }

    public static int compile(Shader... shaders) {
        int program = glCreateProgram();

        for (Shader shader : shaders) {
            glAttachShader(program, shader.getId());
        }

        glLinkProgram(program);
        int status = glGetProgrami(program, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(program));
        }

        for (Shader shader : shaders) {
            glDetachShader(program, shader.getId());
        }

        return program;
    }

}
