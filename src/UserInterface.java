import controlP5.ControlP5;
import controlP5.Slider;
import processing.core.PApplet;

public class UserInterface extends PApplet {
    PApplet pApplet;
    //    OscP5 oscP5;
//    NetAddress netAddress;
    ControlP5 controlP5;

    public Slider size;
    public Slider extrudeDistance;
    public Slider vertexExpandDistance;
    public Slider chamferCornersDistance;
    public Slider chamferEdgesDistance;
    public Slider wireFrameFacets;
    public Slider wireFrameOffset;
    int smoothIterations;
    int soapFilmIterations;

    UserInterface(PApplet pApplet) {
        super();
        this.pApplet = pApplet;
        PApplet.runSketch(new String[]{this.getClass().getName()}, this);
    }

    public void setup() {
        surface.setLocation(10, 10);

//        oscP5 = new OscP5(this, 10001);
//        netAddress = new NetAddress("localhost", 10000);
        controlP5 = new ControlP5(this);
        size = controlP5.addSlider("size").plugTo(pApplet, "size").setPosition(40, 40).setSize(200, 20).setRange(160, 300).setValue(160).setColorCaptionLabel(color(20, 20, 20));
        extrudeDistance = controlP5.addSlider("extrudeDistance").plugTo(pApplet, "extrudeDistance").setPosition(40, 60).setSize(200, 20).setRange(50, 100).setValue(50).setColorCaptionLabel(color(20, 20, 20));
        vertexExpandDistance = controlP5.addSlider("vertexExpandDistance").plugTo(pApplet, "vertexExpandDistance").setPosition(40, 80).setSize(200, 20).setRange(51, 100).setValue(51).setColorCaptionLabel(color(20, 20, 20));
        chamferCornersDistance = controlP5.addSlider("chamferCornersDistance").plugTo(pApplet, "chamferCornersDistance").setPosition(40, 100).setSize(200, 20).setRange(1, 25).setValue(1).setColorCaptionLabel(color(20, 20, 20));
        chamferEdgesDistance = controlP5.addSlider("chamferEdgesDistance").plugTo(pApplet, "chamferEdgesDistance").setPosition(40, 120).setSize(200, 20).setRange(1, 12).setValue(1).setColorCaptionLabel(color(20, 20, 20));
        wireFrameFacets = controlP5.addSlider("wireFrameFacets").plugTo(pApplet, "wireFrameFacets").setPosition(40, 140).setSize(200, 20).setRange(2, 5).setValue(2).setColorCaptionLabel(color(20, 20, 20));
        wireFrameOffset = controlP5.addSlider("wireFrameOffset").plugTo(pApplet, "wireFrameOffset").setPosition(40, 160).setSize(200, 20).setRange(1, 50).setValue(1).setColorCaptionLabel(color(20, 20, 20));
//        controlP5.addSlider("smoothIterations").setPosition(40, 180).setSize(200, 20).setRange(0, 10).setValue(0).setColorCaptionLabel(color(20,20,20));
//        controlP5.addSlider("soapFilmIterations").setPosition(40, 200).setSize(200, 20).setRange(0, 10).setValue(0).setColorCaptionLabel(color(20,20,20));
    }

//    public void draw() {
//        OscMessage oscMessage = new OscMessage("/GUI");
//        oscMessage.add(size);
//        oscMessage.add(extrudeDistance);
//        oscMessage.add(vertexExpandDistance);
//        oscMessage.add(chamferCornersDistance);
//        oscMessage.add(chamferEdgesDistance);
//        oscMessage.add(wireFrameFacets);
//        oscMessage.add(wireFrameOffset);
//        oscMessage.add(smoothIterations);
//        oscMessage.add(soapFilmIterations);
//        oscP5.send(oscMessage, netAddress);
//    }

    public void settings() {
        size(400, 400);
    }

    public void draw() {
        background(0);
    }
}