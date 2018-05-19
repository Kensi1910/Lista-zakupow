package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class przepisy_activity extends AppCompatActivity {

    private List<Przepis> listPrzepis;
    public static Baza baza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepisy_activity);
        setTitle(" Przepisy ");
        GridView gridView = (GridView) findViewById(R.id.gridview);
        baza = new Baza(this);
        listPrzepis = new ArrayList<>();
        listPrzepis = baza.getAllPrzepisy();
     //   listPrzepis.add(new Przepis("Tagliatelle z sosem czosnkowym i grillowanym kurczakiem", R.drawable.agenda ) );

        gridView.setAdapter(new PrzepisyAdapter(this, listPrzepis));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intentf = new Intent(przepisy_activity.this, PrzepisyDetailActivity.class);
                intentf.putExtra("id", position);
                intentf.putExtra("name", listPrzepis.get(position).getName());
                startActivity(intentf);
            }
        });

    }
}
