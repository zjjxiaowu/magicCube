package com.example.administrator.magiccube;

import com.example.administrator.magiccube.constant.Const;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class RotateMsg {
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

    public static RotateMsg translateRotateMsg(int rotateMsg) {
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
}