package com.example.lenovo.myapplication;

/**
 * Created by kensi on 20/04/2018.
 */

public class AddedProdukt {
    private int id;
    private int id_listy;
    private int id_produktu;
    private int ilosc;

    public AddedProdukt() {

    }
    public AddedProdukt(int id, int id_listy, int id_produktu, int ilosc) {
        this.id = id;
        this.id_listy = id_listy;
        this.id_produktu = id_produktu;
        this.ilosc = ilosc;
    }

    public AddedProdukt(int id_listy, int id_produktu, int ilosc) {
        this.id_listy = id_listy;
        this.id_produktu = id_produktu;
        this.ilosc = ilosc;
    }

    public AddedProdukt(int id_listy, int id_produktu) {
        this.id_listy = id_listy;
        this.id_produktu = id_produktu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_listy() {
        return id_listy;
    }

    public void setId_listy(int id_listy) {
        this.id_listy = id_listy;
    }

    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_produktu) {
        this.id_produktu = id_produktu;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }
}
