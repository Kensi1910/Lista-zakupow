package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class AddProductActivity extends AppCompatActivity {

    TextView tv_category_name;
    public static Baza baza;
    private RecyclerView mRecylerView ;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
   // public static ArrayList<Produkt> produktyList2;
    private List<Produkt> produktyList;
    private Button button_add_new_produkt;
    private Button button_wyswietl_baza;
    private TextView tv_wyswietl_baza;
    private EditText add_new_produkt_edit_text;
    private String id_kategori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setTitle(" Dodaj Produkt ");
        baza = new Baza(this);
        produktyList = new ArrayList<>();

        id_kategori = getIntent().getStringExtra("id_kategori");
        produktyList  = MainActivity.baza.getAllProdukt(id_kategori);
       // produktyList2 = new ArrayList<>();
        AddProduktTabLayout.produktyList2 = new ArrayList<>();

        button_add_new_produkt = (Button) findViewById(R.id.add_new_produkt_button);
        add_new_produkt_edit_text = (EditText) findViewById(R.id.add_new_produkt_edit_text);

        mRecylerView= (RecyclerView) findViewById(R.id.my_recycler_view_dodajProdukt);
        mRecylerView.setHasFixedSize(true);

        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapterProdukt(this,produktyList);
        mRecylerView.setAdapter(mAdapter);


        addNewProduktButtonListener();
        mAdapter.notifyDataSetChanged();

      //  Intent intent = new Intent(AddProductActivity.this, ProductListActivity.class);
      //  Bundle bundle = new Bundle();
      //  bundle.putParcelableArrayList("mylist", (ArrayList<? extends Parcelable>) produktyList);
      //  intent.putExtras(bundle);
      //  this.startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mylist",  AddProduktTabLayout.produktyList2);
        Intent intent = new Intent();
        intent.putExtras(bundle);
       //  Toast.makeText(AddProductActivity.this, produktyList2.get(0).getName(), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, intent);
        super.onBackPressed();

    }
    public void addNewProduktButtonListener() {
        button_add_new_produkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_new_produkt_edit_text.getText().toString();
                int id_kategori_int = Integer.parseInt(id_kategori);
                Produkt produkt = new Produkt(name, 0.0f, 0.0f, id_kategori_int, 0.0f, "szt");
                baza.createProdukt(produkt);
              //  Toast.makeText(getApplicationContext(), "Dodano " + name, Toast.LENGTH_LONG).show();
                add_new_produkt_edit_text.setText("");
                produktyList.add(0, baza.getProdukt());
                mAdapter.notifyDataSetChanged();
            }
        });
    }


}
