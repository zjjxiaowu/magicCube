package com.example.administrator.magiccube;

import android.opengl.GLSurfaceView;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.Cube;
import com.example.administrator.magiccube.entity.CubesPlane;
import com.example.administrator.magiccube.entity.Square;
import com.example.administrator.magiccube.entity.Triangle;
import com.example.administrator.magiccube.entity.Vertex;
import com.example.administrator.magiccube.util.CubeUtil;
import com.example.administrator.magiccube.util.MatrixUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class MagicCubeRotate {
    public static void rotateCubes(List<Cube> cubes, int axis, float angle) {
        for (Cube cube : cubes) {
            for (Vertex vertex : cube.getVertices()) {
                if (axis == Const.X_AXIS) {
                    MatrixUtil.rotateAroundX(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                } else if (axis == Const.Y_AXIS) {
                    MatrixUtil.rotateAroundY(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                } else if (axis == Const.Z_AXIS) {
                    MatrixUtil.rotateAroundZ(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                }
            }
        }
    }

    public static void rotateSquares(List<Square> squares, int axis, float angle) {
        for (Square square : squares) {
            Vertex vertex1 = square.getVertex1();
            Vertex vertex2 = square.getVertex2();
            Vertex vertex3 = square.getVertex3();
            Vertex vertex4 = square.getVertex4();
            if (axis == Const.X_AXIS) {
                MatrixUtil.rotateAroundX(vertex1, vertex1.getX(), vertex1.getY(), vertex1.getZ(), angle);
                MatrixUtil.rotateAroundX(vertex2, vertex2.getX(), vertex2.getY(), vertex2.getZ(), angle);
                MatrixUtil.rotateAroundX(vertex3, vertex3.getX(), vertex3.getY(), vertex3.getZ(), angle);
                MatrixUtil.rotateAroundX(vertex4, vertex4.getX(), vertex4.getY(), vertex4.getZ(), angle);
            } else if (axis == Const.Y_AXIS) {
                MatrixUtil.rotateAroundY(vertex1, vertex1.getX(), vertex1.getY(), vertex1.getZ(), angle);
                MatrixUtil.rotateAroundY(vertex2, vertex2.getX(), vertex2.getY(), vertex2.getZ(), angle);
                MatrixUtil.rotateAroundY(vertex3, vertex3.getX(), vertex3.getY(), vertex3.getZ(), angle);
                MatrixUtil.rotateAroundY(vertex4, vertex4.getX(), vertex4.getY(), vertex4.getZ(), angle);
            } else if (axis == Const.Z_AXIS) {
                MatrixUtil.rotateAroundZ(vertex1, vertex1.getX(), vertex1.getY(), vertex1.getZ(), angle);
                MatrixUtil.rotateAroundZ(vertex2, vertex2.getX(), vertex2.getY(), vertex2.getZ(), angle);
                MatrixUtil.rotateAroundZ(vertex3, vertex3.getX(), vertex3.getY(), vertex3.getZ(), angle);
                MatrixUtil.rotateAroundZ(vertex4, vertex4.getX(), vertex4.getY(), vertex4.getZ(), angle);
            }
            for (Triangle triangle : square.getTriangles()) {
                for (Vertex vertex : triangle.getVertices()) {
                    if (axis == Const.X_AXIS) {
                        MatrixUtil.rotateAroundX(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                    } else if (axis == Const.Y_AXIS) {
                        MatrixUtil.rotateAroundY(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                    } else if (axis == Const.Z_AXIS) {
                        MatrixUtil.rotateAroundZ(vertex, vertex.getX(), vertex.getY(), vertex.getZ(), angle);
                    }
                }
            }
        }
    }
    public static int rotateFace(int face, int axis, float angle) {
        float originAngle = 0f;
        double x = 0d;
        double y = 0d;
        if (axis == Const.X_AXIS || axis == Const.X_WHOLE_AXIS) {
            if (face == Const.LEFT_FACE || face == Const.RIGHT_FACE) {
                return face;
            } else if (face == Const.TOP_FACE) {
                originAngle = Const.RIGHT_ANGLE;
            } else if (face == Const.FRONT_FACE) {
                originAngle = 180f;
            } else if (face == Const.BOTTOM_FACE) {
                originAngle = 270f;
            }
            originAngle += angle;
            x = (double) (Const.BACK_FACE);
            y = (double) (Const.TOP_FACE);
            return MatrixUtil.rotateFomXToY(x, y, originAngle);

        } else if (axis == Const.Y_AXIS || axis == Const.Y_WHOLE_AXIS) {
            if (face == Const.TOP_FACE || face == Const.BOTTOM_FACE) {
                return face;
            } else if (face == Const.BACK_FACE) {
                originAngle = Const.RIGHT_ANGLE;
            } else if (face == Const.LEFT_FACE) {
                originAngle = 180f;
            } else if (face == Const.FRONT_FACE) {
                originAngle = 270f;
            }
            originAngle += angle;
            x = (double) (Const.RIGHT_FACE);
            y = (double) (Const.BACK_FACE);
            return MatrixUtil.rotateFomXToY(x, y, originAngle);
        } else if (axis == Const.Z_AXIS || axis == Const.Z_WHOLE_AXIS) {
            if (face == Const.FRONT_FACE || face == Const.BACK_FACE) {
                return face;
            } else if (face == Const.TOP_FACE) {
                originAngle = Const.RIGHT_ANGLE;
            } else if (face == Const.LEFT_FACE) {
                originAngle = 180f;
            } else if (face == Const.BOTTOM_FACE) {
                originAngle = 270f;
            }
            originAngle += angle;
            x = (double) (Const.RIGHT_FACE);
            y = (double) (Const.TOP_FACE);
            return MatrixUtil.rotateFomXToY(x, y, originAngle);
        }
        return face;
    }
    public static void rotate(List<Cube> cubes, List<Cube> cubesShow, Map<Integer, CubesPlane> cubesPlaneMapShow,RotateMsg rotateMsg) {
        CubeUtil.copyCubes(cubes, cubesShow);
        List<Cube> _cubes = cubesPlaneMapShow.get(rotateMsg.face).getCubes();
        rotateCubes(_cubes, rotateMsg.axis, rotateMsg.rotateAngle);
    }
    public static void rotateAnimation(int rotateMsg, GLSurfaceView view) {
        Date curDate = new Date(System.currentTimeMillis());
        float angle = Const.RIGHT_ANGLE;

        if (view == null || rotateMsg == -1) return;
        boolean isRendering = true;
        MagicCubeGlobalValue.isRotating = true;
        MagicCube magicCube = MagicCube.getSingleInstance();
        RotateMsg rotateMsgEntity = RotateMsg.translateRotateMsg(rotateMsg);
        while (isRendering) {
            Date endDate = new Date(System.currentTimeMillis());
            long diff = endDate.getTime() - curDate.getTime();
            float rotateAngle = angle / Const.ROTATE_TIME_MILLIS * diff;
            rotateAngle = rotateAngle >= Const.RIGHT_ANGLE ? Const.RIGHT_ANGLE : rotateAngle;
            rotateMsgEntity.rotateAngle = rotateMsgEntity.angle > 0 ? rotateAngle : -rotateAngle;
            rotate(magicCube.cubes,magicCube.cubesShow,magicCube.cubesPlaneMapShow,rotateMsgEntity);
            magicCube.fillElementArray();
            view.requestRender();
            if (rotateAngle == Const.RIGHT_ANGLE) {
                finishRotate(magicCube.cubes,magicCube.cubesShow,magicCube.cubesPlaneMapShow,rotateMsgEntity);
                isRendering = false;
                MagicCubeGlobalValue.isRotating = false;
            }
        }
    }
    public static void finishRotate(List<Cube> cubes, List<Cube> cubesShow,Map<Integer, CubesPlane> cubesPlaneMapShow,RotateMsg rotateMsgEntity) {
        MagicCube.updateCubeAndMapInfoAfterRotate(rotateMsgEntity, cubesPlaneMapShow);
        CubeUtil.copyCubes(cubesShow, cubes);
    }
}
