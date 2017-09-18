package com.example.administrator.magiccube;

import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.GLText;
import com.example.administrator.magiccube.util.BufferUtil;
import com.example.administrator.magiccube.util.MatrixUtil;
import com.example.administrator.magiccube.util.ShaderUtil;

/**
 * Created by Administrator on 2017/9/3 0003.
 */

public class MagicCubeText {
    public GLText stepInfo;
    public GLText nextStepTip;
    public GLText autoRotateTxt;
    public GLText randomDisturb;
    public int[] textures = new int[4];
    public int mText2dProgramHandle;
    public int mPositionHandle;
    public int mMVPMatrixHandle;
    float[] stepCoordPos = new float[]{0, 1.0f,
            1.0f, 1.0f,
            0, 0,
            1.0f, 0};
    float[] stepRectPos = new float[]{
            -3.0f, 5.0f, 0,
            -2.0f, 5.0f, 0,
            -3.0f, 5.5f, 0,
            -2.0f, 5.5f, 0};
    float[] nextStepRectPos = new float[]{
            1.0f, 5.0f, 0,
            3.0f, 5.0f, 0,
            1.0f, 5.5f, 0,
            3.0f, 5.5f, 0};
    float[] autoRotateRectPos = new float[]{
            -3.0f, -5.5f, 0,
            -1.0f, -5.5f, 0,
            -3.0f, -5.0f, 0,
            -1.0f, -5.0f, 0};
    float[] randomDisturbRectPos = new float[]{
            1.0f, -5.5f, 0,
            3.0f, -5.5f, 0,
            1.0f, -5.0f, 0,
            3.0f, -5.0f, 0};
    private static MagicCubeText magicCubeText;

    private MagicCubeText(){
    }
    public static MagicCubeText getSingleInstance(){
        if(magicCubeText==null){
            magicCubeText = new MagicCubeText();
        }
        return magicCubeText;
    }
    public void init() {
        initText();
        initTexture();
        initShader();
    }

    public void initShader() {
        String text2DVertexShader = ShaderUtil.loadFromAssetsFile(Const.TEXT2D_VERTEX_NAME, MagicCube.getSingleInstance().glSurfaceView.getResources());
        String text2DFragmentShader = ShaderUtil.loadFromAssetsFile(Const.TEXT2D_FRAGMENT_NAME, MagicCube.getSingleInstance().glSurfaceView.getResources());
        mText2dProgramHandle = ShaderUtil.createProgram(text2DVertexShader, text2DFragmentShader);
    }

    public void initTexture() {
        GLES20.glGenTextures(4, textures, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[1]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[2]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[3]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
    }
    public void drawText(GLText glText,String text){
        glText.setText(text);
        glText.drawText(28, Color.RED, Typeface.NORMAL, "宋体");
    }
    public void initText() {
        stepInfo = new GLText("步数:0", stepCoordPos, stepRectPos);
        stepInfo.drawText(28, Color.RED, Typeface.NORMAL, "宋体");
        nextStepTip = new GLText("提示:无", stepCoordPos, nextStepRectPos,190);
        nextStepTip.drawText(28, Color.RED, Typeface.NORMAL, "宋体");
        autoRotateTxt = new GLText("自动旋转", stepCoordPos, autoRotateRectPos,170);
        autoRotateTxt.drawText(28, Color.RED, Typeface.NORMAL, "宋体");
        randomDisturb = new GLText("随机打乱", stepCoordPos, randomDisturbRectPos,150);
        randomDisturb.drawText(28, Color.RED, Typeface.NORMAL, "宋体");
    }
    public void drawText() {
        MatrixUtil.setGLTextMatrix();
        GLES20.glUseProgram(mText2dProgramHandle);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mText2dProgramHandle, "u_MVPMatrix");
        int mTextureUniformHandle = GLES20.glGetUniformLocation(mText2dProgramHandle, "u_Texture");
        mPositionHandle = GLES20.glGetAttribLocation(mText2dProgramHandle, "a_Position");
        int mTextureCoordinateHandle = GLES20.glGetAttribLocation(mText2dProgramHandle, "a_TexCoordinate");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, stepInfo.getTxtBitMap(), 0);

        GLES20.glVertexAttribPointer(mPositionHandle, Const.VERTEX_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(stepInfo.getRectPos()));
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(stepInfo.getCoordPos()));
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MatrixUtil.getFinalMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[1]);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, nextStepTip.getTxtBitMap(), 0);

        MatrixUtil.setGLTextMatrix();
        GLES20.glVertexAttribPointer(mPositionHandle, Const.VERTEX_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(nextStepTip.getRectPos()));
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(nextStepTip.getCoordPos()));
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MatrixUtil.getFinalMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[2]);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, autoRotateTxt.getTxtBitMap(), 0);

        MatrixUtil.setGLTextMatrix();
        GLES20.glVertexAttribPointer(mPositionHandle, Const.VERTEX_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(autoRotateTxt.getRectPos()));
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(autoRotateTxt.getCoordPos()));
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MatrixUtil.getFinalMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[3]);
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, randomDisturb.getTxtBitMap(), 0);

        MatrixUtil.setGLTextMatrix();
        GLES20.glVertexAttribPointer(mPositionHandle, Const.VERTEX_ELEMENT_SIZE, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(randomDisturb.getRectPos()));
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, BufferUtil.floatArray2ByteBuffer(randomDisturb.getCoordPos()));
        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MatrixUtil.getFinalMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
