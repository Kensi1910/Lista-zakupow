package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static android.support.v4.app.FragmentActivity.*;
import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.lenovo.myapplication.MainActivity.baza;

/**
 * Created by kensi on 22/03/2018.
 */

public class RecyclerViewAdapterCategory extends RecyclerView.Adapter<RecyclerViewAdapterCategory.MyRecycleView> {

    private Context mContext;
    private static final int PICK_IMAGE = 1;
    private List<Category> mData;
    private EditText nameEditText;
    private Button pickImageButton;
    private ImageView zdjecie;
    public String nazwa = "";
    private static String categoryId;
    private static final int REQUEST_CODE_KATEGORIA = 122;



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
     //   holder.iv_image_category.setImageResource(mData.get(position).getImage());
        final byte[] categoryImage = mData.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryImage, 0, categoryImage.length);
        holder.iv_image_category.setImageBitmap(bitmap);

        holder.cardView_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nazwa = mData.get(position).getName();
                Intent intent = new Intent(mContext, AddProductActivity.class);
                categoryId = baza.getIDCategory(nazwa);
                intent.putExtra("id_kategori", categoryId);
                ((Activity) mContext).startActivityForResult(intent,REQUEST_CODE_KATEGORIA);

            }
        });


/*
        holder.cardView_category.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(mContext, "Long", Toast.LENGTH_LONG).show();
                PopupMenu popupMenu = new PopupMenu(mContext, holder.cardView_category);
                popupMenu.inflate(R.menu.menu_settings_category);
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
                                                updateKategoriaName(name,oldName,position);

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
                                //removeItem(position);
                                deleteKategoria(position);
                                break;

                            case R.id.action_change_picture:
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
                                LayoutInflater inflater2 = LayoutInflater.from(mContext);
                                View view2 = inflater2.inflate(R.layout.activity_change_picture_category, null);
                                zdjecie = (ImageView) view2.findViewById(R.id.iv_change_picture_category);
                                pickImageButton = (Button) view2.findViewById(R.id.button_change_picture_category);
                                builder2.setView(view2)
                                        .setTitle("Zmiena ikony")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                          //      Intent intent = new Intent(RecyclerViewAdapterCategory.this, AddCategory.class);

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();


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

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       // startActivityForResult(gallery, PICK_IMAGE);

    }

    private void updateKategoriaName(String name, String oldName, int position) {
        Category c = mData.get(position);
        c.setName(name);
        baza.updateKateogria(c, oldName);
        mData.set(position,c);
        notifyItemChanged(position);
    }
    private void deleteKategoria(int position) {
        baza.deleteKategoria(mData.get(position));
        mData.remove(position);
        // notifyItemRemoved(position);
        notifyDataSetChanged();
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
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
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
