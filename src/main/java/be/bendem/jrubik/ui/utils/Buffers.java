package be.bendem.jrubik.ui.utils;

import static org.lwjgl.opengles.GLES20.GL_ARRAY_BUFFER;
import static org.lwjgl.opengles.GLES20.GL_STATIC_DRAW;
import static org.lwjgl.opengles.GLES20.glBindBuffer;
import static org.lwjgl.opengles.GLES20.glBufferData;
import static org.lwjgl.opengles.GLES20.glGenBuffers;

public class Buffers {

    public static int f(float[] values) {
        int vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, values, GL_STATIC_DRAW);
        return vertexBuffer;
    }

}
