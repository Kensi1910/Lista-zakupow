package com.example.lenovo.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
    ArrayList<Produkt> skladnikiList = new ArrayList<>();


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


    private void  setListenerToButtonChangeProduktToList() {
        buttonChangeProduktToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionsDialog();
            }
        });
    }
    private void showActionsDialog() {
        CharSequence colors[] = new CharSequence[]{"istniejacej listy", "nowej"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dodaj do:");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    ToExistList();
                }
                else{
                    ToNewList();
                }

            }
        });
        builder.show();
    }

    private void ToNewList() {
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

                i=0;
                Scanner scanner = new Scanner(gridAdapter.skladniki[position]);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (i%2 == 0) {
                        String line2 = line;
                        Produkt produkt = new Produkt(line2, 0.0f, 0.0f, 0, 0.0f, " ");
                        if (!baza.isProdukt(line2)) {
                            baza.createProdukt(produkt);
                        }
                        skladnikiList.add(produkt);
                        Produkt p =  baza.getProduktByID(Integer.parseInt(baza.getIDProdukt2(skladnikiList.get(j).getName())));
                        baza.createListaProdoktow2(id_listyy, p.getId(),0, " ",0);
                        j++;
                    }
                    i++;

                }
                scanner.close();
                Toast.makeText(PrzepisyDetailActivity.this, "Dodano pomyślnie", Toast.LENGTH_SHORT).show();
    }

    int id_listyy;
    private void ToExistList() {

        List<Lista> lstLista = new ArrayList<>();
        List<String> lstString = new ArrayList<>();
        lstLista = baza.getAllLista();
        for (int i = 0; i < lstLista.size(); i++) {
            lstString.add(lstLista.get(i).getName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_spinner_choose_list, null);
        builder.setTitle("Dodaj do:");
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_choose_list);
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(PrzepisyDetailActivity.this,
                android.R.layout.simple_spinner_item, lstString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        builder.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String id_listy = baza.getIDListy(spinner.getSelectedItem().toString());
                id_listyy = Integer.parseInt(id_listy);

                int k = 0,j=0;

                Scanner scanner2 = new Scanner(gridAdapter.skladniki[position]);
                while (scanner2.hasNextLine()) {
                    String line = scanner2.nextLine();
                    k++;
                }
                scanner2.close();

                int ilosc = baza.getCounterByList(id_listyy);
                baza.updateIloscProduktow(ilosc + k/2, id_listyy);
            //    String tekst = Integer.toString(ilosc + k/2);
            //    Toast.makeText(PrzepisyDetailActivity.this, tekst, Toast.LENGTH_SHORT).show();

                k=0;
                Scanner scanner = new Scanner(gridAdapter.skladniki[position]);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (k%2 == 0) {
                        String line2 = line;
                        Produkt produkt = new Produkt(line2, 0.0f, 0.0f, 0, 0.0f, " ");
                        if (!baza.isProdukt(line2)) {
                            baza.createProdukt(produkt);
                        }
                        skladnikiList.add(produkt);
                        Produkt p =  baza.getProduktByID(Integer.parseInt(baza.getIDProdukt2(skladnikiList.get(j).getName())));
                        baza.createListaProdoktow2(id_listyy, p.getId(),0, " ",0);
                        j++;
                    }
                    k++;

                }
                scanner.close();
                Toast.makeText(PrzepisyDetailActivity.this, "Dodano pomyślnie", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
