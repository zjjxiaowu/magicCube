package com.example.administrator.magiccube.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public abstract class Plane extends Point {
    private List<Vertex> vertices;
    private int originFace;
    private int face;

    public void setOriginFace(int originFace) {
        this.originFace = originFace;
    }

    public int getOriginFace() {
        return originFace;
    }

    public void rightMove(Plane plane, float moveSize){
        for(Vertex vertex : plane.getVertices()){
            vertex.rightMove(vertex,moveSize);
        }
    }
    public void leftMove(Plane plane, float moveSize){
        for(Vertex vertex : plane.getVertices()){
            vertex.leftMove(vertex,moveSize);
        }
    }
    public void upMove(Plane plane, float moveSize){
        for(Vertex vertex : plane.getVertices()){
            vertex.upMove(vertex,moveSize);
        }
    }
    public void downMove(Plane plane, float moveSize){
        for(Vertex vertex : plane.getVertices()){
            vertex.downMove(vertex,moveSize);
        }
    }
    public void frontMove(Plane plane, float moveSize){
        for(Vertex vertex : plane.getVertices()){
            vertex.frontMove(vertex,moveSize);
        }
    }
    public void copyPlane(Plane src, Plane dest){
        dest.setFace(src.getFace());
        boolean isNew = false;
        if(dest.getVertices()==null) isNew = true;
        if(isNew) dest.setVertices(new ArrayList<Vertex>());
        for(int i=0;i<src.getVertices().size();i++){
            Vertex vertex = src.getVertices().get(i);
            Vertex copyVertex = null;
            if(isNew) copyVertex = new Vertex();
            else copyVertex = dest.getVertices().get(i);
            vertex.copyVertex(vertex,copyVertex);
            if(isNew) dest.getVertices().add(copyVertex);
        }
    }
    public void backMove(Plane plane, float moveSize){
        for(Vertex vertex : plane.getVertices()){
            vertex.backMove(vertex,moveSize);
        }
    }
    public void setFace(int face) {
        this.face = face;
    }

    public int getFace() {
        return face;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
}
