package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PrzepisyDetailActivity extends AppCompatActivity {

    int position;
    private ImageView imageView;
    private TextView textView;
    private TextView dtextView;
    private List<Przepis> listPrzepis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepisy_detail);


        // Set tile for the ViewPager
        setTitle(" Grid Details Activity ");
        listPrzepis = new ArrayList<>();
        // get intent data
        Intent i = getIntent();
        position = i.getExtras().getInt("id");

        PrzepisyAdapter gridAdapter = new PrzepisyAdapter(this,listPrzepis);
        // List<MyGridAdapter.Item> mItems = new ArrayList<MyGridAdapter.Item>();

        List<ImageView> mItems = new ArrayList<ImageView>();

        // Retrieve all the images
        for (int position = 0; position < gridAdapter.getCount(); position++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(gridAdapter.mThumbIds[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);




            mItems.add(imageView);
        }


        imageView = (ImageView)findViewById(R.id.image_grddetails);
        imageView.setImageResource(gridAdapter.mThumbIds[position]);

        textView = (TextView)findViewById(R.id.description_TextView);
        textView.setText(gridAdapter.opis[position]);

        dtextView = (TextView)findViewById(R.id.details_text);
        dtextView.setText(gridAdapter.skladniki[position]);




    }
}
