package com.example.findme.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findme.R;
import com.example.findme.adapter.MainPageAdapter;
import com.example.findme.model.MainPageModel;
import com.example.findme.model.SessionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DasboardFragment extends Fragment {
    TextView userName;
    Context mContext;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainPageAdapter mainPageAdapter;

    private DatabaseReference reference;
    private ArrayList<MainPageModel> mainpage;

    private FirebaseAuth auth;

    SessionModel sessionModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_dasboard, container, false);
        recyclerView = view.findViewById(R.id.rvDashboard);
        auth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        getActivity().setTitle("Halaman Utama");

        // load model session
        mContext = getContext();
        sessionModel = new SessionModel(mContext);

        //userName = view.findViewById(R.id.tvNama);
//        userName.setText(sessionModel.getUserName());

        getData();


        return view;
    }


    public void getData(){
        reference = FirebaseDatabase.getInstance().getReference().child("images");
        reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mainpage = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            MainPageModel mainPageModel = snapshot.getValue(MainPageModel.class);
                            mainpage.add(mainPageModel);
                        }
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        mainPageAdapter = new MainPageAdapter(mainpage, getActivity());
                        recyclerView.setAdapter(mainPageAdapter);
                        //Toast.makeText(getActivity(), "Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Toast.makeText(getActivity(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });

    }
}
