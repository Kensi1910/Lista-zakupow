package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by kensi on 06/05/2018.
 */

public class CategoryFragment extends Fragment {

    View v;

    TextView tv_category_name;
    public static Baza baza;
    private RecyclerView mRecylerView ;
   // private RecyclerView.Adapter mAdapter;
    private RecyclerViewAdapterProdukt mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<Produkt> produktyList2;
    private List<Produkt> produktyList;
    private Button button_add_new_produkt;
    private Button button_wyswietl_baza;
    private TextView tv_wyswietl_baza;
    private EditText add_new_produkt_edit_text;
    private String id_kategori;
    private EditText et_nazwa_produktu;

    public CategoryFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baza = new Baza(getContext());
        produktyList = new ArrayList<>();
      //  id_kategori = getActivity().getIntent().getStringExtra("id_kategori");
        produktyList = MainActivity.baza.getAllProdukt();
        produktyList2 = new ArrayList<>();
        AddProduktTabLayout.arraylist2 = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_category, container, false);

        button_add_new_produkt = (Button) v.findViewById(R.id.add_new_produkt_button);
        add_new_produkt_edit_text = (EditText) v.findViewById(R.id.add_new_produkt_edit_text);

        mRecylerView = (RecyclerView) v.findViewById(R.id.my_recycler_view_dodajProdukt);
        mRecylerView.setHasFixedSize(true);

        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerViewAdapterProdukt(getContext(),produktyList);
        mRecylerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        setAddNewProduktEditTextListener();

        return v;
    }


    private void setAddNewProduktEditTextListener() {
        add_new_produkt_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {

        List<Produkt> filterProdukt = new ArrayList<>();

        for (Produkt p : produktyList) {
            String nazwaProduktu = p.getName();
            if (nazwaProduktu.toLowerCase().contains(text.toLowerCase())) {
                filterProdukt.add(p);
             //   AddProduktTabLayout.arraylist2.add(p);
            }
        }

        mAdapter.filterList(filterProdukt);
    }

}
