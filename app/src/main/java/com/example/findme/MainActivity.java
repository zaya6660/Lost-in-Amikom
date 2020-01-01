package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.findme.fragment.DasboardFragment;
import com.example.findme.fragment.HistoryFragment;
import com.example.findme.fragment.InputFragment;
import com.example.findme.fragment.ProfilFragment;
import com.example.findme.model.SessionModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    Context mContext;

    SessionModel sessionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load model session
        mContext = this;
        sessionModel = new SessionModel(mContext);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DasboardFragment()).commit();

        // Code berikut berfungsi untuk mengecek session, Jika session true ( sudah login )
        // maka langsung memulai MainActivity.
        if (!sessionModel.getSudahLogin()){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new DasboardFragment();
                            break;
                        case R.id.nav_input:
                            selectedFragment = new InputFragment();
                            break;
                        case R.id.nav_profil:
                            selectedFragment = new ProfilFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;

                    }

                    if(selectedFragment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.logout:
                logOutConfirmation();
                break;
        }

        return true;
    }

    private void logOutConfirmation(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah anda ingin keluar dari aplikasi ini?")
                .setCancelable(false)
                .setNeutralButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // clear all session login
                        sessionModel.saveString(SessionModel.SESSION_USER_ID, "sessionUserId");
                        sessionModel.saveString(SessionModel.SESSION_USER_NAME, "sessionUserName");
                        sessionModel.saveString(SessionModel.SESSION_EMAIL, "sessionEmail");
                        sessionModel.saveBoolean(SessionModel.SESSION_SUDAH_LOGIN, false);

                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog logOutDialog = alertDialogBuilder.create();
        logOutDialog.show();
    }
}
