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
    private static final String TABLE_PRZEPISY = "przepisy";
    private static final String TABLE_PRZEPISY_PRODUKTY = "przepisy_produkty";


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
    private static final String KEY_ILOSC_PRODUKTOW = "ilosc_produktow";
    private static final String KEY_JEDNOSTKA = "jednostka";
    private static final String KEY_SELECTED = "wybrany";
    private static final String KEY_PRZEPISY = "przepisy_nazwa";
    private static final String KEY_OPIS = "przepisy_opis";
    private static final String KEY_ID_PRZEPISU = "id_przepisy";

     String CREATE_TABLE_KATEGORIA = "CREATE TABLE " + TABLE_KATEGORIA + "("
          + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_KATEGORIA
          + " TEXT NOT NULL," + KEY_IMAGE + " BLOB NOT NULL" + ")";

    String CREATE_TABLE_LISTA = "CREATE TABLE " + TABLE_LISTA + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LISTA + " TEXT NOT NULL,"
            + KEY_CREATED_AT + " TEXT NOT NULL," + KEY_PRZYPOMNIENIE + " TEXT NOT NULL,"
            + KEY_ILOSC_PRODUKTOW + " INTEGER" + ")";

    String CREATE_TABLE_PRODUKTY = "CREATE TABLE " + TABLE_PRODUKTY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUKT + " TEXT NOT NULL,"
            + KEY_CENA_MIN + " FLOAT NOT NULL," + KEY_CENA_MAX + " FLOAT NOT NULL,"
            + KEY_ID_KATEGORIA + " INTEGER,"  + "FOREIGN KEY (" + KEY_ID_KATEGORIA + ") REFERENCES "
             + TABLE_KATEGORIA + "(" + KEY_ID + "))";

    String CREATE_TABLE_LISTA_PRODUKTOW = "CREATE TABLE " + TABLE_LISTA_PRODUKTOW + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ID_LISTA
                + " INTEGER NOT NULL," + KEY_ID_PRODUKT + " INTEGER NOT NULL,"
                + KEY_ILOSC + " FLOAT," + KEY_JEDNOSTKA + " TEXT," + KEY_SELECTED + " INTEGER," + " FOREIGN KEY (" + KEY_ID_LISTA + ") REFERENCES " + TABLE_LISTA + "("
                + KEY_ID + ")," + " FOREIGN KEY (" + KEY_ID_PRODUKT + ") REFERENCES " + TABLE_PRODUKTY + "("
                + KEY_ID + "))";

    String CREATE_TABLE_PRZEPISY = "CREATE TABLE " + TABLE_PRZEPISY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRZEPISY
            + " TEXT NOT NULL," + KEY_OPIS + " TEXT NOT NULL," + KEY_IMAGE + " BLOB NOT NULL" + ")";


    String CREATE_TABLE_PRZEPISY_PRODUKTY = "CREATE TABLE " + TABLE_PRZEPISY_PRODUKTY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ID_PRZEPISU
            + " INTEGER NOT NULL," + KEY_ID_PRODUKT + " INTEGER NOT NULL,"
            + KEY_ILOSC + " INTEGER," + " FOREIGN KEY (" + KEY_ID_PRZEPISU + ") REFERENCES " + TABLE_PRZEPISY + "("
            + KEY_ID + ")," + " FOREIGN KEY (" + KEY_ID_PRODUKT + ") REFERENCES " + TABLE_PRODUKTY + "("
            + KEY_ID + "))";

    Drawable drawable1,drawable2,drawable3,drawable4,drawable5,drawable6,drawable7,drawable8,drawable9,drawable10,drawable11,drawable12,drawable13,drawable14,drawable15,drawable16,drawable17,drawable18,drawable19,drawable20, drawable21,drawable22,drawable23,drawable24, drawable25,drawable26,drawable27,drawable28, drawable29;
    Drawable drawableP1, drawableP2, drawableP3, drawableP4, drawableP5, drawableP6,drawableP7, drawableP8, drawableP9;
    byte[] foto;

    public  String[] opis = {
            //1
            "Krok 1 \n\n" +
                    "Główkę czosnku zawiń w aluminiową folię i wstaw do nagrzanego do 180° C piekarnika na 25 minut. Po tym czasie czosnek obierz, rozgnieć i drobno posiekaj. Natkę oraz cebulę także posiekaj, a ser zetrzyj na tarce o drobnych oczkach. \n\nKrok 2\n\n" +
                    "Pierś osusz papierowym ręcznikiem, skrop oliwą i posyp przyprawą do kurczaka. Pierś usmaż na grillowej patelni (po 4-5 minut z każdej strony), lekko wystudź i pokrój w paski \n\nKrok 3\n\n" +
                    "Na patelni rozgrzej oliwę, zeszklij cebulę, a następnie wlej wino i podgrzewaj do momentu odparowania płynu.\n\nKrok 4\n\n" +
                    "Zawartość opakowania Knorr rozmieszaj w miseczce ze śmietaną, wlej na patelnię i zagotuj. \n\nKrok 5\n\n" +
                    "Zmniejsz ogień, dodaj do sosu upieczony czosnek, kurczaka oraz posiekaną natkę i gotuj 3 minuty. \n\nKrok 6\n\n" +
                    "Makaron ugotuj we wrzącej lekko osolonej wodzie, odcedź. Następnie przełóż do sosu, dopraw pieprzem i wymieszaj. Gotowy makaron przełóż na talerze, posyp startym serem i udekoruj listkami pietruszki.",
            //2
            "Krok 1 \n\n" +
                    "Rozgrzej piekarnik do 200°C. Pokrój w kostkę mięso i marchewkę. Zetrzyj ser na grubej tarce. Przesmaż mięso na złoto w 1 łyżce oleju. Dodaj 200 ml wody i zawartość opakowania Knorr Naturalnie smaczne - Spaghetti Bolognese.\n\n" +
                    "Krok 2 \n\nWymieszaj, doprowadź do wrzenia i gotuj przez ok. 5 minut. Ochłodź. Wypełnij naleśniki farszem mięsnym i zroluj.\n\n" +
                    "Krok 3 \n\nUłóż naleśniki w dobrze natłuszczonym naczyniu do zapiekania. Rozprowadź na nich śmietanę i posyp serem. Piecz przez ok. 10 minut, do momentu gdy ser roztopi się i lekko zrumieni na złoto.",
            //3
            "Krok 1 \n\n" +
                    "Piersi z kurczaka pokrój w paski i obsmaż na patelni. Rozmrożone warzywa dodaj do mięsa i smaż przez chwilę.\n\n"+
                    "Krok 2 \n\nFix Knorr wymieszaj z 300 ml wody i wlej na patelnię. Całość gotuj do momentu, aż sos zgęstnieje.\n\n"+
                    "Krok 3 \n\nGotową potrawę podawaj z ryżem. Jej smak wzmocni łyżka sosu sojowego.\n\n",
            //4
            "Krok 1\n\n"+
                    "W dużym garnku podsmaż na oleju posiekaną cebulę i czosnek.\n\n"+
                    "Krok 2 \n\nPo chwili smażenia dodaj mielony kumin oraz koncentrat pomidorowy. Całość smaż jeszcze 3-4 minuty.\n\n"+
                    "Krok 3 \n\nFix Knorr wymieszaj z wodą. Zalej podsmażone składniki płynem i zagotuj\n\n"+
                    "Krok 4 \n\nKukurydzę i fasolkę odcedź. Ogórki i paprykę pokrój w drobną kostkę i dodaj wszystkie warzywa do zupy.\n\n",
            //5
            "Krok 1\n\n"+
                    "Obierz i pokrój dynię na kawałki. Posiekaj cebulę i czosnek. Obierz i zetrzyj na tarce imbir. Przesmaż cebulę i czosnek na oleju.\n\n"+
                    "Krok 2 \n\nDodaj dynię, imbir i cukier. Smaż przez 5 min razem. Dodaj 1 l wody i kostki bulionowe Knorr. Całość gotuj do miękkości, ok. 10 min.\n\n"+
                    "Krok 3 \n\n Zmiksuj zupę na gładko. Dodaj mleko kokosowe i jeszcze raz zagotuj. Podawaj z pestkami dyni.\n\n",
            //6
            "Krok 1 \n\nRozpuść kostkę Rosołu z kury z pietruszką i lubczykiem Knorr w 3 szklankach gorącej wody.\n\n"+
                    "Krok 2 \n\nRozmroź i obierz bób, dodaj do wywaru, po czym gotuj przez 10 minut.\n\n"+
                    "Krok 3 \n\nDodaj topiony serek oraz zasmażkę Knorr. Zagotuj zupę, a następnie dopraw do smaku solą i pieprzem (jeśli uznasz to za konieczne).\n\n"+
                    "Krok 4 \n\nKrem podawaj z posiekanym szczypiorkiem i grzankami z pszennego chleba.\n\n",
            //7
            "Krok 1 \n\nCebulę i czosnek posiekaj. Ser zetrzyj. Pomidory suszone pokrój w kostkę.\n\n"+
                    "Krok 2 \n\nNa rozgrzanej oliwie zeszklij cebulę i czosnek. Dodaj mielone mięso i koncentrat pomidorowy smaż chwilę dodaj fix wymieszany z woda i suszone pomidory. Duś kilka minutaż sos zgęstnieje.\n\n"+
                    "Krok 3 \n\nNaczynie żaroodporne wysmaruj masłem, wylej na spód 2 łyżki sosu. Na sosie ułóż pierwszą warstwę suchych płatów makaronu lasagne, rozprowadź 1/4 sosu, ułóż 2 plastry szynki.\n\n"+
                    "Krok 4 \n\n Przykryj płatami lasagne. Czynność powtórz jeszcze 2 razy.\n\n"+
                    "Krok 5 \n\n  Ostatnią warstwę makaronu posmaruj resztką sosu i posyp serem.\n\n"+
                    "Krok 6 \n\n  Zapiekaj w piekarniku nagrzanym do temperatury 200 °C przez 30 minut. Danie podawaj na gorąco.\n\n",
            //8
            "Krok 1 \n\nSkładniki na sos do pizzy połącz ze sobą tak, by nie było grudek, dodaj mini kostkę czosnek Knorr i dopraw pieprzem do smaku.\n\n"+
                    "Krok 2 \n\nSkładniki na ciasto do pizzy połącz ze sobą dobrze wyrabiając na stolnicy, odstaw na 10 minut do lodówki skropione wodą, po 10 minutach wyjmij do wyrośnięcia w ciepłym miejscu na 20 minut.\n\n"+
                    "Krok 3 \n\nWyrośnięte ciasto podziel na trzy części. Stolnicę lub blat stołu podsyp mąką, połóż ciasto i rozwałkuj lub uformuj placek ręcznie. Placek powinien mieć około 28 centymetrów średnicy i 1 centymetr grubości.\n\n"+
                    "Krok 4 \n\nPlacki umieść na blasze i posmaruj z wierzchu wcześniej przygotowanym sosem starając się zachować centymetr odstępu od rantu placka. \n\n"+
                    "Krok 5 \n\nPizzę posyp po wierzchu serem, pozostałymi składnikami i ułóż plastry oscypka. Piecz w nagrzanym do 200 stopni piekarniku około 20 minut. Podawaj pizzę skropioną oliwą.\n\n",

    };

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
        drawableP1 = context.getApplicationContext().getResources().getDrawable(R.drawable.nalesniki_kurczak);
        drawableP2 = context.getApplicationContext().getResources().getDrawable(R.drawable.chinskie);
        drawableP3 = context.getApplicationContext().getResources().getDrawable(R.drawable.meksykanska);
        drawableP4 = context.getApplicationContext().getResources().getDrawable(R.drawable.dyniowa);
        drawableP5 = context.getApplicationContext().getResources().getDrawable(R.drawable.serowa);
        drawableP6 = context.getApplicationContext().getResources().getDrawable(R.drawable.lasagne);
        drawableP7 = context.getApplicationContext().getResources().getDrawable(R.drawable.pizza);
        drawableP8 = context.getApplicationContext().getResources().getDrawable(R.drawable.tagliatelle);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_KATEGORIA);
        db.execSQL(CREATE_TABLE_LISTA);
        db.execSQL(CREATE_TABLE_PRODUKTY);
        db.execSQL(CREATE_TABLE_LISTA_PRODUKTOW);
        db.execSQL(CREATE_TABLE_PRZEPISY);
        db.execSQL(CREATE_TABLE_PRZEPISY_PRODUKTY);

        createProduktyStart(new Produkt("bebiko", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("bebilon", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("bobofrut", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("chusteczki nawilzane", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("gerber", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("herbatka", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("kaszka", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("kleik", 1.2f, 4.5f,1, 0.0f), db);
        createProduktyStart(new Produkt("krem pielegnacyjny", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("obiadek", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("oliwka", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("pampersy", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("pieluchy", 1.2f, 4.5f,1), db);
        createProduktyStart(new Produkt("szampon dla dzieci", 1.2f, 4.5f,1), db);

        createProduktyStart(new Produkt("basmati", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("błonnik", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("cukier", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("cukier brazowy", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("cukier puder", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("cukier trzcinowy", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("granola", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("kasza", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("kasza gryczana", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("kasza jaglana", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("kasza jeczmienna", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("kasza manna", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("komosa ryzowa", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("kuskus", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("maka", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("maka krupczatka", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("maka pszenna", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron lasagne", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron muszelki", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron nitki", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron ryzowy", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron spaghetti", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron swiderki", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron wieleoziarnisty", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("makaron zielony", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("maka tortowa", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("maka ziemniaczana", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("muesli", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("musli", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("otreby", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("owoce kandyzowane", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("owsianka", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("platki", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("platki kukurydziane", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("platki owsiane", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("platki ryzowe", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("ryz", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("ryz basmati", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("ryz bialy", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("ryz brazowy", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("ryz paraboiled", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("skrobia kukurydziana", 1.2f, 4.5f,2), db);
        createProduktyStart(new Produkt("slodzik", 1.2f, 4.5f,2), db);

        createProduktyStart(new Produkt("bita smietana", 1.2f, 4.5f,3), db);
        createProduktyStart(new Produkt("bita smietana w proszku", 1.2f, 4.5f,3), db);
        createProduktyStart(new Produkt("bita smietana w sprayu", 1.2f, 4.5f,3), db);
        createProduktyStart(new Produkt("budyn", 1.2f, 4.5f,3), db);
        createProduktyStart(new Produkt("kisel", 1.2f, 3.5f,3), db);
        createProduktyStart(new Produkt("lody", 1.0f, 15.00f,3), db);
        createProduktyStart(new Produkt("ciasto", 6.0f, 20.00f,3), db);
        createProduktyStart(new Produkt("drożdże", 1.5f, 3.0f,3), db);
        createProduktyStart(new Produkt("galaretke", 1.0f, 3.5f,3), db);
        createProduktyStart(new Produkt("mleko w proszku", 2.0f, 3.0f,3), db);
        createProduktyStart(new Produkt("proszek do pieczenia", 1.2f, 4.5f,3), db);
        createProduktyStart(new Produkt("sezam", 3.00f, 5.00f,3), db);
        createProduktyStart(new Produkt("soda", 1.0f, 2.00f,3), db);
        createProduktyStart(new Produkt("wiorki kokosowe", 4.5f, 8.0f,3), db);
        createProduktyStart(new Produkt("rodzynki pakowane", 4.0f, 6.0f,3), db);

        createProduktyStart(new Produkt("konserwa turystyczna", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("makrela w puszce", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("mielonka", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("paprykarz szczeciński", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("pasztet", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("ryby w puszcze", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("sardynki w puszcze", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("śledź w puszcze", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("szprotki w puszcze", 1.2f, 4.5f,4), db);
        createProduktyStart(new Produkt("szynka konserwowa", 1.2f, 4.5f,4), db);

        createProduktyStart(new Produkt("antrykot", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("baleron", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("baranina", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("kiełbasa sląska", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("biała kiełbasa", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("boczek", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("brzuszek", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("cielęcina", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("dziczyzna", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("filet z kurczaka", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("gęś", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("golonka", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("indyk", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("kabanosy", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("karczek", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("karkówka", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("schab", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("kurczak", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("mięso mielone", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("parówki", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("mortadella", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("polędwica", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("porcja rosołowa", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("salami", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("skrzydełka", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("stek", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("słoninia", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("szynka", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("tatar", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("wątróbka", 1.2f, 4.5f,5), db);
        createProduktyStart(new Produkt("żeberka", 1.2f, 4.5f,5), db);

        createProduktyStart(new Produkt("lody", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("mrożone warzywa", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("lód w kostkach", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("mrożony szpinak", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("mrożona pizza", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("mrożona zapiekanka", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("frytki", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("knedle mrożone", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("kopykta mrożone", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("krewetki", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("mieszanka chińska", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("paluszki rybne", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("pierogi mrożone", 1.2f, 4.5f,6), db);
        createProduktyStart(new Produkt("pyzy mrożone", 1.2f, 4.5f,6), db);

        createProduktyStart(new Produkt("ananas w puszcze", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("brzoskwinie w puszcze", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("buraki", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("czerwona fasola", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("dynia konserwowa", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("lody", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("dżem", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("gotowy sos w słoiku", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("groszek konserwowy", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("kapusta kwaszona", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("koncentrat pomidorowy", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("konfitura", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("korniszony", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("kukurydza konserwowa", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("marchewka z groszkiem", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("miód", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("ogórki konserwowe", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("ogórki kiszone", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("oliwki", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("owoce w syropie", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("papryka konserwowa", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("papryka chilli", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("pikle", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("pieczarki marynowane", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("pomidory suszone", 1.2f, 4.5f,7), db);
        createProduktyStart(new Produkt("pomidory w puszcze", 1.2f, 4.5f,7), db);

        createProduktyStart(new Produkt("karma dla psa", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("karma dla kota", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("karma dla rybek", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("karma dla chomika", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("karma dla królika", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("kość do czyszczenia zębów", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("obroża", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("kuweta", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("sianko", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("trociny", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("szampon dla zwierząt", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("żwirek", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("ciastka dla psa", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("szaszetka dla psa", 1.2f, 4.5f,8), db);
        createProduktyStart(new Produkt("szaszetka dla kota", 1.2f, 4.5f,8), db);

        createProduktyStart(new Produkt("gazeta", 1.2f, 4.5f,9), db);
        createProduktyStart(new Produkt("program telewizyjny", 1.2f, 4.5f,9), db);
        createProduktyStart(new Produkt("dziennik łodzki", 1.2f, 4.5f,9), db);
        createProduktyStart(new Produkt("express", 1.2f, 4.5f,9), db);
        createProduktyStart(new Produkt("krzyżówki", 1.2f, 4.5f,9), db);
        createProduktyStart(new Produkt("kolorowanki", 1.2f, 4.5f,9), db);
        createProduktyStart(new Produkt("ksiązka kucharska", 1.2f, 4.5f,9), db);
        createProduktyStart(new Produkt("komiksy", 1.2f, 4.5f,9), db);

        createProduktyStart(new Produkt("tabletki przeciwbólowe", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("maść na stłuczenia", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("bandaż", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("plastry", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("gaza", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("magnez", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("witaminy", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("prezerwatywy", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("strzykawki", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("syrop na kaszel", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("tabletki przeciwgorączkowe", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("woda utleniona", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("chusta trójkątna", 1.2f, 4.5f,10), db);
        createProduktyStart(new Produkt("tabletki na ból gardła ", 1.2f, 4.5f,10), db);

        createProduktyStart(new Produkt("antyperspirant", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("balsam", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("chusteczki", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("chusteczki nawilżane", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("żel antybakteryjny", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("dezodorant", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("farba do włosów", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("gąbka", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("pędzelki do makijażu", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("krem", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("krem do opalania", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("krem do ciała", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("pianka do golenia", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("maszynki do goloenia", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("lakier do włosów", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("woda toaletowa", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("mydło", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("żel pod prysznic", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("pasta do zębów", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("patyczki do uszu", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("płyn do higieny intymnej", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("sól do kąpieli", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("szampon do włosów", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("szczoteczka do zębów", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("waciki", 1.2f, 4.5f,11), db);
        createProduktyStart(new Produkt("lakiery do paznokci", 1.2f, 4.5f,11), db);

        createProduktyStart(new Produkt("jogurt", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("mleko", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("biały ser", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("żółty ser", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("twaróg", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("jajka", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("jogurt grecki", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("jogurt naturalny", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("masło", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("margaryna", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("kefir", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("siadłe mleko", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("maślanka", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("przekąski serowe", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("serek wiejski", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("ser feta", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("ser kanapkowy", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("mozzarella", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("mascarpone", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("ser plesniowy", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("śmietana 12%", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("śmietana 18%", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("śmietana 30%", 1.2f, 4.5f,12), db);
        createProduktyStart(new Produkt("śmietanka do kawy", 1.2f, 4.5f,12), db);

        createProduktyStart(new Produkt("zastawa", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("sztuce", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("deska do krojenia", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("noże", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("filtr do wody", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("filtr do kawy", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("folia aluminiowa", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("folia spożywcza", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("folia do pieczenia", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("foremki", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("gąbki do zmywania", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("druciak", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("mop", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("plastikowe kubki", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("plastikowe talerzyki", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("plastikowe sztuce", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("pojemnik do lodu", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("podgrzewacze", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("rękawice kuchenne", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("serwetki", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("szuszarka do naczyń", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("świeczki", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("tacki aluminiowe", 1.2f, 4.5f,13), db);
        createProduktyStart(new Produkt("worki na śmieci", 1.2f, 4.5f,13), db);

        createProduktyStart(new Produkt("długopisy", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("karteczki samoprzylepne", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("klej", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("notes", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("ołówek", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("papier do drukarki", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("papier do pakowania", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("płyty CD", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("płyty DVD", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("spinacze biurowe", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("taśma klejąca", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("teczka", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("torebka na prezent", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("zeszyt", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("spinacze", 1.2f, 4.5f,14), db);
        createProduktyStart(new Produkt("zszywki", 1.2f, 4.5f,14), db);

        createProduktyStart(new Produkt("bielizna", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("bluzka", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("bokserki", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("buty", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("czapka", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("kalosze", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("kapcie", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("klapki", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("koszula", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("krawat", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("kurtka", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("leginsy", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("pantofle", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("rajstopy", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("rękawiczki", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("skarpetki", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("spodnie", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("strój kąpielowy", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("sweter", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("szalik", 1.2f, 4.5f,15), db);
        createProduktyStart(new Produkt("t-shirt", 1.2f, 4.5f,15), db);

        createProduktyStart(new Produkt("coca cola", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("cola zero", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("energetyk", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("kubuś", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("woda niegazowana", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("woda gazowana", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("sok pomarańczowy", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("sok jabłkowy", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("sok", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("napój", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("nektar", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("napój izotoniczny", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("sprite", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("syrop malinowy", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("syrop owocowy", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("tonik", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("kubuś", 1.2f, 4.5f,16), db);
        createProduktyStart(new Produkt("sok świeżo wyciskany", 1.2f, 4.5f,16), db);

        createProduktyStart(new Produkt("ananas", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("arbuz", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("bakłażan", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("banan", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("bazylia", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("borówka", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("brokuły", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("brukselka", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("brzoskwinie", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("cebula", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("cukinia", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("cytrusy", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("czosnek", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("dynia", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("fasola", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("grejpfrut", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("groszek", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("gruszka", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("grzyby", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("jabłka", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("kapusta", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("kiwi", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("kokos", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("koper", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("kukurydza", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("maliny", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("mandarynki", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("pomarańcze", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("melon", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("truskawki", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("marchewka", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("ogórek", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("papryka", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("pieczarki", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("pomidory", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("por", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("rzodkiewka", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("sałata", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("śliwki", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("szpinak", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("winogrono", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("wiśnie", 1.2f, 4.5f,17), db);
        createProduktyStart(new Produkt("ziemniaki", 1.2f, 4.5f,17), db);

        createProduktyStart(new Produkt("ciastka owsiane", 1.2f, 4.5f,18), db);
        createProduktyStart(new Produkt("kotlety sojowe", 1.2f, 4.5f,18), db);
        createProduktyStart(new Produkt("mleko sojowe", 1.2f, 4.5f,18), db);
        createProduktyStart(new Produkt("otręby", 1.2f, 4.5f,18), db);
        createProduktyStart(new Produkt("napój ryżowy", 1.2f, 4.5f,18), db);
        createProduktyStart(new Produkt("sok z brzozy", 1.2f, 4.5f,18), db);
        createProduktyStart(new Produkt("produkty sojowe", 1.2f, 4.5f,18), db);
        createProduktyStart(new Produkt("chipsy naturalne", 1.2f, 4.5f,18), db);

        createProduktyStart(new Produkt("farba", 1.2f, 4.5f,19), db);
        createProduktyStart(new Produkt("kulki", 1.2f, 4.5f,19), db);
        createProduktyStart(new Produkt("kwiaty", 1.2f, 4.5f,19), db);
        createProduktyStart(new Produkt("nasiona", 1.2f, 4.5f,19), db);
        createProduktyStart(new Produkt("lotto", 1.2f, 4.5f,19), db);
        createProduktyStart(new Produkt("żarówka", 1.2f, 4.5f,19), db);
        createProduktyStart(new Produkt("bezpieczniki", 1.2f, 4.5f,19), db);
        createProduktyStart(new Produkt("taśma malarska", 1.2f, 4.5f,19), db);

        createProduktyStart(new Produkt("baterie", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("telewizor", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("opiekacz", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("konsola", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("mini wieża", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("głosniki", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("odtwarzacz DVD", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("kino domowe", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("soundbar", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("ładowarki do telefonu", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("powerbank", 1.2f, 4.5f,20), db);
        createProduktyStart(new Produkt("pendrive", 1.2f, 4.5f,20), db);

        createProduktyStart(new Produkt("cif", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("domestos", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("impregrant do butów", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("kostki toaletowe", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("kostki do zmywarki", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("proszek do prania", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("kret", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("srodek przeciwowadowy", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("płyn do naczyń", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("mleczko do czyszczenia", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("nabłyszczacz do mebli", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("odkamieniacz", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("odświeżacz powietrza", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("płyn do podłogi", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("proszek do dywanów", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("płyn do pukania tkanin", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("płyn do szyb", 1.2f, 4.5f,21), db);
        createProduktyStart(new Produkt("sól do zmywarki", 1.2f, 4.5f,21), db);

        createProduktyStart(new Produkt("chusteczki higieniczne", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("papier toaletowy", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("ręczniki kuchenne", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("podpaski", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("tampony", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("wkładki", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("żel antybakteryjny", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("mydło", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("chusteczki nawilżone", 1.2f, 4.5f,22), db);
        createProduktyStart(new Produkt("prezerwatywy", 1.2f, 4.5f,22), db);

        createProduktyStart(new Produkt("bagietka", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("bułka grahamka", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("bułka tarta", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("bułka ciabatta", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("chleb", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("chleb kroiony", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("chleb tostowy", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("chleb razowy", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("ciastka na wage", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("drożdżówka", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("grzanki", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("kajzerki", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("pączki", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("bagietka czosnkowa", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("pizzerki", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("suchary", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("tort", 1.2f, 4.5f,23), db);
        createProduktyStart(new Produkt("tortilla", 1.2f, 4.5f,23), db);

        createProduktyStart(new Produkt("bazylia", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("bulion", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("chilli", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("chrzan", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("curry", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("cynamon", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("czosnek granulowany", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("estragon", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("gałka muszkatałowa", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("gożdziki", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("imbir", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("ketchup", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("kminek", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("koperek", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("kostka rosołowa", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("kwasek cytrynowy", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("laska wanilii", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("liście laurowe", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("lubczyk", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("maggi", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("majeranek", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("majonez", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("mleko kokosowe", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("musztarda", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("ocet", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("oregano", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("ostra papryka", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("papryka słodka", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("pieprz mielony", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("pieprz ziarnisty", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("przyprawa do grila", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("przyprawa do miesa", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("przyprawa do ryb", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("sól", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("sos salsa", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("sos czosnkowy", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("sos do spaghetti", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("sos sałatkowy", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("ziele angielskie", 1.2f, 4.5f,24), db);
        createProduktyStart(new Produkt("zioła prawansalskie", 1.2f, 4.5f,24), db);

        createProduktyStart(new Produkt("dorsz", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("filet rybny", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("flądra", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("halibut", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("karp", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("kraby", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("krewetki", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("łosoś", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("łosoś wędzony", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("makrela", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("małże", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("mintaj", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("miruna", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("okoń", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("paluszki rybne", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("pstrąg", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("sandacz", 1.2f, 4.5f,25), db);
        createProduktyStart(new Produkt("śledź", 1.2f, 4.5f,25), db);

        createProduktyStart(new Produkt("baton", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("biszkopt", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("bombonierka", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("chałwa", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("chipsy", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("chrupki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("ciasteczka", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("ciastka", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("cukierki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("czekolada", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("delicje", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("draże", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("dropsy", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("żelki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("guma do żucia", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("herbatniki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("krakersy", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("masło orzechowe", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("nutella", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("orzeszki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("słonecznik", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("paluszki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("popcorn", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("prażynki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("precelki", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("ptasie mleczko", 1.2f, 4.5f,26), db);
        createProduktyStart(new Produkt("wafelki", 1.2f, 4.5f,26), db);

        createProduktyStart(new Produkt("margaryna", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("masło", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("osełka", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("olej kokosowy", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("olej lniany", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("olej ryżowy", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("olej rzepakowy", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("olej sezamowy", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("olej słonecznikowy", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("oliwa", 1.2f, 4.5f,27), db);
        createProduktyStart(new Produkt("smalec", 1.2f, 4.5f,27), db);

        createProduktyStart(new Produkt("czarnidło do opon", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("kosmetyki samochodowe", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("olej silnikowy", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("płyn do chłodnicy", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("płyn do felg", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("płyn do spryskiwaczy", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("spray do klimatyzacji", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("szampon do auta", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("woda destylowana", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("wosk do auta", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("apteczka", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("lina do holowania", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("bezpieczniki", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("zarówki", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("opony", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("sciereczki do czyszczenia", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("uchwyt na telefon", 1.2f, 4.5f,28), db);
        createProduktyStart(new Produkt("ładowarka samochodowa", 1.2f, 4.5f,28), db);

        long produkt = createAddedProdukt2(new Produkt("Mleko", 1.2f, 4.5f,5 ),db);
        long produkt2 = createAddedProdukt2(new Produkt("Jajka", 1.2f, 4.5f,5),db);
        long c = createAddedProduktLista(new Lista("Lista Startowa","12-04-2017","12-04-2017",2), new long[] { produkt },db);
        createListaProdoktow2(c,produkt2,5.9f, 1, "MM", db);

    //    long c2 = createProduktyFromPrzepisy(initStartPrzepisy2(drawableP1,"KRR",opis[0],db),new long[] { produkt }, db);
     //   createListaProdoktowFromPrzepis(c2, produkt2, db);
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
        initStartKategorie(drawable29,"motoryzacja",db);


        initStartPrzepisy(drawableP1,"Naleśniki", opis[0], db);
        initStartPrzepisy(drawableP2,"Kurczak po chińsku", opis[1], db);
        initStartPrzepisy(drawableP3,"Zupa meksykańska", opis[2], db);
        initStartPrzepisy(drawableP4,"Jesienna zupa", opis[3], db);
        initStartPrzepisy(drawableP5,"Krem serowy", opis[4], db);
        initStartPrzepisy(drawableP6,"Lasagne", opis[5], db);
        initStartPrzepisy(drawableP7,"Pizza góralska", opis[6], db);
        initStartPrzepisy(drawableP8,"Tagliatelle", opis[7], db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUKTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTA_PRODUKTOW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRZEPISY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRZEPISY_PRODUKTY);
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

    private void initStartPrzepisy(Drawable drawable, String name, String opis, SQLiteDatabase db) {
        Drawable d = drawable;
        foto = imageViewToByte(d);
        createPrzepisyStart(new Przepis(name, opis, foto),db);
    }
    private Przepis initStartPrzepisy2(Drawable drawable, String name, String opis, SQLiteDatabase db) {
        Drawable d = drawable;
        foto = imageViewToByte(d);
        createPrzepisyStart(new Przepis(name, opis, foto),db);
        return new Przepis(name,opis,foto);
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
        values.put(KEY_ILOSC_PRODUKTOW, lista.getIlosc());

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
        values.put(KEY_ILOSC_PRODUKTOW, lista.getIlosc());


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
        values.put(KEY_ILOSC_PRODUKTOW, lista.getIlosc());


        // insert row
        db.insert(TABLE_LISTA, null, values);

    }
   public void createListaStart(Lista lista) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LISTA, lista.getName());
        values.put(KEY_CREATED_AT, lista.getData());
        values.put(KEY_PRZYPOMNIENIE, lista.getData2());
        values.put(KEY_ILOSC_PRODUKTOW, lista.getIlosc());


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
     //   values.put(KEY_ILOSC, produkt.getIlosc());
     //   values.put(KEY_JEDNOSTKA, produkt.getJednostka());

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
     //   values.put(KEY_ILOSC, produkt.getIlosc());
        values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());
     //   values.put(KEY_JEDNOSTKA, produkt.getJednostka());

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
        values.put(KEY_ILOSC_PRODUKTOW, lista.getIlosc());

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

    /*
    public long createListaProdoktow(long produktID, long addedProduktID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Produkt produkt = new Produkt();
        produkt = getProduktByID(produktID);
        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_LISTA, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
        values.put(KEY_ILOSC, .getIlosc());

        // insert row
        long added_produkt_id = db.insert(TABLE_LISTA_PRODUKTOW, null, values);


        db.close();
        return added_produkt_id;
    }
*/
    public long createListaProdoktow(long produktID, long addedProduktID) {
        SQLiteDatabase db = this.getWritableDatabase();
      //  Produkt produkt = new Produkt();
//        produkt = getProduktByID(produktID);
        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_LISTA, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
      //  values.put(KEY_ILOSC, produkt.getIlosc());

        // insert row
        long added_produkt_id = db.insert(TABLE_LISTA_PRODUKTOW, null, values);


        db.close();
        return added_produkt_id;
    }

    public void createListaProdoktow2(long produktID, long addedProduktID, float ilosc, int selected, String jednostka, SQLiteDatabase db) {
        //    db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_LISTA, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
        values.put(KEY_ILOSC, ilosc);
        values.put(KEY_SELECTED, selected);
        values.put(KEY_JEDNOSTKA, jednostka);

        // insert row
        db.insert(TABLE_LISTA_PRODUKTOW, null, values);

    }

    public long createListaProdoktow2(long produktID, long addedProduktID, float ilosc, String jednostka, int selected) {
        SQLiteDatabase db = this.getWritableDatabase();
        //  Produkt produkt = new Produkt();
//        produkt = getProduktByID(produktID);
        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_LISTA, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
        values.put(KEY_ILOSC, ilosc);
        values.put(KEY_SELECTED, selected);
        values.put(KEY_JEDNOSTKA, jednostka);

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
   //     values.put(KEY_ILOSC, produkt.getIlosc());
    //    values.put(KEY_JEDNOSTKA, produkt.getJednostka());

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
   //     values.put(KEY_ILOSC, produkt.getIlosc());
    //    values.put(KEY_JEDNOSTKA, produkt.getJednostka());

        // insert row
        db.insert(TABLE_PRODUKTY, null, values);

        db.close();

    }

    private void createPrzepisyStart(Przepis przepis, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_PRZEPISY, przepis.getName());
        values.put(KEY_OPIS, przepis.getOpis());
        values.put(KEY_IMAGE, przepis.getImage());
        // insert row
        db.insert(TABLE_PRZEPISY, null, values);

    }
    public void createPrzepisyStart(Przepis przepis) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRZEPISY, przepis.getName());
        values.put(KEY_OPIS, przepis.getOpis());
        values.put(KEY_IMAGE, przepis.getImage());
        // insert row
        db.insert(TABLE_PRZEPISY, null, values);

        db.close();

    }

    public long createProduktyFromPrzepisy(Przepis przepis, long[] addedProdukts, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_PRZEPISY, przepis.getName());
        values.put(KEY_OPIS, przepis.getOpis());
        values.put(KEY_IMAGE, przepis.getImage());

        // insert row
        long przepis_id = db.insert(TABLE_PRZEPISY, null, values);

        for (long addedProdukt : addedProdukts) {
            createListaProdoktowFromPrzepis(przepis_id, addedProdukt,db);
        }
        //      db.close();
        return przepis_id;
    }
    public void createListaProdoktowFromPrzepis(long produktID, long addedProduktID, SQLiteDatabase db) {
        //    db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_PRZEPISU, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
        //  values.put(KEY_ILOSC, addedProdukt.getIlosc());

        // insert row
        db.insert(TABLE_PRZEPISY_PRODUKTY, null, values);

        //  for (long produktID : produktIDs) {
        //
        //   }
        //   db.close();
        // return added_produkt_id;
    }

    public long createListaProdoktowFromPrzepis(long produktID, long addedProduktID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //   values.put(KEY_ID_LISTA, addedProdukt.getId_listy());

        values.put(KEY_ID_PRZEPISU, produktID);
        values.put(KEY_ID_PRODUKT, addedProduktID);
        //  values.put(KEY_ILOSC, addedProdukt.getIlosc());

        // insert row
        long added_produkt_id = db.insert(TABLE_PRZEPISY_PRODUKTY, null, values);


        db.close();
        return added_produkt_id;
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
        lst.setIlosc(c.getInt(c.getColumnIndex(KEY_ILOSC_PRODUKTOW)));

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
    //    pdt.setIlosc(c.getFloat(c.getColumnIndex(KEY_ILOSC)));
    //    pdt.setJednostka(c.getString(c.getColumnIndex(KEY_JEDNOSTKA)));

        c.close();
        return pdt;
    }


    public Float getIloscByProduktAndList(int id_listy, int id_produktu) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_PRODUKT + " = " + id_produktu + " AND " + KEY_ID_LISTA + " = " + id_listy;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();
   //     Produkt pdt = new Produkt();

        //  pdt.setIlosc(c.getFloat(c.getColumnIndex(KEY_ILOSC)));
        Float f = c.getFloat(c.getColumnIndex(KEY_ILOSC));
        c.close();
        return f;

    }

    public String getJednostkaByProduktAndList(int id_listy, int id_produktu) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_PRODUKT + " = " + id_produktu + " AND " + KEY_ID_LISTA + " = " + id_listy;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        String s = c.getString(c.getColumnIndex(KEY_JEDNOSTKA));
        c.close();
        return s;
    }

    public int getCounterByList(int id_listy) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LISTA + " WHERE " + KEY_ID + " = " + id_listy;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        int i = c.getInt(c.getColumnIndex(KEY_ILOSC_PRODUKTOW));
        c.close();
        return i;
    }
    public int getSelectedByProduktAndList(int id_listy, int id_produktu) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_PRODUKT + " = " + id_produktu + " AND " + KEY_ID_LISTA + " = " + id_listy;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToLast();

        int i = c.getInt(c.getColumnIndex(KEY_SELECTED));
        c.close();
        return i;
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
    //    pdt.setIlosc(c.getFloat(c.getColumnIndex(KEY_ILOSC)));
    //    pdt.setJednostka(c.getString(c.getColumnIndex(KEY_JEDNOSTKA)));

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
                  //      pdt.setIlosc(c.getFloat(c.getColumnIndex(KEY_ILOSC)));
                  //      pdt.setJednostka(c.getString(c.getColumnIndex(KEY_JEDNOSTKA)));

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

    public List<Produkt> getAddedSelectedAndNotSecectedProductyID(String id_listy) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_LISTA + " = " + id_listy +
                " AND " + KEY_SELECTED + " = " + 0;
        String selectQuery3 = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_LISTA + " = " + id_listy +
                " AND " + KEY_SELECTED + " = " + 1;
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
                        //      pdt.setIlosc(c.getFloat(c.getColumnIndex(KEY_ILOSC)));
                        //      pdt.setJednostka(c.getString(c.getColumnIndex(KEY_JEDNOSTKA)));

                        // adding category list
                        lstProdukt.add(pdt);
                    } while (c.moveToNext());
                }
                c.close();
            } while (cursor.moveToNext());
        }
        cursor.close();
        Cursor cursor2 = db.rawQuery(selectQuery3, null);
        if (cursor2.moveToFirst()) {
            do {
                int id = cursor2.getInt(cursor2.getColumnIndex(KEY_ID_PRODUKT));
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

                        lstProdukt.add(pdt);
                    } while (c.moveToNext());
                }
                c.close();
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        return lstProdukt;
    }

    public String getIDProdukt2(String name) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PRODUKTY +
                " WHERE " + KEY_PRODUKT + " = '" + name + "'";
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

    public boolean isProdukt(String name) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean b = true;
        String selectQuery = "SELECT * FROM " + TABLE_PRODUKTY +
                " WHERE " + KEY_PRODUKT + " = '" + name + "'";
        //   Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.getCount() == 0) {
            b = false;
        }

        c.close();

        return b;
    }

    /*
    public boolean isProduktInAddedProduktsList(String name) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean b = true;
        String selectQuery = "SELECT * FROM " + TABLE_PRODUKTY + " WHERE " + KEY_PRODUKT + " = '" + name + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        cursor.close();
        String selectQuery2 = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_PRODUKT + " = " + id ;
        Cursor c = db.rawQuery(selectQuery2, null);
        if (c.getCount() == 0) {
            b = false;
        }
       // int id2 = cursor.getInt(c.getColumnIndex(KEY_ID));
        c.close();
        return b;
    }

    public int getIdProduktInAddedProduktsList(String name) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUKTY + " WHERE " + KEY_PRODUKT + " = '" + name + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        int id_produktu = 0;
        cursor.close();
        String selectQuery2 = "SELECT * FROM " + TABLE_LISTA_PRODUKTOW + " WHERE " + KEY_ID_PRODUKT + " = " + id ;
        Cursor c = db.rawQuery(selectQuery2, null);
        if (c.getCount() != 0) {
           id_produktu = c.getInt(c.getColumnIndex(KEY_ID_PRODUKT));
        }
        // int id2 = cursor.getInt(c.getColumnIndex(KEY_ID));
        c.close();
        return id_produktu;
    }
*/
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
                lst.setIlosc(c.getInt(c.getColumnIndex(KEY_ILOSC_PRODUKTOW)));


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
            //    pdt.setIlosc(c.getFloat(c.getColumnIndex(KEY_ILOSC)));
            //    pdt.setJednostka(c.getString(c.getColumnIndex(KEY_JEDNOSTKA)));

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
           //     pdt.setIlosc(c.getFloat(c.getColumnIndex(KEY_ILOSC)));
           //     pdt.setJednostka(c.getString(c.getColumnIndex(KEY_JEDNOSTKA)));

                // adding category list
                lstProdukt.add(pdt);
            } while (c.moveToNext());
        }

        c.close();
        return lstProdukt;

    }

    public List<Przepis> getAllPrzepisy() {
        List<Przepis> lstPrzepisy = new ArrayList<Przepis>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRZEPISY;

        //  Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Przepis przepis = new Przepis();
                przepis.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                przepis.setName((c.getString(c.getColumnIndex(KEY_PRZEPISY))));
                przepis.setOpis(c.getString(c.getColumnIndex(KEY_OPIS)));
                przepis.setImage(c.getBlob(c.getColumnIndex(KEY_IMAGE)));

                // adding category list
                lstPrzepisy.add(przepis);
            } while (c.moveToNext());
        }

        c.close();
        return lstPrzepisy;
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
        values.put(KEY_ILOSC_PRODUKTOW, lista.getIlosc());

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
        values.put(KEY_ID_KATEGORIA, produkt.getId_kategoria());
    //    values.put(KEY_ILOSC, produkt.getIlosc());
    //    values.put(KEY_JEDNOSTKA, produkt.getJednostka());
        return db.update(TABLE_PRODUKTY, values, KEY_PRODUKT + " = ?",
                new String[] { String.valueOf(name) });
    }

    public int updateIlosc(float ilosc, int id_produktu, int id_listy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ILOSC, ilosc);

        return db.update(TABLE_LISTA_PRODUKTOW, values, KEY_ID_PRODUKT + " = ?" + " AND " + KEY_ID_LISTA + " = ?",
                new String[] { String.valueOf(id_produktu), String.valueOf(id_listy) });
    }


    public int updateJednostka(String jednostka, int id_produktu, int id_listy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_JEDNOSTKA, jednostka);

        return db.update(TABLE_LISTA_PRODUKTOW, values, KEY_ID_PRODUKT + " = ?" + " AND " + KEY_ID_LISTA + " = ?",
                new String[] { String.valueOf(id_produktu), String.valueOf(id_listy) });
    }

    public int updateSelected(int selected, int id_produktu, int id_listy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SELECTED, selected);

        return db.update(TABLE_LISTA_PRODUKTOW, values, KEY_ID_PRODUKT + " = ?" + " AND " + KEY_ID_LISTA + " = ?",
                new String[] { String.valueOf(id_produktu), String.valueOf(id_listy) });
    }

    public int updateIloscProduktow(int ilosc, int id_listy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ILOSC_PRODUKTOW, ilosc);

        return db.update(TABLE_LISTA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id_listy) });
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




