package com.example.administrator.magiccube.util;

import android.opengl.Matrix;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.Vertex;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class MatrixUtil {
    private static float[] mMVPMatrix = new float[16];
    private static float[] mViewMatrix = new float[16];
    private static float[] mModelMatrix = new float[16];
    private static float[] mProjectionMatrix = new float[16];

    public static float[] getModelMatrix() {
        return mModelMatrix;
    }
    public static float[] getProjectionMatrix(){
        return mProjectionMatrix;
    }
    public static float[] setLookAtM(int rmOffset, float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        Matrix.setLookAtM(mViewMatrix, rmOffset, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        return mViewMatrix;
    }
    public static float[] getViewMatrix(){
        return mViewMatrix;
    }
    public static float[] getModelViewMatrix(){
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        return mMVPMatrix;
    }
    public static float[] getFinalMatrix() {
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }
    public static float[] setIdentityV(int offset) {
        Matrix.setIdentityM(mViewMatrix,0);
        return mModelMatrix;
    }
    public static float[] setIdentityP(int offset) {
        Matrix.setIdentityM(mProjectionMatrix,0);
        return mModelMatrix;
    }
    public static float[] setIdentityM(int offset) {
        Matrix.setIdentityM(mModelMatrix, offset);
        return mModelMatrix;
    }

    public static void scaleM(float x, float y, float z) {
        Matrix.scaleM(mModelMatrix, 0, x, y, z);
    }

    public static float[] translateM(int offset, float x, float y, float z) {
        Matrix.translateM(mModelMatrix, offset, x, y, z);
        return mModelMatrix;
    }

    public static float[] rotateM(int offset, float a, float x, float y, float z) {
        Matrix.rotateM(mModelMatrix, offset, a, x, y, z);
        return mModelMatrix;
    }

    public static float[] frustumM(int offset, float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjectionMatrix, offset, left, right, bottom, top, near, far);
        return mProjectionMatrix;
    }

    public static void rotateAroundX(Vertex vertex, double x, double y, double z, double angle) {
        double angleR = Math.toRadians(angle);
        double cosR = Math.cos(angleR);
        double sinR = Math.sin(angleR);
        cosR = cosR < Const.SMALL_NUM && cosR > 0d ? 0d : cosR;
        cosR = cosR <0 &&cosR >-Const.SMALL_NUM ? 0d : cosR;
        sinR = sinR < Const.SMALL_NUM && sinR > 0d ? 0d : sinR;
        sinR = sinR < 0 && sinR > -Const.SMALL_NUM ? 0d : sinR;
        double ry = y * cosR - z * sinR;
        double rz = y * sinR + z * cosR;
        double rx = x;
        vertex.setX((float) rx);
        vertex.setY((float) ry);
        vertex.setZ((float) rz);
    }

    public static void rotateAroundY(Vertex vertex, float x, float y, float z, float angle) {
        double angleR = Math.toRadians(angle);
        double cosR = Math.cos(angleR);
        double sinR = Math.sin(angleR);
        cosR = cosR < Const.SMALL_NUM && cosR > 0d ? 0d : cosR;
        cosR = cosR <0 &&cosR >-Const.SMALL_NUM ? 0d : cosR;
        sinR = sinR < Const.SMALL_NUM && sinR > 0d ? 0d : sinR;
        sinR = sinR < 0 && sinR > -Const.SMALL_NUM ? 0d : sinR;
        double rz = z * cosR - x * sinR;
        double rx = z * sinR + x * cosR;
        double ry = y;
        vertex.setX((float) rx);
        vertex.setY((float) ry);
        vertex.setZ((float) rz);
    }

    public static void rotateAroundZ(Vertex vertex, float x, float y, float z, float angle) {
        double angleR = Math.toRadians(angle);
        double cosR = Math.cos(angleR);
        double sinR = Math.sin(angleR);
        cosR = cosR < Const.SMALL_NUM && cosR > 0d ? 0d : cosR;
        cosR = cosR <0 &&cosR >-Const.SMALL_NUM ? 0d : cosR;
        sinR = sinR < Const.SMALL_NUM && sinR > 0d ? 0d : sinR;
        sinR = sinR < 0 && sinR > -Const.SMALL_NUM ? 0d : sinR;
        double rx = x * cosR - y * sinR;
        double ry = x * sinR + y * cosR;
        double rz = z;
        vertex.setX((float) rx);
        vertex.setY((float) ry);
        vertex.setZ((float) rz);
    }
    public static int rotateFomXToY(double x, double y,float angle) {
        double angleR = Math.toRadians(angle);
        double cosR = Math.cos(angleR);
        double sinR = Math.sin(angleR);
        cosR = cosR < Const.SMALL_NUM && cosR > 0d ? 0d : cosR;
        cosR = cosR <0 &&cosR >-Const.SMALL_NUM ? 0d : cosR;
        sinR = sinR < Const.SMALL_NUM && sinR > 0d ? 0d : sinR;
        sinR = sinR < 0 && sinR > -Const.SMALL_NUM ? 0d : sinR;
        int result = (int)(x*cosR+y*sinR);
        return result;
    }
    public static void setCubeMatrix(){
        MatrixUtil.setIdentityM(0);
        MatrixUtil.scaleM(1.1f, 1.1f, 1.1f);
        MatrixUtil.rotateM(0, 50f,0.0f,-1.0f,0.0f);
        MatrixUtil.rotateM(0, 25f,1.0f,0.0f,0.0f);
        MatrixUtil.rotateM(0, 25f,0.0f,0.0f,-1.0f);
    }
    public static void setGLTextMatrix(){
        MatrixUtil.setIdentityM(0);
    }
}
