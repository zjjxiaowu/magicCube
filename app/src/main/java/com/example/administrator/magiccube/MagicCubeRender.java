package com.example.administrator.magiccube;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.administrator.magiccube.util.MatrixUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class MagicCubeRender implements GLSurfaceView.Renderer {

    private MagicCube magicCube;
    private MagicCubeSurfaceView context;
    public static int viewPort[];

    public MagicCubeRender(MagicCubeSurfaceView context) {
        this.context = context;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        magicCube.draw();
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        GLES20.glViewport(0, 0, MagicCubeGlobalValue.screenWidth, MagicCubeGlobalValue.screenHeight);
        final float ratio = (float) MagicCubeGlobalValue.screenWidth / MagicCubeGlobalValue.screenHeight;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;
        MatrixUtil.setIdentityP(0);
        MatrixUtil.frustumM(0, left, right, bottom, top, near, far);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // TODO Auto-generated method stub
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 6.0f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        MatrixUtil.setIdentityV(0);
        MatrixUtil.setLookAtM(0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
        viewPort = new int[]{0, 0, MagicCubeGlobalValue.screenWidth, MagicCubeGlobalValue.screenHeight};
        magicCube = MagicCube.getSingleInstance();
        magicCube.initMagicCube(this.context);
    }
}
