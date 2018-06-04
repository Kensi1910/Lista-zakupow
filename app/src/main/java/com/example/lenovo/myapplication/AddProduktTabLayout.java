package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kensi on 05/05/2018.
 */

public class AddProduktTabLayout extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    public static ArrayList<Produkt> arraylist2;
    public static ArrayList<Produkt> produktyList2;
    private List<Category> lstCategory;
    private static final int REQUEST_CODE = 1423;
    private static final int REQUEST_CODE_KATEGORIA = 122;
    private String newCategoryName;
    private byte[] newCategoryImage;
    private Baza baza;
    private RecyclerView recyclerViewCategory;
    private RecyclerViewAdapterCategory mAdapter;
    byte[] foto;
    private CardView cardView_category;
    private Button pickImageButton;
    private ImageView zdjecie;
    private TextView opis_zmien_zdjecie;
    final int REQUEST_CODE_GALLERY = 999;
    private static final int PICK_IMAGE = 1;
    private Button addNewCategoryButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_produkt_tab_layout);
        setTitle("  ");
        produktyList2 = new ArrayList<>();

        addNewCategoryButton = (Button) findViewById(R.id.button_add_new_category);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new LastProduktFragment(), " Kategorie");
        adapter.AddFragment(new CategoryFragment(), "Ostatnio dodane");
   //     adapter.AddFragment(new FavFragment(), "Popularne");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mylist", arraylist2);
        Intent intent = new Intent();
        intent.putExtras(bundle);
      //  Toast.makeText(AddProduktTabLayout.this, arraylist2.get(0).getName(), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQUEST_CODE_KATEGORIA:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    arraylist2 = bundle.getParcelableArrayList("mylist");
                }
        }
    }

}
