package com.example.findme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;



public class input extends Fragment {

    EditText namaBarang, tanggal, waktu, tempat, keterangan, nomer;
    Button gambar, tambahkan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("Tambahkan");
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        namaBarang = view.findViewById(R.id.namabarang);
        tanggal = view.findViewById(R.id.tanggal);
        waktu = view.findViewById(R.id.waktu);
        tempat = view.findViewById(R.id.tempat);
        keterangan = view.findViewById(R.id.keterangan);
        nomer = view.findViewById(R.id.nomer);
        gambar = view.findViewById(R.id.gambar);
        tambahkan = view.findViewById(R.id.tambahkan);

        return inflater.inflate(R.layout.fragment_input, container, false);
    }
}
