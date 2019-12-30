package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {

    TextView mmasuk;
    EditText mnama, mpassword, memail;
    FirebaseAuth auth;
    Button mdaftar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // hide the action bar
        getSupportActionBar().hide();

        mmasuk = findViewById(R.id.masuk);
        mnama = findViewById(R.id.nama);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        mdaftar = findViewById(R.id.daftar);

        mdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nama = mnama.getText().toString();
                final String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email harus diisi");
                    return;
                }

                if(TextUtils.isEmpty(nama)){
                    mnama.setError("nama harus diisi");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Eemailmail harus diisi");
                    return;
                }

                if(password.length() < 6){
                    mpassword.setError("Password kurang kuat");
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    User information = new User(
                                            nama,
                                            email
                                    );

                                    FirebaseDatabase.getInstance().getReference("findme")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(registerActivity.this, "Daftar berhasil", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),loginActivity.class));
                                        }
                                    });
                                } else {
                                    Toast.makeText(registerActivity.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        mmasuk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });
    }
}
