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
import java.util.Collections;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private FloatingActionButton fabAddProdukt;
    public static Baza baza;
    public static   List<Produkt> lstAddedProduct;
  //  public  List<Produkt> lstAddedProduct;
    private RecyclerView mRecylerView ;
    private  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final int REQUEST_CODE_GETMESSAGE = 999;
    private TextView tv_show_baza;
    private Button button_show_baza;
    private TextView tv_name_list;
    ArrayList<Produkt> arraylist;
    public static int counterItem;
    public static  List<AddedProdukt> mAddedProdukt;
    public static ArrayList<Produkt> produktyList2;
    private List<Produkt> listAddedProduktChecked;
    private List<Produkt> listAddedProduktNotChecked;
    private String id;
    private int id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        init();
        setTitle(" Produkty ");
        String nazwa2 = getIntent().getStringExtra("ListName");
        id = getIntent().getStringExtra("ListId");
        id2 = getIntent().getIntExtra("ListId2", 0);
        tv_name_list.setText(nazwa2);

        baza = new Baza(this);

        produktyList2 = new ArrayList<>();
        lstAddedProduct = new ArrayList<>();
        arraylist = new ArrayList<>();
        mAddedProdukt = new ArrayList<>();
        lstAddedProduct = baza.getAddedProductyID(id);
        listAddedProduktChecked = new ArrayList<>();
        listAddedProduktNotChecked = new ArrayList<>();
       // for (int i = 0; i < lstAddedProduct.size(); i++) {
        //    if (lstAddedProduct.get(i).isSelected()) {
        //        listAddedProduktChecked.add(lstAddedProduct.get(i));
         //   }
         //   else {
         //       listAddedProduktNotChecked.add(lstAddedProduct.get(i));
         //   }
       // }
      //  lstAddedProduct.addAll(listAddedProduktNotChecked);
       // lstAddedProduct.addAll(listAddedProduktChecked);
        counterItem = lstAddedProduct.size();

        mRecylerView = (RecyclerView) findViewById(R.id.my_recycler_view_add_produkt);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapterAddedProduct(this, lstAddedProduct);
        mRecylerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        addListenerToButtonAddProduct();
        setProductAsSelected();

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
                        Collections.reverse(lstAddedProduct);
                        lstAddedProduct.addAll(arraylist);
                        Collections.reverse(lstAddedProduct);
                        mAdapter.notifyDataSetChanged();
                    }

                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setProductAsSelected();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        int ilosc = lstAddedProduct.size();
        if (ilosc > 0) {
            baza.updateIloscProduktow(ilosc, Integer.parseInt(id));
            Bundle bundle = new Bundle();
            bundle.putInt("ilosc_produktow", ilosc);
            bundle.putInt("id_listyy", id2);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
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

    private void setProductAsSelected() {
        for (int i = 0; i < lstAddedProduct.size(); i++) {
            int f = baza.getSelectedByProduktAndList(Integer.parseInt(MainActivity.getListId()), lstAddedProduct.get(i).getId());
            if (f == 1) {
                lstAddedProduct.get(i).setSelected(true);
            }
            else {
                lstAddedProduct.get(i).setSelected(false);
            }
        }
    }
    private void init() {
        fabAddProdukt = (FloatingActionButton) findViewById(R.id.fab_add_produkt);
        tv_name_list = (TextView) findViewById(R.id.tv_name_list);
    }
}
