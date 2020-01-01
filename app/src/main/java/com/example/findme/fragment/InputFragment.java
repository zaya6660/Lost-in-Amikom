package com.example.findme.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.findme.R;
import com.example.findme.model.SessionModel;
import com.example.findme.model.UploadInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class InputFragment extends Fragment {

    EditText namaBarang, tanggal, waktu, tempat, keterangan, nomer;
    Button gambar, tambahkan;
    ImageView imgview;
    int Image_Request_Code = 7;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri FilePathUri;

    Context mContext;
    SessionModel sessionModel;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        getActivity().setTitle("Tambahkan");

        mContext = getContext();
        sessionModel = new SessionModel(mContext);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        namaBarang = view.findViewById(R.id.namabarang);
        tanggal = view.findViewById(R.id.tanggal);
        waktu = view.findViewById(R.id.waktu);
        tempat = view.findViewById(R.id.tempat);
        keterangan = view.findViewById(R.id.keterangan);
        nomer = view.findViewById(R.id.nomer);
        gambar = view.findViewById(R.id.gambar);
        tambahkan = view.findViewById(R.id.tambahkan);
        imgview = view.findViewById(R.id.image_view);
        storageReference = FirebaseStorage.getInstance().getReference("images");
        databaseReference = FirebaseDatabase.getInstance().getReference("images");

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), Image_Request_Code);
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });

        tambahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = mContext.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage() {

        if (FilePathUri != null) {

            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String TmpNamaBarang = namaBarang.getText().toString().trim();
                    String TmpWaktu = waktu.getText().toString().trim();
                    String TmpTanggal = tanggal.getText().toString().trim();
                    String TmpTempat = tempat.getText().toString().trim();
                    String TmpNomer = nomer.getText().toString().trim();
                    String TmpKeterangan = keterangan.getText().toString().trim();

                    Toast.makeText(mContext, "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                    @SuppressWarnings("VisibleForTests")
                    UploadInfo imageUploadInfo = new UploadInfo(sessionModel.getUserId(), TmpNamaBarang, taskSnapshot.getUploadSessionUri().toString(), TmpWaktu, TmpTanggal, TmpTempat, TmpNomer, TmpKeterangan);

                    Log.d("UPLOAD ERROR", imageUploadInfo.toString());

                    String ImageUploadId = databaseReference.push().getKey();
                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                }
            });
        }
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimeDialog(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                waktu.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

}
