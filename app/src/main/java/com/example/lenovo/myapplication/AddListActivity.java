package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddListActivity extends AppCompatActivity {

    private EditText shop_list_name;
    private Button button_wyslij;
    private Button button_wypisz_baza;
    private TextView tv_wyswietl_all_lista;
    public static Baza baza;
    private Lista lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        init();
        baza = new Baza(this);

        addListenerOnButton();


    }

    public void addListenerOnButton() {
        button_wyslij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shop_list_name.getText().toString().equals("")) {
                    Toast.makeText(AddListActivity.this, "Podaj nazwe listy", Toast.LENGTH_SHORT);
                }
                else {
                  //  Toast.makeText(AddListActivity.this, "Dodano listę " + shop_list_name.getText(), Toast.LENGTH_SHORT).show();
                    try{
                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String name = shop_list_name.getText().toString().trim();
                        String data_u = dateFormat.format(currentDate);
                        String data_p = dateFormat.format(currentDate);
                        lista = new Lista(name,data_u,data_p);
                        baza.createLista(lista);

                        Toast.makeText(getApplicationContext(), "Dodano pomyślnie", Toast.LENGTH_LONG).show();
                        shop_list_name.setText("");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    lista = baza.getLista();
                    String listNameString = lista.getName();
                    String listDataUString = lista.getData();
                    String listDataPString = lista.getData2();

                    Intent intent = new Intent();
                    intent.putExtra("key_lista_name",listNameString);
                    intent.putExtra("key_lista_data_u",listDataUString);
                    intent.putExtra("key_lista_data_p",listDataPString);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    //    }
                }

            }
        });
    }

    private void init() {
        button_wyslij = (Button) findViewById(R.id.button_send);
        shop_list_name = (EditText) findViewById(R.id.new_list_edit_text);
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, AddListActivity.class);
    }
}