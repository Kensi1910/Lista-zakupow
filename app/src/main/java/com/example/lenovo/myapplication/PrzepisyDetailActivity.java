package com.example.lenovo.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    public static Baza baza;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepisy_detail);

        baza = new Baza(this);
        // Set tile for the ViewPager
        setTitle(" Przepisy - Opis ");
        listPrzepis = new ArrayList<>();
        listPrzepis = baza.getAllPrzepisy();
        // get intent data
        Intent i = getIntent();
        position = i.getExtras().getInt("id");

        PrzepisyAdapter gridAdapter = new PrzepisyAdapter(this,listPrzepis);


        List<ImageView> mItems = new ArrayList<ImageView>();

        // Retrieve all the images
        /*
        for (int position = 0; position < gridAdapter.getCount(); position++) {
            ImageView imageView = new ImageView(this);
        //    imageView.setImageResource(gridAdapter.mThumbIds[position]);
            final byte[] przepisyImage = listPrzepis.get(position).getImage();
            bitmap = BitmapFactory.decodeByteArray(przepisyImage, 0, przepisyImage.length);

            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            mItems.add(imageView);
        }
        */

        ImageView imageView = new ImageView(this);
        final byte[] przepisyImage = listPrzepis.get(position).getImage();
        bitmap = BitmapFactory.decodeByteArray(przepisyImage, 0, przepisyImage.length);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        imageView = (ImageView)findViewById(R.id.image_grddetails);
        imageView.setImageBitmap(bitmap);

        textView = (TextView)findViewById(R.id.description_TextView);
        textView.setText(listPrzepis.get(position).getOpis());

        dtextView = (TextView)findViewById(R.id.details_text);
        dtextView.setText(gridAdapter.skladniki[position]);




    }
}
