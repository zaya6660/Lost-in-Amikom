package com.example.findme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {

    TextView daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide the action bar
        getSupportActionBar().hide();

        daftar = findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });
    }
}
