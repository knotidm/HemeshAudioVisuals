import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import peasy.PeasyCam;
import processing.core.PApplet;
import wblut.geom.WB_Line;
import wblut.hemesh.*;
import wblut.processing.WB_Render;

public class HemeshAudioVisuals extends PApplet {
    OscP5 oscP5;
    NetAddress netAddress;
    PeasyCam peasyCam;
    WB_Line wb_Line;
    HE_Mesh he_Mesh;
    WB_Render wb_Render;

    //region Fields
    int view;
    int archimedesType, he_MeshType;
    float px, py, pz, vx, vy, vz;
    int wireFrameFacets, smoothIterations, soapFilmIterations;
    final float wireFrameRadius = 200;
    float   size,
            extrudeDistance,
            vertexExpandDistance,
            chamferCornersDistance,
            chamferEdgesDistance,
            wireFrameOffset,
            strokeAlpha = 255,
            fillAlpha = 255,
            twistAngleFactor,
            noiseDistance = 10;

    public void oscEvent(OscMessage theOscMessage) {
        theOscMessage.print(); // just for debuging

        if (theOscMessage.checkAddrPattern("/GUI")) {
            if (theOscMessage.checkTypetag("fffffifii")) { // f is Float, i is Int
                size = theOscMessage.get(0).floatValue();
                extrudeDistance = theOscMessage.get(1).floatValue();
                vertexExpandDistance = theOscMessage.get(2).floatValue();
                chamferCornersDistance = theOscMessage.get(3).floatValue();
                chamferEdgesDistance = theOscMessage.get(4).floatValue();
                wireFrameFacets = theOscMessage.get(5).intValue();
                wireFrameOffset = theOscMessage.get(6).floatValue();
                smoothIterations = theOscMessage.get(7).intValue();
                soapFilmIterations = theOscMessage.get(8).intValue();
            }
        }

        if (theOscMessage.checkAddrPattern("/LHighHz")) {
            if (theOscMessage.checkTypetag("f")) {
            }
        }

        if (theOscMessage.checkAddrPattern("/LMidHz")) {
            if (theOscMessage.checkTypetag("f")) {
            }
        }

        if (theOscMessage.checkAddrPattern("/LLowHz")) {
            if (theOscMessage.checkTypetag("f")) {
            }
        }
    }
    //endregion

    //region Processing Sketch Structure
    public void settings() { // You must to specify size here
        size(800, 600, P3D);
        smooth();
    }

    public void setup() {
        oscP5 = new OscP5(this, 10000);
        netAddress = new NetAddress("localhost", 12000);
        peasyCam = new PeasyCam(this, 300);
        he_MeshType = 1;
        view = 1;
        archimedesType = 1;
        wb_Render = new WB_Render(this);
    }

    public void draw() {
        background(0);
        lights();
        stroke(255, 0, 0, strokeAlpha);
        fill(0, 200, 200, fillAlpha);
        createMesh();
        createModifiers();
        createDividers();
        renderMesh();
    }

    static public void main(String[] passedArgs) { // It is required
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "HemeshAudioVisuals"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
    //endregion

    //region Mesh Manipulations
    public void createMesh() {
        HEC_Archimedes hec_Archimedes = new HEC_Archimedes()
                .setType(archimedesType) //13 typ\u00f3w
                .setEdge(size);

        HEC_Tetrahedron hec_Tetrahedron = new HEC_Tetrahedron()
                .setEdge(size);

        HEC_Octahedron hec_Octahedron = new HEC_Octahedron()
                .setEdge(size);

        HEC_Icosahedron hec_Icosahedron = new HEC_Icosahedron()
                .setEdge(size);

        HEC_Dodecahedron hec_Dodecahedron = new HEC_Dodecahedron()
                .setEdge(size);

        HEC_Sphere hec_Sphere = new HEC_Sphere()
                .setRadius(50)
                .setUFacets(8)
                .setVFacets(8);

        HEC_Geodesic hec_Geodestic = new HEC_Geodesic()
                .setType(0) // 5 typ\u00f3w
                .setRadius(50)
                .setB(2)
                .setC(2);

        HEC_SuperDuper hec_SuperDuper = new HEC_SuperDuper()
                .setRadius(50)
                .setU(25)
                .setV(5)
                .setUWrap(true)
                .setVWrap(false)
                //.setDonutParameters(0, 10, 10, 10, 5, 6, 12, 12, 3, 1);
                //.setShellParameters(0, 10, 0, 0, 0, 10, 0, 0, 2, 1, 1, 5);
                .setSuperShapeParameters(4, 10, 10, 10, 4, 10, 10, 10);
        //.setGeneralParameters(0, 11, 0, 0, 13, 10, 15, 10, 4, 0, 0, 0, 5, 0.3, 2.2);
        //.setGeneralParameters(0, 10, 0, 0, 6, 10, 6, 10, 3, 0, 0, 0, 4, 0.5, 0.25);

        switch (he_MeshType) {
            case 1:
                he_Mesh = new HE_Mesh(hec_Tetrahedron);
                break;
            case 2:
                he_Mesh = new HE_Mesh(hec_Octahedron);
                break;
            case 3:
                he_Mesh = new HE_Mesh(hec_Icosahedron);
                break;
            case 4:
                he_Mesh = new HE_Mesh(hec_Dodecahedron);
                break;
            case 5:
                he_Mesh = new HE_Mesh(hec_Sphere);
                break;
            case 6:
                he_Mesh = new HE_Mesh(hec_Geodestic);
                break;
            case 9:
                he_Mesh = new HE_Mesh(hec_SuperDuper);
                break;
            case 0:
                he_Mesh = new HE_Mesh(hec_Archimedes);
                break;
        }
    }

    public void createModifiers() {
        HEM_Extrude hem_Extrude = new HEM_Extrude()
                .setDistance(extrudeDistance);
        he_Mesh.modify(hem_Extrude);

        HEM_VertexExpand hem_VertexExpand = new HEM_VertexExpand()
                .setDistance(vertexExpandDistance);
        he_Mesh.modify(hem_VertexExpand);

        HEM_ChamferCorners hem_ChamferCorners = new HEM_ChamferCorners()
                .setDistance(chamferCornersDistance);
        he_Mesh.modify(hem_ChamferCorners);

        HEM_ChamferEdges hem_ChamferEdges = new HEM_ChamferEdges()
                .setDistance(chamferEdgesDistance);
        he_Mesh.modify(hem_ChamferEdges);

        HEM_Wireframe hem_Wireframe = new HEM_Wireframe()
                .setStrutRadius(wireFrameRadius)
                .setStrutFacets(wireFrameFacets)
                .setMaximumStrutOffset(wireFrameOffset);
        he_Mesh.modify(hem_Wireframe);

//        HEM_Lattice hem_Lattice = new HEM_Lattice()
//                .setDepth(latticeDepth);
//        he_Mesh.modify(hem_Lattice);

//        HEM_Smooth hem_Smooth = new HEM_Smooth()
//                .setIterations(smoothIterations)
//                .setAutoRescale(true);
//        he_Mesh.modify(hem_Smooth);

//        HEM_Soapfilm hem_Soapfilm = new HEM_Soapfilm()
//                .setIterations(soapFilmIterations)
//                .setAutoRescale(true);
//        he_Mesh.modify(hem_Soapfilm);



//        wb_Line = new WB_Line(0, 0, 400, 0, 0, -400); // point x y z vector x y z moga byc ujemne
//
//        HEM_Twist hem_Twist = new HEM_Twist()
//                .setAngleFactor(twistAngleFactor)
//                .setTwistAxis(wb_Line);
//        he_Mesh.modify(hem_Twist);





        //        HEM_Noise hem_Noise = new HEM_Noise()
        //                .setDistance(noiseDistance);
        //        he_Mesh.modify(hem_Noise);

        //        HEM_Spherify hem_Spherify = new HEM_Spherify()
        //                .setRadius(50)
        //                .setCenter(0.5 * (mouseX - width/2), 0, 0)
        //                .setFactor(mouseY / 800.0);
        //        he_Mesh.modify(hem_Spherify);
    }

    public void createDividers() {
        //        he_Mesh.simplify(new HES_TriDec().setGoal(160));
        //
        //        HES_CatmullClark hes_CatmullClark = new HES_CatmullClark()
        //                .setKeepBoundary(true)
        //                .setKeepEdges(true);
        //
        //        he_Mesh.subdivide(hes_CatmullClark, 1);

        //        HES_DooSabin hes_DooSabin = new HES_DooSabin();
        //        hes_DooSabin.setFactors(1, 1);
        //        hes_DooSabin.setAbsolute(false);
        //        hes_DooSabin.setDistance(50);
        //        he_Mesh.subdivide(hes_DooSabin, 1);
    }

    public void renderMesh() {
        switch (view) {
            case 1:
                wb_Render.drawFaces(he_Mesh);
                break;
            case 2:
                noStroke();
                wb_Render.drawFacesSmooth(he_Mesh);
                break;
            case 3:
                stroke(255, 0, 0);
                strokeWeight(0.5f);
                wb_Render.drawEdges(he_Mesh);
                break;
            case 4:
                stroke(0, 255, 0);
                wb_Render.drawFaces(he_Mesh);
                wb_Render.drawFaceNormals(20, he_Mesh);
                break;
            case 5:
                stroke(0, 255, 0);
                wb_Render.drawHalfedges(20, he_Mesh);
                break;
            case 6:
                stroke(0, 255, 0);
                fill(255, 0, 0);
                wb_Render.drawFaces(he_Mesh);
                wb_Render.drawVertexNormals(30, he_Mesh);
        }
    }
    //endregion

    //region Keyboard Input
    public void keyPressed() {
        switch (key) {
            case '0':
                he_MeshType = 0;
                break;
            case '1':
                he_MeshType = 1;
                break;
            case '2':
                he_MeshType = 2;
                break;
            case '3':
                he_MeshType = 3;
                break;
            case '4':
                he_MeshType = 4;
                break;
            case '5':
                he_MeshType = 5;
                break;
            case '6':
                he_MeshType = 6;
                break;
            case '9':
                he_MeshType = 9;
                break;
            case '-':
                if (archimedesType != 1) archimedesType--;
                break;
            case '=':
                if (archimedesType != 13) archimedesType++;
                break;
            case ',':
                if (view != 0) view--;
                break;
            case '.':
                if (view != 7) view++;
                break;
        }
    }
    //endregion
}