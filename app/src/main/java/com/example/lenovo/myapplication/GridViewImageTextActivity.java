package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class GridViewImageTextActivity extends AppCompatActivity {

    GridView androidGridView;

    String[] gridViewString = {
            "Alkohol", "Nabiał",
            "Mrozonki", "Warzywa",
            "Napoje", "Chemia Gospodarcza"



    } ;
    int[] gridViewImageId = {
            R.drawable.alkohol,  R.drawable.milk,
            R.drawable.mroznia, R.drawable.warzywa,
            R.drawable.napoje, R.drawable.chemia,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_image_text);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(GridViewImageTextActivity.this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(GridViewImageTextActivity.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });

    }

    public void ButtonAddNewCategory(View view) {
        Intent intent = new Intent(this, FormulaAddNewCategoryActivity.class);
        startActivity(intent);
    }
}
