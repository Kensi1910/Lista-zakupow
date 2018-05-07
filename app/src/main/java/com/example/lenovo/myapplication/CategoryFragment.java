package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<Produkt> produktyList2;
    private List<Produkt> produktyList;
    private Button button_add_new_produkt;
    private Button button_wyswietl_baza;
    private TextView tv_wyswietl_baza;
    private EditText add_new_produkt_edit_text;
    private String id_kategori;

    public CategoryFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baza = new Baza(getContext());
        produktyList = new ArrayList<>();
      //  id_kategori = getActivity().getIntent().getStringExtra("id_kategori");
        produktyList = MainActivity.baza.getAllProdukt();
     //   produktyList2 = new ArrayList<>();
        AddProduktTabLayout.arraylist2 = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_category, container, false);

        tv_wyswietl_baza = (TextView) v.findViewById(R.id.tv_wyswietl_produkty_baza);
        button_wyswietl_baza = (Button) v.findViewById(R.id.wyswietlProdukt);
        button_add_new_produkt = (Button) v.findViewById(R.id.add_new_produkt_button);
        add_new_produkt_edit_text = (EditText) v.findViewById(R.id.add_new_produkt_edit_text);

        mRecylerView = (RecyclerView) v.findViewById(R.id.my_recycler_view_dodajProdukt);
        mRecylerView.setHasFixedSize(true);

        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerViewAdapterProdukt(getContext(),produktyList);
        mRecylerView.setAdapter(mAdapter);


        button_wyswietl_baza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_wyswietl_baza.setText(baza.WypiszProdukt());

            }
        });

        addNewProduktButtonListener();
        mAdapter.notifyDataSetChanged();

        return v;
    }

    public void addNewProduktButtonListener() {
        button_add_new_produkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_new_produkt_edit_text.getText().toString();
               // int id_kategori_int = Integer.parseInt(id_kategori);
                Produkt produkt = new Produkt(name, 0.0f, 0.0f);
                baza.createProdukt(produkt);
                Toast.makeText(getActivity().getApplicationContext(), "Dodano " + name, Toast.LENGTH_LONG).show();
                add_new_produkt_edit_text.setText("");
                produktyList.add(0, baza.getProdukt());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddListActivity.class);
    }


}
