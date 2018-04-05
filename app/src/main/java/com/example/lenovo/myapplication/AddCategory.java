package com.example.lenovo.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddCategory extends AppCompatActivity {
    Baza db = new Baza(this);
    EditText kategoriaDodaj;
    TextView wypisz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        kategoriaDodaj = findViewById(R.id.kategoriaDodaj);
        wypisz = findViewById(R.id.wypisKategorii);
    }
    public void dodaj(View view){
        String nazwa;
        nazwa = kategoriaDodaj.getText().toString();
        db.DodajKategorie(nazwa);
    }
    public void wypisz(View view){
        //List<String>lista = db.WypiszKategorie();
        //String kategorie = "";

       /* for (String i : lista) {
            kategorie = kategorie + i +"\n";
        }*/
        wypisz.setText(db.WypiszKategorie());
    }
}
