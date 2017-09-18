package com.example.administrator.magiccube.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class Cube extends Plane {
    private List<Square> planes;
    private List<Integer> faces = new ArrayList<>();
    public void rightMove(Cube cube, float moveSize) {
        for (Square plane : cube.getPlanes()) {
            plane.rightMove(plane, moveSize);
        }
    }

    public void leftMove(Cube cube, float moveSize) {
        for (Square plane : cube.getPlanes()) {
            plane.leftMove(plane, moveSize);
        }
    }

    public void upMove(Cube cube, float moveSize) {
        for (Square plane : cube.getPlanes()) {
            plane.upMove(plane, moveSize);
        }
    }

    public void downMove(Cube cube, float moveSize) {
        for (Square plane : cube.getPlanes()) {
            plane.downMove(plane, moveSize);
        }
    }

    public void frontMove(Cube cube, float moveSize) {
        for (Square plane : cube.getPlanes()) {
            plane.frontMove(plane, moveSize);
        }
    }

    public void backMove(Cube cube, float moveSize) {
        for (Square plane : cube.getPlanes()) {
            plane.backMove(plane, moveSize);
        }
    }

    public void setFaces(List<Integer> faces) {
        this.faces = faces;
    }

    public List<Integer> getFaces() {
        return faces;
    }

    public void setPlanes(List<Square> planes) {
        this.planes = planes;
    }

    public List<Square> getPlanes() {
        return planes;
    }

    public void copyCube(Cube src, Cube dest) {
        boolean isNew = false;
        if (dest.getPlanes() == null) isNew = true;
        if (isNew) {
            dest.setPlanes(new ArrayList<Square>());
            dest.setFaces(new ArrayList<Integer>());
        }
        dest.setFaces(new ArrayList<Integer>());
        for (int i = 0; i < src.getFaces().size(); i++) {
            int face = src.getFaces().get(i);
            dest.getFaces().add(face);
        }
        for (int i = 0; i < src.getPlanes().size(); i++) {
            Square plane = src.getPlanes().get(i);
            Square copyPlane = null;
            if (isNew) copyPlane = new Square();
            else copyPlane = dest.getPlanes().get(i);
            plane.copySquare(plane, copyPlane);
            if (isNew) dest.getPlanes().add(copyPlane);
        }
    }

    public List<Vertex> getVertices() {
        List<Vertex> vertices = new ArrayList<>();
        for (Square square : planes) {
            vertices.add(square.getVertex1());
            vertices.add(square.getVertex2());
            vertices.add(square.getVertex3());
            vertices.add(square.getVertex4());
            for (Triangle triangle : square.getTriangles()) {
                vertices.addAll(triangle.getVertices());
            }
        }
        return vertices;
    }
}
