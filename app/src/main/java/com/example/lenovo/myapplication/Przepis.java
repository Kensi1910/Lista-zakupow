package com.example.lenovo.myapplication;

/**
 * Created by Daymos on 2018-05-09.
 */

public class Przepis {

    private  String name;
    private   int  drawableId;


    public Przepis(String name, int drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
