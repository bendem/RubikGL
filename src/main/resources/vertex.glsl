#version 330 core

layout(location = 0) in vec3 vertexPosition_modelspace;
layout(location = 1) in vec3 vertexColor;

out vec3 fragmentColor;
uniform mat4 mvp;
uniform vec3 rotationAngles;

void main() {
    vec3 point = vertexPosition_modelspace;

    // Rotations
    if (rotationAngles.x != 0) {
        float angle = rotationAngles.x;
        point = mat3(
            1,          0,           0,
            0, cos(angle), -sin(angle),
            0, sin(angle),  cos(angle)) * point;
    } else if (rotationAngles.y != 0) {
        float angle = rotationAngles.y;
        point = mat3(
             cos(angle), 0, sin(angle),
                      0, 1,          0,
            -sin(angle), 0, cos(angle)) * point;
    } else if (rotationAngles.z != 0) {
        float angle = rotationAngles.z;
        point = mat3(
            cos(angle), -sin(angle), 0,
            sin(angle),  cos(angle), 0,
                     0,           0, 1) * point;
    }

    // Output position of the vertex, in clip space (camera rotation)
    gl_Position =  mvp * vec4(point, 1);

    // The color of each vertex will be interpolated
    // to produce the color of each fragment
    fragmentColor = vertexColor;
}
