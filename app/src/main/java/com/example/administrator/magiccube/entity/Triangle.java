package com.example.administrator.magiccube.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class Triangle extends Plane{
    private List<Vertex> vertices;
    private int face;

    @Override
    public void setFace(int face) {
        this.face = face;
    }

    @Override
    public int getFace() {
        return face;
    }

    @Override
    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    @Override
    public List<Vertex> getVertices() {
        return vertices;
    }
}
