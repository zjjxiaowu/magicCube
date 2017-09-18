package com.example.administrator.magiccube.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class GLText {
    private Bitmap txtBitMap;
    private String text;
    private float[] coordPos;
    private float[] rectPos;
    private float[] triangle1;
    private float[] triangle2;
    private Integer width;

    public float[] getTriangle1() {
        return triangle1;
    }

    public float[] getTriangle2() {
        return triangle2;
    }

    public float[] getCoordPos() {
        return coordPos;
    }

    public float[] getRectPos() {
        return rectPos;
    }

    public GLText(String text, float[] coordPos, float[] rectPos) {
        this.text = text;
        this.coordPos = coordPos;
        this.rectPos = rectPos;
        fillTriangles();
    }

    public GLText(String text, float[] coordPos, float[] rectPos, int width) {
        this.text = text;
        this.coordPos = coordPos;
        this.rectPos = rectPos;
        this.width = width;
        fillTriangles();
    }

    private void fillTriangles() {
        triangle1 = new float[9];
        triangle2 = new float[9];
        int[] index = {0, 1, 2, 0, 2, 3};
        int k = 0;
        for (int i = 0; i < index.length - 3; i++) {
            for (int j = index[i] * 3; j < index[i] * 3 + 3; j++) {
                triangle1[k] = rectPos[j];
                k++;
            }
        }
        k = 0;
        for (int i = 3; i < index.length; i++) {
            for (int j = index[i] * 3; j < index[i] * 3 + 3; j++) {
                triangle2[k] = rectPos[j];
                k++;
            }
        }
    }

    public Bitmap getTxtBitMap() {
        return txtBitMap;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void drawText(int fontSize, int fontColor, int fontBold, String fontType) {
        Paint p = new Paint();
        //字体设置
        Typeface typeface = Typeface.create(fontType, fontBold);
        //消除锯齿
        p.setAntiAlias(true);
        //字体为红色
        p.setColor(fontColor);
        p.setTypeface(typeface);
        p.setTextSize(fontSize);
        int textWidth = (int) Math.ceil(p.measureText(text));
        if (this.width != null) {
            textWidth = this.width;
        }
        int textHeight = (int) Math.ceil(-p.ascent()) + (int) Math.ceil(p.descent());
        this.txtBitMap = Bitmap.createBitmap(textWidth, textHeight + 10, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(txtBitMap);
        //背景颜色
        canvas.drawColor(Color.BLACK);
        //绘制字体
        canvas.drawText(this.text, 0, textHeight, p);
    }
}
