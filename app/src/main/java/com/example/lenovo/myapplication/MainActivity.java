package com.example.lenovo.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DialogFragment;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_GETMESSAGE = 1423;
    ArrayList<Lista> lstLista;
    String newName;
    String newData;
    private RecyclerView myrecyclerview;
    private RecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Przycisk dodajacy nowa liste
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddListActivity.makeIntent(MainActivity.this);
                startActivityForResult(intent, REQUEST_CODE_GETMESSAGE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //RecyclerView zawierajacy listy
        myrecyclerview = (RecyclerView) findViewById(R.id.new_list_recyclerview);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myrecyclerview.setItemAnimator(new DefaultItemAnimator());
        lstLista = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(lstLista, this);
        myrecyclerview.setAdapter(mAdapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Utworz_liste) {
            Intent intent = AddListActivity.makeIntent(MainActivity.this);
            startActivityForResult(intent, REQUEST_CODE_GETMESSAGE);
        } else if (id == R.id.lista_zakupow) {

        } else if (id == R.id.edytuj_liste) {

        } else if (id == R.id.usun_listy) {
            removeAllLists();
            Toast.makeText(this, "Wszystkie listy zostały usuniete",Toast.LENGTH_LONG).show();
        } else if (id == R.id.wyslij) {

        } else if (id == R.id.o_autorach) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * Dodawanie nowej listy
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_GETMESSAGE:
                if (resultCode == Activity.RESULT_OK) {
                    newName = data.getStringExtra("Nazwa");
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    newData= dateFormat.format(currentDate);
                    addNewList(new Lista(newName, newData));
                }
        }
    }

    public void addNewList(Lista lista){
        lstLista.add(0, lista);
        mAdapter.notifyItemInserted(0);

    }

    public void removeAllLists() {
        lstLista.removeAll(lstLista);
        mAdapter.notifyDataSetChanged();
    }


    //Nowe activity, klikajac na liste
    public void onClickList(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }


}
