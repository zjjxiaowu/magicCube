package com.example.administrator.magiccube;

import java.util.Random;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class RandomDisturbThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if (MagicCubeGlobalValue.isRandomDisturbing) {
                Random random = new Random();
                int rotateMsg = random.nextInt(18) + 1;
                RotateMsg rotateMsgEntity = RotateMsg.translateRotateMsg(rotateMsg);
                rotateMsgEntity.rotateAngle = rotateMsgEntity.angle;
                MagicCubeGlobalValue.setInfoBeforeRotating();
                MagicCubeRotate.rotateAnimation(rotateMsg, MagicCube.getSingleInstance().glSurfaceView);
                MagicCubeGlobalValue.setInfoAfterRotating();
            } else {
                break;
            }
        }
        MagicCubeGlobalValue.setInfoAfterRandomDisturbing();
    }
}
