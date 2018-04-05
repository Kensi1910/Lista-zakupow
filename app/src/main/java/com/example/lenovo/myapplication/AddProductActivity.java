package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AddProductActivity extends AppCompatActivity {

    TextView tv_category_name;
   // Bundle extras = getIntent().getExtras();

    //String nazwa = extras.getString("nazwa3");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        tv_category_name = (TextView) findViewById(R.id.category_name);
        if (savedInstanceState == null) {
            String nazwa2 = getIntent().getStringExtra("nazwa3");
            tv_category_name.setText(nazwa2);
        }


    }
}
