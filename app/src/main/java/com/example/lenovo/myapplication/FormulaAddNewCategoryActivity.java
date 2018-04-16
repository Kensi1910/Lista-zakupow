package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormulaAddNewCategoryActivity extends AppCompatActivity {

    private EditText category_name;
    private Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_add_new_category);
   //     addListenerOnButton();
    }
/*
    public void addListenerOnButton() {
        button_send = (Button) findViewById(R.id.add_new_category_formula_button);
        category_name = (EditText) findViewById(R.id.name_new_category_formula_edit_text);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category_name.getText().toString().equals("")) {
                    Toast.makeText(FormulaAddNewCategoryActivity.this, "Podaj nazwe kategorii", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(FormulaAddNewCategoryActivity.this, "Dodano kategorie " + category_name.getText(), Toast.LENGTH_SHORT).show();

                    String listNameString = category_name.getText().toString();
                    Intent intent = new Intent();
                 //   intent.putExtra("Nazwa", listNameString);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddListActivity.class);
    }
    */
}