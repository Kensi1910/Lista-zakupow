package com.example.lenovo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static android.content.ContentValues.TAG;


/**
 * Created by kensi on 27/03/2018.
 */

public class Baza extends SQLiteOpenHelper {

    private static final String LOG = "Baza";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ListaDb";

    // Table Names
    private static final String TABLE_KATEGORIA = "kategoria";
    private static final String TABLE_LISTA = "lista";
    private static final String TABLE_PRODUKTY = "produkty";
    private static final String TABLE_LISTA_PRODUKTOW = "lista_produktow";


    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_PRZYPOMNIENIE = "przypomnienie";

    private static final String KEY_KATEGORIA = "kategoria_nazwa";
    private static final String KEY_LISTA = "lista_nazwa";
    private static final String KEY_IMAGE = "kategoria_image";
    private static final String KEY_PRODUKT = "produkt_nazwa";
    private static final String KEY_CENA_MIN = "cena_min";
    private static final String KEY_CENA_MAX = "cena_max";
    private static final String KEY_ID_KATEGORIA = "id_kategoria";
    private static final String KEY_ID_LISTA = "id_lista";
    private static final String KEY_ID_PRODUKT = "id_produkt";
    private static final String KEY_ILOSC = "ilosc";

     String CREATE_TABLE_KATEGORIA = "CREATE TABLE " + TABLE_KATEGORIA + "("
          + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_KATEGORIA
          + " TEXT NOT NULL," + KEY_IMAGE + " BLOB NOT NULL" + ")";

    String CREATE_TABLE_LISTA = "CREATE TABLE " + TABLE_LISTA + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LISTA + " TEXT NOT NULL,"
            + KEY_CREATED_AT + " TEXT NOT NULL," + KEY_PRZYPOMNIENIE + " TEXT NOT NULL" + ")";

    String CREATE_TABLE_PRODUKTY = "CREATE TABLE " + TABLE_PRODUKTY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUKT + " TEXT NOT NULL,"
            + KEY_CENA_MIN + " FLOAT NOT NULL," + KEY_CENA_MAX + " FLOAT NOT NULL,"
            + KEY_ID_KATEGORIA + " INTEGER," + "FOREIGN KEY (" + KEY_ID_KATEGORIA + ") REFERENCES "
             + TABLE_KATEGORIA + "(" + KEY_ID + "))";

    String CREATE_TABLE_LISTA_PRODUKTOW = "CREATE TABLE " + TABLE_LISTA_PRODUKTOW + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ID_LISTA
                + " INTEGER NOT NULL," + KEY_ID_PRODUKT + " INTEGER NOT NULL,"
                + KEY_ILOSC + " INTEGER," + " FOREIGN KEY (" + KEY_ID_LISTA + ") REFERENCES " + TABLE_LISTA + "("
                + KEY_ID + ")," + " FOREIGN KEY (" + KEY_ID_PRODUKT + ") REFERENCES " + TABLE_PRODUKTY + "("
                + KEY_ID + "))";

    Drawable drawable1,drawable2,drawable3,drawable4,drawable5,drawable6,drawable7,drawable8,drawable9,drawable10,drawable11,drawable12,drawable13,drawable14,drawable15,drawable16,drawable17,drawable18,drawable19,drawable20, drawable21,drawable22,drawable23,drawable24, drawable25,drawable26,drawable27,drawable28, drawable29;
    byte[] foto;

    public Baza(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        drawable1 = context.getApplicationContext().getResources().getDrawable(R.drawable.food1);
        drawable2 = context.getApplicationContext().getResources().getDrawable(R.drawable.food2);
        drawable3 = context.getApplicationContext().getResources().getDrawable(R.drawable.food3);
        drawable4 = context.getApplicationContext().getResources().getDrawable(R.drawable.food4);
        drawable5 = context.getApplicationContext().getResources().getDrawable(R.drawable.food5);
        drawable6 = context.getApplicationContext().getResources().getDrawable(R.drawable.food6);
        drawable7 = context.getApplicationContext().getResources().getDrawable(R.drawable.food8);
        drawable8 = context.getApplicationContext().getResources().getDrawable(R.drawable.dog);
        drawable9 = context.getApplicationContext().getResources().getDrawable(R.drawable.agenda);
        drawable10 = context.getApplicationContext().getResources().getDrawable(R.drawable.animals);
        drawable11 = context.getApplicationContext().getResources().getDrawable(R.drawable.bottle);
        drawable12 = context.getApplicationContext().getResources().getDrawable(R.drawable.cheese);
        drawable13 = context.getApplicationContext().getResources().getDrawable(R.drawable.cleaning);
        drawable14 = context.getApplicationContext().getResources().getDrawable(R.drawable.clip);
        drawable15 = context.getApplicationContext().getResources().getDrawable(R.drawable.commerce);
        drawable16 = context.getApplicationContext().getResources().getDrawable(R.drawable.drink);
        drawable17 = context.getApplicationContext().getResources().getDrawable(R.drawable.fruit);
        drawable18 = context.getApplicationContext().getResources().getDrawable(R.drawable.fruit1);
        drawable19 = context.getApplicationContext().getResources().getDrawable(R.drawable.suitcase);
        drawable20 = context.getApplicationContext().getResources().getDrawable(R.drawable.technology);
        drawable21 = context.getApplicationContext().getResources().getDrawable(R.drawable.tool);
        drawable22 = context.getApplicationContext().getResources().getDrawable(R.drawable.tool1);
        drawable23 = context.getApplicationContext().getResources().getDrawable(R.drawable.tool2);
        drawable29 = context.getApplicationContext().getResources().getDrawable(R.drawable.transport);
        drawable24 = context.getApplicationContext().getResources().getDrawable(R.drawable.food7);
        drawable25 = context.getApplicationContext().getResources().getDrawable(R.drawable.food9);
        drawable26 = context.getApplicationContext().getResources().getDrawable(R.drawable.food10);
        drawable27 = context.getApplicationContext().getResources().getDrawable(R.drawable.food11);
        drawable28 = context.getApplicationContext().getResources().getDrawable(R.drawable.food12);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_KATEGORIA);
        db.execSQL(CREATE_TABLE_LISTA);
        db.execSQL(CREATE_TABLE_PRODUKTY);
        db.execSQL(CREATE_TABLE_LISTA_PRODUKTOW);

        createProduktyStart(new Produkt("Produkt1", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("Produkt2", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("Produkt3", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("Produkt4", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("Produkt5", 1.2f, 4.5f,3), db);
        createProduktyStart(new Produkt("Produkt6", 1.2f, 4.5f,4), db);

        long produkt = createAddedProdukt2(new Produkt("Mleko", 1.2f, 4.5f),db);
        long produkt2 = createAddedProdukt2(new Produkt("Jajka", 1.2f, 4.5f),db);


        long c = createAddedProduktLista(new Lista("Lista Startowa","12-04-2017","12-04-2017"), new long[] { produkt },db);
        createListaProdoktow(c,produkt2,db);

      //  createListaStart(new Lista("Lista startowa","12-04-2017","12-04-2017"),db);


        initStartKategorie(drawable1,"art. dzieciece",db);
        initStartKategorie(drawable2,"art. sypkie",db);
        initStartKategorie(drawable3,"ciasta, desery",db);
        initStartKategorie(drawable4,"konserwy",db);
        initStartKategorie(drawable5,"mieso i wedliny",db);
        initStartKategorie(drawable6,"mrozonki i lody",db);
        initStartKategorie(drawable7,"przetwory",db);
        initStartKategorie(drawable8,"art. dla zwierzat",db);
        initStartKategorie(drawable9,"prasa, ksiazki",db);
        initStartKategorie(drawable10,"apteczka",db);
        initStartKategorie(drawable11,"kosmetyki",db);
        initStartKategorie(drawable12,"nabial",db);
        initStartKategorie(drawable13,"art. domowe",db);
        initStartKategorie(drawable14,"art. biurowe",db);
        initStartKategorie(drawable15,"ubrania",db);
        initStartKategorie(drawable16,"woda i napoje",db);
        initStartKategorie(drawable17,"warzywa i owoce",db);
        initStartKategorie(drawable18,"fit food",db);
        initStartKategorie(drawable19,"Inne",db);
        initStartKategorie(drawable20,"agd i rtv",db);
        initStartKategorie(drawable22,"chemia gospodarcza",db);
        initStartKategorie(drawable23,"higiena",db);
        initStartKategorie(drawable24,"pieczywo",db);
        initStartKategorie(drawable25,"przyprawy, sosy",db);
        initStartKategorie(drawable26,"ryby",db);
        initStartKategorie(drawable27,"slodycze i przekaski",db);
        initStartKategorie(drawable28,"tluszcze",db);
        initStartKategorie(drawable28,"motoryzacja",db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUKTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTA_PRODUKTOW);
        onCreate(db);
    }

    /**
    *   Tworzenie kategorii
    */
    public long createCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KATEGORIA, category.getName());
        values.put(KEY_IMAGE, category.getImage());

        // insert row
        long category_id = db.insert(TABLE_KATEGORIA, null, values);

        db.close();
        return category_id;
    }

    /**
     *   Tworzenie kategorii, startowe kategorie
     */
    private void createCategoryStart(Category category, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_KATEGORIA, category.getName());
        values.put(KEY_IMAGE, category.getImage());

        // insert row
        db.insert(TABLE_KATEGORIA, null, values);

    }

    /**
     *   Tworzenie kategorii, startowe kategorie
     */
    public void createCategoryStart(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KATEGORIA, category.getName());
        values.put(KEY_IMAGE, category.getImage());

        // insert row
        db.insert(TABLE_KATEGORIA, null, values);

        db.close();
    }

    /**
     *     zamiana zdjecia na format byte[] potrzeby do zapisu do bazy
     */
    private static byte[] imageViewToByte(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer= out.toByteArray();
        return buffer;
    }


    /**
     *
     * @param drawable logo kategori
     * @param name nazwa kategori
     * @param db baza
     */
    private void initStartKategorie(Drawable drawable, String name, SQLiteDatabase db) {
        Drawable d = drawable;
        foto = imageViewToByte(d);
        createCategoryStart(new Category(name,foto),db);
    }
    /*
    private void initStartProdukty(String name,Float cena_min, Float cena_max, SQLiteDatabase db) {
        createProduktyStart(new Produkt(name, cena_min, cena_max),db);
    }
    */
    /**
     *   Tworzenie listy zakupow
     */
    public long createLista(Lista lista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());

        // insert row
        long lista_id = db.insert(TABLE_LISTA, null, values);

        db.close();
        return lista_id;
    }
    private long createLista(Lista lista, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());

        // insert row
        long lista_id = db.insert(TABLE_LISTA, null, values);

        db.close();
        return lista_id;
    }

    /**
     * Tworzenie przykladowej listy zakupwo - startowej
     * @param lista
     * @param db
     */
    private void createListaStart(Lista lista, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());

        // insert row
        db.insert(TABLE_LISTA, null, values);

    }
   public void createListaStart(Lista lista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());

        // insert row
        db.insert(TABLE_LISTA, null, values);

        db.close();

    }

    /*
    private long createdListaStart(Lista lista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());

        // insert row
        long lista_id = db.insert(TABLE_LISTA, null, values);

        db.close();
        return lista_id;
    }
*/

    /**
     * Tworzenie produktu
     * @param produkt
     * @return
     */
    public long createProdukt(Produkt produkt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUKT, produkt.getName());
        values.put(KEY_CENA_MIN, produkt.getCena_min());
        values.put(KEY_CENA_MAX, produkt.getCena_max());
        values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());

        // insert row
        long produkt_id = db.insert(TABLE_PRODUKTY, null, values);

        db.close();
        return produkt_id;
    }

    /**
     * Tworzenie produktu dodanego do listy - startowy
     * @param produkt
     * @param db
     * @return
     */
    public long createAddedProdukt2(Produkt produkt, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUKT, produkt.getName());
        values.put(KEY_CENA_MIN, produkt.getCena_min());
        values.put(KEY_CENA_MAX, produkt.getCena_max());
        values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());

        // insert row
        long produkt_id = db.insert(TABLE_PRODUKTY, null, values);

      //  db.close();
        return produkt_id;
    }

    public long createAddedProduktLista(Lista lista, long[] addedProdukts, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());
        //   values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());

        // insert row
        long produkt_id = db.insert(TABLE_LISTA, null, values);

        for (long addedProdukt : addedProdukts) {
            createListaProdoktow(produkt_id, addedProdukt,db);
        }
  //      db.close();
        return produkt_id;
    }


    public void createListaProdoktow(long produktID, long addedProduktID, SQLiteDatabase db) {
        //    db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_LISTA, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
        //  values.put(KEY_ILOSC, addedProdukt.getIlosc());

        // insert row
        db.insert(TABLE_LISTA_PRODUKTOW, null, values);

        //  for (long produktID : produktIDs) {
        //
        //   }
     //   db.close();
       // return added_produkt_id;
    }

    public long createListaProdoktow(long produktID, long addedProduktID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_LISTA, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
        //  values.put(KEY_ILOSC, addedProdukt.getIlosc());

        // insert row
        long added_produkt_id = db.insert(TABLE_LISTA_PRODUKTOW, null, values);


        db.close();
        return added_produkt_id;
    }

    private void createProduktyStart(Produkt produkt, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUKT, produkt.getName());
        values.put(KEY_CENA_MIN, produkt.getCena_min());
        values.put(KEY_CENA_MAX, produkt.getCena_max());
        values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());

        // insert row
        db.insert(TABLE_PRODUKTY, null, values);

    }
    public void createProduktyStart(Produkt produkt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUKT, produkt.getName());
        values.put(KEY_CENA_MIN, produkt.getCena_min());
        values.put(KEY_CENA_MAX, produkt.getCena_max());
        values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());

        // insert row
        db.insert(TABLE_PRODUKTY, null, values);

        db.close();

    }
/*
    public long createListaProdoktow(AddedProdukt addedProdukt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_LISTA, addedProdukt.getId_listy());
        values.put(KEY_ID_PRODUKT, addedProdukt.getId_produktu());
        values.put(KEY_ILOSC, addedProdukt.getIlosc());

        // insert row
        long added_produkt_id = db.insert(TABLE_LISTA_PRODUKTOW, null, values);

      //  for (long produktID : produktIDs) {
    //
     //   }
        db.close();
        return added_produkt_id;
    }
*/
    /**
     *  get single category, the last
     */
    public Category getCategory() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_KATEGORIA;
      //  Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
             c.moveToLast();

        Category ctg = new Category();
        ctg.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        ctg.setName((c.getString(c.getColumnIndex(KEY_KATEGORIA))));
        ctg.setImage(c.getBlob(c.getColumnIndex(KEY_IMAGE)));

        c.close();
        return ctg;
    }

    /**
     *  get single lista, the last
     */
    public Lista getLista() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LISTA;
     //   Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        Lista lst = new Lista();
        lst.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        lst.setName((c.getString(c.getColumnIndex(KEY_LISTA))));
        lst.setData(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
        lst.setData2(c.getString(c.getColumnIndex(KEY_PRZYPOMNIENIE)));

        c.close();
        return lst;
    }

    /**
     *  get single produkt, the last
     */
    public Produkt getProdukt() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUKTY;
        //   Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        Produkt pdt = new Produkt();
        pdt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        pdt.setName(c.getString(c.getColumnIndex(KEY_PRODUKT)));
        pdt.setCena_min(c.getFloat(c.getColumnIndex(KEY_CENA_MIN)));
        pdt.setCena_max(c.getFloat(c.getColumnIndex(KEY_CENA_MAX)));
        pdt.setId_kategoria(c.getInt(c.getColumnIndex(KEY_ID_KATEGORIA)));

        c.close();
        return pdt;
    }

    public Produkt getProduktByID(int produkt_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUKTY + " WHERE " + KEY_ID + " = " + produkt_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        Produkt pdt = new Produkt();
        pdt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        pdt.setName(c.getString(c.getColumnIndex(KEY_PRODUKT)));
        pdt.setCena_min(c.getFloat(c.getColumnIndex(KEY_CENA_MIN)));
        pdt.setCena_max(c.getFloat(c.getColumnIndex(KEY_CENA_MAX)));
        pdt.setId_kategoria(c.getInt(c.getColumnIndex(KEY_ID_KATEGORIA)));

        c.close();
        return pdt;
    }
    /*
    public Produkt getAddedProductID() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToLast();

        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID_PRODUKT));
        String selectQuery2 = "SELECT * FROM " + TABLE_PRODUKTY + " WHERE " + KEY_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery2, null);

        if (c != null)
            c.moveToLast();
        Produkt pdt = new Produkt();
        pdt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        pdt.setName(c.getString(c.getColumnIndex(KEY_PRODUKT)));
        pdt.setCena_min(c.getFloat(c.getColumnIndex(KEY_CENA_MIN)));
        pdt.setCena_max(c.getFloat(c.getColumnIndex(KEY_CENA_MAX)));

        c.close();
        cursor.close();
        return pdt;
    }
    */
    public List<Produkt> getAddedProductyID(String id_listy) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_LISTA + " = " + id_listy;
        List<Produkt> lstProdukt = new ArrayList<Produkt>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID_PRODUKT));
                String selectQuery2 = "SELECT * FROM " + TABLE_PRODUKTY + " WHERE " + KEY_ID + " = " + id;
                Cursor c = db.rawQuery(selectQuery2, null);
                if (c.moveToFirst()) {
                    do {
                        Produkt pdt = new Produkt();
                        pdt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                        pdt.setName(c.getString(c.getColumnIndex(KEY_PRODUKT)));
                        pdt.setCena_min(c.getFloat(c.getColumnIndex(KEY_CENA_MIN)));
                        pdt.setCena_max(c.getFloat(c.getColumnIndex(KEY_CENA_MAX)));
                        pdt.setId_kategoria(c.getInt(c.getColumnIndex(KEY_ID_KATEGORIA)));

                        // adding category list
                        lstProdukt.add(pdt);
                    } while (c.moveToNext());
                }
                c.close();
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lstProdukt;
    }

    /**
 * getting all katogoria
 * */
    public List<Category> getAllKategoria() {
        List<Category> lstCategory = new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + TABLE_KATEGORIA;

      //  Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Category ctg = new Category();
                ctg.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                ctg.setName((c.getString(c.getColumnIndex(KEY_KATEGORIA))));
                ctg.setImage(c.getBlob(c.getColumnIndex(KEY_IMAGE)));

                // adding category list
               lstCategory.add(ctg);
            } while (c.moveToNext());
        }

        c.close();
        return lstCategory;
    }



    /**
     * getting all lista
     * */

    public List<Lista> getAllLista() {
        List<Lista> lstLista = new ArrayList<Lista>();
        String selectQuery = "SELECT * FROM " + TABLE_LISTA;
       // Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Lista lst = new Lista();
                lst.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                lst.setName((c.getString(c.getColumnIndex(KEY_LISTA))));
                lst.setData((c.getString(c.getColumnIndex(KEY_CREATED_AT))));
                lst.setData2((c.getString(c.getColumnIndex(KEY_PRZYPOMNIENIE))));

                // adding category list
                lstLista.add(lst);
            } while (c.moveToNext());
        }

        c.close();
        return lstLista;
    }


    /**
     * getting all produkt
     * */

    public List<Produkt> getAllProdukt(String id_kategori) {
        List<Produkt> lstProdukt = new ArrayList<Produkt>();
        String selectQuery2 = "SELECT * FROM " + TABLE_PRODUKTY + " WHERE " + KEY_ID_KATEGORIA + " = " + id_kategori;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery2, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Produkt pdt = new Produkt();
                pdt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                pdt.setName(c.getString(c.getColumnIndex(KEY_PRODUKT)));
                pdt.setCena_min(c.getFloat(c.getColumnIndex(KEY_CENA_MIN)));
                pdt.setCena_max(c.getFloat(c.getColumnIndex(KEY_CENA_MAX)));
                pdt.setId_kategoria(c.getInt(c.getColumnIndex(KEY_ID_KATEGORIA)));

                // adding category list
                lstProdukt.add(pdt);
            } while (c.moveToNext());
        }

        c.close();
        return lstProdukt;

    }

    public List<Produkt> getAllProdukt() {
        List<Produkt> lstProdukt = new ArrayList<Produkt>();
        String selectQuery2 = "SELECT * FROM " + TABLE_PRODUKTY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery2, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Produkt pdt = new Produkt();
                pdt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                pdt.setName(c.getString(c.getColumnIndex(KEY_PRODUKT)));
                pdt.setCena_min(c.getFloat(c.getColumnIndex(KEY_CENA_MIN)));
                pdt.setCena_max(c.getFloat(c.getColumnIndex(KEY_CENA_MAX)));
                pdt.setId_kategoria(c.getInt(c.getColumnIndex(KEY_ID_KATEGORIA)));

                // adding category list
                lstProdukt.add(pdt);
            } while (c.moveToNext());
        }

        c.close();
        return lstProdukt;

    }

    /**
     * getting all produkt
     * */
/*
    public List<AddedProdukt> getAllAddedProdukt() {
        List<AddedProdukt> lstAddedProdukt = new ArrayList<AddedProdukt>();
        String selectQuery = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW;
        // Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                AddedProdukt addp = new AddedProdukt();
                addp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                addp.setId_listy(c.getInt(c.getColumnIndex(KEY_ID_LISTA)));
                addp.setId_produktu(c.getInt(c.getColumnIndex(KEY_ID_PRODUKT)));
                addp.setIlosc(c.getInt(c.getColumnIndex(KEY_ILOSC)));

               lstAddedProdukt.add(addp);
            } while (c.moveToNext());
        }

        c.close();
        return lstAddedProdukt;
    }
    */
    /**
 * Updating a katogoria
 */
    public int updateKateogria(Category category, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KATEGORIA, category.getName());
        values.put(KEY_IMAGE, category.getImage());

        return db.update(TABLE_KATEGORIA, values, KEY_KATEGORIA + " = ?",
                new String[] { String.valueOf(name) });
    }

    /**
     * Updating a lista
     */
    public int updateLista(Lista lista, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());

        return db.update(TABLE_LISTA, values, KEY_LISTA + " = ?",
                new String[] { String.valueOf(name) });
    }


    /**
     * Updating a lista
     */
    public int updateProdukt(Produkt produkt, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUKT, produkt.getName());
        values.put(KEY_CENA_MIN, produkt.getCena_min());
        values.put(KEY_CENA_MAX, produkt.getCena_max());
        //   values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());

        return db.update(TABLE_PRODUKTY, values, KEY_PRODUKT + " = ?",
                new String[] { String.valueOf(name) });
    }

    /**
 * Deleting a kategoria
 */
    public void deleteKategoria(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_KATEGORIA, KEY_KATEGORIA + " = ?",
                new String[] { String.valueOf(category.getName()) });
        db.close();
    }

    /**
     * Deleting a lista
     */
    public void deleteLista(Lista lista) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_LISTA, KEY_LISTA + " = ?",
                new String[] { String.valueOf(lista.getName()) });
        db.close();
    }

    public void deleteListaAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LISTA, null, null);
    }

    /**
     * Deleting a produkt
     */
    public void deleteProdukt(Produkt produkt) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PRODUKTY, KEY_PRODUKT + " = ?",
                new String[] { String.valueOf(produkt.getName()) });
        db.close();
    }
    // closing database

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_ID + " FROM " + TABLE_KATEGORIA +
                " WHERE " + KEY_KATEGORIA + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public String getIDProdukt(String name) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUKTY +
                " WHERE " + KEY_KATEGORIA + " = '" + name + "'";
        //   Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        int id;
        id = c.getInt(c.getColumnIndex(KEY_ID));

        c.close();
        String id_string = String.valueOf(id);
        return id_string;
    }

    public String getIDListy(String name) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LISTA +
        " WHERE " + KEY_LISTA + " = '" + name + "'";
        //   Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        int id;
         id = c.getInt(c.getColumnIndex(KEY_ID));

        c.close();
        String id_string = String.valueOf(id);
        return id_string;
    }

    public String getIDCategory(String name) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_KATEGORIA +
                " WHERE " + KEY_KATEGORIA + " = '" + name + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        int id;
        id = c.getInt(c.getColumnIndex(KEY_ID));

        c.close();
        String id_string = String.valueOf(id);
        return id_string;
    }
    public StringBuilder WypiszKategorie() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM kategoria", null);
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                sb.append(cursor.getInt(0));
                sb.append(") ");
                sb.append(cursor.getString(1))/* + String.valueOf(cursor.getInt(0)) +". " +cursor.getString(1) +*/ ;
                sb.append(",");
                sb.append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sb;
    }

    public StringBuilder WypiszLista() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lista", null);
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                sb.append(cursor.getInt(0));
                sb.append(") ");
                sb.append(cursor.getString(1))/* + String.valueOf(cursor.getInt(0)) +". " +cursor.getString(1) +*/ ;
                sb.append(",");
                sb.append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sb;
    }

    public StringBuilder WypiszProdukt() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM produkty", null);
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                sb.append(cursor.getInt(0));
                sb.append(") ");
                sb.append(cursor.getString(1))/* + String.valueOf(cursor.getInt(0)) +". " +cursor.getString(1) +*/ ;
                sb.append(",");
                sb.append(cursor.getInt(4));
                sb.append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sb;
    }
    public StringBuilder WypiszListeProdukt() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lista_produktow", null);
        StringBuilder sb = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                sb.append(cursor.getInt(0));
                sb.append(") ");
                sb.append(cursor.getString(1))/* + String.valueOf(cursor.getInt(0)) +". " +cursor.getString(1) +*/ ;
                sb.append(",");
                sb.append(cursor.getString(2));
                sb.append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sb;
    }
}




