package com.example.administrator.magiccube.Formula;

import com.example.administrator.magiccube.constant.Const;

import java.util.List;

/**
 * Created by Administrator on 2017/9/3 0003.
 */

public abstract class MagicCubeFormula {

    public static void R(List<Integer> steps) {
        steps.add(Const.RIGHT_FACE_CLOCKWISE);
    }

    public static void _R(List<Integer> steps) {
        steps.add(Const.RIGHT_FACE_ANTI_CLOCKWISE);
    }

    public static void L(List<Integer> steps) {
        steps.add(Const.LEFT_FACE_CLOCKWISE);
    }

    public static void _L(List<Integer> steps) {
        steps.add(Const.LEFT_FACE_ANTI_CLOCKWISE);
    }

    public static void _L2(List<Integer> steps) {
        steps.add(Const.LEFT_FACE_ANTI_CLOCKWISE);
        steps.add(Const.LEFT_FACE_ANTI_CLOCKWISE);
    }

    public static void F(List<Integer> steps) {
        steps.add(Const.FRONT_FACE_CLOCKWISE);
    }

    public static void _F2(List<Integer> steps) {
        steps.add(Const.FRONT_FACE_ANTI_CLOCKWISE);
        steps.add(Const.FRONT_FACE_ANTI_CLOCKWISE);
    }

    public static void _F(List<Integer> steps) {
        steps.add(Const.FRONT_FACE_ANTI_CLOCKWISE);
    }

    public static void D(List<Integer> steps) {
        steps.add(Const.BOTTOM_FACE_CLOCKWISE);
    }

    public static void _D(List<Integer> steps) {
        steps.add(Const.BOTTOM_FACE_ANTI_CLOCKWISE);
    }

    public static void U(List<Integer> steps) {
        steps.add(Const.TOP_FACE_CLOCKWISE);
    }

    public static void _U(List<Integer> steps) {
        steps.add(Const.TOP_FACE_ANTI_CLOCKWISE);
    }

    public static void B(List<Integer> steps) {
        steps.add(Const.BACK_FACE_CLOCKWISE);
    }

    public static void B2(List<Integer> steps) {
        steps.add(Const.BACK_FACE_CLOCKWISE);
        steps.add(Const.BACK_FACE_CLOCKWISE);
    }

    public static void _B(List<Integer> steps) {
        steps.add(Const.BACK_FACE_ANTI_CLOCKWISE);
    }

    public static void U2(List<Integer> steps) {
        steps.add(Const.TOP_FACE_CLOCKWISE);
        steps.add(Const.TOP_FACE_CLOCKWISE);
    }

    public static void _U2(List<Integer> steps) {
        steps.add(Const.TOP_FACE_ANTI_CLOCKWISE);
        steps.add(Const.TOP_FACE_ANTI_CLOCKWISE);
    }

    public static void D2(List<Integer> steps) {
        steps.add(Const.BOTTOM_FACE_CLOCKWISE);
        steps.add(Const.BOTTOM_FACE_CLOCKWISE);
    }

    public static void _D2(List<Integer> steps) {
        steps.add(Const.BOTTOM_FACE_ANTI_CLOCKWISE);
        steps.add(Const.BOTTOM_FACE_ANTI_CLOCKWISE);
    }

    public static void R2(List<Integer> steps) {
        steps.add(Const.RIGHT_FACE_CLOCKWISE);
        steps.add(Const.RIGHT_FACE_CLOCKWISE);
    }

    public static void L2(List<Integer> steps) {
        steps.add(Const.LEFT_FACE_CLOCKWISE);
        steps.add(Const.LEFT_FACE_CLOCKWISE);
    }

    public static void _R2(List<Integer> steps) {
        steps.add(Const.RIGHT_FACE_ANTI_CLOCKWISE);
        steps.add(Const.RIGHT_FACE_ANTI_CLOCKWISE);
    }

    public static void M2(List<Integer> steps) {
        L2(steps);
        R2(steps);
        _x(steps);
        _x(steps);
    }

    public static void _M(List<Integer> steps) {
        _L(steps);
        _R(steps);
        x(steps);
    }

    public static void _b(List<Integer> steps) {
        _F(steps);
        z(steps);
    }

    public static void _d(List<Integer> steps) {
        _U(steps);
        y(steps);
    }

    public static void u(List<Integer> steps) {
        _D(steps);
        y(steps);
    }

    public static void _u(List<Integer> steps) {
        D(steps);
        _y(steps);
    }

    public static void x(List<Integer> steps) {
        wholeXCW(steps);
    }

    public static void _x(List<Integer> steps) {
        wholeXCCW(steps);
    }

    public static void y(List<Integer> steps) {
        wholeYCW(steps);
    }

    public static void _y(List<Integer> steps) {
        wholeYCCW(steps);
    }

    public static void z(List<Integer> steps) {
        wholeZCW(steps);
    }

    public static void _z(List<Integer> steps) {
        wholeZCCW(steps);
    }

    public static void wholeYCW(List<Integer> steps) {
        steps.add(Const.ROTATE_Y_WHOLE_CLOCKWISE);
    }

    public static void wholeYCCW(List<Integer> steps) {
        steps.add(Const.ROTATE_Y_WHOLE_ANTI_CLOCKWISE);
    }

    public static void wholeZCCW(List<Integer> steps) {
        steps.add(Const.ROTATE_Z_WHOLE_ANTI_CLOCKWISE);
    }

    public static void wholeZCW(List<Integer> steps) {
        steps.add(Const.ROTATE_Z_WHOLE_CLOCKWISE);
    }

    public static void wholeXCCW(List<Integer> steps) {
        steps.add(Const.ROTATE_X_WHOLE_ANTI_CLOCKWISE);
    }

    public static void wholeXCW(List<Integer> steps) {
        steps.add(Const.ROTATE_X_WHOLE_CLOCKWISE);
    }
}
