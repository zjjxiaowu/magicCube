package com.example.administrator.magiccube;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class MagicCubeGlobalValue {
    public static boolean isRotating = false;
    public static boolean isAutoRotating = false;
    public static boolean isRandomDisturbing = false;
    public static boolean isUserRotating = false;
    public static int stepCount = 0;
    public static int screenWidth;
    public static int screenHeight;
    public static List<Integer> steps;

    public static void setInfoBeforeRandomDisturbing() {
        MagicCubeGlobalValue.isRandomDisturbing = true;
        MagicCubeGlobalValue.stepCount = 0;
        setInfoBeforeRotating();
        MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().stepInfo, "步数:0");
        MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().nextStepTip, "提示:无");
    }

    public static void setInfoAfterRandomDisturbing() {
        MagicCubeGlobalValue.isRandomDisturbing = false;
        setInfoAfterRotating();
        MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().stepInfo, "步数:0");
        MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().nextStepTip, "提示:无");
    }

    public static void setInfoBeforeAutoRotating() {
        MagicCubeGlobalValue.isAutoRotating = true;
        setInfoBeforeRotating();
        MagicCubeText.getSingleInstance().drawText(MagicCubeText.getSingleInstance().nextStepTip, "提示:无");
    }

    public static void setInfoAfterAutoRotating() {
        setInfoAfterRotating();
        MagicCubeGlobalValue.isAutoRotating = false;
    }

    public static void setInfoBeforeRotating() {
        MagicCubeGlobalValue.isRotating = true;
    }

    public static void setInfoAfterRotating() {
        MagicCubeGlobalValue.isRotating = false;
    }

    public static void setInfoBeforeUserRotating() {
        MagicCubeGlobalValue.isUserRotating = true;
    }

    public static void setInfoAfterUserRotating() {
        MagicCubeGlobalValue.isUserRotating = false;
    }
}
