#define PROCESSING_TEXLIGHT_SHADER
varying vec2 vN;

uniform mat4 modelview;
uniform mat3 normalMatrix;
uniform mat4 transform;

attribute vec4 vertex;
attribute vec3 normal;

void main() {

    vec4 p = vec4( vertex);

    vec3 e = normalize( vec3( modelview * p ) );
    vec3 n = normalize( normalMatrix * normal );

    vec3 r = reflect( e, n );

    float m = 2.0 * sqrt(
        pow( r.x, 2.0 ) +
        pow( r.y, 2.0 ) +
        pow( r.z + 1.0, 2.0 )
    );

    vN = r.xy / m + 0.5;

    gl_Position = transform  * modelview * p;

}
