package com.example.administrator.magiccube.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class CubesPlane extends Plane {
    private List<Cube> cubes;
    private int face;

    public void setFace(int face) {
        this.face = face;
    }

    public int getFace() {
        return face;
    }

    public void setCubes(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public void copyCubesPlane(CubesPlane src, CubesPlane dest) {
        dest.setFace(src.getFace());
        dest.setCubes(new ArrayList<Cube>());
        for (Cube cube : src.getCubes()) {
            Cube copy = new Cube();
            copy.copyCube(cube, copy);
            dest.getCubes().add(copy);
        }
    }
}
