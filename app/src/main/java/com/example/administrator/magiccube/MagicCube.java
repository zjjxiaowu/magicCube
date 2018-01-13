package com.example.administrator.magiccube;

import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.Cube;
import com.example.administrator.magiccube.entity.CubesPlane;
import com.example.administrator.magiccube.entity.Plane;
import com.example.administrator.magiccube.entity.Square;
import com.example.administrator.magiccube.util.BufferUtil;
import com.example.administrator.magiccube.util.CalculateUtil;
import com.example.administrator.magiccube.util.CubeUtil;
import com.example.administrator.magiccube.util.MatrixUtil;
import com.example.administrator.magiccube.util.ShaderUtil;

import java.util.ArrayList;
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
        CubeUtil.copyCubes(cubes, cubesShow);
        CubeUtil.fillCubesPlaneByCubes(cubesShow, cubesPlaneMapShow);
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

    public static void updateSquaresFaces(List<Square> squares, int axis, float angle) {
        for (Square Square : squares) {
            Square.setOriginFace(MagicCubeRotate.rotateFace(Square.getOriginFace(), axis, angle));
        }
    }

    public static void updateCubesFaces(List<Cube> cubes, int axis, float angle) {
        for (Cube cube : cubes) {
            List<Integer> faces = cube.getFaces();
            for (int i = 0; i < faces.size(); i++) {
                faces.set(i, MagicCubeRotate.rotateFace(faces.get(i), axis, angle));
            }
            for (Plane plane : cube.getPlanes()) {
                plane.setOriginFace(MagicCubeRotate.rotateFace(plane.getOriginFace(), axis, angle));
            }
        }
    }

    public static void updateCubeAndMapInfoAfterRotate(RotateMsg rotateMsg, Map<Integer, CubesPlane> map) {
        int face = rotateMsg.face;
        List<Cube> updateCubes = map.get(face).getCubes();
        List<Cube> allCubes = map.get(Const.CENTER_FACE).getCubes();
        updateCubesFaces(updateCubes, rotateMsg.axis, rotateMsg.rotateAngle);
        map.clear();
        CubeUtil.fillCubesPlaneByCubes(allCubes, map);
    }

    public void disturbMagicCube() {
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int rotateMsg = random.nextInt(18) + 1;
            RotateMsg rotateMsgEntity = RotateMsg.translateRotateMsg(rotateMsg);
            rotateMsgEntity.rotateAngle = rotateMsgEntity.angle;
            MagicCubeRotate.rotate(cubes, cubesShow, cubesPlaneMapShow,rotateMsgEntity);
            MagicCubeRotate.finishRotate(cubes, cubesShow,cubesPlaneMapShow, rotateMsgEntity);
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
            MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().nextStepTip, "提示:" + RotateMsg.translateRotateMsg(steps.get(0)).rotateName);
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
