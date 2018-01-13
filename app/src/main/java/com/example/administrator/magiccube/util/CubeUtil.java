package com.example.administrator.magiccube.util;

import android.opengl.GLU;

import com.example.administrator.magiccube.MagicCube;
import com.example.administrator.magiccube.MagicCubeRender;
import com.example.administrator.magiccube.MagicCubeRotate;
import com.example.administrator.magiccube.MagicCubeText;
import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.Cube;
import com.example.administrator.magiccube.entity.CubesPlane;
import com.example.administrator.magiccube.entity.GLText;
import com.example.administrator.magiccube.entity.Square;
import com.example.administrator.magiccube.entity.Triangle;
import com.example.administrator.magiccube.entity.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class CubeUtil {

    private static float cubeUnitSize = 1;
    public static int centerX = 0;
    public static int centerY = 0;
    public static int centerZ = 0;
    private static Vertex centerVertex;

    public static void initMagicCube(List<Cube> allCubes) {
        centerVertex = new Vertex();
        centerVertex.setX(centerX);
        centerVertex.setY(centerY);
        centerVertex.setZ(centerZ);
        createAllCubes(allCubes);
    }

    public static void createAllCubes(List<Cube> allCubes) {
        Cube centerCube = createCenterCubeByCenterPoint();
        allCubes.add(centerCube);

        Cube cube_front = new Cube();
        cube_front.copyCube(centerCube, cube_front);
        cube_front.frontMove(cube_front, cubeUnitSize);
        List<Integer> faces5 = new ArrayList<>();
        faces5.add(Const.FRONT_FACE);
        cube_front.setFaces(faces5);
        allCubes.add(cube_front);

        Cube cube_left_front = new Cube();
        cube_left_front.copyCube(centerCube, cube_left_front);
        cube_left_front.leftMove(cube_left_front, cubeUnitSize);
        cube_left_front.frontMove(cube_left_front, cubeUnitSize);
        List<Integer> faces11 = new ArrayList<>();
        faces11.add(Const.LEFT_FACE);
        faces11.add(Const.FRONT_FACE);
        cube_left_front.setFaces(faces11);
        allCubes.add(cube_left_front);

        Cube cube_down_left_front = new Cube();
        cube_down_left_front.copyCube(centerCube, cube_down_left_front);
        cube_down_left_front.downMove(cube_down_left_front, cubeUnitSize);
        cube_down_left_front.leftMove(cube_down_left_front, cubeUnitSize);
        cube_down_left_front.frontMove(cube_down_left_front, cubeUnitSize);
        List<Integer> faces22 = new ArrayList<>();
        faces22.add(Const.BOTTOM_FACE);
        faces22.add(Const.LEFT_FACE);
        faces22.add(Const.FRONT_FACE);
        cube_down_left_front.setFaces(faces22);
        allCubes.add(cube_down_left_front);

        allCubes.add(createCubeByRotate(cube_front, Const.RIGHT_ANGLE, Const.X_AXIS));
        allCubes.add(createCubeByRotate(cube_front, -Const.RIGHT_ANGLE, Const.X_AXIS));

        Cube topRotateCube = createCubeByRotate(cube_left_front, -Const.RIGHT_ANGLE, Const.Z_AXIS);
        Cube bottomRotateCube = createCubeByRotate(cube_left_front, Const.RIGHT_ANGLE, Const.Z_AXIS);
        Cube topRotateCube2 = createCubeByRotate(cube_down_left_front, -Const.RIGHT_ANGLE, Const.Z_AXIS);

        allCubes.add(topRotateCube);
        allCubes.add(bottomRotateCube);
        allCubes.add(topRotateCube2);

        for (float a = Const.RIGHT_ANGLE; a <= 270f; a = a + Const.RIGHT_ANGLE) {
            allCubes.add(createCubeByRotate(cube_front, a, Const.Y_AXIS));
            allCubes.add(createCubeByRotate(cube_left_front, a, Const.Y_AXIS));
            allCubes.add(createCubeByRotate(cube_down_left_front, a, Const.Y_AXIS));
            allCubes.add(createCubeByRotate(topRotateCube, a, Const.Y_AXIS));
            allCubes.add(createCubeByRotate(bottomRotateCube, a, Const.Y_AXIS));
            allCubes.add(createCubeByRotate(topRotateCube2, a, Const.Y_AXIS));
        }
    }

    private static void repairColorAfterRotate(Cube origin, Cube dest) {
        for (Square squareOrigin : origin.getPlanes()) {
            for (Square squareDest : dest.getPlanes()) {
                if (squareDest.getOriginFace() == squareOrigin.getOriginFace()) {
                    squareDest.setColor(squareOrigin.getColor());
                    Vertex vertexOrigin = squareOrigin.getTriangles().get(0).getVertices().get(0);
                    for (Triangle triangle : squareDest.getTriangles()) {
                        for (Vertex vertexDest : triangle.getVertices()) {
                            vertexDest.setColorA(vertexOrigin.getColorA());
                            vertexDest.setColorR(vertexOrigin.getColorR());
                            vertexDest.setColorG(vertexOrigin.getColorG());
                            vertexDest.setColorB(vertexOrigin.getColorB());
                        }
                    }
                }
            }
        }
    }

    private static Cube createCubeByRotate(Cube cubeOrigin, float angle, int axis) {
        Cube cube = new Cube();
        cubeOrigin.copyCube(cubeOrigin, cube);
        MagicCubeRotate.rotateCubes(Arrays.asList(cube), axis, angle);
        MagicCube.updateCubesFaces(Arrays.asList(cube), axis, angle);
        repairColorAfterRotate(cubeOrigin, cube);
        return cube;
    }

    public static Square createSquareByRotate(Square origin, float angle, int axis, float r, float g, float b, float a, int color) {
        Square dest = new Square();
        origin.copySquare(origin, dest);
        MagicCubeRotate.rotateSquares(Arrays.asList(dest), axis, angle);
        MagicCube.updateSquaresFaces(Arrays.asList(dest), axis, angle);
        for (Triangle triangle : dest.getTriangles()) {
            for (Vertex vertex : triangle.getVertices()) {
                vertex.setColorA(a);
                vertex.setColorR(r);
                vertex.setColorG(g);
                vertex.setColorB(b);
            }
        }
        dest.setColor(color);
        return dest;
    }

    public static Cube createCenterCubeByCenterPoint() {
        Cube cube = new Cube();
        cube.setPlanes(new ArrayList<Square>());
        cube.setFaces(new ArrayList<Integer>());
        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        Vertex vertex3 = new Vertex();
        Vertex vertex4 = new Vertex();
        vertex1.copyVertex(centerVertex, vertex1);
        vertex1.frontMove(vertex1, cubeUnitSize * 0.5f);
        vertex1.upMove(vertex1, cubeUnitSize * 0.5f);
        vertex1.leftMove(vertex1, cubeUnitSize * 0.5f);
        vertex2.copyVertex(vertex1, vertex2);
        vertex2.mirrorX(vertex2);
        vertex3.copyVertex(vertex1, vertex3);
        vertex3.mirrorY(vertex3);
        vertex4.copyVertex(vertex3, vertex4);
        vertex4.mirrorX(vertex4);

        Square square_front = new Square();
        fillColors(Const.RGBA_RED_R, Const.RGBA_RED_G, Const.RGBA_RED_B, Const.RGBA_DEFAULT_A, vertex1, vertex2, vertex3, vertex4);
        square_front.setColor(Const.RED);
        Vertex vertex11 = new Vertex();
        Vertex vertex22 = new Vertex();
        Vertex vertex33 = new Vertex();
        Vertex vertex44 = new Vertex();
        vertex11.copyVertex(vertex1, vertex11);
        vertex22.copyVertex(vertex2, vertex22);
        vertex33.copyVertex(vertex3, vertex33);
        vertex44.copyVertex(vertex4, vertex44);
        square_front.setVertex1(vertex11);
        square_front.setVertex2(vertex22);
        square_front.setVertex3(vertex33);
        square_front.setVertex4(vertex44);
        square_front.fillTrianglesByVertices(Const.FRONT_FACE);
        square_front.setOriginFace(Const.FRONT_FACE);
        cube.getPlanes().add(square_front);

        cube.getPlanes().add(createSquareByRotate(square_front, -180f, Const.X_AXIS, Const.RGBA_ORANGE_R, Const.RGBA_ORANGE_G, Const.RGBA_ORANGE_B, Const.RGBA_DEFAULT_A, Const.ORANGE));
        cube.getPlanes().add(createSquareByRotate(square_front, Const.RIGHT_ANGLE, Const.X_AXIS, Const.RGBA_GREEN_R, Const.RGBA_GREEN_G, Const.RGBA_GREEN_B, Const.RGBA_DEFAULT_A, Const.GREEN));
        cube.getPlanes().add(createSquareByRotate(square_front, -Const.RIGHT_ANGLE, Const.X_AXIS, Const.RGBA_BLUE_R, Const.RGBA_BLUE_G, Const.RGBA_BLUE_B, Const.RGBA_DEFAULT_A, Const.BLUE));
        cube.getPlanes().add(createSquareByRotate(square_front, Const.RIGHT_ANGLE, Const.Y_AXIS, Const.RGBA_YELLOW_R, Const.RGBA_YELLOW_G, Const.RGBA_YELLOW_B, Const.RGBA_DEFAULT_A, Const.YELLOW));
        cube.getPlanes().add(createSquareByRotate(square_front, -Const.RIGHT_ANGLE, Const.Y_AXIS, Const.RGBA_WHITE_R, Const.RGBA_WHITE_G, Const.RGBA_WHITE_B, Const.RGBA_DEFAULT_A, Const.WHITE));


        return cube;
    }

    private static void fillColors(float r, float g, float b, float a, Vertex vertex1, Vertex vertex2, Vertex vertex3, Vertex vertex4) {
        vertex1.setColorR(r);
        vertex1.setColorG(g);
        vertex1.setColorB(b);
        vertex1.setColorA(a);
        vertex2.copyColor(vertex1, vertex2);
        vertex3.copyColor(vertex1, vertex3);
        vertex4.copyColor(vertex1, vertex4);
    }


    public static Map<Integer, List<Integer>> isPointInSquare(float winX, float winY) {
        Map<Integer, List<Integer>> retMap = new HashMap<>();
        Map<Integer, CubesPlane> cubesPlaneMap = MagicCube.getSingleInstance().cubesPlaneMapShow;
        MagicCubeText magicCubeText = MagicCube.getSingleInstance().magicCubeText;
        testText(winX, winY, magicCubeText, retMap);
        if (retMap.size() != 0) {
            return retMap;
        }
        testCube(winX, winY, cubesPlaneMap, retMap);
        return retMap;
    }

    public static void testCube(float winX,float winY, Map<Integer, CubesPlane> cubesPlaneMap, Map<Integer, List<Integer>> retMap) {
        float[] r1 = new float[4];
        float[] r2 = new float[4];
        MatrixUtil.setCubeMatrix();
        GLU.gluUnProject(winX, MagicCubeRender.viewPort[3] - winY, 0f, MatrixUtil.getModelViewMatrix(), 0, MatrixUtil.getProjectionMatrix(), 0, MagicCubeRender.viewPort, 0, r1, 0);
        GLU.gluUnProject(winX, MagicCubeRender.viewPort[3] - winY, 1f, MatrixUtil.getModelViewMatrix(), 0, MatrixUtil.getProjectionMatrix(), 0, MagicCubeRender.viewPort, 0, r2, 0);
        for (int i = 0; i < 3; i++) {
            r1[i] /= r1[3];
            r2[i] /= r2[3];
        }

        for (Integer key : cubesPlaneMap.keySet()) {
            if (key == Const.FRONT_FACE || key == Const.RIGHT_FACE || key == Const.TOP_FACE) {
                List<Cube> cubes = cubesPlaneMap.get(key).getCubes();
                for (Cube cube : cubes) {
                    for (Square square : cube.getPlanes()) {
                        if (square.getOriginFace() != key) {
                            continue;
                        }
                        for (Triangle triangle : square.getTriangles()) {
                            TriangleV t = new TriangleV();
                            List<Vertex> vertices = triangle.getVertices();
                            t.V0[0] = vertices.get(0).getX();
                            t.V0[1] = vertices.get(0).getY();
                            t.V0[2] = vertices.get(0).getZ();

                            t.V1[0] = vertices.get(1).getX();
                            t.V1[1] = vertices.get(1).getY();
                            t.V1[2] = vertices.get(1).getZ();

                            t.V2[0] = vertices.get(2).getX();
                            t.V2[1] = vertices.get(2).getY();
                            t.V2[2] = vertices.get(2).getZ();
                            float[] i = new float[3];
                            int result = intersectRayAndTriangle(r2, r1, t, i);
                            if (result == 1) {
                                retMap.put(key, cube.getFaces());
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void testText(float winX,float winY , MagicCubeText magicCubeText, Map<Integer, List<Integer>> retMap) {
        float[] r1 = new float[4];
        float[] r2 = new float[4];
        MatrixUtil.setGLTextMatrix();
        GLU.gluUnProject(winX, MagicCubeRender.viewPort[3] - winY, 0f, MatrixUtil.getModelViewMatrix(), 0, MatrixUtil.getProjectionMatrix(), 0, MagicCubeRender.viewPort, 0, r1, 0);
        GLU.gluUnProject(winX, MagicCubeRender.viewPort[3] - winY, 1f, MatrixUtil.getModelViewMatrix(), 0, MatrixUtil.getProjectionMatrix(), 0, MagicCubeRender.viewPort, 0, r2, 0);
        for (int i = 0; i < 3; i++) {
            r1[i] /= r1[3];
            r2[i] /= r2[3];
        }
        float[] i = new float[3];
        GLText autoRotateTxt = magicCubeText.autoRotateTxt;
        GLText randomDisturb = magicCubeText.randomDisturb;
        creteTriangleV(autoRotateTxt.getTriangle1());
        int result = intersectRayAndTriangle(r2, r1, creteTriangleV(autoRotateTxt.getTriangle1()), i);
        if (result == 1) {
            retMap.put(Const.AUTO_ROTATE_MSG, new ArrayList<Integer>());
        }
        result = intersectRayAndTriangle(r2, r1, creteTriangleV(autoRotateTxt.getTriangle2()), i);
        if (result == 1) {
            retMap.put(Const.AUTO_ROTATE_MSG, new ArrayList<Integer>());
        }
        result = intersectRayAndTriangle(r2, r1, creteTriangleV(randomDisturb.getTriangle1()), i);
        if (result == 1) {
            retMap.put(Const.RANDOM_DISTURB_MSG, new ArrayList<Integer>());
        }
        result = intersectRayAndTriangle(r2, r1, creteTriangleV(randomDisturb.getTriangle2()), i);
        if (result == 1) {
            retMap.put(Const.RANDOM_DISTURB_MSG, new ArrayList<Integer>());
        }
    }

    private static TriangleV creteTriangleV(float[] t) {
        TriangleV triangleV = new TriangleV();
        triangleV.V0[0] = t[0];
        triangleV.V0[1] = t[1];
        triangleV.V0[2] = t[2];

        triangleV.V1[0] = t[3];
        triangleV.V1[1] = t[4];
        triangleV.V1[2] = t[5];

        triangleV.V2[0] = t[6];
        triangleV.V2[1] = t[7];
        triangleV.V2[2] = t[8];
        return triangleV;
    }

    public static int intersectRayAndTriangle(float[] farCoords, float[] nearCoords, TriangleV T, float[] I) {
        float[] u, v, n;             // triangle vectors
        float[] dir, w0, w;          // ray vectors
        float r, a, b;             // params to calc ray-plane intersect
        // get triangle edge vectors and plane normal
        u = Vector.minus(T.V1, T.V0);
        v = Vector.minus(T.V2, T.V0);
        n = Vector.crossProduct(u, v);             // cross product
        if (Arrays.equals(n, new float[]{0.0f, 0.0f, 0.0f})) {           // triangle is degenerate
            return -1;                 // do not deal with this case
        }
        dir = Vector.minus(farCoords, nearCoords);             // ray direction vector
        w0 = Vector.minus(nearCoords, T.V0);
        a = -Vector.dot(n, w0);
        b = Vector.dot(n, dir);
        if (Math.abs(b) < Const.SMALL_NUM) {     // ray is parallel to triangle plane
            if (a == 0) {                // ray lies in triangle plane
                return 2;
            } else {
                return 0;             // ray disjoint from plane
            }
        }
        // get intersect point of ray with triangle plane
        r = a / b;
        if (r < 0.0f) {                   // ray goes away from triangle
            return 0;                  // => no intersect
        }
        float[] tempI = Vector.addition(nearCoords, Vector.scalarProduct(r, dir));           // intersect point of ray and plane
        I[0] = tempI[0];
        I[1] = tempI[1];
        I[2] = tempI[2];
        // is I inside T?
        float uu, uv, vv, wu, wv, D;
        uu = Vector.dot(u, u);
        uv = Vector.dot(u, v);
        vv = Vector.dot(v, v);
        w = Vector.minus(I, T.V0);
        wu = Vector.dot(w, u);
        wv = Vector.dot(w, v);
        D = (uv * uv) - (uu * vv);
        // get and test parametric coords
        float s, t;
        s = ((uv * wv) - (vv * wu)) / D;
        if (s < 0.0f || s > 1.0f)        // I is outside T
            return 0;
        t = (uv * wu - uu * wv) / D;
        if (t < 0.0f || (s + t) > 1.0f)  // I is outside T
            return 0;

        return 1;                      // I is in T
    }

    public static void fillCubesPlaneByCubes(List<Cube> cubes, Map<Integer, CubesPlane> cubesPlaneMap) {
        CubesPlane allCubesPlane = new CubesPlane();
        allCubesPlane.setFace(Const.CENTER_FACE);
        allCubesPlane.setCubes(cubes);
        cubesPlaneMap.put(Const.CENTER_FACE, allCubesPlane);
        for (Cube cube : cubes) {
            List<Integer> faces = cube.getFaces();
            for (Integer face : faces) {
                int f = face;
                CubesPlane cubesPlane = cubesPlaneMap.get(f);
                if (cubesPlane == null) {
                    cubesPlane = new CubesPlane();
                    cubesPlane.setCubes(new ArrayList<Cube>());
                }
                cubesPlane.setFace(face);
                cubesPlane.getCubes().add(cube);
                cubesPlaneMap.put(f, cubesPlane);
            }
        }
    }

    public static void copyCubesPlaneMap(Map<Integer, CubesPlane> src, Map<Integer, CubesPlane> dest) {
        for (Integer key : src.keySet()) {
            CubesPlane srcCubesPlane = src.get(key);
            CubesPlane destCubesPlane = new CubesPlane();
            destCubesPlane.copyCubesPlane(srcCubesPlane, destCubesPlane);
            dest.put(key, destCubesPlane);
        }
    }

    public static void copyCubes(List<Cube> cubesSrc, List<Cube> cubesDest) {
        boolean isAdd = false;
        if (cubesDest.size() == 0) isAdd = true;
        for (int i = 0; i < cubesSrc.size(); i++) {
            Cube cubeShow = null;
            Cube cube = cubesSrc.get(i);
            if (isAdd) {
                cubeShow = new Cube();
            } else {
                cubeShow = cubesDest.get(i);
            }
            cubeShow.copyCube(cube, cubeShow);
            if (isAdd) {
                cubesDest.add(cubeShow);
            }
        }
    }
}

class Vector {
    // dot product 内积
    public static float dot(float[] u, float[] v) {
        return ((u[X] * v[X]) + (u[Y] * v[Y]) + (u[Z] * v[Z]));
    }

    public static float[] minus(float[] u, float[] v) {
        return new float[]{u[X] - v[X], u[Y] - v[Y], u[Z] - v[Z]};
    }

    public static float[] addition(float[] u, float[] v) {
        return new float[]{u[X] + v[X], u[Y] + v[Y], u[Z] + v[Z]};
    }

    //scalar product 缩放
    public static float[] scalarProduct(float r, float[] u) {
        return new float[]{u[X] * r, u[Y] * r, u[Z] * r};
    }

    // cross product 外积
    public static float[] crossProduct(float[] u, float[] v) {
        return new float[]{(u[Y] * v[Z]) - (u[Z] * v[Y]), (u[Z] * v[X]) - (u[X] * v[Z]), (u[X] * v[Y]) - (u[Y] * v[X])};
    }

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
}

class TriangleV {
    public float[] V0 = new float[3];
    public float[] V1 = new float[3];
    public float[] V2 = new float[3];

};