package com.example.administrator.magiccube;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class AutoRotateThread extends Thread {
    @Override
    public void run(){
        MagicCubeAutoRotate.getSingleInstance().autoRotate(MagicCube.getSingleInstance().cubesPlaneMapShow,MagicCube.getSingleInstance().glSurfaceView);
    }
}
