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
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by kensi on 22/03/2018.
 */

public class RecyclerViewAdapterCategory extends RecyclerView.Adapter<RecyclerViewAdapterCategory.MyRecycleView> {

    private Context mContext;
    private List<Category> mData;
    private EditText nameEditText;
    public String nazwa = "";

    public RecyclerViewAdapterCategory(Context mContext, List<Category> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyRecycleView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_category, parent, false);
        return new MyRecycleView(view);
    }

    @Override
    public void onBindViewHolder(final MyRecycleView holder, final int position) {

        holder.tv_category_name.setText(mData.get(position).getName());
        holder.iv_image_category.setImageResource(mData.get(position).getImage());

        holder.cardView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nazwa = mData.get(position).getName();

                Intent intent = new Intent(mContext, AddProductActivity.class);
                intent.putExtra("nazwa3", nazwa);
                mContext.startActivity(intent);
            }
        });

        holder.cardView_category.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(mContext, "Long", Toast.LENGTH_LONG).show();
                PopupMenu popupMenu = new PopupMenu(mContext, holder.cardView_category);
                popupMenu.inflate(R.menu.menu_settings_list);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String option = menuItem.getTitle().toString();
                        Toast.makeText(mContext, mData.get(position).getName(), Toast.LENGTH_SHORT).show();
                        switch (menuItem.getItemId()) {
                            case R.id.action_change_name_list:
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
                                                changeNameItem(name,position);

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();

                                break;

                            case R.id.action_delete_list:
                                Toast.makeText(mContext, "Usunieto kategorie " + mData.get(position).getName(), Toast.LENGTH_SHORT).show();
                                 removeItem(position);
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }


    //Usuwa liste
    public void removeItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }
    //Zmienia nazwe listy
    public void changeNameItem(String name, int position) {
        mData.get(position).setName(name);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyRecycleView extends RecyclerView.ViewHolder {

        private TextView tv_category_name;
        private ImageView iv_image_category;
        private CardView cardView_category;
        public MyRecycleView(View itemView) {
            super(itemView);

            tv_category_name = (TextView) itemView.findViewById(R.id.category_name_id);
            iv_image_category = (ImageView) itemView.findViewById(R.id.category_image_id);
            cardView_category = (CardView) itemView.findViewById(R.id.card_view_category_id);
        }
    }

}
