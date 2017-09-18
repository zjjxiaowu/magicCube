package com.example.administrator.magiccube.entity;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class Vertex extends Point {
    private float x;
    private float y;
    private float z;
    private float colorR;
    private float colorG;
    private float colorB;
    private float colorA;

    public void setColorR(float colorR) {
        this.colorR = colorR;
    }

    public void setColorG(float colorG) {
        this.colorG = colorG;
    }

    public void setColorB(float colorB) {
        this.colorB = colorB;
    }

    public float getColorR() {
        return colorR;
    }

    public float getColorG() {
        return colorG;
    }

    public float getColorB() {
        return colorB;
    }

    public void copyColor(Vertex src, Vertex dest){
        dest.setColorR(src.getColorR());
        dest.setColorG(src.getColorG());
        dest.setColorB(src.getColorB());
        dest.setColorA(src.getColorA());
    }
    public void setColorA(float colorA) {
        this.colorA = colorA;
    }

    public float getColorA() {
        return colorA;
    }

    public void mirrorX(Vertex vertex){
        vertex.setX(0-vertex.getX());
    }
    public void mirrorY(Vertex vertex){
        vertex.setY(0-vertex.getY());
    }
    public void mirrorZ(Vertex vertex){
        vertex.setZ(0-vertex.getZ());
    }
    public void rightMove(Vertex vertex, float moveSize){
        vertex.setX(vertex.getX()+moveSize);
    }
    public void leftMove(Vertex vertex, float moveSize){
        vertex.setX(vertex.getX()-moveSize);
    }
    public void upMove(Vertex vertex, float moveSize){
        vertex.setY(vertex.getY()+moveSize);
    }
    public void downMove(Vertex vertex, float moveSize){
        vertex.setY(vertex.getY()-moveSize);
    }
    public void frontMove(Vertex vertex, float moveSize){
        vertex.setZ(vertex.getZ()+moveSize);
    }
    public void backMove(Vertex vertex, float moveSize){
        vertex.setZ(vertex.getZ()-moveSize);
    }
    public void copyVertex(Vertex src, Vertex dest){
        dest.setX(src.getX());
        dest.setY(src.getY());
        dest.setZ(src.getZ());
        dest.setColorR(src.getColorR());
        dest.setColorG(src.getColorG());
        dest.setColorB(src.getColorB());
        dest.setColorA(src.getColorA());
    }
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

}
