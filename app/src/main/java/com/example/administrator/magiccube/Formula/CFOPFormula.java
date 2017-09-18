package com.example.administrator.magiccube.Formula;

import java.util.List;

/**
 * Created by Administrator on 2017/9/3 0003.
 */

public class CFOPFormula extends MagicCubeFormula {
    public static void PLL1(List<Integer> steps) {
        R(steps);
        _U(steps);
        R(steps);
        U(steps);
        R(steps);
        U(steps);
        R(steps);
        _U(steps);
        _R(steps);
        _U(steps);
        R2(steps);
    }

    public static void PLL2(List<Integer> steps) {
        _R2(steps);
        U(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        _R(steps);
        _U(steps);
        _R(steps);
        U(steps);
        _R(steps);
    }

    public static void PLL3(List<Integer> steps) {
        M2(steps);
        U(steps);
        M2(steps);
        U2(steps);
        M2(steps);
        U(steps);
        M2(steps);
    }

    public static void PLL4(List<Integer> steps) {
        M2(steps);
        U(steps);
        M2(steps);
        U(steps);
        _M(steps);
        U2(steps);
        M2(steps);
        U2(steps);
        _M(steps);
        U2(steps);
    }

    public static void PLL5(List<Integer> steps) {
        _x(steps);
        R2(steps);
        _D2(steps);
        _R(steps);
        _U(steps);
        R(steps);
        _D2(steps);
        _R(steps);
        U(steps);
        _R(steps);
        x(steps);
    }

    public static void PLL6(List<Integer> steps) {
        _x(steps);
        R(steps);
        _U(steps);
        R(steps);
        _D2(steps);
        _R(steps);
        U(steps);
        R(steps);
        _D2(steps);
        R2(steps);
        x(steps);
    }

    public static void PLL7(List<Integer> steps) {
        R2(steps);
        U(steps);
        _R(steps);
        _U(steps);
        y(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        R(steps);
        U(steps);
        _R(steps);
        _y(steps);
        R(steps);
        _U(steps);
        R2(steps);
    }

    public static void PLL8(List<Integer> steps) {
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        _R(steps);
        F(steps);
        R2(steps);
        _U(steps);
        _R(steps);
        _U(steps);
        R(steps);
        U(steps);
        _R(steps);
        _F(steps);
    }

    public static void PLL9(List<Integer> steps) {
        R(steps);
        U(steps);
        _R(steps);
        _F(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        _R(steps);
        F(steps);
        R2(steps);
        _U(steps);
        _R(steps);
        _U(steps);
    }

    public static void PLL10(List<Integer> steps) {
        z(steps);
        _U(steps);
        R(steps);
        D(steps);
        R2(steps);
        U(steps);
        _R(steps);
        _U(steps);
        R2(steps);
        U(steps);
        _D(steps);
        _R(steps);
    }

    public static void PLL11(List<Integer> steps) {
        F(steps);
        R(steps);
        _U(steps);
        _R(steps);
        _U(steps);
        R(steps);
        U(steps);
        _R(steps);
        _F(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        _R(steps);
        F(steps);
        R(steps);
        _F(steps);
    }

    public static void PLL12(List<Integer> steps) {
        _U(steps);
        _R(steps);
        U(steps);
        R(steps);
        _U(steps);
        _R2(steps);
        _b(steps);
        x(steps);
        _R(steps);
        U(steps);
        R(steps);
        _y(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        R2(steps);
    }

    public static void PLL13(List<Integer> steps) {
        _R(steps);
        U(steps);
        _R(steps);
        _d(steps);
        _R(steps);
        _F(steps);
        R2(steps);
        _U(steps);
        _R(steps);
        U(steps);
        _R(steps);
        F(steps);
        R(steps);
        F(steps);
    }

    public static void PLL14(List<Integer> steps) {
        R(steps);
        _U2(steps);
        _R(steps);
        U2(steps);
        R(steps);
        B(steps);
        _R(steps);
        _U(steps);
        R(steps);
        U(steps);
        R(steps);
        _B(steps);
        _R2(steps);
        U(steps);
    }

    public static void PLL15(List<Integer> steps) {
        _R(steps);
        U2(steps);
        R(steps);
        _U2(steps);
        _R(steps);
        F(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        _R(steps);
        _F(steps);
        R2(steps);
        _U(steps);
    }

    public static void PLL16(List<Integer> steps) {
        z(steps);
        _R(steps);
        U(steps);
        _R(steps);
        _z(steps);
        R(steps);
        U2(steps);
        L(steps);
        U(steps);
        _R(steps);
        z(steps);
        U(steps);
        _R(steps);
        _z(steps);
        R(steps);
        U2(steps);
        L(steps);
        U(steps);
        _R(steps);
    }

    public static void PLL17(List<Integer> steps) {
        z(steps);
        _U(steps);
        R(steps);
        D(steps);
        R2(steps);
        U(steps);
        _R(steps);
        _U(steps);
        _z(steps);
        R(steps);
        U(steps);
        _R(steps);
        z(steps);
        R2(steps);
        U(steps);
        _R(steps);
        _D(steps);
        _R(steps);
    }

    public static void PLL18(List<Integer> steps) {
        _R2(steps);
        _u(steps);
        R(steps);
        _U(steps);
        R(steps);
        U(steps);
        _R(steps);
        u(steps);
        _R2(steps);
        y(steps);
        R(steps);
        _U(steps);
        _R(steps);
    }

    public static void PLL19(List<Integer> steps) {
        R(steps);
        U(steps);
        _R(steps);
        _y(steps);
        _R2(steps);
        _u(steps);
        R(steps);
        _U(steps);
        _R(steps);
        U(steps);
        _R(steps);
        u(steps);
        R2(steps);
    }

    public static void PLL20(List<Integer> steps) {
        _R2(steps);
        u(steps);
        _R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        R(steps);
        _u(steps);
        _R2(steps);
        _y(steps);
        _R(steps);
        U(steps);
        R(steps);
    }

    public static void PLL21(List<Integer> steps) {
        _R(steps);
        _d(steps);
        F(steps);
        _R2(steps);
        u(steps);
        _R(steps);
        U(steps);
        R(steps);
        _U(steps);
        R(steps);
        _u(steps);
        R2(steps);
    }

    public static void F2LForStepThree(List<Integer> steps) {
        for (int i = 0; i < 2; i++) {
            R(steps);
            _U2(steps);
            _R(steps);
            U(steps);
        }
        _y(steps);
        _R(steps);
        _U(steps);
        R(steps);
    }
}
