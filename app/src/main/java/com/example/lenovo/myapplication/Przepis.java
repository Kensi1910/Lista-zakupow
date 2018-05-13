package com.example.lenovo.myapplication;

/**
 * Created by Daymos on 2018-05-09.
 */

public class Przepis {

    private int id;
    private  String name;
    private  String opis;
    private   int  drawableId;
    private byte[] image;


    public Przepis() {
    }

    public Przepis(String name, int drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    public Przepis(String name, String opis, int drawableId) {
        this.name = name;
        this.opis = opis;
        this.drawableId = drawableId;
    }

    public Przepis(String name, String opis, byte[] image) {
        this.name = name;
        this.opis = opis;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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
