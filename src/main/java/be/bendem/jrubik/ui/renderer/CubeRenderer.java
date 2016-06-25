package be.bendem.jrubik.ui.renderer;

import be.bendem.jrubik.core.Color;
import be.bendem.jrubik.core.Cube;
import be.bendem.jrubik.core.Face;
import be.bendem.jrubik.core.Orientation;
import be.bendem.jrubik.core.Position;
import be.bendem.jrubik.ui.State;
import be.bendem.jrubik.ui.utils.Point;
import be.bendem.jrubik.ui.utils.Shader;
import be.bendem.jrubik.ui.utils.Shaders;
import be.bendem.jrubik.ui.utils.Shapes;
import org.lwjgl.system.MemoryUtil;

import java.util.Map;

import static org.lwjgl.opengles.GLES20.*;

public class CubeRenderer implements Renderer<Cube> {

    private static final float RUBIK_SIZE = 1f;
    private static final float GAP = 0.01f;
    private static final float CUBE_SIZE = (RUBIK_SIZE - GAP * 2) / 3;
    private static final float CUBE_NEGATIVE_ORIGIN = CUBE_SIZE * -1.5f - GAP;
    private static final float CUBE_NEGATIVE_ORIGIN2 = CUBE_SIZE * -0.5f;
    private static final float CUBE_POSITIVE_ORIGIN = CUBE_SIZE * 0.5f + GAP;

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
    public void render(State state, Cube cube) {
        float[] vertices = Shapes.rect(normalizePosition(cube.getPosition()), CUBE_SIZE);
        float[] colors = applyOrientation(cube.getColors(), cube.getOrientation());

        // TODO Move to #init

        glUseProgram(program);

        glUniformMatrix4fv(matrix, false, state.computeMvpMatrix());

        // bind parameter 0 to vertices array
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, MemoryUtil.NULL);

        // bind parameter 1 to colors array
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);
        glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, MemoryUtil.NULL);

        // Draw the triangle
        glDrawArrays(GL_TRIANGLES, 0, vertices.length / 3);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

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

    private float[] applyOrientation(Map<Face, Color> colors, Orientation orientation) {
        // a face: 2 triangles, a triangle: 3 points, a point: 3 colors
        // order of the output array: Left, Back, Bottom, Front, Right, Top
        float[] out = new float[18 * 6];

        colors.forEach((face, color) -> {
            int i = getIndexForOrientation(face, orientation);
            float[] colorArray = color.array();
            for (int triangle = 0; triangle < 6; triangle++) {
                System.arraycopy(colorArray, 0, out, i + 3 * triangle, 3);
            }
        });

        return out;
    }

    private int getIndexForOrientation(Face face, Orientation orientation) {
        return face.ordinal() * 18;
    }

    @Override
    public void close() {
        glDeleteBuffers(vertexBuffer);
        glDeleteBuffers(colorBuffer);
        glDeleteProgram(program);
    }

}
