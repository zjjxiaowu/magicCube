package com.example.administrator.magiccube.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class Square extends Plane {
    private List<Triangle> triangles;
    private int face;
    private int color;
    private Vertex vertex1;
    private Vertex vertex2;
    private Vertex vertex3;
    private Vertex vertex4;

    private Vertex vertex1_temp;
    private Vertex vertex2_temp;
    private Vertex vertex3_temp;
    private Vertex vertex4_temp;
    private Vertex vertex5_temp;
    private Vertex vertex6_temp;

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void fillTrianglesByVertices(int face) {
        List<Triangle> triangles = new ArrayList<>();
        Triangle triangle1 = new Triangle();
        List<Vertex> vertices1 = new ArrayList<>();
        Triangle triangle2 = new Triangle();
        List<Vertex> vertices2 = new ArrayList<>();
        fillVertices(face);
        vertices1.add(vertex1_temp);
        vertices1.add(vertex2_temp);
        vertices1.add(vertex3_temp);
        vertices2.add(vertex4_temp);
        vertices2.add(vertex5_temp);
        vertices2.add(vertex6_temp);
        triangle1.setVertices(vertices1);
        triangle2.setVertices(vertices2);
        triangles.add(triangle1);
        triangles.add(triangle2);
        this.triangles = triangles;
    }

    private void fillVertices(int face) {
        vertex1_temp = new Vertex();
        vertex2_temp = new Vertex();
        vertex3_temp = new Vertex();
        vertex4_temp = new Vertex();
        vertex5_temp = new Vertex();
        vertex6_temp = new Vertex();
        vertex2.copyVertex(vertex1, vertex1_temp);
        vertex2.copyVertex(vertex3, vertex2_temp);
        vertex2.copyVertex(vertex2, vertex3_temp);
        vertex2.copyVertex(vertex3, vertex4_temp);
        vertex2.copyVertex(vertex4, vertex5_temp);
        vertex2.copyVertex(vertex2, vertex6_temp);
    }

    public void setVertex1(Vertex vertex1) {
        this.vertex1 = vertex1;
    }

    public void setVertex2(Vertex vertex2) {
        this.vertex2 = vertex2;
    }

    public void setVertex3(Vertex vertex3) {
        this.vertex3 = vertex3;
    }

    public void setVertex4(Vertex vertex4) {
        this.vertex4 = vertex4;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public Vertex getVertex3() {
        return vertex3;
    }

    public Vertex getVertex4() {
        return vertex4;
    }

    @Override
    public void setFace(int face) {
        this.face = face;
    }

    @Override
    public int getFace() {
        return face;
    }

    public void copySquare(Square src, Square dest) {
        boolean isNew = false;
        if (dest.getTriangles() == null) isNew = true;
        if (isNew) dest.setTriangles(new ArrayList<Triangle>());
        dest.setFace(src.getFace());
        dest.setColor(src.getColor());
        Vertex vertex1 = null;
        Vertex vertex2 = null;
        Vertex vertex3 = null;
        Vertex vertex4 = null;
        if (isNew) {
            vertex1 = new Vertex();
            vertex2 = new Vertex();
            vertex3 = new Vertex();
            vertex4 = new Vertex();
        } else {
            vertex1 = dest.getVertex1();
            vertex2 = dest.getVertex2();
            vertex3 = dest.getVertex3();
            vertex4 = dest.getVertex4();
        }
        src.getVertex1().copyVertex(src.getVertex1(), vertex1);
        dest.setVertex1(vertex1);
        src.getVertex2().copyVertex(src.getVertex2(), vertex2);
        dest.setVertex2(vertex2);
        src.getVertex3().copyVertex(src.getVertex3(), vertex3);
        dest.setVertex3(vertex3);
        src.getVertex4().copyVertex(src.getVertex4(), vertex4);
        dest.setVertex4(vertex4);
        dest.setOriginFace(src.getOriginFace());
        for (int i = 0; i < src.getTriangles().size(); i++) {
            Triangle triangle = src.getTriangles().get(i);
            Triangle copyTriangle = null;
            if (isNew) copyTriangle = new Triangle();
            else copyTriangle = dest.getTriangles().get(i);
            triangle.copyPlane(triangle, copyTriangle);
            if (isNew) dest.getTriangles().add(copyTriangle);
        }
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void rightMove(Square plane, float moveSize) {
        plane.getVertex1().rightMove(plane.getVertex1(), moveSize);
        plane.getVertex2().rightMove(plane.getVertex2(), moveSize);
        plane.getVertex3().rightMove(plane.getVertex3(), moveSize);
        plane.getVertex4().rightMove(plane.getVertex4(), moveSize);
        for (Triangle triangle : plane.getTriangles()) {
            triangle.rightMove(triangle, moveSize);
        }
    }

    public void leftMove(Square plane, float moveSize) {
        plane.getVertex1().leftMove(plane.getVertex1(), moveSize);
        plane.getVertex2().leftMove(plane.getVertex2(), moveSize);
        plane.getVertex3().leftMove(plane.getVertex3(), moveSize);
        plane.getVertex4().leftMove(plane.getVertex4(), moveSize);
        for (Triangle triangle : plane.getTriangles()) {
            triangle.leftMove(triangle, moveSize);
        }
    }

    public void upMove(Square plane, float moveSize) {
        plane.getVertex1().upMove(plane.getVertex1(), moveSize);
        plane.getVertex2().upMove(plane.getVertex2(), moveSize);
        plane.getVertex3().upMove(plane.getVertex3(), moveSize);
        plane.getVertex4().upMove(plane.getVertex4(), moveSize);
        for (Triangle triangle : plane.getTriangles()) {
            triangle.upMove(triangle, moveSize);
        }
    }

    public void downMove(Square plane, float moveSize) {
        plane.getVertex1().downMove(plane.getVertex1(), moveSize);
        plane.getVertex2().downMove(plane.getVertex2(), moveSize);
        plane.getVertex3().downMove(plane.getVertex3(), moveSize);
        plane.getVertex4().downMove(plane.getVertex4(), moveSize);
        for (Triangle triangle : plane.getTriangles()) {
            triangle.downMove(triangle, moveSize);
        }
    }

    public void frontMove(Square plane, float moveSize) {
        plane.getVertex1().frontMove(plane.getVertex1(), moveSize);
        plane.getVertex2().frontMove(plane.getVertex2(), moveSize);
        plane.getVertex3().frontMove(plane.getVertex3(), moveSize);
        plane.getVertex4().frontMove(plane.getVertex4(), moveSize);
        for (Triangle triangle : plane.getTriangles()) {
            triangle.frontMove(triangle, moveSize);
        }
    }

    public void backMove(Square plane, float moveSize) {
        plane.getVertex1().backMove(plane.getVertex1(), moveSize);
        plane.getVertex2().backMove(plane.getVertex2(), moveSize);
        plane.getVertex3().backMove(plane.getVertex3(), moveSize);
        plane.getVertex4().backMove(plane.getVertex4(), moveSize);
        for (Triangle triangle : plane.getTriangles()) {
            triangle.backMove(triangle, moveSize);
        }
    }
}
