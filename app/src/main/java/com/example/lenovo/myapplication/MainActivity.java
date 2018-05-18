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
import android.widget.ListView;
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
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemClickListener {

    public static Baza baza;
    private static final int REQUEST_CODE_GETMESSAGE = 1423;
    private static final int REQUEST_CODEE = 142;
    public List<Lista> lstLista;
    String newName;
    String newData;
    String newData2;
    int ilosc;
    String ilosc2;
    LinearLayout item_list;
    private RecyclerView myrecyclerview;
    private RecyclerViewAdapter mAdapter;
    private static String listName;
    private static String lstId;
    private static int lstId2;
    public static int[] itemCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        baza = new Baza(this);
        lstLista = new ArrayList<>();
        lstLista = MainActivity.baza.getAllLista();

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
        mAdapter = new RecyclerViewAdapter(lstLista, this);
        myrecyclerview.setAdapter(mAdapter);

        mAdapter.setClickListener(this);
        mAdapter.notifyDataSetChanged();

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
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
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
            baza.deleteListaAll();
            Toast.makeText(this, "Wszystkie listy zostały usuniete",Toast.LENGTH_LONG).show();
        } else if (id == R.id.przepisy_item) {
            Intent intent = new Intent(MainActivity.this,przepisy_activity.class);
            startActivity(intent);

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
                    Bundle bundle = data.getExtras();
                    newName = bundle.getString("key_lista_name");
                    newData = bundle.getString("key_lista_data_u");
                    newData2 = bundle.getString("key_lista_data_p");
                    ilosc = bundle.getInt("key_ilosc_produktow");
                    addNewList(new Lista(newName, newData, newData2,ilosc));
                }
                break;
            case REQUEST_CODEE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int ilosc = bundle.getInt("ilosc_produktow");
                    int id_listyy = bundle.getInt("id_listyy");
                    lstLista.get(id_listyy).setIlosc(ilosc);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }

    }
    // on activiry result nedzie zwracalo id proudktow i listy i zaposywalo do addedlist, a pozniej z tego nea tpwrzone prodiktu,
    //  a potem itemy produktow
    public void addNewList(Lista lista){
        lstLista.add(0, lista);
        mAdapter.notifyItemInserted(0);
    }

    public void removeAllLists() {
        lstLista.removeAll(lstLista);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view, int position) {
        final Lista lista = lstLista.get(position);
        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
        listName = lista.getName();
        lstId = baza.getIDListy(listName);
        lstId2 = position;
        intent.putExtra("ListId", lstId);
        intent.putExtra("ListName", listName);
        intent.putExtra("ListId2", lstId2);
        startActivityForResult(intent,REQUEST_CODEE);
    }

    public static String getListName() {
        return listName;
    }


    public static String getListId() {
        return lstId;
    }

    //Nowe activity, klikajac na liste
/*
    public void setLisntnerOnButtonItemList() {
        item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            //    String id = mAdapter.getName();
            //    Toast.makeText(MainActivity.this, id, Toast.LENGTH_LONG).show();
               // intent.putExtra("ID",id);
                startActivity(intent);
            }
        });
    }
*/

}
