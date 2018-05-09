package com.example.lenovo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daymos on 2018-05-09.
 */

public class PrzepisyAdapter extends BaseAdapter {

    //  private List<Item> mItems = new ArrayList<Item>();
    private LayoutInflater inflater;
    private Context context;
    private List<Przepis> mData;


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

    public static final String[] skladniki =

            {
                    //skladniki 1 przepis
                    "makaron tagliatelle 300 g \n\n" +
                            "Fix Naturalnie smaczne Tagliatelle z kurczakiem Knorr 1 opak.\n\n" +
                            "pierś z kurczaka 1 szt\n\n" +
                            "Przyprawa do złotego kurczaka Knorr 1 łyżka\n\n" +
                            "czosnek 1 szt. \n\n" +
                            "cebula 1 szt.\n\n" +
                            "natka pietruszki 1 pęczek\n\n"+
                            "wino białe wytrawne 100 ml\n\n"+
                            "Śmietana 30% 200 ml\n\n"+
                            "parmezan lub inny twardy ser 20 g \n\n"+
                            "pieprz czarny świeżo mielony 1 szczypta \n\n"+
                            "oliwa z oliwek 4 łyżki \n\n",
                    //skladniki 2 przepis
                    "gotowe naleśniki 5 szt.\n\n" +
                            "Fix Naturalnie smaczne Spaghetti Bolognese Knorr 1 opak.\n\n" +
                            "filet z piersi kurczaka 200 g\n\n" +
                            "marchewka 1 szt.\n\n" +
                            "ser żółty ementaler 100 g\n\n" +
                            "kwaśna śmietana 18% 100 ml",
                    //skladniki 3 przepis
                    "pierś z kurczaka 400 g\n\n "+
                            "Fix Do potraw chińskich Knorr 1 opak. \n\n"+
                            "chińska mieszanka warzyw na patelnię 1 opak. \n\n"+
                            "olej 3 łyżki \n\n"+
                            "woda 300 ml",
                    //skladniki 4 przepis
                    " fasola czerwona z puszki 1 opak.\n\n"+
                            "ogórki konserwowe 5 szt.\n\n"+
                            "papryka konserwowa czerwona 4 szt.\n\n"+
                            "kukurydza z puszki 1 opak.\n\n"+
                            "Fix Chili con carne Knorr 1 opak. \n\n"+
                            "cebula czerwona 1 szt.\n\n"+
                            "przecier pomidorowy 2 łyżki\n\n"+
                            "kumin mielony 1 łyżeczka\n\n"+
                            "ząbki czosnku 2 szt.\n\n"+
                            "kolendra1 pęczek \n\n"+
                            "woda 1,5l",
                    //skladniki 5 przepis
                    "dynia np. Hokkaido 1 kg\n\n"+
                            "Rosół z kury Knorr 1 szt.\n\n"+
                            "mleko kokosowe 200 ml \n\n"+
                            "cebula 1 szt.\n\n"+
                            "ząbek czosnku 1 szt.\n\n"+
                            "kawałek świeżego imbiru, ok. 5 cm \n\n"+
                            "brązowy cukier 1 łyżka\n\n"+
                            "pestki z dyni 50 g\n\n"+
                            "olej rzepakowy 1 łyżka\n\n",
                    //skladniki 6
                    "Rosół z kury z pietruszką i lubczykiem Knorr 1 szt.\n\n"+
                            "Zasmażka błyskawiczna jasna Knorr 1 opak.\n\n"+
                            "mrożony bób 100 g\n\n"+
                            "topiony serek śmietankowy 1 szt.\n\n"+
                            "gorąca woda 3 szklanki\n\n"+
                            "sól i pieprz do smaku\n\n"+
                            "posiekany szczypiorek 2 łyżki\n\n"+
                            "grzanki chleba",
                    //skladniki 7
                    "mielone mięso drobiowe 600 g\n\n"+
                            "wędzona szynka drobiowa 200 g\n\n"+
                            "Fix Lasagne Knorr 1 opak.\n\n"+
                            "makaron lasagne - płaty \n\n"+
                            "żółty ser 200 g\n\n"+
                            "pomidory suszone 200 g\n\n"+
                            "czosnek w ząbkach 2 szt.\n\n"+
                            "cebula 1 szt.\n\n"+
                            "sól i pieprz do smaku\n\n"+
                            "masło 1 łyżka\n\n"+
                            "oliwa do smażenia\n\n"+
                            "woda 300 ml",
                    //skladnik 8
                    "starty ser mozzarella 150 g\n\n"+
                            "papryczki jalapeno 50 g\n\n"+
                            "fasola z puszki 1 opak.\n\n"+
                            "boczek 6 plastrów \n\n"+
                            "mąka do podsypania ciasta 50 g\n\n"+
                            "Przyprawa w Mini kostkach Czosnek Knorr 1 szt.\n\n"+
                            "oliwa z oliwek 2 łyżki\n\n"+
                            "oscypek 1 szt.\n\n\n"+
                            "\t\t sos do pizzy\n\n"+
                            "koncentrat pomidorowy 100 g\n\n"+
                            "cukier 1 łyżeczka\n\n"+
                            "sól 1 łyżeczka\n\n"+
                            "Oregano z Turcji Knorr 2 łyżeczki\n\n"+
                            "woda \n\n\n"+
                            " \t\t ciasto do pizzy \n\n"+
                            "mąka 1 kg\n\n"+
                            "woda 500 ml \n\n"+
                            "cukier 25 g\n\n"+
                            "sól 20 g \n\n"+
                            "olej 50 ml \n\n"+
                            "drożdże 8 g\n\n",


            };



    // Keep all Images in array
    public Integer[] mThumbIds = {


            R.drawable.cup, R.drawable.chinskie, R.drawable.meksykanska, R.drawable.dyniowa,
            R.drawable.serowa, R.drawable.lasagne, R.drawable.pizza, R.drawable.tagliatelle,
    };


    public PrzepisyAdapter(Context context, List<Przepis> mData){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = mData;


        mData.add(new Przepis("Naleśniki z kurczakiem w sosie bolognese", R.drawable.nalesniki_kurczak ) );
        mData.add(new Przepis("Kurczak po chińsku z mrożonymi warzywami", R.drawable.chinskie ) );
        mData.add(new Przepis("Zupa meksykańska z ogórkiem i papryką", R.drawable.meksykanska ) );
        mData.add(new Przepis("Jesienna zupa krem z dyni", R.drawable.dyniowa ) );
        mData.add(new Przepis("Krem serowy z ziarnami bobu", R.drawable.serowa ) );
        mData.add(new Przepis("Lasagne z mięsa mielonego drobiowego", R.drawable.lasagne ) );
        mData.add(new Przepis("Pizza góralska", R.drawable.pizza ) );
        mData.add(new Przepis("Tagliatelle z sosem czosnkowym i grillowanym kurczakiem", R.drawable.tagliatelle ) );



    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Przepis getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return mData.get(i).getDrawableId();
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        ImageView picture;
        TextView name;
        if (v==null){

         //   v= inflater.inflate(R.layout.item_przepis, viewGroup, false);
         //   v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_przepis, viewGroup, false);
            v = inflater.inflate(R.layout.item_przepis, viewGroup, false);

               v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text,v.findViewById(R.id.text));

        }

        picture = (ImageView) v.findViewById(R.id.picture);
        name = (TextView) v.findViewById(R.id.text);
        Przepis item = getItem(i);
        picture.setImageResource(item.getDrawableId());
        name.setText(item.getName());
        return v;
    }

    /*
    private static class Item {
        public final String name;
        public final  int  drawableId;
        Item(String name, int drawableId){
            this.name = name;
            this.drawableId = drawableId;
        }
    }
*/
}
