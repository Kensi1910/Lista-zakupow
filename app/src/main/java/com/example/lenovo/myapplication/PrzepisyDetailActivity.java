package com.example.lenovo.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PrzepisyDetailActivity extends AppCompatActivity {

    private int position;
    private String listName;
    private ImageView imageView;
    private TextView textView;
    private TextView dtextView;
    private List<Przepis> listPrzepis;
    public static Baza baza;
    private Button buttonChangeProduktToList;
    Bitmap bitmap;
    PrzepisyAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przepisy_detail);

        baza = new Baza(this);
        buttonChangeProduktToList = (Button) findViewById(R.id.button_change_przepisy_produkt_to_list);
        setTitle(" Przepisy - Opis ");
        listPrzepis = new ArrayList<>();
        listPrzepis = baza.getAllPrzepisy();
        Intent i = getIntent();
        position = i.getExtras().getInt("id");
        listName = i.getStringExtra("name");
        gridAdapter = new PrzepisyAdapter(this,listPrzepis);


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
      //  dtextView.setText(gridA);
        setListenerToButtonChangeProduktToList();

    }

    ArrayList<Produkt> skladnikiList = new ArrayList<>();

    private void setListenerToButtonChangeProduktToList() {
        buttonChangeProduktToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0,j=0;

                Scanner scanner2 = new Scanner(gridAdapter.skladniki[position]);
                while (scanner2.hasNextLine()) {
                    String line = scanner2.nextLine();
                    i++;
                }
                scanner2.close();

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String data_u = dateFormat.format(currentDate);
                String data_p = dateFormat.format(currentDate);


                Lista lista = new Lista(listName, data_u, data_p, i/2);
                MainActivity.lstLista.add(lista);
                baza.createLista(lista);
                String id_listy = baza.getIDListy(lista.getName());
                int id_listyy = Integer.parseInt(id_listy);

                Scanner scanner = new Scanner(gridAdapter.skladniki[position]);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (i%2 == 0) {
                          String line2 = line;
                          Produkt produkt = new Produkt(line2, 0.0f, 0.0f, 0, 0.0f, " ");
                          if (!baza.isProdukt(line2)) {
                              baza.createProdukt(produkt);
                          }
                       //   baza.createProdukt(produkt);

                          skladnikiList.add(produkt);
                          Produkt p =  baza.getProduktByID(Integer.parseInt(baza.getIDProdukt2(skladnikiList.get(j).getName())));
                          baza.createListaProdoktow2(id_listyy, p.getId(),0, " ",0);
                          j++;
//                          AddProductActivity.produktyList.add(0, baza.getProdukt());

                      //    Toast.makeText(PrzepisyDetailActivity.this, line, Toast.LENGTH_SHORT).show();
                    }
                    i++;

                }
                scanner.close();
//                ProductListActivity.lstAddedProduct.addAll(skladnikiList);



            //    Toast.makeText(PrzepisyDetailActivity.this, id_listy, Toast.LENGTH_LONG).show();
              //  Toast.makeText(PrzepisyDetailActivity.this, String.valueOf(skladnikiList.get(0).getId()), Toast.LENGTH_LONG).show();
           //     Toast.makeText(PrzepisyDetailActivity.this, String.valueOf(p.getId()), Toast.LENGTH_LONG).show();
           //     Toast.makeText(PrzepisyDetailActivity.this, skladnikiList.get(0).getName(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
