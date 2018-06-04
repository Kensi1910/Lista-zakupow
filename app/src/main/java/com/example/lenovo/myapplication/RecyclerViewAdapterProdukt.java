package com.example.lenovo.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.myapplication.MainActivity.baza;


public class RecyclerViewAdapterProdukt extends RecyclerView.Adapter<RecyclerViewAdapterProdukt.MyRecycleView> {

    private Context mContext;
    private List<Produkt> mData;
    private List<Lista> mLista;
    private List<AddedProdukt> mAddedProdukt;
    AddedProdukt addedProdukt;
    public List<Produkt> lstProdukt = new ArrayList<>();
    Lista lista;
    private EditText nameEditText;
    private int licznik = 0;

    public RecyclerViewAdapterProdukt(Context mContext, List<Produkt> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyRecycleView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_produkt, parent, false);
        return new MyRecycleView(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyRecycleView holder, final int position) {

        final int pos = position;
        final String name_listy = MainActivity.getListName();
      //  final int id_listy = MainActivity.getListId();
        holder.tvNazwa.setText(mData.get(position).getName());
        holder.chkSelected.setChecked(mData.get(position).isSelected());
        holder.chkSelected.setTag(mData.get(position));

        holder.chkSelected.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
             //   CheckBox cb = (CheckBox) v;
                CheckBox cb = (CheckBox) v;
                Produkt contact = (Produkt) cb.getTag();

                contact.setSelected((cb.isChecked()));
                mData.get(pos).setSelected(cb.isChecked());

                if (cb.isChecked())
                   ProductListActivity.counterItem++;
                else
                    ProductListActivity.counterItem--;

                Float f = (float) ProductListActivity.counterItem;
                mData.get(pos).setIlosc(1.0f);


                final String id_listy = MainActivity.getListId();
                int id_listyy = Integer.parseInt(id_listy);

                int id_produktu = mData.get(pos).getId();



                //    Toast.makeText(
                  //      v.getContext(),
                  //      "Clicked on Checkbox: " + cb.getText() + " produkt: " + id_produktu + "Nazwa listy: " + name_listy + "id listy: " + id_listyy, Toast.LENGTH_LONG).show();
             //   if (ProductListActivity.counterItem < 2) {
            //    AddProduktTabLayout.arraylist2.get(po)
                List<Produkt> arrayList = baza.getAddedProductyID(id_listy);
                if (arrayList.size() > 0) {
                    if (cb.isChecked()) {

                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getId() != id_produktu ) {
                                baza.updateProdukt(mData.get(pos), mData.get(pos).getName());
                             //   mData.get(pos).setIlosc(1.0f);
                       //         mAddedProdukt.get(pos).setIlosc();
                          //      mData.get(pos).setJednostka(" ");
                             //   baza.createListaProdoktow(id_listyy,id_produktu);
                                baza.createListaProdoktow2(id_listyy,id_produktu, 1.0f," ", 0);
                                AddProduktTabLayout.produktyList2.add(baza.getProduktByID(id_produktu));
                                AddProduktTabLayout.arraylist2.add(baza.getProduktByID(id_produktu));
                                break;
                            }
                            else {
                                break;
                            }

                        }
                    }
                }
                else  {
                    if (cb.isChecked()) {
                        baza.createListaProdoktow2(id_listyy, id_produktu,1.0f," ", 0);
                        AddProduktTabLayout.produktyList2.add(baza.getProduktByID(id_produktu));
                        AddProduktTabLayout.arraylist2.add(baza.getProduktByID(id_produktu));
                    }
                }


                ProductListActivity.counterItem = 0;
            }
        });

        holder.rl_item_produkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   showSetDetailsProduktDialog(position);
            }
        });

        holder.rl_item_produkt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showActionsDialog(position);
                return false;
            }
        });

        /*
        holder.rl_item_produkt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.rl_item_produkt);
                popupMenu.inflate(R.menu.menu_settings_list);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String option = menuItem.getTitle().toString();
                        Toast.makeText(mContext, mData.get(position).getName(), Toast.LENGTH_SHORT).show();
                        switch (menuItem.getItemId()) {
                            case R.id.action_change_name_list:
                                final String oldName = mData.get(position).getName();
                                //    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                LayoutInflater inflater = LayoutInflater.from(mContext);
                                View view = inflater.inflate(R.layout.dialog_change_list_name, null);
                                nameEditText = (EditText) view.findViewById(R.id.change_name_list_edit_text);
                                builder.setView(view)
                                        .setTitle("Zmiena nazwy")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String name = nameEditText.getText().toString();
                                                // changeNameItem(name,position);
                                                updateProduktName(name,oldName,position);

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();

                                break;

                            case R.id.action_delete_list:
                                Toast.makeText(mContext, "Usunieto produkt " + mData.get(position).getName(), Toast.LENGTH_SHORT).show();
                                //removeItem(position);
                                deleteProdukt(position);
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
        */

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setCounter(int count) {
        this.licznik = count;
    }
    private void updateProduktName(String name, String oldName, int position) {
        Produkt p = mData.get(position);
        p.setName(name);
        baza.updateProdukt(p, oldName);
        mData.set(position,p);
        notifyItemChanged(position);
    }
    private void updateIlosc(String ilosc, String n, int position) {
        Produkt p = mData.get(position);
        Float f= Float.parseFloat(ilosc);
        p.setIlosc(f);
        baza.updateProdukt(p, n);
        mData.set(position,p);
        notifyItemChanged(position);
    }
    private void updateJednostka(String jednostka, String n, int position) {
        Produkt p = mData.get(position);
        String s = jednostka;
        p.setJednostka(s);
        baza.updateProdukt(p, n);
        mData.set(position,p);
        notifyItemChanged(position);
    }
    private void updateCena(String cena, String n, int position) {
        Produkt p = mData.get(position);
        Float f= Float.parseFloat(cena);
        p.setCena_max(f);
        baza.updateProdukt(p, n);
        mData.set(position,p);
        notifyItemChanged(position);
    }
    private void deleteProdukt(int position) {
        baza.deleteProdukt(mData.get(position));
        mData.remove(position);
        // notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Zmien nazwe", "Usun"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Wybierz opcje");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showChangeNameDialog(position);
                }
                else{
                    deleteProdukt(position);
                }

            }
        });
        builder.show();
    }

    private void showChangeNameDialog(final int position) {
        final String oldName = mData.get(position).getName();
        //    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_change_produkt_name, null);
        nameEditText = (EditText) view.findViewById(R.id.change_name_produkt_edit_text);
        builder.setView(view)
                .setTitle("Zmiena nazwy")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        // changeNameItem(name,position);
                        updateProduktName(name,oldName,position);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

 //   private EditText nameEditText;
    private EditText iloscEditText;
    private EditText jednostkaEditText;
    private EditText cenaEditText;

    private void showSetDetailsProduktDialog(final int position) {
            final String oldName = mData.get(position).getName();
            final String oldIlosc = mData.get(position).getIlosc().toString();
            final String oldJednostka = mData.get(position).getJednostka();
            final String oldCena = mData.get(position).getCena_max().toString();

            //    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.dialog_set_details_added_produkt, null);
     //       nameEditText = (EditText) view.findViewById(R.id.change_name_produkt_edit_text2);
            iloscEditText = (EditText) view.findViewById(R.id.et_edit_ilosc);
            jednostkaEditText = (EditText) view.findViewById(R.id.et_edit_jednostka);
            cenaEditText = (EditText) view.findViewById(R.id.et_edit_cena);

     //       nameEditText.setText(oldName);
            iloscEditText.setText(oldIlosc);
            jednostkaEditText.setText(oldJednostka);
            cenaEditText.setText(oldCena);

            builder.setView(view)
                    .setTitle("Edycja")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                      //      String name = nameEditText.getText().toString();
                            String ilosc = iloscEditText.getText().toString();
                            String jednostka = jednostkaEditText.getText().toString();
                            String cena = cenaEditText.getText().toString();

                      //      updateProduktName(name,oldName,position);
                         //   updateIlosc(ilosc,oldName, position);
                         //   updateCena(cena, oldName, position);
                         //   updateJednostka(jednostka, oldName, position);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
    }
    public void filterList(List<Produkt> listProdukt) {
        this.mData = listProdukt;
        notifyDataSetChanged();
    }
    public static class MyRecycleView extends RecyclerView.ViewHolder {

        private TextView tvNazwa;
        private CheckBox     chkSelected;
        private RelativeLayout rl_item_produkt;

        public MyRecycleView(View itemView) {
            super(itemView);

            tvNazwa = (TextView) itemView.findViewById(R.id.tvNazwa);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            rl_item_produkt = (RelativeLayout) itemView.findViewById(R.id.rl_item_produkt);
        }

    }


}
