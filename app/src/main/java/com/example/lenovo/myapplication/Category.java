package com.example.lenovo.myapplication;

/**
 * Created by kensi on 22/03/2018.
 */

public class Category {
    private int id;
    private String name;
    private byte[] image;

    public Category() {}
    public Category(int id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public Category(String name, byte[] image) {
        this.name = name;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
