package com.example.administrator.magiccube;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class MagicCubeSurfaceView extends GLSurfaceView {

    public MagicCubeSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new MagicCubeRender(this));
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}
