package com.example.lenovo.myapplication;

/**
 * Created by kensi on 17/03/2018.
 */

public class Lista {
    private int id;
    private String name;
    private String data;
    private String data2;
    private int ilosc;

    public Lista() {

    }

    public Lista(String name, String data, String data2, int ilosc) {
        this.name = name;
        this.data = data;
        this.data2 = data2;
        this.ilosc = ilosc;
    }

    public Lista(int id, String name, String data, String data2) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.data2 = data2;
    }
    public Lista(String name, String data, String data2) {
        this.name = name;
        this.data = data;
        this.data2 = data2;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getName() {
        return name;
    }

    public String getData() { return data; }

    public String getData2() { return data2; }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) { this.data = data; }

    public void setData2(String data2) { this.data2 = data2; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
