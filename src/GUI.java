import controlP5.ControlP5;
import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

public class GUI extends PApplet {
    OscP5 oscP5;
    NetAddress netAddress;

    ControlP5 controlP5;
    float size;
    float extrudeDistance;
    float vertexExpandDistance;
    float chamferCornersDistance;
    float chamferEdgesDistance;
    int wireFrameFacets;
    float wireFrameOffset;
    int smoothIterations;
    int soapFilmIterations;

    public void setup() {
        oscP5 = new OscP5(this, 10001);
        netAddress = new NetAddress("localhost", 10000);
        controlP5 = new ControlP5(this);
        controlP5.addSlider("size").setPosition(40, 40).setSize(200, 20).setRange(100, 300).setValue(100).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("extrudeDistance").setPosition(40, 60).setSize(200, 20).setRange(0, 100).setValue(0).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("vertexExpandDistance").setPosition(40, 80).setSize(200, 20).setRange(0, 100).setValue(0).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("chamferCornersDistance").setPosition(40, 100).setSize(200, 20).setRange(0, 100).setValue(0).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("chamferEdgesDistance").setPosition(40, 120).setSize(200, 20).setRange(0, 100).setValue(0).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("wireFrameFacets").setPosition(40, 140).setSize(200, 20).setRange(2, 10).setValue(2).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("wireFrameOffset").setPosition(40, 160).setSize(200, 20).setRange(0, 100).setValue(0).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("smoothIterations").setPosition(40, 180).setSize(200, 20).setRange(0, 10).setValue(0).setColorCaptionLabel(color(20,20,20));
        controlP5.addSlider("soapFilmIterations").setPosition(40, 200).setSize(200, 20).setRange(0, 10).setValue(1).setColorCaptionLabel(color(20,20,20));
    }

    public void draw() {
        OscMessage oscMessage = new OscMessage("/GUI");
        oscMessage.add(size);
        oscMessage.add(extrudeDistance);
        oscMessage.add(vertexExpandDistance);
        oscMessage.add(chamferCornersDistance);
        oscMessage.add(chamferEdgesDistance);
        oscMessage.add(wireFrameFacets);
        oscMessage.add(wireFrameOffset);
        oscMessage.add(smoothIterations);
        oscMessage.add(soapFilmIterations);
        oscP5.send(oscMessage, netAddress);
    }

    public void settings() {
        size(400, 400);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "GUI"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}