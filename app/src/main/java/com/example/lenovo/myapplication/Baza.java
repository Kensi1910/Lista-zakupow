package com.example.lenovo.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kensi on 27/03/2018.
 */

public class Baza extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ListaDb";

    public Baza(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_KATEGORIA = "CREATE TABLE kategoria\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    nazwa TEXT NOT NULL\n" +
                ")";


        String SQL_CREATE_PRODUKTY =
                "CREATE TABLE produkty(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nazwa TEKST NOT NULL," +
                        "cena_min REAL NOT NULL," +
                        "cena_max REAL NOT NULL," +
                        "id_kategoria INTEGER NOT NULL," +
                        "FOREIGN KEY (id_kategoria) REFERENCES kategoria(_id))";


        String SQL_CREATE_LISTA =
                "CREATE TABLE lista(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nazwa TEKST NOT NULL," +
                        "data_utworzenia TEXT NOT NULL," +
                        "data_przypomnienia TEXT NOT NULL)";

        String SQL_CREATE_PRZEPISY =
                "CREATE TABLE przepisy(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nazwa TEKST NOT NULL," +
                        "opis REAL NOT NULL)";


        String SQL_CREATE_PRZEPISY_PRODUKTY =
                "CREATE TABLE przepisy_produkty(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ilosc INTEGER NOT NULL," +
                        "id_przepisu INTEGER NOT NULL," +
                        "id_produktu INTEGER NOT NULL," +
                        "FOREIGN KEY (id_przepisu) REFERENCES przepisy(_id)," +
                        "FOREIGN KEY (id_produktu) REFERENCES produkty(_id))";


        String SQL_CREATE_LISTA_PRODUKTOW =
                "CREATE TABLE lista_produktow(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_listy INTEGER NOT NULL," +
                        "id_produktu INTEGER NOT NULL," +
                        "ilosc INTEGER NOT NULL," +
                        "FOREIGN KEY (id_listy) REFERENCES lista(_id)," +
                        "FOREIGN KEY (id_produktu) REFERENCES produkty(_id))";
//zmiana obrazkow kategorii edycja kategorii dodawanie list do bazzy edycja i produkty to samo
        db.execSQL(SQL_CREATE_KATEGORIA);
        db.execSQL(SQL_CREATE_LISTA);
        db.execSQL(SQL_CREATE_PRODUKTY);
        db.execSQL(SQL_CREATE_LISTA_PRODUKTOW);
        db.execSQL(SQL_CREATE_PRZEPISY);
        db.execSQL(SQL_CREATE_PRZEPISY_PRODUKTY);

        DodajKategorie("Nabiał", db);
        DodajKategorie("Mięso",db);
        DodajKategorie("Słodycze",db);
        DodajKategorie("Ryby i owoce morza",db);
        DodajKategorie("Dania gotowe i sosy",db);
        DodajKategorie("Garmażeria",db);
        DodajKategorie("Przetwory",db);
        DodajKategorie("Sypkie",db);
        DodajKategorie("Napoje",db);
        DodajKategorie("Przyprawy",db);
        DodajKategorie("Kawa i herbata",db);
        DodajKategorie("Alkohol",db);
        DodajKategorie("Inne",db);
        //String opisJajecznicy = "Wbij jajka na nagrzaną patelnie i dopraw do smaku";

        /*DodajPrzepis("Jajecznica", opisJajecznicy,db);

        DodajProdukt("Jajko", 0.35, 1, 13,db);
        DodajListe("Lista1", "01.01.2019",db);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS kategoria");
        db.execSQL("DROP TABLE IF EXISTS lista");
        db.execSQL("DROP TABLE IF EXISTS produkty");
        db.execSQL("DROP TABLE IF EXISTS lista_produktow");
        db.execSQL("DROP TABLE IF EXISTS przepisy");
        db.execSQL("DROP TABLE IF EXISTS przepisy_produkty");

        onCreate(db);
    }

    private void DodajKategorie(String nazwa,  SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put("nazwa", nazwa);

        db.insert("kategoria", null, values);
    }

    public void DodajKategorie(String nazwa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nazwa", nazwa);

        db.insert("kategoria", null, values);
        db.close();
    }

   /* private void DodajPrzepis(String nazwa, String opis, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put("nazwa", nazwa);
        values.put("opis", opis);

        db.insert("przepisy", null, values);

    }
    public void DodajPrzepis(String nazwa, String opis) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nazwa", nazwa);
        values.put("opis", opis);

        db.insert("przepisy", null, values);
        db.close();
    }

   private void DodajProdukt(String nazwa, double cenaMin, double cenaMax, int idKategoria, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put("nazwa", nazwa);
        values.put("cena_minin", cenaMin);
        values.put("cena_max", cenaMax);
        values.put("id_kategoria", idKategoria);

        db.insert("produkty", null, values);
        db.close();
    }
    public void DodajProdukt(String nazwa, double cenaMin, double cenaMax, int idKategoria) {
        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();


        values.put("nazwa", nazwa);
        values.put("cena_minin", cenaMin);
        values.put("cena_max", cenaMax);
        values.put("id_kategoria", idKategoria);

        db.insert("produkty", null, values);
        db.close();
    }

    private void DodajListe(String nazwa, String dataPrzypomnienia, SQLiteDatabase db) {
        Date dataUtworzenia = Calendar.getInstance().getTime();

        ContentValues values = new ContentValues();

        values.put("nazwa", nazwa);
        values.put("data_utworzenia", dataUtworzenia.toString());
        values.put("data_przypomnienia", dataPrzypomnienia);

        db.insert("lista", null, values);
        db.close();

    }
    public void DodajListe(String nazwa, String dataPrzypomnienia) {
        Date dataUtworzenia = Calendar.getInstance().getTime();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nazwa", nazwa);
        values.put("data_utworzenia", dataUtworzenia.toString());
        values.put("data_przypomnienia", dataPrzypomnienia);

        db.insert("lista", null, values);
        db.close();

    }*/

    public StringBuilder WypiszKategorie() {

        //String asd = "";

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

}



