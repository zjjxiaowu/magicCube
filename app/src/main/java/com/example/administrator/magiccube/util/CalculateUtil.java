package com.example.administrator.magiccube.util;

import com.example.administrator.magiccube.constant.Const;
import com.example.administrator.magiccube.entity.Cube;
import com.example.administrator.magiccube.entity.Square;
import com.example.administrator.magiccube.entity.Triangle;
import com.example.administrator.magiccube.entity.Vertex;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class CalculateUtil {
    public static void fillElementList(List<Cube> cubes, List<Float> vertexList, List<Float> colorList, List<Float> linesList, List<Float> linesColorList, Map<String, Integer> lineMap) {
        for (Cube cube : cubes) {
            for (Square plane : cube.getPlanes()) {
                CalculateUtil.fillLinesVertexAndColorList(plane, linesList, linesColorList, lineMap);
                for (Triangle triangle : plane.getTriangles()) {
                    for (Vertex vertex : triangle.getVertices()) {
                        vertexList.add(vertex.getX());
                        vertexList.add(vertex.getY());
                        vertexList.add(vertex.getZ());
                        colorList.add(vertex.getColorR());
                        colorList.add(vertex.getColorG());
                        colorList.add(vertex.getColorB());
                        colorList.add(vertex.getColorA());
                    }
                }
            }
        }
    }

    public static void fillLinesVertexAndColorList(Square plane, List<Float> linesList, List<Float> linesColorList, Map<String, Integer> lineMap) {
        float x1 = plane.getVertex1().getX();
        float y1 = plane.getVertex1().getY();
        float z1 = plane.getVertex1().getZ();
        float x2 = plane.getVertex2().getX();
        float y2 = plane.getVertex2().getY();
        float z2 = plane.getVertex2().getZ();

        float x3 = plane.getVertex3().getX();
        float y3 = plane.getVertex3().getY();
        float z3 = plane.getVertex3().getZ();
        float x4 = plane.getVertex4().getX();
        float y4 = plane.getVertex4().getY();
        float z4 = plane.getVertex4().getZ();

        String lineStr1 = x1 + "_" + y1 + "_" + z1 + "_" + x2 + "_" + y2 + "_" + z2;
        String lineStr2 = x3 + "_" + y3 + "_" + z3 + "_" + x4 + "_" + y4 + "_" + z4;
        String lineStr3 = x1 + "_" + y1 + "_" + z1 + "_" + x3 + "_" + y3 + "_" + z3;
        String lineStr4 = x2 + "_" + y2 + "_" + z2 + "_" + x4 + "_" + y4 + "_" + z4;
        if (lineMap.get(lineStr1) == null) {
            linesList.add(plane.getVertex1().getX());
            linesList.add(plane.getVertex1().getY());
            linesList.add(plane.getVertex1().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);
            linesList.add(plane.getVertex2().getX());
            linesList.add(plane.getVertex2().getY());
            linesList.add(plane.getVertex2().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);
            lineMap.put(lineStr1, 1);
        }

        if (lineMap.get(lineStr2) == null) {
            linesList.add(plane.getVertex3().getX());
            linesList.add(plane.getVertex3().getY());
            linesList.add(plane.getVertex3().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);
            linesList.add(plane.getVertex4().getX());
            linesList.add(plane.getVertex4().getY());
            linesList.add(plane.getVertex4().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);
            lineMap.put(lineStr2, 1);
        }
        if (lineMap.get(lineStr3) == null) {
            linesList.add(plane.getVertex1().getX());
            linesList.add(plane.getVertex1().getY());
            linesList.add(plane.getVertex1().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);
            linesList.add(plane.getVertex3().getX());
            linesList.add(plane.getVertex3().getY());
            linesList.add(plane.getVertex3().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);
            lineMap.put(lineStr3, 1);
        }
        if (lineMap.get(lineStr4) == null) {
            linesList.add(plane.getVertex2().getX());
            linesList.add(plane.getVertex2().getY());
            linesList.add(plane.getVertex2().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);

            linesList.add(plane.getVertex4().getX());
            linesList.add(plane.getVertex4().getY());
            linesList.add(plane.getVertex4().getZ());
            linesColorList.add(Const.RGBA_BLACK_R);
            linesColorList.add(Const.RGBA_BLACK_G);
            linesColorList.add(Const.RGBA_BLACK_B);
            linesColorList.add(Const.RGBA_DEFAULT_A);
            lineMap.put(lineStr4, 1);
        }
    }
}
