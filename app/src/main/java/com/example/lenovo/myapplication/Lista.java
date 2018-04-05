package com.example.lenovo.myapplication;

/**
 * Created by kensi on 17/03/2018.
 */

public class Lista {
    private String name;
    private String data;

    public Lista() {

    }
    public Lista(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getData() { return data; }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) { this.data = data; }
}
