package com.example.findme.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findme.R;
import com.example.findme.model.SessionModel;


public class ProfilFragment extends Fragment {
    TextView txtNama;
    TextView txtEmail;
    Context mContext;

    SessionModel sessionModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        mContext = getContext();
        sessionModel = new SessionModel(mContext);
        txtNama = view.findViewById(R.id.textNama);
        txtEmail = view.findViewById(R.id.textEmail);

        txtNama.setText(sessionModel.getUserName());
        txtEmail.setText(sessionModel.getEmail());

        getActivity().setTitle("Profil");

        return view;
    }


}
