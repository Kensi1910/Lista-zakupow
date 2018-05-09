package com.example.lenovo.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryActivity extends AppCompatActivity{

    private List<Category> lstCategory;
    private static final int REQUEST_CODE = 1423;
    private static final int REQUEST_CODE_KATEGORIA = 122;
    private String newCategoryName;
    private byte[] newCategoryImage;
    private Baza baza;
    private RecyclerView recyclerViewCategory;
    private RecyclerViewAdapterCategory mAdapter;
    byte[] foto;
    private CardView cardView_category;
    private Button pickImageButton;
    private ImageView zdjecie;
    private TextView opis_zmien_zdjecie;
    final int REQUEST_CODE_GALLERY = 999;
    private static final int PICK_IMAGE = 1;
    public static ArrayList<Produkt> arraylist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        cardView_category = findViewById(R.id.card_view_category_id);
        baza = new Baza(this);
        lstCategory = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        lstCategory = MainActivity.baza.getAllKategoria();

        recyclerViewCategory = (RecyclerView) findViewById(R.id.category_recyclerview);
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new RecyclerViewAdapterCategory(this, lstCategory);
        recyclerViewCategory.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        recyclerViewCategory.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerViewCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);

          //      Toast.makeText(CategoryActivity.this, "ff", Toast.LENGTH_LONG).show();
            }
        }));

     //   zdjecie = (ImageView) findViewById(R.id.iv_change_picture_category);
      //  pickImageButton = (Button) findViewById(R.id.button_change_picture_category);
    }

    private static byte[] imageViewToByte(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] buffer= out.toByteArray();
        return buffer;
    }

    private void updateKategoriaName(String name, String oldName, int position) {
        Category c = lstCategory.get(position);
        c.setName(name);
        baza.updateKateogria(c, oldName);
        lstCategory.set(position,c);
        mAdapter.notifyItemChanged(position);
    }
    private void deleteKategoria(int position) {
        baza.deleteKategoria(lstCategory.get(position));
      //  baza.deleteKategoria(baza.);
      //  Category c = lstCategory.get(position);
       // String name = c.getName();
      //  Cursor cursor = baza.getItemID(name);

        //baza.deleteKategoria(cursor);
      //  int id = c.getId();
        lstCategory.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Zmien nazwe", "Usun", "Zmien zdjecie"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wybierz opcje");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, lstCategory.get(position), position);
                }
                else if (which == 1) {
                    deleteKategoria(position);
                }
                else {
                    showChangePictureDialog(true, lstCategory.get(position), position);
                   // btnAddOnClick(view);

                }
            }
        });
        builder.show();
    }

    private void showNoteDialog(final boolean shouldUpdate, final Category category, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_change_name_category, null);

        Category c = lstCategory.get(position);
        final String name = c.getName();
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(CategoryActivity.this);
        alertDialogBuilderUserInput.setView(view);


        final EditText inputCategory = view.findViewById(R.id.note);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputCategory.getText().toString())) {
                    Toast.makeText(CategoryActivity.this, "Wpisz nazwe!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                updateKategoriaName(inputCategory.getText().toString(), name, position);

            }
        });
    }

    private void showChangePictureDialog(final boolean shouldUpdate, final Category category, final int position) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_change_picture_category);
        dialog.setTitle("Alert Dialog View");
        //   Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        // btnExit.setOnClickListener(new View.OnClickListener() {
        //     @Override public void onClick(View v) {
        //         dialog.dismiss();
        //     }
        //   });
        dialog.findViewById(R.id.iv_change_picture_category)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeGallery();
                    }
                });
        dialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "Nie masz uprawnien do dostępu", Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void AddNewCategoryButton(View view) {
      //  Intent intent = new Intent(this, AddCategory.class);
     //   startActivity(intent);
        Intent intent = AddCategory.makeIntent(CategoryActivity.this);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
     //   startActivityForResult(intent, PICK_IMAGE);
        startActivity(intent);
        Uri uri = intent.getData();

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            zdjecie.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void btnAddOnClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_change_picture_category);
        dialog.setTitle("Alert Dialog View");
     //   Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
       // btnExit.setOnClickListener(new View.OnClickListener() {
       //     @Override public void onClick(View v) {
       //         dialog.dismiss();
       //     }
     //   });
        dialog.findViewById(R.id.iv_change_picture_category)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeGallery();
                    }
                });
      //  dialog.findViewById(R.id.btnTakePhoto)
           //     .setOnClickListener(new View.OnClickListener() {
             //       @Override public void onClick(View v) {
               //         activeTakePhoto();
               //     }
             //   });

        // show dialog on screen
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    newCategoryName = data.getStringExtra("key_category_name");
                    newCategoryImage = data.getByteArrayExtra("key_category_image");
                    addNewCategory(new Category(newCategoryName, newCategoryImage));
                }
            case REQUEST_CODE_KATEGORIA:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    arraylist2 = bundle.getParcelableArrayList("mylist");

                }
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mylist", arraylist2);
        Intent intent = new Intent();
        intent.putExtras(bundle);
       // Toast.makeText(CategoryActivity.this, arraylist2.get(0).getName() + " " + arraylist2.get(1).getName(), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, intent);
        super.onBackPressed();

    }
    public void addNewCategory(Category category){
        lstCategory.add(lstCategory.size(), category);
        mAdapter.notifyItemInserted(lstCategory.size());
        recyclerViewCategory.scrollToPosition(lstCategory.size() - 1);

    }

}
