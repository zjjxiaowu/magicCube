package com.example.administrator.magiccube.Formula;

import java.util.List;

/**
 * Created by Administrator on 2017/9/3 0003.
 */

public class LayerFirstFormula extends MagicCubeFormula {
    public static void fishOne(List<Integer> steps) {
        _R(steps);
        _U(steps);
        R(steps);
        _U(steps);
        _R(steps);
        _U2(steps);
        R(steps);
    }

    public static void fishTwo(List<Integer> steps) {
        F(steps);
        U(steps);
        _F(steps);
        U(steps);
        F(steps);
        U2(steps);
        _F(steps);
    }

    public static void stepThreeFront(List<Integer> steps) {
        U(steps);
        R(steps);
        _U(steps);
        _R(steps);
        _U(steps);
        _F(steps);
        U(steps);
        F(steps);
    }

    public static void stepThreeRight(List<Integer> steps) {
        _U(steps);
        _F(steps);
        U(steps);
        F(steps);
        U(steps);
        R(steps);
        _U(steps);
        _R(steps);
    }

    public static void stepFour(List<Integer> steps) {
        F(steps);
        R(steps);
        U(steps);
        _R(steps);
        _U(steps);
        _F(steps);
    }
}
