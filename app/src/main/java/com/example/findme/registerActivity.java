package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findme.model.SessionModel;
import com.example.findme.model.UserManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView mmasuk;
    EditText mnama, mpassword, memail;
    FirebaseAuth auth;
    Button mdaftar;
    ProgressBar progressBar;

    SessionModel sessionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // hide the action bar
        getSupportActionBar().hide();

        // load model session
        sessionModel = new SessionModel(this);

        mmasuk = findViewById(R.id.masuk);
        mnama = findViewById(R.id.nama);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        mdaftar = findViewById(R.id.daftar);
        progressBar = findViewById(R.id.progressBar);

        // Code berikut berfungsi untuk mengecek session, Jika session true ( sudah login )
        // maka langsung memulai MainActivity.
        if (sessionModel.getSudahLogin()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        mdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nama = mnama.getText().toString();
                final String email = memail.getText().toString().trim();
                final String password = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email harus diisi");
                    return;
                }

                if(TextUtils.isEmpty(nama)){
                    mnama.setError("nama harus diisi");
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

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    UserManagement information = new UserManagement(
                                            nama,
                                            email,
                                            password
                                    );

                                    FirebaseDatabase.getInstance().getReference("user")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(RegisterActivity.this, "Daftar berhasil", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });

        mmasuk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
