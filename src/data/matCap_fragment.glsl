#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;

varying vec2 vN;

void main() {

    vec3 base = texture2D( texture, vN ).rgb;
    gl_FragColor = vec4( base, 1. );
}
