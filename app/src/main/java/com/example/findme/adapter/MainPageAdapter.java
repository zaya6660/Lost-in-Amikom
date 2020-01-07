package com.example.findme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findme.R;
import com.example.findme.fragment.DasboardFragment;
import com.example.findme.model.MainPageModel;
import com.google.android.gms.flags.Flag;

import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder>{

    private List<MainPageModel> mPModel;
    private Context context1;

    public MainPageAdapter(List<MainPageModel> mPModel, Context context) {
        this.mPModel = mPModel;
        this.context1 = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_dashboard, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        String nama = mPModel.get(position).getNamaBarang();
        String tanggal = mPModel.get(position).getTanggalPenemuan();
        String waktu = mPModel.get(position).getWaktuPenemuan();
        String image = mPModel.get(position).getImageUrl();
        String tempat = mPModel.get(position).getTempatPenemuan();



        holder.nama.setText("Nama barang : "+nama);
        holder.tanggal.setText("Tanggal DItemukan : "+tanggal);
        holder.waktu.setText("Waktu Penemuan : "+waktu);
        holder.tempat.setText("Tempat Penemuan : "+tempat);


        Glide.with(context1)
                // LOAD URL DARI INTERNET
                .load(image)
                // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                .placeholder(R.drawable.ic_profil)
                //. LOAD GAMBAR SAAT TERJADI KESALAHAN MEMUAT GMBR UTAMA
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageV);
    }

    @Override
    public int getItemCount() {
        return mPModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama,tanggal,waktu,tempat;
        private ImageView imageV;
        private CardView cview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

                //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
                nama = itemView.findViewById(R.id.namaBarang);
                tanggal = itemView.findViewById(R.id.tglTemuan);
                waktu = itemView.findViewById(R.id.waktuTemuan);
                imageV = itemView.findViewById(R.id.imgHilang);
                tempat = itemView.findViewById(R.id.tempatTemuan);
                cview = itemView.findViewById(R.id.cview);

        }
    }
}
