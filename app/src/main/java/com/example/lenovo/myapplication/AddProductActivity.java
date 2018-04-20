package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    TextView tv_category_name;
    public static Baza baza;
    private RecyclerView mRecylerView ;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Produkt> produktyList;
    private Button button_add_new_produkt;
    private Button button_wyswietl_baza;
    private TextView tv_wyswietl_baza;
    private EditText add_new_produkt_edit_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        /*
        tv_category_name = (TextView) findViewById(R.id.category_name);
        if (savedInstanceState == null) {
             String nazwa2 = getIntent().getStringExtra("nazwa3");
            tv_category_name.setText(nazwa2);
        }
        */
        baza = new Baza(this);
        produktyList = new ArrayList<>();
       // produktyList.add(new Produkt("Chleb", 1.4f, 2.5f));
      //  produktyList.add(MainActivity.baza.getProdukt());
        produktyList  = MainActivity.baza.getAllProdukt();

        tv_wyswietl_baza = (TextView) findViewById(R.id.tv_wyswietl_produkty_baza);
        button_wyswietl_baza = (Button) findViewById(R.id.wyswietlProdukt);
        button_add_new_produkt = (Button) findViewById(R.id.add_new_produkt_button);
        add_new_produkt_edit_text = (EditText) findViewById(R.id.add_new_produkt_edit_text);

        mRecylerView= (RecyclerView) findViewById(R.id.my_recycler_view_dodajProdukt);
        mRecylerView.setHasFixedSize(true);

        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapterProdukt(this,produktyList);
        mRecylerView.setAdapter(mAdapter);


        button_wyswietl_baza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_wyswietl_baza.setText(baza.WypiszProdukt());

            }
        });

        addNewProduktButtonListener();
        mAdapter.notifyDataSetChanged();

    }

    public void addNewProduktButtonListener() {
        button_add_new_produkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_new_produkt_edit_text.getText().toString();
                Produkt produkt = new Produkt(name, 0.0f, 0.0f);
                baza.createProdukt(produkt);
                Toast.makeText(getApplicationContext(), "Dodano " + name, Toast.LENGTH_LONG).show();
                add_new_produkt_edit_text.setText("");
               // produktyList.add(0,produkt);
                produktyList.add(0, baza.getProdukt());
              //  mAdapter.notifyItemInserted(0);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, AddListActivity.class);
    }

}
