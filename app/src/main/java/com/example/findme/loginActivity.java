package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findme.model.SessionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView daftar;
    EditText memail, mpassword;
    FirebaseAuth auth;
    Button masuk;
    ProgressBar progressBar;

    SessionModel sessionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // load model session
        sessionModel = new SessionModel(this);

        // hide the action bar
        getSupportActionBar().hide();

        masuk = findViewById(R.id.masuk);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        daftar = findViewById(R.id.daftar);
        progressBar = findViewById(R.id.progressBar);

        // Code berikut berfungsi untuk mengecek session, Jika session true ( sudah login )
        // maka langsung memulai MainActivity.
        if (sessionModel.getSudahLogin()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                            Log.d("AUTH INFO", "onComplete: currentUserUid---->" + currentFirebaseUser.getUid());
                            Log.d("AUTH INFO", "onComplete: currentUserEmail---->" + currentFirebaseUser.getEmail());

                            sessionModel.saveString(sessionModel.SESSION_USER_ID, currentFirebaseUser.getUid());
                            sessionModel.saveString(sessionModel.SESSION_EMAIL, currentFirebaseUser.getEmail());
                            sessionModel.saveBoolean(sessionModel.SESSION_SUDAH_LOGIN, true);

                            // Get user login name
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference ref = database.child("user").child(currentFirebaseUser.getUid());
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String nama = dataSnapshot.child("nama").getValue().toString();

                                    Log.d("AUTH INFO", "onComplete: currentUserName---->" + nama);
                                    sessionModel.saveString(sessionModel.SESSION_USER_NAME, nama);

                                    Toast.makeText(LoginActivity.this, "Selamat Datang " + nama, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.e("AUTH INFO", "Failed to load user data.");
                                    Toast.makeText(LoginActivity.this, "Error ! Failed to load user data.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });

        daftar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
