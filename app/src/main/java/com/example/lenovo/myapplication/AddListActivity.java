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

public class AddListActivity extends AppCompatActivity {

    private EditText shop_list_name;
    private Button button_wyslij;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        button_wyslij = (Button) findViewById(R.id.button_send);
        shop_list_name = (EditText) findViewById(R.id.new_list_edit_text);
        button_wyslij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shop_list_name.getText().toString().equals("")) {
                    Toast.makeText(AddListActivity.this, "Podaj nazwe listy", Toast.LENGTH_SHORT);
                }
                else {
                    Toast.makeText(AddListActivity.this, "Dodano listę " + shop_list_name.getText() , Toast.LENGTH_SHORT).show();

                    String listNameString = shop_list_name.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("Nazwa",listNameString);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddListActivity.class);
    }
}
