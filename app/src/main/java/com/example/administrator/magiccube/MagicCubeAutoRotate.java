package com.example.administrator.magiccube;

import android.opengl.GLSurfaceView;

import com.example.administrator.magiccube.Formula.CFOPFormula;
import com.example.administrator.magiccube.Formula.LayerFirstFormula;
import com.example.administrator.magiccube.Formula.MagicCubeFormula;
import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.Cube;
import com.example.administrator.magiccube.entity.CubesPlane;
import com.example.administrator.magiccube.entity.Square;
import com.example.administrator.magiccube.util.CubeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class MagicCubeAutoRotate {
    private static final int FACES_SIZE = 6;
    private static final int FACE_SQUARE_SIZE = 9;
    private int[][] squares = new int[FACES_SIZE][FACE_SQUARE_SIZE];
    private int frontFacePos;
    private int backFacePos;
    private int leftFacePos;
    private int rightFacePos;
    private int topFacePos;
    private int bottomFacePos;
    private int bottomColor;
    private int topColor;
    private int leftColor;
    private int rightColor;
    private int frontColor;
    private int backColor;
    private int curStep;
    private List<Integer> steps;
    private static MagicCubeAutoRotate magicCubeAutoRotate;

    private MagicCubeAutoRotate() {

    }

    public static MagicCubeAutoRotate getSingleInstance() {
        if (magicCubeAutoRotate == null) {
            magicCubeAutoRotate = new MagicCubeAutoRotate();
        }
        return magicCubeAutoRotate;
    }

    public void autoRotate(Map<Integer, CubesPlane> cubesPlaneMap, GLSurfaceView view) {
        steps = new ArrayList<>();
        curStep = 1;
        initFacesPos(cubesPlaneMap);
        rotateTransaction(view);
        while (curStep < 8) {
            steps.clear();
            fillSquares(cubesPlaneMap);
            if (curStep == 1) {
                rotateForStepOneTransaction();
            } else if (curStep == 2) {
                rotateForStepTwoTransaction();
            } else if (curStep == 3) {
                rotateForStepThreeTransaction();
            } else if (curStep == 4) {
                rotateForStepFourTransaction();
            } else if (curStep == 5) {
                rotateForStepFiveTransaction();
            } else if (curStep == 6) {
                rotateForStepSixTransaction();
            }
            rotateTransaction(view);
            if (curStep == 8) {
                MagicCubeGlobalValue.setInfoAfterAutoRotating();
/*               MagicCubeGlobalValue.isRendering = false;
                MagicCubeGlobalValue.isAutoRotating = false;
                MagicCubeGlobalValue.stepCount = 0;
                MagicCube.getSingleInstance().disturbMagicCube(false);
                curStep = 1;*/
            }
        }
    }

    public int getCurrentStep() {
        if (isFinishedStepOne()) {
            if (isFinishedStepTwo()) {
                if (isFinishedStepThree()) {
                    if (isFinishedStepFour()) {
                        if (isFinishedStepFive()) {
                            if (isFinishedStepSeven()) {
                                return 8;
                            } else {
                                return 6;
                            }
                        } else {
                            return 5;
                        }
                    } else {
                        return 4;
                    }
                } else {
                    return 3;
                }
            } else {
                return 2;
            }
        }
        return 1;
    }

    public List<Integer> getCurrentStepInfoList(Map<Integer, CubesPlane> cubesPlaneMap) {
        steps = new ArrayList<>();
        curStep = 1;
        List<Integer> retSteps = new ArrayList<>();
        initFacesPos(cubesPlaneMap);
        fillSquares(cubesPlaneMap);
        if (steps.size() != 0) {
            retSteps.addAll(steps);
            return retSteps;
        } else {
            curStep = getCurrentStep();
            switch (curStep) {
                case 1:
                    rotateForStepOneTransaction();
                    break;
                case 2:
                    rotateForStepTwoTransaction();
                    break;
                case 3:
                    rotateForStepThreeTransaction();
                    break;
                case 4:
                    rotateForStepFourTransaction();
                    break;
                case 5:
                    rotateForStepFiveTransaction();
                    break;
                case 6:
                    rotateForStepSixTransaction();
                    break;
            }
            retSteps.addAll(steps);
            return retSteps;
        }
    }

    private void rotateTransaction(GLSurfaceView view) {
        MagicCubeGlobalValue.setInfoBeforeRotating();
        rotate(view);
        MagicCubeGlobalValue.setInfoAfterRotating();
    }

    private void rotate(GLSurfaceView view) {
        for (Integer rotateMsg : steps) {
            if (MagicCubeGlobalValue.isAutoRotating) {
                MagicCubeRotate.rotateAnimation(rotateMsg, view);
                MagicCube.getSingleInstance().drawCountStep();
            } else {
                curStep = 8;
                return;
            }
        }
    }

    private void initFacesPos(Map<Integer, CubesPlane> cubesPlaneMap) {
        fillInitSteps(cubesPlaneMap);
    }

    private void fillInitSteps(Map<Integer, CubesPlane> cubesPlaneMap) {
        Map<Integer, CubesPlane> map = new HashMap<>();
        CubeUtil.copyCubesPlaneMap(cubesPlaneMap, map);
        List<Integer> localSteps = new ArrayList<>();
        fillSquares(map);
        int step = getCurrentStep();
        List<Cube> allCubes = map.get(Const.CENTER_FACE).getCubes();
        for (int i = 1; i <= 3; i++) {
            int rotateMsg = Const.ROTATE_X_WHOLE_ANTI_CLOCKWISE;
            localSteps.add(rotateMsg);
            MagicCubeRotate.rotateCubes(allCubes, Const.X_AXIS, 90f);
            MagicCube.updateCubesFaces(allCubes, Const.X_AXIS, 90f);
            map.clear();
            CubeUtil.fillCubesPlaneByCubes(allCubes, map);
            fillSquares(map);
            int _step = getCurrentStep();
            if (_step > step) {
                step = _step;
                steps.clear();
                steps.addAll(localSteps);
            }
        }
        localSteps.clear();
        for (int i = 1; i <= 3; i++) {
            int rotateMsg = Const.ROTATE_Z_WHOLE_ANTI_CLOCKWISE;
            localSteps.add(rotateMsg);
            MagicCubeRotate.rotateCubes(allCubes, Const.Z_AXIS, 90f);
            MagicCube.updateCubesFaces(allCubes, Const.Z_AXIS, 90f);
            map.clear();
            CubeUtil.fillCubesPlaneByCubes(allCubes, map);
            fillSquares(map);
            int _step = getCurrentStep();
            if (_step > step) {
                step = _step;
                steps.clear();
                steps.addAll(localSteps);
            }
        }
    }

    private void fillSquares(Map<Integer, CubesPlane> cubesPlaneMap) {
        int facePos = -1;
        int squarePos;
        for (Integer key : cubesPlaneMap.keySet()) {
            if (key.equals(Const.CENTER_FACE)) continue;
            facePos++;
            squarePos = -1;
            for (Cube cube : cubesPlaneMap.get(key).getCubes()) {
                for (Square square : cube.getPlanes()) {
                    if (square.getOriginFace() == key) {
                        squarePos++;
                        squarePos = translateCubePosition(cube.getFaces(), key, facePos, square.getColor());
                        squares[facePos][squarePos] = square.getColor();
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private void useFormulaForStepThreeFront() {
        LayerFirstFormula.stepThreeFront(steps);
    }

    private void useFormulaForStepThreeRight() {
        LayerFirstFormula.stepThreeRight(steps);
    }

    private void useF2LFormulaForStepThree() {
        CFOPFormula.F2LForStepThree(steps);
    }

    private void useFormulaForStepFour() {
        LayerFirstFormula.stepFour(steps);
    }

    private void useFishOne() {
        LayerFirstFormula.fishOne(steps);
    }

    private void useFishTwo() {
        LayerFirstFormula.fishTwo(steps);
    }

    private void useFormulaPLL1() {
        CFOPFormula.PLL1(steps);
    }

    private void useFormulaPLL2() {
        CFOPFormula.PLL2(steps);
    }

    private void useFormulaPLL3() {
        CFOPFormula.PLL3(steps);
    }

    private void useFormulaPLL4() {
        CFOPFormula.PLL4(steps);
    }

    private void useFormulaPLL5() {
        CFOPFormula.PLL5(steps);
    }

    private void useFormulaPLL6() {
        CFOPFormula.PLL6(steps);
    }

    private void useFormulaPLL7() {
        CFOPFormula.PLL7(steps);
    }

    private void useFormulaPLL8() {
        CFOPFormula.PLL8(steps);
    }

    private void useFormulaPLL9() {
        CFOPFormula.PLL9(steps);
    }

    private void useFormulaPLL10() {
        CFOPFormula.PLL10(steps);
    }

    private void useFormulaPLL11() {
        CFOPFormula.PLL11(steps);
    }

    private void useFormulaPLL12() {
        CFOPFormula.PLL12(steps);
    }

    private void useFormulaPLL13() {
        CFOPFormula.PLL13(steps);
    }

    private void useFormulaPLL14() {
        CFOPFormula.PLL14(steps);
    }

    private void useFormulaPLL15() {
        CFOPFormula.PLL15(steps);
    }

    private void useFormulaPLL16() {
        CFOPFormula.PLL16(steps);
    }

    private void useFormulaPLL17() {
        CFOPFormula.PLL17(steps);
    }

    private void useFormulaPLL18() {
        CFOPFormula.PLL18(steps);
    }

    private void useFormulaPLL19() {
        CFOPFormula.PLL19(steps);
    }

    private void useFormulaPLL20() {
        CFOPFormula.PLL20(steps);
    }

    private void useFormulaPLL21() {
        CFOPFormula.PLL21(steps);
    }

    private void useFormulaPLLForStepSix() {
        if (squares[backFacePos][0] == squares[backFacePos][1] && squares[backFacePos][0] == squares[backFacePos][2]) {
            if (squares[rightFacePos][0] == squares[rightFacePos][2] && squares[rightFacePos][1] == squares[leftFacePos][0]) {
                if (frontColor != squares[leftFacePos][1]) {
                    MagicCubeFormula._d(steps);
                    return;
                } else {
                    useFormulaPLL1();
                    return;
                }
            } else if (squares[rightFacePos][0] == squares[rightFacePos][2] && squares[rightFacePos][1] == squares[frontFacePos][0]) {
                if (frontColor != squares[rightFacePos][1]) {
                    MagicCubeFormula._d(steps);
                    return;
                } else {
                    useFormulaPLL2();
                    return;
                }
            } else if (squares[rightFacePos][0] == squares[frontFacePos][0] && squares[rightFacePos][1] == squares[rightFacePos][2]) {
                if (frontColor != squares[rightFacePos][1]) {
                    MagicCubeFormula._d(steps);
                    return;
                } else {
                    useFormulaPLL10();
                    return;
                }
            }
        } else if (squares[leftFacePos][0] == squares[leftFacePos][1] && squares[leftFacePos][0] == squares[leftFacePos][2]) {
            if (squares[backFacePos][0] == squares[backFacePos][1] && squares[backFacePos][2] == squares[frontFacePos][2]) {
                if (frontColor != squares[rightFacePos][1]) {
                    MagicCubeFormula._d(steps);
                    return;
                } else {
                    useFormulaPLL9();
                    return;
                }
            } else if (squares[backFacePos][0] == squares[rightFacePos][2] && squares[backFacePos][1] == squares[rightFacePos][0] && squares[backFacePos][2] == squares[rightFacePos][1]) {
                if (frontColor != squares[backFacePos][1]) {
                    MagicCubeFormula._d(steps);
                    return;
                } else {
                    useFormulaPLL12();
                    return;
                }
            }
        } else if (squares[backFacePos][0] == squares[backFacePos][2] && squares[backFacePos][0] == squares[frontFacePos][1] && squares[backFacePos][1] == squares[frontFacePos][0] && squares[frontFacePos][0] == squares[frontFacePos][2]) {
            if (frontColor != squares[backFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL3();
                return;
            }
        } else if (squares[backFacePos][0] == squares[backFacePos][2] && squares[backFacePos][1] == squares[leftFacePos][0] && squares[rightFacePos][0] == squares[rightFacePos][2] && squares[rightFacePos][1] == squares[frontFacePos][0]) {
            if (frontColor != squares[rightFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL4();
                return;
            }
        } else if (squares[backFacePos][0] == squares[backFacePos][1] && squares[backFacePos][2] == squares[rightFacePos][1] && squares[rightFacePos][0] == squares[rightFacePos][2]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL5();
                return;
            }
        } else if (squares[backFacePos][0] == squares[backFacePos][1] && squares[backFacePos][2] == squares[frontFacePos][1] && squares[leftFacePos][0] == squares[rightFacePos][0] && squares[rightFacePos][1] == squares[frontFacePos][0] && squares[rightFacePos][2] == squares[backFacePos][0]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL6();
                return;
            }
        } else if (squares[backFacePos][0] == squares[frontFacePos][0] && squares[backFacePos][1] == squares[rightFacePos][0] && squares[backFacePos][2] == squares[frontFacePos][2] && squares[rightFacePos][2] == squares[frontFacePos][1]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL7();
                return;
            }
        } else if (squares[backFacePos][0] == squares[backFacePos][1] && squares[backFacePos][2] == squares[frontFacePos][2] && squares[rightFacePos][0] == squares[frontFacePos][0] && squares[rightFacePos][1] == squares[leftFacePos][0] && squares[rightFacePos][2] == squares[backFacePos][0]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL8();
                return;
            }
        } else if (squares[backFacePos][0] == squares[frontFacePos][0] && squares[frontFacePos][0] == squares[frontFacePos][1] && squares[frontFacePos][2] == squares[backFacePos][2] && squares[backFacePos][1] == squares[leftFacePos][2]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL11();
                return;
            }
        } else if (squares[backFacePos][0] == squares[frontFacePos][0] && squares[frontFacePos][0] == squares[frontFacePos][1] && squares[frontFacePos][2] == squares[backFacePos][2] && squares[backFacePos][1] == squares[leftFacePos][0]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL13();
                return;
            }
        } else if (squares[backFacePos][0] == squares[backFacePos][2] && squares[backFacePos][0] == squares[rightFacePos][1] && squares[backFacePos][1] == squares[rightFacePos][0] && squares[rightFacePos][2] == squares[frontFacePos][1]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL14();
                return;
            }
        } else if (squares[backFacePos][0] == squares[rightFacePos][2] && squares[backFacePos][1] == squares[rightFacePos][0] && squares[backFacePos][2] == squares[leftFacePos][1] && squares[rightFacePos][1] == squares[frontFacePos][0]) {
            if (frontColor != squares[rightFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL15();
                return;
            }
        } else if (squares[backFacePos][0] == squares[backFacePos][1] && squares[backFacePos][0] == squares[frontFacePos][0] && squares[backFacePos][2] == squares[frontFacePos][1] && squares[frontFacePos][1] == squares[frontFacePos][2]) {
            if (frontColor != squares[backFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL16();
                return;
            }
        } else if (squares[backFacePos][0] == squares[frontFacePos][0] & squares[frontFacePos][0] == squares[frontFacePos][1] && squares[backFacePos][1] == squares[backFacePos][2] && squares[backFacePos][1] == squares[frontFacePos][2]) {
            if (frontColor != squares[backFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL17();
                return;
            }
        } else if (squares[backFacePos][0] == squares[frontFacePos][1] && squares[backFacePos][1] == squares[backFacePos][2] && squares[backFacePos][1] == squares[frontFacePos][2] && squares[frontFacePos][0] == squares[leftFacePos][1]) {
            if (frontColor != squares[rightFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL18();
                return;
            }
        } else if (squares[backFacePos][0] == squares[rightFacePos][1] && squares[rightFacePos][1] == squares[rightFacePos][2] && squares[backFacePos][1] == squares[rightFacePos][0] && squares[backFacePos][2] == squares[frontFacePos][2]) {
            if (frontColor != squares[leftFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL19();
                return;
            }
        } else if (squares[backFacePos][0] == squares[rightFacePos][2] && squares[backFacePos][1] == squares[frontFacePos][0] && squares[backFacePos][2] == squares[frontFacePos][2] && squares[frontFacePos][1] == squares[frontFacePos][2]) {
            if (frontColor != squares[frontFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL20();
                return;
            }
        } else if (squares[rightFacePos][0] == squares[rightFacePos][1] && squares[rightFacePos][0] == squares[frontFacePos][0] && squares[rightFacePos][2] == squares[frontFacePos][1] && squares[frontFacePos][2] == squares[backFacePos][2]) {
            if (frontColor != squares[backFacePos][1]) {
                MagicCubeFormula._d(steps);
                return;
            } else {
                useFormulaPLL21();
                return;
            }
        }
        MagicCubeFormula.U(steps);
    }

    private void rotateForStepSix() {
        useFormulaPLLForStepSix();
    }

    private void rotateForStepFive() {
        int topSquareCount = 0;
        for (int j = 0; j < FACE_SQUARE_SIZE; j++) {
            if (squares[topFacePos][j] == topColor) {
                topSquareCount++;
            }
        }
        int rest = FACE_SQUARE_SIZE - topSquareCount;
        if (rest == 4) {
            if (squares[frontFacePos][2] == topColor && squares[leftFacePos][0] == topColor && squares[leftFacePos][2] == topColor && squares[backFacePos][2] == topColor) {
                useFishOne();
            } else if (squares[leftFacePos][0] == topColor && squares[leftFacePos][2] == topColor && squares[rightFacePos][0] == topColor && squares[rightFacePos][2] == topColor) {
                useFishOne();
            } else {
                MagicCubeFormula.y(steps);
            }
        } else if (rest == 3) {
            if (squares[frontFacePos][0] == topColor && squares[rightFacePos][2] == topColor && squares[backFacePos][2] == topColor) {
                useFishOne();
            } else if (squares[frontFacePos][2] == topColor && squares[leftFacePos][2] == topColor && squares[rightFacePos][0] == topColor) {
                useFishTwo();
            } else {
                MagicCubeFormula.y(steps);
            }
        } else if (rest == 2) {
            if (squares[frontFacePos][0] == topColor && squares[backFacePos][0] == topColor) {
                useFishOne();
            } else if (squares[backFacePos][0] == topColor && squares[backFacePos][2] == topColor) {
                useFishOne();
            } else if (squares[backFacePos][0] == topColor && squares[rightFacePos][2] == topColor) {
                useFishOne();
            } else {
                MagicCubeFormula.y(steps);
            }
        }
    }

    private void rotateForStepFour() {
        if (squares[frontFacePos][1] == topColor && squares[rightFacePos][1] == topColor) {
            useFormulaForStepFour();
        } else if (squares[topFacePos][5] == topColor && squares[topFacePos][7] == topColor) {
            useFormulaForStepFour();
        } else if (squares[topFacePos][3] == topColor && squares[topFacePos][5] == topColor && squares[frontFacePos][1] == topColor) {
            useFormulaForStepFour();
        } else {
            MagicCubeFormula.y(steps);
        }
    }

    private void rotateForStepThree() {
        if (squares[frontFacePos][5] == frontColor && squares[rightFacePos][5] == rightColor) {
            MagicCubeFormula.y(steps);
        } else if (squares[topFacePos][7] == rightColor && squares[frontFacePos][1] == frontColor) {
            useFormulaForStepThreeFront();
        } else if (squares[topFacePos][5] == frontColor && squares[rightFacePos][1] == rightColor) {
            useFormulaForStepThreeRight();
        } else if (squares[frontFacePos][5] == rightColor && squares[rightFacePos][5] == frontColor) {
            useF2LFormulaForStepThree();
        } else {
            List<Integer> colors = new ArrayList<>();
            colors.add(squares[topFacePos][1]);
            colors.add(squares[backFacePos][1]);
            if (colors.contains(frontColor) && colors.contains(rightColor)) {
                MagicCubeFormula.U(steps);
            } else {
                colors.clear();
                colors.add(squares[topFacePos][3]);
                colors.add(squares[leftFacePos][1]);
                if (colors.contains(frontColor) && colors.contains(rightColor)) {
                    MagicCubeFormula.U(steps);
                } else {
                    colors.clear();
                    colors.add(squares[topFacePos][5]);
                    colors.add(squares[rightFacePos][1]);
                    if (colors.contains(frontColor) && colors.contains(rightColor)) {
                        MagicCubeFormula.U(steps);
                    } else {
                        colors.clear();
                        colors.add(squares[topFacePos][7]);
                        colors.add(squares[frontFacePos][1]);
                        if (colors.contains(frontColor) && colors.contains(rightColor)) {
                            MagicCubeFormula.U(steps);
                        } else {
                            colors.clear();
                            colors.add(squares[frontFacePos][5]);
                            colors.add(squares[rightFacePos][5]);
                            if (!colors.contains(topColor)) {
                                if (squares[topFacePos][7] == topColor || squares[frontFacePos][1] == topColor) {
                                    useFormulaForStepThreeFront();
                                } else {
                                    MagicCubeFormula.U(steps);
                                }
                            } else {
                                MagicCubeFormula.y(steps);
                            }
                        }
                    }
                }
            }
        }
    }

    private void rotateForStepTwo() {

        if ((squares[frontFacePos][6] == frontColor && squares[bottomFacePos][6] == bottomColor && squares[leftFacePos][8] == leftColor) &&
                (squares[frontFacePos][8] == frontColor && squares[bottomFacePos][8] == bottomColor && squares[rightFacePos][8] == rightFacePos)) {
            MagicCubeFormula.y(steps);
        } else {
            List<Integer> colors = new ArrayList<>();
            colors.add(squares[frontFacePos][0]);
            colors.add(squares[leftFacePos][2]);
            colors.add(squares[topFacePos][6]);
            if (colors.contains(frontColor) && colors.contains(leftColor) && colors.contains(bottomColor)) {
                if (squares[frontFacePos][0] == bottomColor) {
                    MagicCubeFormula._U(steps);
                    MagicCubeFormula.L(steps);
                    MagicCubeFormula.U(steps);
                    MagicCubeFormula._L(steps);
                } else if (squares[leftFacePos][2] == bottomColor) {
                    MagicCubeFormula.U(steps);
                    MagicCubeFormula.F(steps);
                    MagicCubeFormula._U(steps);
                    MagicCubeFormula._F(steps);
                } else if (squares[topFacePos][6] == bottomColor) {
                    MagicCubeFormula.L(steps);
                    MagicCubeFormula.U(steps);
                    MagicCubeFormula._L(steps);
                    MagicCubeFormula._U2(steps);
                }
            } else {
                colors.clear();
                colors.add(squares[frontFacePos][2]);
                colors.add(squares[rightFacePos][2]);
                colors.add(squares[topFacePos][8]);
                if (colors.contains(frontColor) && colors.contains(rightColor) && colors.contains(bottomColor)) {
                    if (squares[frontFacePos][2] == bottomColor) {
                        MagicCubeFormula.U(steps);
                        MagicCubeFormula.R(steps);
                        MagicCubeFormula._U(steps);
                        MagicCubeFormula._R(steps);
                    } else if (squares[rightFacePos][2] == bottomColor) {
                        MagicCubeFormula._U(steps);
                        MagicCubeFormula._F(steps);
                        MagicCubeFormula.U(steps);
                        MagicCubeFormula.F(steps);
                    } else if (squares[topFacePos][8] == bottomColor) {
                        MagicCubeFormula.R(steps);
                        MagicCubeFormula._U(steps);
                        MagicCubeFormula._R(steps);
                        MagicCubeFormula.U2(steps);
                    }
                } else {
                    colors.clear();
                    colors.add(squares[backFacePos][0]);
                    colors.add(squares[leftFacePos][0]);
                    colors.add(squares[topFacePos][0]);
                    if (colors.contains(frontColor) && colors.contains(leftColor) && colors.contains(bottomColor)) {
                        MagicCubeFormula._U(steps);
                    } else if (colors.contains(frontColor) && colors.contains(rightColor) && colors.contains(bottomColor)) {
                        MagicCubeFormula._U2(steps);
                    } else {
                        colors.clear();
                        colors.add(squares[backFacePos][2]);
                        colors.add(squares[rightFacePos][0]);
                        colors.add(squares[topFacePos][2]);
                        if (colors.contains(frontColor) && colors.contains(rightColor) && colors.contains(bottomColor)) {
                            MagicCubeFormula.U(steps);
                        } else if (colors.contains(frontColor) && colors.contains(leftColor) && colors.contains(bottomColor)) {
                            MagicCubeFormula.U2(steps);
                        } else {
                            colors.clear();
                            colors.add(squares[frontFacePos][6]);
                            colors.add(squares[bottomFacePos][6]);
                            colors.add(squares[leftFacePos][8]);
                            if ((squares[frontFacePos][6] != frontColor || squares[leftFacePos][8] != leftColor) && colors.contains(bottomColor)) {
                                colors.clear();
                                colors.add(squares[frontFacePos][2]);
                                colors.add(squares[topFacePos][8]);
                                colors.add(squares[rightFacePos][2]);
                                if (colors.contains(bottomColor)) {
                                    MagicCubeFormula._U(steps);
                                } else {
                                    MagicCubeFormula.L(steps);
                                    MagicCubeFormula.U(steps);
                                    MagicCubeFormula._L(steps);
                                }
                            } else {
                                colors.clear();
                                colors.add(squares[frontFacePos][8]);
                                colors.add(squares[rightFacePos][8]);
                                colors.add(squares[bottomFacePos][8]);
                                if ((squares[frontFacePos][8] != frontColor || squares[rightFacePos][8] != rightColor) && colors.contains(bottomColor)) {
                                    colors.clear();
                                    colors.add(squares[frontFacePos][0]);
                                    colors.add(squares[topFacePos][6]);
                                    colors.add(squares[leftFacePos][2]);
                                    if (colors.contains(bottomColor)) {
                                        MagicCubeFormula.U(steps);
                                    } else {
                                        MagicCubeFormula.R(steps);
                                        MagicCubeFormula._U(steps);
                                        MagicCubeFormula._R(steps);
                                    }
                                } else {
                                    MagicCubeFormula.y(steps);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void rotateForStepOne() {
        int bottomColorCount = 0;
        if (squares[bottomFacePos][1] == bottomColor && squares[backFacePos][7] == backColor) {
            bottomColorCount++;
        }
        if (squares[bottomFacePos][3] == bottomColor && squares[leftFacePos][7] == leftColor) {
            bottomColorCount++;
        }
        if (squares[bottomFacePos][5] == bottomColor && squares[rightFacePos][7] == rightColor) {
            bottomColorCount++;
        }
        if (squares[bottomFacePos][7] == bottomColor && squares[frontFacePos][7] == frontColor) {
            bottomColorCount++;
        }
        if (squares[topFacePos][1] == bottomColor) {
            bottomColorCount++;
        }
        if (squares[topFacePos][3] == bottomColor) {
            bottomColorCount++;
        }
        if (squares[topFacePos][5] == bottomColor) {
            bottomColorCount++;
        }
        if (squares[topFacePos][7] == bottomColor) {
            bottomColorCount++;
        }
        if (bottomColorCount == 4) {
            if (squares[topFacePos][7] != bottomColor) {
                MagicCubeFormula.y(steps);
            } else {
                if (squares[frontFacePos][1] == frontColor) {
                    MagicCubeFormula.F(steps);
                    MagicCubeFormula.F(steps);
                } else if (squares[frontFacePos][1] == leftColor) {
                    MagicCubeFormula.U(steps);
                    MagicCubeFormula._L(steps);
                    MagicCubeFormula._L(steps);
                } else if (squares[frontFacePos][1] == squares[backFacePos][4]) {
                    MagicCubeFormula.U2(steps);
                    MagicCubeFormula._B(steps);
                    MagicCubeFormula._B(steps);
                } else if (squares[frontFacePos][1] == squares[rightFacePos][4]) {
                    MagicCubeFormula._U(steps);
                    MagicCubeFormula._R(steps);
                    MagicCubeFormula._R(steps);
                }
            }
        } else {
            if (squares[frontFacePos][3] == bottomColor) {
                if (squares[topFacePos][3] != bottomColor) {
                    MagicCubeFormula.L(steps);
                } else {
                    MagicCubeFormula.U(steps);
                }
            } else if (squares[frontFacePos][5] == bottomColor) {
                if (squares[topFacePos][5] != bottomColor) {
                    MagicCubeFormula.R(steps);
                } else {
                    MagicCubeFormula.U(steps);
                }
            } else if (squares[frontFacePos][1] == bottomColor || squares[frontFacePos][7] == bottomColor) {
                MagicCubeFormula.F(steps);
            } else if (squares[bottomFacePos][7] == bottomColor) {
                if (squares[topFacePos][7] == bottomColor) {
                    MagicCubeFormula._U(steps);
                } else {
                    MagicCubeFormula.F(steps);
                }
            } else {
                MagicCubeFormula.y(steps);
            }
        }

    }

    private boolean isNeedRotate(int step) {
        if (curStep != step) {
            return false;
        }
        return true;
    }

    private boolean isFinishedStepOne() {
        if (curStep > 1) {
            return true;
        } else {
            if (squares[bottomFacePos][1] == bottomColor
                    && squares[bottomFacePos][3] == bottomColor
                    && squares[bottomFacePos][5] == bottomColor
                    && squares[bottomFacePos][7] == bottomColor) {
                if (squares[frontFacePos][7] == frontColor
                        && squares[leftFacePos][7] == leftColor
                        && squares[backFacePos][7] == backColor
                        && squares[rightFacePos][7] == rightColor
                        ) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFinishedStepTwo() {
        if (squares[frontFacePos][6] == frontColor && squares[frontFacePos][7] == frontColor && squares[frontFacePos][8] == frontColor) {
            if (squares[leftFacePos][6] == leftColor && squares[leftFacePos][7] == leftColor && squares[leftFacePos][8] == leftColor) {
                if (squares[backFacePos][6] == backColor && squares[backFacePos][7] == backColor && squares[backFacePos][8] == backColor) {
                    if (squares[rightFacePos][6] == rightColor && squares[rightFacePos][7] == rightColor && squares[rightFacePos][8] == rightColor) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isFinishedStepThree() {
        if (squares[frontFacePos][3] == frontColor
                && squares[frontFacePos][5] == frontColor
                && squares[leftFacePos][3] == leftColor
                && squares[leftFacePos][5] == leftColor
                && squares[backFacePos][3] == backColor
                && squares[backFacePos][5] == backColor
                && squares[rightFacePos][3] == rightColor
                && squares[rightFacePos][5] == rightColor) {
            return true;
        }
        return false;
    }

    private boolean isFinishedStepFour() {
        if (squares[topFacePos][1] == topColor
                && squares[topFacePos][3] == topColor
                && squares[topFacePos][5] == topColor
                && squares[topFacePos][7] == topColor
                ) {
            return true;
        } else {
            int rest = FACE_SQUARE_SIZE - getTopSquareCount();
            if (rest == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isFinishedStepFive() {
        int rest = FACE_SQUARE_SIZE - getTopSquareCount();
        if (rest == 0) {
            return true;
        }
        return false;
    }

    private boolean isFinishedStepSeven() {
        for (int i = 0; i < FACES_SIZE; i++) {
            for (int j = 0; j < FACE_SQUARE_SIZE; j++) {
                if (squares[i][j] != squares[i][4]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void rotateForStepOneTransaction() {
        if (!isNeedRotate(1)) {
            return;
        } else if (isFinishedStepOne()) {
            curStep = 2;
            return;
        } else {
            rotateForStepOne();
        }
    }

    private void rotateForStepTwoTransaction() {
        if (!isNeedRotate(2)) {
            return;
        } else if (isFinishedStepTwo()) {
            curStep = 3;
            return;
        } else {
            rotateForStepTwo();
        }
    }

    private void rotateForStepThreeTransaction() {
        if (!isNeedRotate(3)) {
            return;
        } else if (isFinishedStepThree()) {
            curStep = 4;
            return;
        } else {
            rotateForStepThree();
        }
    }

    private void rotateForStepFourTransaction() {
        if (!isNeedRotate(4)) {
            return;
        } else if (isFinishedStepFour()) {
            curStep = 5;
            return;
        } else {
            rotateForStepFour();
        }
    }

    private void rotateForStepFiveTransaction() {
        if (!isNeedRotate(5)) {
            return;
        } else if (isFinishedStepSeven() || isFinishedStepFive()) {
            curStep = 6;
            return;
        } else {
            rotateForStepFive();
        }
    }

    private void rotateForStepSixTransaction() {
        if (!isNeedRotate(6)) {
            return;
        } else if (isFinishedStepSeven()) {
            curStep = 8;
            return;
        } else {
            rotateForStepSix();
        }
    }

    private int getTopSquareCount() {
        int topSquareCount = 0;
        for (int j = 0; j < FACE_SQUARE_SIZE; j++) {
            if (squares[topFacePos][j] == topColor) {
                topSquareCount++;
            }
        }
        return topSquareCount;
    }

    private int translateCubePosition(List<Integer> faces, int key, int facePos, int color) {
        int pos = -1;
        switch (key) {
            case Const.FRONT_FACE:
                pos = getSquarePosition(faces, Const.LEFT_FACE, Const.RIGHT_FACE, Const.TOP_FACE);
                frontFacePos = facePos;
                if (pos == 4) {
                    frontColor = color;
                }
                break;
            case Const.BACK_FACE:
                pos = getSquarePosition(faces, Const.LEFT_FACE, Const.RIGHT_FACE, Const.TOP_FACE);
                backFacePos = facePos;
                if (pos == 4) {
                    backColor = color;
                }
                break;
            case Const.RIGHT_FACE:
                pos = getSquarePosition(faces, Const.BACK_FACE, Const.FRONT_FACE, Const.TOP_FACE);
                rightFacePos = facePos;
                if (pos == 4) {
                    rightColor = color;
                }
                break;
            case Const.LEFT_FACE:
                pos = getSquarePosition(faces, Const.BACK_FACE, Const.FRONT_FACE, Const.TOP_FACE);
                leftFacePos = facePos;
                if (pos == 4) {
                    leftColor = color;
                }
                break;
            case Const.BOTTOM_FACE:
                pos = getSquarePosition(faces, Const.LEFT_FACE, Const.RIGHT_FACE, Const.BACK_FACE);
                bottomFacePos = facePos;
                if (pos == 4) {
                    bottomColor = color;
                }
                break;
            case Const.TOP_FACE:
                pos = getSquarePosition(faces, Const.LEFT_FACE, Const.RIGHT_FACE, Const.BACK_FACE);
                topFacePos = facePos;
                if (pos == 4) {
                    topColor = color;
                }
                break;
        }
        return pos;
    }

    private int getSquarePosition(List<Integer> faces, int leftFace, int rightFace,
                                  int topFace) {
        int facesSize = faces.size();
        if (facesSize == 1) {
            return 4;
        } else if (facesSize == 2) {
            if (faces.contains(leftFace)) {
                return 3;
            } else if (faces.contains(rightFace)) {
                return 5;
            } else if (faces.contains(topFace)) {
                return 1;
            } else {
                return 7;
            }
        } else if (facesSize == 3) {
            if (faces.contains(leftFace)) {
                if (faces.contains(topFace)) {
                    return 0;
                } else {
                    return 6;
                }
            } else if (faces.contains(rightFace)) {
                if (faces.contains(topFace)) {
                    return 2;
                } else {
                    return 8;
                }
            }
        }
        return -1;
    }
}
