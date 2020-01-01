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

public class DasboardFragment extends Fragment {
    TextView userName;
    Context mContext;

    SessionModel sessionModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_dasboard, container, false);

        // Inflate the layout for this fragment
        getActivity().setTitle("Halaman Utama");

        // load model session
        mContext = getContext();
        sessionModel = new SessionModel(mContext);

        userName = view.findViewById(R.id.tvNama);
        userName.setText(sessionModel.getUserName());

        return view;
    }
}
