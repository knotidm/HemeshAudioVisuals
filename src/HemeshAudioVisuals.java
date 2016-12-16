import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import peasy.PeasyCam;
import processing.core.PApplet;

public class HemeshAudioVisuals extends PApplet {
    OscP5 oscP5;
    NetAddress netAddress;
    PeasyCam peasyCam;
    UserInterface userInterface;
    TexturedHemesh texturedHemesh;

    public void oscEvent(OscMessage theOscMessage) {
        //theOscMessage.print(); // just for debuging

        if (theOscMessage.checkAddrPattern("/LHighHz")) {
            if (theOscMessage.checkTypetag("f")) {
                userInterface.wireFrameFacets.setValue(theOscMessage.get(0).floatValue() / 10);
            }
        }

        if (theOscMessage.checkAddrPattern("/RHighHz")) {
            if (theOscMessage.checkTypetag("f")) {
                userInterface.wireFrameOffset.setValue(50 - theOscMessage.get(0).floatValue());
            }
        }

        if (theOscMessage.checkAddrPattern("/LMidHz")) {
            if (theOscMessage.checkTypetag("f")) {
                userInterface.chamferCornersDistance.setValue((theOscMessage.get(0).floatValue() + 1) / 5);
            }
        }

        if (theOscMessage.checkAddrPattern("/RMidHz")) {
            if (theOscMessage.checkTypetag("f")) {
                userInterface.chamferEdgesDistance.setValue((theOscMessage.get(0).floatValue() + 1) / 10);
            }
        }

        if (theOscMessage.checkAddrPattern("/LLowHz")) {
            if (theOscMessage.checkTypetag("f")) {

                userInterface.extrudeDistance.setValue(theOscMessage.get(0).floatValue() + 50);
            }
        }

        if (theOscMessage.checkAddrPattern("/RLowHz")) {
            if (theOscMessage.checkTypetag("f")) {

                userInterface.vertexExpandDistance.setValue(theOscMessage.get(0).floatValue() + 51);
            }
        }
    }

    public void setup() {
        oscP5 = new OscP5(this, 10000);
        netAddress = new NetAddress("localhost", 12000);
        peasyCam = new PeasyCam(this, 300);
        userInterface = new UserInterface(this);
        surface.setLocation(420, 10);
        texturedHemesh = new TexturedHemesh(this, "data/image/12719.jpg", 200);
    }

    public void draw() {
        background(0);
        lights();

        texturedHemesh.he_Mesh = texturedHemesh.setHemeshType();
        texturedHemesh.modify(userInterface);
        texturedHemesh.pShape = texturedHemesh.createPShapeFromHemesh(texturedHemesh.he_Mesh, texturedHemesh.pImage, false);
        shape(texturedHemesh.pShape);
        shader(texturedHemesh.matCapShader);
    }

    public void keyPressed() {
        switch (key) {
            case '0':
                texturedHemesh.he_MeshType = 0;
                break;
            case '1':
                texturedHemesh.he_MeshType = 1;
                break;
            case '2':
                texturedHemesh.he_MeshType = 2;
                break;
            case '3':
                texturedHemesh.he_MeshType = 3;
                break;
            case '4':
                texturedHemesh.he_MeshType = 4;
                break;
            case '5':
                texturedHemesh.he_MeshType = 5;
                break;
            case '6':
                texturedHemesh.he_MeshType = 6;
                break;
            case '9':
                texturedHemesh.he_MeshType = 9;
                break;
            case '-':
                if (texturedHemesh.archimedesType != 1) texturedHemesh.archimedesType--;
                break;
            case '=':
                if (texturedHemesh.archimedesType != 13) texturedHemesh.archimedesType++;
                break;
            case ',':
                if (texturedHemesh.view != 0) texturedHemesh.view--;
                break;
            case '.':
                if (texturedHemesh.view != 7) texturedHemesh.view++;
                break;
        }
    }

    public void settings() { // You must to specify size here
        size(1280, 720, P3D);
        smooth();
    }

    static public void main(String[] passedArgs) { // It is required
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "HemeshAudioVisuals"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}