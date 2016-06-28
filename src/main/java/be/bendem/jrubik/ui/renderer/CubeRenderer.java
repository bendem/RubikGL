package be.bendem.jrubik.ui.renderer;

import be.bendem.jrubik.core.Color;
import be.bendem.jrubik.core.Cube;
import be.bendem.jrubik.core.Face;
import be.bendem.jrubik.core.Position;
import be.bendem.jrubik.core.Rotation;
import be.bendem.jrubik.ui.State;
import be.bendem.jrubik.ui.utils.Point;
import be.bendem.jrubik.ui.utils.Shader;
import be.bendem.jrubik.ui.utils.Shaders;
import be.bendem.jrubik.ui.utils.Shapes;
import org.lwjgl.system.MemoryUtil;

import java.util.Map;

import static org.lwjgl.opengles.GLES20.*;

public class CubeRenderer implements Renderer<Cube> {

    public static final float RUBIK_SIZE = 1f;
    public static final float GAP = 0.01f;
    public static final float CUBE_SIZE = (RUBIK_SIZE - GAP * 2) / 3;
    public static final float CUBE_NEGATIVE_ORIGIN = CUBE_SIZE * -1.5f - GAP;
    public static final float CUBE_NEGATIVE_ORIGIN2 = CUBE_SIZE * -0.5f;
    public static final float CUBE_POSITIVE_ORIGIN = CUBE_SIZE * 0.5f + GAP;

    private final float[] vertices = new float[108];
    private final float[] colors = new float[108];
    private int program;
    private int matrix;
    private int vertexBuffer;
    private int colorBuffer;

    @Override
    public void init() {
        try (Shader vertex = Shaders.fromFile(GL_VERTEX_SHADER, "vertex.glsl");
             Shader fragment = Shaders.fromFile(GL_FRAGMENT_SHADER, "fragment.glsl")) {
            program = Shaders.compile(vertex, fragment);
        }

        matrix = glGetUniformLocation(program, "mvp");

        vertexBuffer = glGenBuffers();
        colorBuffer = glGenBuffers();
    }

    @Override
    public float[] prepare(State state, Cube cube) {
        Shapes.rect(normalizePosition(cube.position()), CUBE_SIZE, vertices);
        applyRotation(cube.colors(), cube.rotation());

        return vertices;
    }

    @Override
    public void render(State state, float[] vertices) {
        glUseProgram(program);

        glUniformMatrix4fv(matrix, false, state.computeMvpMatrix());

        // bind parameter 0 to vertices array
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STREAM_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, MemoryUtil.NULL);

        // bind parameter 1 to colors array
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STREAM_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, MemoryUtil.NULL);

        glDrawArrays(GL_TRIANGLES, 0, vertices.length / 3);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    public void cleanup() {}

    private Point normalizePosition(Position position) {
        return Point.at(
            normalizeCoordinate(position.x()),
            normalizeCoordinate(position.y()),
            normalizeCoordinate(position.z()));
    }

    private float normalizeCoordinate(int c) {
        switch (c) {
        case -1: return CUBE_NEGATIVE_ORIGIN;
        case  0: return CUBE_NEGATIVE_ORIGIN2;
        case  1: return CUBE_POSITIVE_ORIGIN;
        default: throw new AssertionError("Invalid cube coordinate: " + c);
        }
    }

    private float[] applyRotation(Map<Face, Color> colorPerFace, Rotation rotation) {
        colorPerFace.forEach((face, color) -> {
            Face rotated = rotation.apply(face);
            int i = getIndexForRotation(rotated);
            color.array(colors, i);
            for (int triangle = 1; triangle < 6; triangle++) {
                System.arraycopy(colors, i, colors, i + 3 * triangle, 3);
            }
        });

        return colors;
    }

    private int getIndexForRotation(Face face) {
        return face.ordinal() * 18;
    }

    @Override
    public void close() {
        glDeleteBuffers(vertexBuffer);
        glDeleteBuffers(colorBuffer);
        glDeleteProgram(program);
    }

}
