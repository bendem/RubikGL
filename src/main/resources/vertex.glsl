#version 330 core

layout(location = 0) in vec3 vertexPosition_modelspace;
layout(location = 1) in vec3 vertexColor;

out vec3 fragmentColor;
uniform mat4 mvp;

void main() {
    // Output position of the vertex, in clip space : mvp * position
    gl_Position =  mvp * vec4(vertexPosition_modelspace, 1);

    // The color of each vertex will be interpolated
    // to produce the color of each fragment
    fragmentColor = vertexColor;
}
