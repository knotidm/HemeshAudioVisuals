
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;
import processing.opengl.PShader;
import wblut.geom.WB_Coord;
import wblut.geom.WB_Line;
import wblut.hemesh.*;
import wblut.processing.WB_Render;

import static processing.core.PConstants.NORMAL;
import static processing.core.PConstants.TRIANGLES;

class TexturedHemesh {
    private PApplet pApplet;
    PShader matCapShader;
    PImage pImage;
    PShape pShape;

    WB_Line wb_Line;
    HE_Mesh he_Mesh;
    WB_Render wb_Render;

    Integer view, he_MeshType, archimedesType, size;

//    float px, py, pz, vx, vy, vz;

//    int wireFrameFacets, smoothIterations, soapFilmIterations;
    private final float wireFrameRadius = 200;
//    float extrudeDistance,
//            vertexExpandDistance,
//            chamferCornersDistance,
//            chamferEdgesDistance,
//            wireFrameOffset,
//            strokeAlpha = 255,
//            fillAlpha = 255,
//            twistAngleFactor,
//            noiseDistance = 10;

    TexturedHemesh(PApplet pApplet, String imagePath, Integer size) {
        this.pApplet = pApplet;
        this.matCapShader = pApplet.loadShader("data/glsl/matCap_fragment.glsl", "data/glsl/matCap_vertex.glsl");
        this.pImage = pApplet.loadImage(imagePath);
        this.view = this.he_MeshType = this.archimedesType = 1;
        this.size = size;

        he_Mesh = setHemeshType();
        wb_Render = new WB_Render(pApplet);

        this.pShape = createPShapeFromHemesh(he_Mesh, pImage, false);
    }

    //region Mesh Manipulations
    HE_Mesh setHemeshType() {
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
                .setRadius(size)
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
                return new HE_Mesh(hec_Tetrahedron);
            case 2:
                return new HE_Mesh(hec_Octahedron);
            case 3:
                return new HE_Mesh(hec_Icosahedron);
            case 4:
                return new HE_Mesh(hec_Dodecahedron);
            case 5:
                return new HE_Mesh(hec_Sphere);
            case 6:
                return new HE_Mesh(hec_Geodestic);
            case 9:
                return new HE_Mesh(hec_SuperDuper);
            case 0:
                return new HE_Mesh(hec_Archimedes);
        }
        return null;
    }

    public void modify(UserInterface userInterface) {
        HEM_Extrude hem_Extrude = new HEM_Extrude()
                .setDistance(userInterface.extrudeDistance.getValue());
        he_Mesh.modify(hem_Extrude);

        HEM_VertexExpand hem_VertexExpand = new HEM_VertexExpand()
                .setDistance(userInterface.vertexExpandDistance.getValue());
        he_Mesh.modify(hem_VertexExpand);

        HEM_ChamferCorners hem_ChamferCorners = new HEM_ChamferCorners()
                .setDistance(userInterface.chamferCornersDistance.getValue());
        he_Mesh.modify(hem_ChamferCorners);

        HEM_ChamferEdges hem_ChamferEdges = new HEM_ChamferEdges()
                .setDistance(userInterface.chamferEdgesDistance.getValue());
        he_Mesh.modify(hem_ChamferEdges);

        HEM_Wireframe hem_Wireframe = new HEM_Wireframe()
                .setStrutRadius(wireFrameRadius)
                .setStrutFacets((int) userInterface.wireFrameFacets.getValue())
                .setMaximumStrutOffset(userInterface.wireFrameOffset.getValue());
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
                wb_Render.drawFaces(he_Mesh, pImage);
                break;
            case 2:
                pApplet.noStroke();
                wb_Render.drawFacesSmooth(he_Mesh, pImage);
                break;
            case 3:
                pApplet.stroke(255, 0, 0);
                pApplet.strokeWeight(0.5f);
                wb_Render.drawEdges(he_Mesh);
                break;
            case 4:
                pApplet.stroke(0, 255, 0);
                wb_Render.drawFaces(he_Mesh, pImage);
                wb_Render.drawFaceNormals(20, he_Mesh);
                break;
            case 5:
                pApplet.stroke(0, 255, 0);
                wb_Render.drawHalfedges(20, he_Mesh);
                break;
            case 6:
                pApplet.stroke(0, 255, 0);
                pApplet.fill(255, 0, 0);
                wb_Render.drawFaces(he_Mesh, pImage);
                wb_Render.drawVertexNormals(30, he_Mesh);
        }
    }
    //endregion

    PShape createPShapeFromHemesh(HE_Mesh he_Mesh, PImage pImage, boolean perVertexNormals) {
        he_Mesh.triangulate();
        int[][] facesAsInt = he_Mesh.getFacesAsInt();
        float[][] verticesAsFloat = he_Mesh.getVerticesAsFloat();
        HE_Face[] facesAsArray = he_Mesh.getFacesAsArray();
        WB_Coord faceNormal = null;
        WB_Coord[] vertexNormals = null;
        if (perVertexNormals) {
            vertexNormals = he_Mesh.getVertexNormals();
        }

        PShape pShape = pApplet.createShape();
        pShape.beginShape(TRIANGLES);

        for (int i = 0; i < facesAsInt.length; i++) {

            if (!perVertexNormals) {
                faceNormal = facesAsArray[i].getFaceNormal();
            }

            pShape.fill(facesAsArray[i].getLabel());

            for (int j = 0; j < 3; j++) {
                int index = facesAsInt[i][j];
                float[] vertexHemesh = verticesAsFloat[index];
                if (perVertexNormals) {
                    faceNormal = vertexNormals[index];
                }
                pShape.normal(faceNormal.xf(), faceNormal.yf(), faceNormal.zf());
                pShape.vertex(vertexHemesh[0], vertexHemesh[1], vertexHemesh[2]);
            }
        }
        pShape.endShape();
        addTextureUV(pShape, pImage);
        return pShape;
    }

    private void addTextureUV(PShape pShape, PImage pImage) {
        pShape.setStroke(false);
        pShape.setTexture(pImage);
        pShape.setTextureMode(NORMAL);
        for (int i = 0; i < pShape.getVertexCount(); i++) {
            PVector v = pShape.getVertex(i);
            pShape.setTextureUV(i, PApplet.map(v.x, 0, pApplet.width, 0, 1), PApplet.map(v.y, 0, pApplet.height, 0, 1));
        }
    }
}