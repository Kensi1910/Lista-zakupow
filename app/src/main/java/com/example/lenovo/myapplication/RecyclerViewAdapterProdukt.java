package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    public void onBindViewHolder(final MyRecycleView holder, final int position) {

        final int pos = position;
        final String name_listy = MainActivity.getListName();
      //  final int id_listy = MainActivity.getListId();
        final String id_listy = MainActivity.getListId();
        holder.tvNazwa.setText(mData.get(position).getName());
        holder.chkSelected.setChecked(mData.get(position).isSelected());
        holder.chkSelected.setTag(mData.get(position));

        holder.chkSelected.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                CheckBox cb = (CheckBox) v;
                Produkt contact = (Produkt) cb.getTag();

                contact.setSelected((cb.isChecked()));
                mData.get(pos).setSelected(cb.isChecked());

                int id_produktu = mData.get(pos).getId();
                int id_listyy = Integer.parseInt(id_listy);

                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " produkt: " + id_produktu + "Nazwa listy: " + name_listy + "id listy: " + id_listyy, Toast.LENGTH_LONG).show();

                baza.createListaProdoktow(id_listyy,id_produktu);
                AddProductActivity.produktyList2.add(baza.getProduktByID(id_produktu));
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

    private void updateProduktName(String name, String oldName, int position) {
        Produkt p = mData.get(position);
        p.setName(name);
        baza.updateProdukt(p, oldName);
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
    public static class MyRecycleView extends RecyclerView.ViewHolder {

        private TextView tvNazwa;
        private CheckBox chkSelected;
        private RelativeLayout rl_item_produkt;

        public MyRecycleView(View itemView) {
            super(itemView);

            tvNazwa = (TextView) itemView.findViewById(R.id.tvNazwa);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
            rl_item_produkt = (RelativeLayout) itemView.findViewById(R.id.rl_item_produkt);
        }

    }
    public List<Produkt> getproduktis() {
        return mData;
    }
}
