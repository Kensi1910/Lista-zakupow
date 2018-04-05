package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<Category> lstCategory;
    private static final int REQUEST_CODE_GETMESSAGE = 1423;
    private String newCategoryName;
    private RecyclerView recyclerViewCategory;
    private RecyclerViewAdapterCategory mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        lstCategory = new ArrayList<>();
        addCategory();


        recyclerViewCategory = (RecyclerView) findViewById(R.id.category_recyclerview);
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new RecyclerViewAdapterCategory(this, lstCategory);
        recyclerViewCategory.setAdapter(mAdapter);

    }

    public void addCategory() {
        lstCategory.add(new Category("Nabiał", R.drawable.alkohol));
        lstCategory.add(new Category("Mięso", R.drawable.chemia));
        lstCategory.add(new Category("Słodycze", R.drawable.milk));
        lstCategory.add(new Category("Ryby i owoce morza", R.drawable.napoje));
        lstCategory.add(new Category("Dania gotowe i sosy", R.drawable.alkohol));
        lstCategory.add(new Category("Garmażeria", R.drawable.chemia));
        lstCategory.add(new Category("Przetwory", R.drawable.milk));
        lstCategory.add(new Category("Sypkie", R.drawable.napoje));
        lstCategory.add(new Category("Napoje", R.drawable.chemia));
        lstCategory.add(new Category("Przyprawy", R.drawable.milk));
        lstCategory.add(new Category("Kawa i herbata", R.drawable.napoje));
        lstCategory.add(new Category("Alkohol", R.drawable.napoje));
    }
    public void AddNewCategoryButton(View view) {
        Intent intent = new Intent(this, AddCategory.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQUEST_CODE_GETMESSAGE:
                if (resultCode == Activity.RESULT_OK) {
                    newCategoryName = data.getStringExtra("Nazwa");
                    addNewCategory(new Category(newCategoryName, R.drawable.napoje));
                }
        }
    }

    public void addNewCategory(Category category){
        lstCategory.add(lstCategory.size(), category);
        mAdapter.notifyItemInserted(lstCategory.size());
        recyclerViewCategory.scrollToPosition(lstCategory.size() - 1);

    }
}
