package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    TextView daftar;
    EditText memail, mpassword;
    FirebaseAuth auth;
    Button mmasuk;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide the action bar
        getSupportActionBar().hide();

        mmasuk = findViewById(R.id.masuk);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        daftar = findViewById(R.id.daftar);

        userid = auth.getCurrentUser().getUid();

        mmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email harus diisi");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Email harus diisi");
                    return;
                }

                if(password.length() < 6){
                    mpassword.setError("Kata sandi harus lebih dari 6 kata");
                    return;
                }

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String email = user.getEmail();
                            Toast.makeText(loginActivity.this, "Selamat Datang " + email, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(loginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });

        daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });
    }
}
