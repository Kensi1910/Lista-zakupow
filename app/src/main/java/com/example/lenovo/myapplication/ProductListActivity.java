package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private FloatingActionButton fabAddProdukt;
    public static Baza baza;
    public  List<Produkt> lstAddedProduct;
    private RecyclerView mRecylerView ;
    private  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final int REQUEST_CODE_GETMESSAGE = 999;
    private TextView tv_show_baza;
    private Button button_show_baza;
    private TextView tv_name_list;
    ArrayList<Produkt> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        init();

        String nazwa2 = getIntent().getStringExtra("ListName");
        tv_name_list.setText(nazwa2);

        baza = new Baza(this);


        lstAddedProduct = new ArrayList<>();
        arraylist = new ArrayList<>();
        lstAddedProduct = baza.getAddedProductyID(nazwa2);

        mRecylerView = (RecyclerView) findViewById(R.id.my_recycler_view_add_produkt);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapterAddedProduct(this, lstAddedProduct);
        mRecylerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        addListenerToButtonAddProduct();

        button_show_baza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_show_baza.setText(baza.WypiszListeProdukt());
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GETMESSAGE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    arraylist = bundle.getParcelableArrayList("mylist");
                    if (!(arraylist.isEmpty())) {
                        lstAddedProduct.addAll(arraylist);
                        mAdapter.notifyDataSetChanged();
                    }

                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private void addListenerToButtonAddProduct() {
        fabAddProdukt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, AddProduktTabLayout.class);
                startActivityForResult(intent, REQUEST_CODE_GETMESSAGE);

            }
        });

    }

    private void init() {
        fabAddProdukt = (FloatingActionButton) findViewById(R.id.fab_add_produkt);
        tv_show_baza = (TextView) findViewById(R.id.tv_show_baza_lista_produktow);
        button_show_baza = (Button) findViewById(R.id.button_show_baza_lista_produktow);
        tv_name_list = (TextView) findViewById(R.id.tv_name_list);
    }
}
