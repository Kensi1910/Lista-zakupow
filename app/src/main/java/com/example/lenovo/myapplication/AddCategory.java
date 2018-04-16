package com.example.lenovo.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCategory extends AppCompatActivity {
    public static Baza baza;
    private EditText nameKategoria;
    private ImageView zdjecie;
    private Button addPictureButton;
    private Button dodajKategorieButton;
    private Button wyswietl;
    private TextView tv_wyswietl;
    private String nazwa2;
    private byte[] image2;
    final int REQUEST_CODE_GALLERY = 999;
    Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        init();

        baza = new Baza(getApplicationContext());

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AddCategory.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });



        addCategoryListener();

        wyswietl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_wyswietl.setText(baza.WypiszKategorie());
            }
        });


    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "Nie masz uprawnien do dostępu", Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                zdjecie.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        nameKategoria = (EditText) findViewById(R.id.et_name_kateogria);
        zdjecie = (ImageView) findViewById(R.id.iv_new_picture);
        addPictureButton = (Button) findViewById(R.id.add_picture_button);
        dodajKategorieButton = (Button) findViewById(R.id.button_dodaj_kategorie);
        wyswietl = (Button) findViewById(R.id.wyswietlButton);
        tv_wyswietl = (TextView) findViewById(R.id.wypisKategorii);
    }

    public void addCategoryListener() {

        dodajKategorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameKategoria.getText().toString().equals("")) {
                    Toast.makeText(AddCategory.this, "Podaj nazwe kategorii", Toast.LENGTH_SHORT);
                }
                else {
                    try{
                        String s = nameKategoria.getText().toString().trim();
                        byte[] b = imageViewToByte(zdjecie);
                        category = new Category(s,b);
                        baza.createCategory(category);

                        Toast.makeText(getApplicationContext(), "Dodano pomyślnie", Toast.LENGTH_LONG).show();
                        nameKategoria.setText("");
                        zdjecie.setImageResource(R.mipmap.ic_launcher);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    category = baza.getCategory();
                    nazwa2 = category.getName();
                    image2 = category.getImage();

                    Intent intent = new Intent();
                    intent.putExtra("key_category_name", nazwa2);
                    intent.putExtra("key_category_image", image2);
                    setResult(Activity.RESULT_OK, intent);
                    //finish();
                }
                }

        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCategory.class);
    }

}
