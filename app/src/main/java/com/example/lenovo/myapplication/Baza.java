package com.example.lenovo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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


    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_PRZYPOMNIENIE = "przypomnienie";

    private static final String KEY_KATEGORIA = "kategoria_nazwa";
    private static final String KEY_LISTA = "lista_nazwa";
    private static final String KEY_IMAGE = "kategoria_image";

     String CREATE_TABLE_KATEGORIA = "CREATE TABLE " + TABLE_KATEGORIA + "("
          + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_KATEGORIA
          + " TEXT," + KEY_IMAGE + " BLOB" + ")";

    String CREATE_TABLE_LISTA = "CREATE TABLE " + TABLE_LISTA + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LISTA + " TEXT,"
            + KEY_CREATED_AT + " TEXT," + KEY_PRZYPOMNIENIE + " TEXT" + ")";

    Drawable drawable1,drawable2,drawable3,drawable4,drawable5,drawable6,drawable7,drawable8,drawable9,drawable10;
    byte[] foto;

    public Baza(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        drawable1 = context.getApplicationContext().getResources().getDrawable(R.drawable.alkohol);
        drawable2 = context.getApplicationContext().getResources().getDrawable(R.drawable.milk);
        drawable3 = context.getApplicationContext().getResources().getDrawable(R.drawable.warzywa);
        drawable4 = context.getApplicationContext().getResources().getDrawable(R.drawable.napoje);
        drawable5 = context.getApplicationContext().getResources().getDrawable(R.drawable.mroznia);
        drawable6 = context.getApplicationContext().getResources().getDrawable(R.drawable.napoje);
        drawable7 = context.getApplicationContext().getResources().getDrawable(R.drawable.napoje);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_KATEGORIA);
        db.execSQL(CREATE_TABLE_LISTA);
        createListaStart(new Lista("Lista startowa","12-04-2017","12-04-2017"),db);


        initStartKategorie(drawable1,"Alkohol",db);
        initStartKategorie(drawable2,"Chemia",db);
        initStartKategorie(drawable3,"Nabiał",db);
        initStartKategorie(drawable4,"Warzywa",db);
        initStartKategorie(drawable5,"Napoje",db);
        initStartKategorie(drawable6,"Mrożonki",db);
        initStartKategorie(drawable7,"Mięso",db);
        initStartKategorie(drawable7,"Słodycze",db);
        initStartKategorie(drawable7,"Ryby i owoce morza",db);
        initStartKategorie(drawable7,"Dania gotowe i sosy",db);
        initStartKategorie(drawable7,"Garmażeria",db);
        initStartKategorie(drawable7,"Przetwory",db);
        initStartKategorie(drawable7,"Sypkie",db);
        initStartKategorie(drawable7,"Przyprawy",db);
        initStartKategorie(drawable7,"Kawa i herbata",db);
        initStartKategorie(drawable7,"Inne",db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTA);

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

    private void createCategoryStart(Category category, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_KATEGORIA, category.getName());
        values.put(KEY_IMAGE, category.getImage());

        // insert row
        db.insert(TABLE_KATEGORIA, null, values);

    }

    private static byte[] imageViewToByte(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] buffer= out.toByteArray();
        return buffer;
    }
    public void createCategoryStart(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KATEGORIA, category.getName());
        values.put(KEY_IMAGE, category.getImage());

        // insert row
        db.insert(TABLE_KATEGORIA, null, values);

        db.close();
    }

    private void initStartKategorie(Drawable drawable, String name, SQLiteDatabase db) {
        Drawable d = drawable;
        foto = imageViewToByte(d);
        createCategoryStart(new Category(name,foto),db);
    }
    /**
     *   Tworzenie list
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

    /**
     *   Tworzenie list
     */
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
 * Deleting a kategoria
 */
    public void deleteKategoria(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_KATEGORIA, KEY_KATEGORIA + " = ?",
                new String[] { String.valueOf(category.getName()) });
        db.close();
    }

    /**
     * Deleting a kategoria
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
}




