package com.example.qrcreate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText keycode;
    Button search;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keycode = (EditText)findViewById(R.id.keycode);
        btn = (Button)findViewById(R.id.btn);
        search = (Button)findViewById(R.id.search);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CreateQR.class);
                text = keycode.getText().toString();
                intent.putExtra("keycode", text);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, building_search.class);
                startActivity(intent);
            }
        });
    }
}