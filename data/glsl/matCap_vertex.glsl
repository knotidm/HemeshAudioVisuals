#define PROCESSING_TEXLIGHT_SHADER
varying vec2 vectorNormal;

uniform mat4 projection;
uniform mat4 modelview;
uniform mat3 normalMatrix;

attribute vec4 vertex;
attribute vec3 normal;

void main() {
    vec3 eye = normalize( vec3( modelview * vertex ) );
    vec3 nnormal = normalize( normalMatrix * normal );
    vec3 rreflect = reflect( eye, nnormal );

    float matCapFormula = 2.0 * sqrt(
        pow( rreflect.x, 2.0 ) +
        pow( rreflect.y, 2.0 ) +
        pow( rreflect.z + 1.0, 2.0 )
    );
    vectorNormal = rreflect.xy / matCapFormula + 0.5;
    gl_Position = projection  * modelview * vertex;
}