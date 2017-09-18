package com.example.administrator.magiccube;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.Cube;
import com.example.administrator.magiccube.entity.CubesPlane;
import com.example.administrator.magiccube.entity.GLText;
import com.example.administrator.magiccube.entity.Plane;
import com.example.administrator.magiccube.entity.Square;
import com.example.administrator.magiccube.entity.Triangle;
import com.example.administrator.magiccube.entity.Vertex;
import com.example.administrator.magiccube.util.BufferUtil;
import com.example.administrator.magiccube.util.CalculateUtil;
import com.example.administrator.magiccube.util.CubeUtil;
import com.example.administrator.magiccube.util.MatrixUtil;
import com.example.administrator.magiccube.util.ShaderUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class MagicCube {
    public List<Cube> cubes;
    public List<Cube> cubesShow;
    public Map<Integer, CubesPlane> cubesPlaneMapShow;
    private Map<String, Integer> lineMap;
    private List<Float> vertexList;
    private List<Float> colorList;
    private List<Float> linesList;
    private List<Float> linesColorList;
    public int mProgramHandle;
    public int mColorHandle;
    public int mPositionHandle;
    public int mMVPMatrixHandle;

    public GLSurfaceView glSurfaceView;
    public MagicCubeText magicCubeText;

    private float[] linesArray;
    private float[] linesColorArray;
    private float[] vertexArray;

    private float[] colorArray;
    private static MagicCube singleInstance;

    public static MagicCube getSingleInstance() {
        if (singleInstance == null) {
            singleInstance = new MagicCube();
        }
        return singleInstance;
    }

    private MagicCube() {

    }

    public void initMagicCube(MagicCubeSurfaceView glSurfaceView) {
        this.glSurfaceView = glSurfaceView;
        initVariable();
        initShader();
        MagicCubeGlobalValue.setInfoBeforeRandomDisturbing();
        disturbMagicCube();
        MagicCubeGlobalValue.setInfoAfterRandomDisturbing();
        fillNextStepTipInfo(-1);
        fillElementArray();
    }

    public void initVariable() {
        cubes = new ArrayList<>();
        cubesShow = new ArrayList<>();
        cubesPlaneMapShow = new HashMap<>();
        lineMap = new HashMap<>();
        vertexList = new ArrayList<>();
        colorList = new ArrayList<>();
        linesList = new ArrayList<>();
        linesColorList = new ArrayList<>();
        magicCubeText = MagicCubeText.getSingleInstance();
        magicCubeText.init();
        CubeUtil.initMagicCube(cubes);
        copyCubeList(cubes, cubesShow);
        fillCubesPlaneMapInfo(cubesShow, cubesPlaneMapShow);
    }

    public void initShader() {
        String vertexShader = ShaderUtil.loadFromAssetsFile(Const.VERTEX_FILE_NAME, glSurfaceView.getResources());
        String fragmentShader = ShaderUtil.loadFromAssetsFile(Const.FRAGMENT_FILE_NAME, glSurfaceView.getResources());
        ShaderUtil.setaColorName(Const.COLOR_NAME);
        ShaderUtil.setaPositionName(Const.POSITION_NAME);
        mProgramHandle = ShaderUtil.createProgram(vertexShader, fragmentShader);
    }

    public void draw() {
        drawCubes();
        drawText();

    }

    public void drawText() {
        magicCubeText.drawText();
    }

    public void drawCubes() {
        GLES20.glUseProgram(mProgramHandle);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, Const.MATRIX_NAME);
        mPositionHandle = GLES20.glGetAttribLocation(mProgramHandle, Const.POSITION_NAME);
        mColorHandle = GLES20.glGetAttribLocation(mProgramHandle, Const.COLOR_NAME);

        MatrixUtil.setCubeMatrix();

        GLES20.glVertexAttribPointer(mPositionHandle, Const.VERTEX_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(vertexArray));
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mColorHandle, Const.COLOR_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(colorArray));
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MatrixUtil.getFinalMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexArray.length / Const.VERTEX_ELEMENT_SIZE);
        GLES20.glVertexAttribPointer(mPositionHandle, Const.VERTEX_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(linesArray));
        GLES20.glVertexAttribPointer(mColorHandle, Const.COLOR_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(linesColorArray));
        GLES20.glLineWidth(10.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, linesArray.length / Const.VERTEX_ELEMENT_SIZE);
    }

    public Map<Integer, List<Integer>> getTouchInfo(float winX, float winY) {
        return CubeUtil.isPointInSquare(winX, winY);
    }

    public void fillElementArray() {
        vertexList.clear();
        colorList.clear();
        linesList.clear();
        linesColorList.clear();
        lineMap.clear();
        CalculateUtil.fillElementList(cubesShow, vertexList, colorList, linesList, linesColorList, lineMap);
        if (vertexArray == null) {
            linesArray = new float[linesList.size()];
            linesColorArray = new float[linesColorList.size()];
            vertexArray = new float[vertexList.size()];
            colorArray = new float[colorList.size()];
        } else if (linesArray.length != linesList.size()) {
            linesArray = new float[linesList.size()];
            linesColorArray = new float[linesColorList.size()];
        }
        for (int p = 0; p < linesList.size(); p++) {
            linesArray[p] = linesList.get(p);
        }
        for (int p = 0; p < linesColorList.size(); p++) {
            linesColorArray[p] = linesColorList.get(p);
        }
        int c = 0;
        for (int i = 0; i < vertexList.size(); i++) {
            vertexArray[i] = vertexList.get(i);
        }
        for (int i = 0; i < colorList.size(); i++) {
            colorArray[i] = colorList.get(i);
        }
    }

    public void rotateCubes(List<Cube> cubes, int axis, float angle) {
        for (Cube cube : cubes) {
            for (Vertex vertex : cube.getVertices()) {
                if (axis == Const.X_AXIS) {
                    MatrixUtil.rotateAroundX(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                } else if (axis == Const.Y_AXIS) {
                    MatrixUtil.rotateAroundY(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                } else if (axis == Const.Z_AXIS) {
                    MatrixUtil.rotateAroundZ(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                }
            }
        }
    }

    public void rotateSquares(List<Square> squares, int axis, float angle) {
        for (Square square : squares) {
            Vertex vertex1 = square.getVertex1();
            Vertex vertex2 = square.getVertex2();
            Vertex vertex3 = square.getVertex3();
            Vertex vertex4 = square.getVertex4();
            if (axis == Const.X_AXIS) {
                MatrixUtil.rotateAroundX(vertex1, vertex1.getX(), vertex1.getY(), vertex1.getZ(), angle);
                MatrixUtil.rotateAroundX(vertex2, vertex2.getX(), vertex2.getY(), vertex2.getZ(), angle);
                MatrixUtil.rotateAroundX(vertex3, vertex3.getX(), vertex3.getY(), vertex3.getZ(), angle);
                MatrixUtil.rotateAroundX(vertex4, vertex4.getX(), vertex4.getY(), vertex4.getZ(), angle);
            } else if (axis == Const.Y_AXIS) {
                MatrixUtil.rotateAroundY(vertex1, vertex1.getX(), vertex1.getY(), vertex1.getZ(), angle);
                MatrixUtil.rotateAroundY(vertex2, vertex2.getX(), vertex2.getY(), vertex2.getZ(), angle);
                MatrixUtil.rotateAroundY(vertex3, vertex3.getX(), vertex3.getY(), vertex3.getZ(), angle);
                MatrixUtil.rotateAroundY(vertex4, vertex4.getX(), vertex4.getY(), vertex4.getZ(), angle);
            } else if (axis == Const.Z_AXIS) {
                MatrixUtil.rotateAroundZ(vertex1, vertex1.getX(), vertex1.getY(), vertex1.getZ(), angle);
                MatrixUtil.rotateAroundZ(vertex2, vertex2.getX(), vertex2.getY(), vertex2.getZ(), angle);
                MatrixUtil.rotateAroundZ(vertex3, vertex3.getX(), vertex3.getY(), vertex3.getZ(), angle);
                MatrixUtil.rotateAroundZ(vertex4, vertex4.getX(), vertex4.getY(), vertex4.getZ(), angle);
            }
            for (Triangle triangle : square.getTriangles()) {
                for (Vertex vertex : triangle.getVertices()) {
                    if (axis == Const.X_AXIS) {
                        MatrixUtil.rotateAroundX(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                    } else if (axis == Const.Y_AXIS) {
                        MatrixUtil.rotateAroundY(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                    } else if (axis == Const.Z_AXIS) {
                        MatrixUtil.rotateAroundZ(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                    }
                }
            }
        }
    }


    public void copyCubeList(List<Cube> srcList, List<Cube> destList) {
        CubeUtil.copyCubes(srcList, destList);
    }

    public void fillCubesPlaneMapInfo(List<Cube> list, Map<Integer, CubesPlane> map) {
        CubeUtil.fillCubesPlaneByCubes(list, map);
    }

    public int rotateFace(int face, int axis, float angle) {
        float originAngle = 0f;
        double x = 0d;
        double y = 0d;
        if (axis == Const.X_AXIS || axis == Const.X_WHOLE_AXIS) {
            if (face == Const.LEFT_FACE || face == Const.RIGHT_FACE) {
                return face;
            } else if (face == Const.TOP_FACE) {
                originAngle = Const.RIGHT_ANGLE;
            } else if (face == Const.FRONT_FACE) {
                originAngle = 180f;
            } else if (face == Const.BOTTOM_FACE) {
                originAngle = 270f;
            }
            originAngle += angle;
            x = (double) (Const.BACK_FACE);
            y = (double) (Const.TOP_FACE);
            return MatrixUtil.rotateFomXToY(x, y, originAngle);

        } else if (axis == Const.Y_AXIS || axis == Const.Y_WHOLE_AXIS) {
            if (face == Const.TOP_FACE || face == Const.BOTTOM_FACE) {
                return face;
            } else if (face == Const.BACK_FACE) {
                originAngle = Const.RIGHT_ANGLE;
            } else if (face == Const.LEFT_FACE) {
                originAngle = 180f;
            } else if (face == Const.FRONT_FACE) {
                originAngle = 270f;
            }
            originAngle += angle;
            x = (double) (Const.RIGHT_FACE);
            y = (double) (Const.BACK_FACE);
            return MatrixUtil.rotateFomXToY(x, y, originAngle);
        } else if (axis == Const.Z_AXIS || axis == Const.Z_WHOLE_AXIS) {
            if (face == Const.FRONT_FACE || face == Const.BACK_FACE) {
                return face;
            } else if (face == Const.TOP_FACE) {
                originAngle = Const.RIGHT_ANGLE;
            } else if (face == Const.LEFT_FACE) {
                originAngle = 180f;
            } else if (face == Const.BOTTOM_FACE) {
                originAngle = 270f;
            }
            originAngle += angle;
            x = (double) (Const.RIGHT_FACE);
            y = (double) (Const.TOP_FACE);
            return MatrixUtil.rotateFomXToY(x, y, originAngle);
        }
        return face;
    }

    public void updateSquaresFaces(List<Square> squares, int axis, float angle) {
        for (Square Square : squares) {
            Square.setOriginFace(rotateFace(Square.getOriginFace(), axis, angle));
        }
    }

    public void updateCubesFaces(List<Cube> cubes, int axis, float angle) {
        for (Cube cube : cubes) {
            List<Integer> faces = cube.getFaces();
            for (int i = 0; i < faces.size(); i++) {
                faces.set(i, rotateFace(faces.get(i), axis, angle));
            }
            for (Plane plane : cube.getPlanes()) {
                plane.setOriginFace(rotateFace(plane.getOriginFace(), axis, angle));
            }
        }
    }

    public void updateCubeAndMapInfoAfterRotate(RotateMsg rotateMsg, Map<Integer, CubesPlane> map) {
        int face = rotateMsg.face;
        List<Cube> updateCubes = map.get(face).getCubes();
        List<Cube> allCubes = map.get(Const.CENTER_FACE).getCubes();
        updateCubesFaces(updateCubes, rotateMsg.axis, rotateMsg.rotateAngle);
        map.clear();
        fillCubesPlaneMapInfo(allCubes, map);
    }

    class RotateMsg {
        public int face;
        public float angle;
        public int axis;
        public int msg;
        public float rotateAngle;
        public String rotateName;

        public RotateMsg(int msg, int face, float angle, int axis, String rotateName) {
            this.msg = msg;
            this.face = face;
            this.angle = angle;
            this.axis = axis;
            this.rotateName = rotateName;
        }
    }


    public RotateMsg translateRotateMsg(int rotateMsg) {
        RotateMsg msg = null;
        switch (rotateMsg) {
            case Const.FRONT_FACE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.FRONT_FACE, -Const.RIGHT_ANGLE, Const.Z_AXIS, "F");
                break;
            case Const.FRONT_FACE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.FRONT_FACE, Const.RIGHT_ANGLE, Const.Z_AXIS, "F'");
                break;
            case Const.BACK_FACE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.BACK_FACE, -Const.RIGHT_ANGLE, Const.Z_AXIS, "B");
                break;
            case Const.BACK_FACE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.BACK_FACE, Const.RIGHT_ANGLE, Const.Z_AXIS, "B'");
                break;
            case Const.LEFT_FACE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.LEFT_FACE, -Const.RIGHT_ANGLE, Const.X_AXIS, "L");
                break;
            case Const.LEFT_FACE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.LEFT_FACE, Const.RIGHT_ANGLE, Const.X_AXIS, "L'");
                break;
            case Const.RIGHT_FACE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.RIGHT_FACE, -Const.RIGHT_ANGLE, Const.X_AXIS, "R");
                break;
            case Const.RIGHT_FACE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.RIGHT_FACE, Const.RIGHT_ANGLE, Const.X_AXIS, "R'");
                break;
            case Const.TOP_FACE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.TOP_FACE, -Const.RIGHT_ANGLE, Const.Y_AXIS, "U");
                break;
            case Const.TOP_FACE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.TOP_FACE, Const.RIGHT_ANGLE, Const.Y_AXIS, "U'");
                break;
            case Const.BOTTOM_FACE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.BOTTOM_FACE, -Const.RIGHT_ANGLE, Const.Y_AXIS, "D");
                break;
            case Const.BOTTOM_FACE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.BOTTOM_FACE, Const.RIGHT_ANGLE, Const.Y_AXIS, "D'");
                break;
            case Const.ROTATE_X_WHOLE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.CENTER_FACE, -Const.RIGHT_ANGLE, Const.X_AXIS, "x");
                break;
            case Const.ROTATE_X_WHOLE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.CENTER_FACE, Const.RIGHT_ANGLE, Const.X_AXIS, "x'");
                break;
            case Const.ROTATE_Y_WHOLE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.CENTER_FACE, -Const.RIGHT_ANGLE, Const.Y_AXIS, "y");
                break;
            case Const.ROTATE_Y_WHOLE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.CENTER_FACE, Const.RIGHT_ANGLE, Const.Y_AXIS, "y'");
                break;
            case Const.ROTATE_Z_WHOLE_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.CENTER_FACE, -Const.RIGHT_ANGLE, Const.Z_AXIS, "z");
                break;
            case Const.ROTATE_Z_WHOLE_ANTI_CLOCKWISE:
                msg = new RotateMsg(rotateMsg, Const.CENTER_FACE, Const.RIGHT_ANGLE, Const.Z_AXIS, "z'");
                break;
        }
        return msg;
    }

    public void finishRotate(RotateMsg rotateMsgEntity) {
        updateCubeAndMapInfoAfterRotate(rotateMsgEntity, cubesPlaneMapShow);
        copyCubeList(cubesShow, cubes);
    }

    public void rotate(RotateMsg rotateMsg) {
        copyCubeList(cubes, cubesShow);
        List<Cube> cubes = cubesPlaneMapShow.get(rotateMsg.face).getCubes();
        rotateCubes(cubes, rotateMsg.axis, rotateMsg.rotateAngle);
    }

    public void disturbMagicCube() {
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int rotateMsg = random.nextInt(18) + 1;
            RotateMsg rotateMsgEntity = translateRotateMsg(rotateMsg);
            rotateMsgEntity.rotateAngle = rotateMsgEntity.angle;
            rotate(rotateMsgEntity);
            finishRotate(rotateMsgEntity);
        }
    }

    public static void rotateAnimation(int rotateMsg, GLSurfaceView view) {
        Date curDate = new Date(System.currentTimeMillis());
        float angle = Const.RIGHT_ANGLE;

        if (view == null || rotateMsg == -1) return;
        boolean isRendering = true;
        MagicCubeGlobalValue.isRotating = true;
        MagicCube magicCube = MagicCube.getSingleInstance();
        MagicCube.RotateMsg rotateMsgEntity = magicCube.translateRotateMsg(rotateMsg);
        while (isRendering) {
            Date endDate = new Date(System.currentTimeMillis());
            long diff = endDate.getTime() - curDate.getTime();
            float rotateAngle = angle / Const.ROTATE_TIME_MILLIS * diff;
            rotateAngle = rotateAngle >= Const.RIGHT_ANGLE ? Const.RIGHT_ANGLE : rotateAngle;
            rotateMsgEntity.rotateAngle = rotateMsgEntity.angle > 0 ? rotateAngle : -rotateAngle;
            magicCube.rotate(rotateMsgEntity);
            magicCube.fillElementArray();
            view.requestRender();
            if (rotateAngle == Const.RIGHT_ANGLE) {
                magicCube.finishRotate(rotateMsgEntity);
                isRendering = false;
                MagicCubeGlobalValue.isRotating = false;
            }
        }
    }

    public void fillNextStepTipInfo(int rotateMsg) {
        if (MagicCubeGlobalValue.steps == null) {
            MagicCubeGlobalValue.steps = new ArrayList<>();
        }
        if (MagicCubeGlobalValue.steps == null || MagicCubeGlobalValue.steps.size() == 0) {
            MagicCubeGlobalValue.steps = MagicCubeAutoRotate.getSingleInstance().getCurrentStepInfoList(cubesPlaneMapShow);
            drawNextStepTipInfo(MagicCubeGlobalValue.steps);
        } else {
            if (rotateMsg == MagicCubeGlobalValue.steps.get(0)) {
                if (MagicCubeGlobalValue.steps.size() == 1) {
                    MagicCubeGlobalValue.steps = MagicCubeAutoRotate.getSingleInstance().getCurrentStepInfoList(cubesPlaneMapShow);
                } else {
                    MagicCubeGlobalValue.steps = MagicCubeGlobalValue.steps.subList(1, MagicCubeGlobalValue.steps.size());
                }
                drawNextStepTipInfo(MagicCubeGlobalValue.steps);
            } else {
                MagicCubeGlobalValue.steps = MagicCubeAutoRotate.getSingleInstance().getCurrentStepInfoList(cubesPlaneMapShow);
                drawNextStepTipInfo(MagicCubeGlobalValue.steps);
            }
        }
    }

    private void drawNextStepTipInfo(List<Integer> steps) {
        if (steps.size() == 0) {
            MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().nextStepTip, "提示:无");
        } else {
            MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().nextStepTip, "提示:" + MagicCube.getSingleInstance().translateRotateMsg(steps.get(0)).rotateName);
        }
        glSurfaceView.requestRender();
    }
    public void drawCountStep(){
        MagicCubeGlobalValue.stepCount++;
        magicCubeText.stepInfo.setText("步数:" + MagicCubeGlobalValue.stepCount);
        magicCubeText.stepInfo.drawText(28, Color.RED, Typeface.NORMAL, "宋体");
        glSurfaceView.requestRender();
    }
}
