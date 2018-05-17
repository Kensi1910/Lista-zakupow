package com.example.lenovo.myapplication;

/**
 * Created by Daymos on 2018-04-06.
 */
        import android.os.Parcel;
        import android.os.Parcelable;

        import java.io.Serializable;


public class Produkt implements Parcelable {
    private int id;
    private String nazwa;
    private Float cena_min = 0.0f;
    private Float cena_max = 0.0f;
    private int id_kategoria;
    private boolean isSelected;
    private Float ilosc;
    private String jednostka;

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

    public Produkt(String nazwa, Float cena_min, Float cena_max, int id_kategoria) {
        this.nazwa = nazwa;
        this.cena_min = cena_min;
        this.cena_max = cena_max;
        this.id_kategoria = id_kategoria;
    }

    public Produkt(String nazwa, Float cena_min, Float cena_max, int id_kategoria, Float ilosc) {
        this.nazwa = nazwa;
        this.cena_min = cena_min;
        this.cena_max = cena_max;
        this.id_kategoria = id_kategoria;
        this.ilosc = ilosc;
    }
    public Produkt(String nazwa, Float cena_min, Float cena_max, int id_kategoria, Float ilosc, String jednostka) {
        this.nazwa = nazwa;
        this.cena_min = cena_min;
        this.cena_max = cena_max;
        this.id_kategoria = id_kategoria;
        this.ilosc = ilosc;
        this.jednostka = jednostka;
    }


    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public Float getIlosc() {
        return ilosc;
    }

    public void setIlosc(Float ilosc) {
        this.ilosc = ilosc;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nazwa);
        parcel.writeFloat(cena_min);
        parcel.writeFloat(cena_max);
        parcel.writeFloat(ilosc);
     //   parcel.writeBooleanArray(isSelected);
    }


    public static final Parcelable.Creator<Produkt> CREATOR = new Parcelable.Creator<Produkt>() {
        public Produkt createFromParcel(Parcel in) {
            return new Produkt(in);
        }

        public Produkt[] newArray(int size) {
            return new Produkt[size];
        }
    };

    private Produkt(Parcel in) {
        id = in.readInt();
        nazwa = in.readString();
        cena_min = in.readFloat();
        cena_max = in.readFloat();
        ilosc = in.readFloat();
    }
}
