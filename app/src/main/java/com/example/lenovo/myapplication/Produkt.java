package com.example.lenovo.myapplication;

/**
 * Created by Daymos on 2018-04-06.
 */
        import java.io.Serializable;


public class Produkt  {
    private int id;
    private String nazwa;
    private Float cena_min;
    private Float cena_max;
    private int id_kategoria;
    private boolean isSelected;

    public Produkt(){

    }

    public Produkt(String nazwa) {
        this.nazwa = nazwa;
    }

    public Produkt(String nazwa, Float cena_min, Float cena_max) {
        this.nazwa = nazwa;
        this.cena_min = cena_min;
        this.cena_max = cena_max;
    }

    public Produkt(int id, String nazwa, Float cena_min, Float cena_max, int id_kategoria, boolean isSelected) {
        this.id = id;
        this.nazwa = nazwa;
        this.cena_min = cena_min;
        this.cena_max = cena_max;
        this.id_kategoria = id_kategoria;
        this.isSelected = isSelected;
    }

    public String getName(){
        return nazwa;
    }

    public void setName(String nazwa){
        this.nazwa=nazwa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getCena_min() {
        return cena_min;
    }

    public void setCena_min(Float cena_min) {
        this.cena_min = cena_min;
    }

    public Float getCena_max() {
        return cena_max;
    }

    public void setCena_max(Float cena_max) {
        this.cena_max = cena_max;
    }

    public int getId_kategoria() {
        return id_kategoria;
    }

    public void setId_kategoria(int id_kategoria) {
        this.id_kategoria = id_kategoria;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean isSelected){
        this.isSelected=isSelected;
    }
}
