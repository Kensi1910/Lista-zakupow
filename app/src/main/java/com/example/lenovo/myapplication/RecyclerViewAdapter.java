package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.lenovo.myapplication.MainActivity.baza;

/**
 * Created by kensi on 18/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Lista> mData;
    private Context context;
    private EditText nameEditText;
    String name;
    private ItemClickListener clickListener;


    public RecyclerViewAdapter(List<Lista> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_data.setText(mData.get(position).getData());
    //    if (MainActivity.itemCounter[position] < 0) {
       //     MainActivity.itemCounter[position] = 0;
     //   }
        int il = mData.get(position).getIlosc();
        if (il == 1) {
            holder.tv_count_produkt.setText(String.valueOf(mData.get(position).getIlosc()) + " produkt");
        }
        else if (il == 2 || il == 3 || il == 4) {
            holder.tv_count_produkt.setText(String.valueOf(mData.get(position).getIlosc()) + " produkty");
        }
        else {
            holder.tv_count_produkt.setText(String.valueOf(mData.get(position).getIlosc()) + " produktow");
        }
//        holder.tv_count_produkt.setText(baza.getCounterByList(mData.get(position).getId()));
        holder.settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.settings_icon);
                popupMenu.inflate(R.menu.menu_settings_list);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String option = menuItem.getTitle().toString();
                        Toast.makeText(context, mData.get(position).getName(), Toast.LENGTH_SHORT).show();
                        switch (menuItem.getItemId()) {
                            case R.id.action_change_name_list:
                                final String oldName = mData.get(position).getName();
                            //    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                LayoutInflater inflater = LayoutInflater.from(context);
                                View view = inflater.inflate(R.layout.dialog_change_list_name, null);
                                nameEditText = (EditText) view.findViewById(R.id.change_name_list_edit_text);
                                builder.setView(view)
                                        .setTitle("Zmiena nazwy")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String name = nameEditText.getText().toString();
                                                updateListaName(name,oldName,position);
                                                //  changeNameItem(name,position);

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();

                                break;

                            case R.id.action_delete_list:
                                Toast.makeText(context, "Usunieto liste " + mData.get(position).getName(), Toast.LENGTH_SHORT).show();
                                //removeItem(position);
                                deleteLista(position);
                                break;

                            case R.id.action_powiadomienia_list:
                                Toast.makeText(context, "Powiadomienie listy:  " + mData.get(position).getName(), Toast.LENGTH_SHORT).show();

                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    private void updateListaName(String name, String oldName, int position) {
        Lista l = mData.get(position);
        l.setName(name);
        baza.updateLista(l, oldName);
        mData.set(position,l);
        notifyItemChanged(position);
    }
    private void deleteLista(int position) {
        baza.deleteLista(mData.get(position));
        mData.remove(position);
       // notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private LinearLayout item_contact;
        private TextView tv_name;
        private TextView tv_data;
        private TextView tv_count_produkt;
        private ImageView settings_icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_contact = (LinearLayout) itemView.findViewById(R.id.new_list_item);
            tv_name = (TextView) itemView.findViewById(R.id.name_list);
            tv_data = (TextView) itemView.findViewById(R.id.create_list_date);
            settings_icon = (ImageView) itemView.findViewById(R.id.settings_list_icon);
            tv_count_produkt = (TextView) itemView.findViewById(R.id.cout_produkt);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }
}

