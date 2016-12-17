import controlP5.ControlP5;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

public class UserInterface extends PApplet {
    PApplet pApplet;
    ControlP5 controlP51;
    ControlP5 controlP52;

    int smoothIterations;
    int soapFilmIterations;

    public boolean newWindow;

    UserInterface(PApplet pApplet) {
        super();
        this.pApplet = pApplet;
        this.newWindow = false;
        PApplet.runSketch(new String[]{this.getClass().getName()}, this);

        controlP51 = new ControlP5(pApplet);
        controlP51.setAutoDraw(false);
        controlP51.addSlider("size").plugTo(pApplet, "size").setPosition(40, 40).setSize(200, 20).setRange(160, 300).setValue(160).setColorCaptionLabel(color(20, 20, 20));
        controlP51.addSlider("extrudeDistance").plugTo(pApplet, "extrudeDistance").setPosition(40, 60).setSize(200, 20).setRange(50, 100).setValue(50).setColorCaptionLabel(color(20, 20, 20));
        controlP51.addSlider("vertexExpandDistance").plugTo(pApplet, "vertexExpandDistance").setPosition(40, 80).setSize(200, 20).setRange(51, 100).setValue(51).setColorCaptionLabel(color(20, 20, 20));
        controlP51.addSlider("chamferCornersDistance").plugTo(pApplet, "chamferCornersDistance").setPosition(40, 100).setSize(200, 20).setRange(1, 25).setValue(1).setColorCaptionLabel(color(20, 20, 20));
        controlP51.addSlider("chamferEdgesDistance").plugTo(pApplet, "chamferEdgesDistance").setPosition(40, 120).setSize(200, 20).setRange(1, 12).setValue(1).setColorCaptionLabel(color(20, 20, 20));
        controlP51.addSlider("wireFrameFacets").plugTo(pApplet, "wireFrameFacets").setPosition(40, 140).setSize(200, 20).setRange(2, 5).setValue(2).setColorCaptionLabel(color(20, 20, 20));
        controlP51.addSlider("wireFrameOffset").plugTo(pApplet, "wireFrameOffset").setPosition(40, 160).setSize(200, 20).setRange(1, 50).setValue(1).setColorCaptionLabel(color(20, 20, 20));

        setMode();
    }

    public void setup() {
        surface.setLocation(10, 10);
        controlP52 = new ControlP5(this);
        controlP52.addSlider("size").plugTo(pApplet, "size").setPosition(40, 40).setSize(200, 20).setRange(160, 300).setValue(160).setColorCaptionLabel(color(20, 20, 20));
        controlP52.addSlider("extrudeDistance").plugTo(pApplet, "extrudeDistance").setPosition(40, 60).setSize(200, 20).setRange(50, 100).setValue(50).setColorCaptionLabel(color(20, 20, 20));
        controlP52.addSlider("vertexExpandDistance").plugTo(pApplet, "vertexExpandDistance").setPosition(40, 80).setSize(200, 20).setRange(51, 100).setValue(51).setColorCaptionLabel(color(20, 20, 20));
        controlP52.addSlider("chamferCornersDistance").plugTo(pApplet, "chamferCornersDistance").setPosition(40, 100).setSize(200, 20).setRange(1, 25).setValue(1).setColorCaptionLabel(color(20, 20, 20));
        controlP52.addSlider("chamferEdgesDistance").plugTo(pApplet, "chamferEdgesDistance").setPosition(40, 120).setSize(200, 20).setRange(1, 12).setValue(1).setColorCaptionLabel(color(20, 20, 20));
        controlP52.addSlider("wireFrameFacets").plugTo(pApplet, "wireFrameFacets").setPosition(40, 140).setSize(200, 20).setRange(2, 5).setValue(2).setColorCaptionLabel(color(20, 20, 20));
        controlP52.addSlider("wireFrameOffset").plugTo(pApplet, "wireFrameOffset").setPosition(40, 160).setSize(200, 20).setRange(1, 50).setValue(1).setColorCaptionLabel(color(20, 20, 20));

//        controlP5.addSlider("smoothIterations").setPosition(40, 180).setSize(200, 20).setRange(0, 10).setValue(0).setColorCaptionLabel(color(20,20,20));
//        controlP5.addSlider("soapFilmIterations").setPosition(40, 200).setSize(200, 20).setRange(0, 10).setValue(0).setColorCaptionLabel(color(20,20,20));
    }

    public void setMode() {
        if (newWindow) {
            surface.setVisible(true);
            controlP51.hide();
            controlP52.getController("size").setValue(controlP51.getController("size").getValue());
            controlP52.getController("extrudeDistance").setValue(controlP51.getController("extrudeDistance").getValue());
            controlP52.getController("vertexExpandDistance").setValue(controlP51.getController("vertexExpandDistance").getValue());
            controlP52.getController("chamferCornersDistance").setValue(controlP51.getController("chamferCornersDistance").getValue());
            controlP52.getController("chamferEdgesDistance").setValue(controlP51.getController("chamferEdgesDistance").getValue());
            controlP52.getController("wireFrameFacets").setValue(controlP51.getController("wireFrameFacets").getValue());
            controlP52.getController("wireFrameOffset").setValue(controlP51.getController("wireFrameOffset").getValue());

        } else {
            surface.setVisible(false);
            controlP51.show();

            controlP51.getController("size").setValue(controlP52.getController("size").getValue());
            controlP51.getController("extrudeDistance").setValue(controlP52.getController("extrudeDistance").getValue());
            controlP51.getController("vertexExpandDistance").setValue(controlP52.getController("vertexExpandDistance").getValue());
            controlP51.getController("chamferCornersDistance").setValue(controlP52.getController("chamferCornersDistance").getValue());
            controlP51.getController("chamferEdgesDistance").setValue(controlP52.getController("chamferEdgesDistance").getValue());
            controlP51.getController("wireFrameFacets").setValue(controlP52.getController("wireFrameFacets").getValue());
            controlP51.getController("wireFrameOffset").setValue(controlP52.getController("wireFrameOffset").getValue());
        }
    }

    public void settings() {
        size(400, 400);
    }

    public void draw() {
        background(0);
    }

    public void onFrontOfPeasyCam(PeasyCam peasyCam) {
        pApplet.hint(PConstants.DISABLE_DEPTH_TEST);
        peasyCam.beginHUD();
        controlP51.draw();
        peasyCam.endHUD();
        pApplet.hint(PConstants.ENABLE_DEPTH_TEST);
    }
}