package com.example.restoapp.features.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restoapp.R;
import com.example.restoapp.features.TestActivity;
import com.example.restoapp.models.ModelMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProductActivity extends AppCompatActivity {
    @BindView(R.id.ivBtnBack)
    ImageView ivBtnBack;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etDeskripsi)
    EditText etDeskripsi;
    @BindView(R.id.spTipe)
    Spinner spTipe;
    @BindView(R.id.etHarga)
    EditText etHarga;
    @BindView(R.id.etFoto)
    EditText etFoto;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    private DatabaseReference database;
    ProgressDialog progressDialog;
    String saveCurrentDate, saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        setup();
    }

    void setup() {
        database = FirebaseDatabase.getInstance().getReference();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMenu();
            }
        });
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }


    private void uploadMenu() {
        progressDialog = new ProgressDialog(AddProductActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        String id = saveCurrentDate + "," + saveCurrentTime;
        String tipe = spTipe.getSelectedItem().toString();
        String name = etName.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        String harga = etHarga.getText().toString();
        String foto = etFoto.getText().toString();
        Integer newHarga = 0;
        if (!harga.equalsIgnoreCase("")) {
            newHarga = Integer.valueOf(harga);
        }

        if (!isEmpty(name) && !isEmpty(deskripsi) && !isEmpty(harga) && !isEmpty(foto)) {
            submitProduct(new ModelMenu(id, name, deskripsi, tipe, newHarga, foto));
        } else {
            progressDialog.dismiss();
            Toast.makeText(AddProductActivity.this, "Data tidak boleh kosong !", Toast.LENGTH_SHORT).show();
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
    }

    private void submitProduct(ModelMenu modelMenu) {
        database.child("menu").push().setValue(modelMenu).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                etName.setText("");
                etDeskripsi.setText("");
                etHarga.setText("");
                etFoto.setText("");
                Toast.makeText(AddProductActivity.this, "Produk berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

//    public static Intent getActIntent(Activity activity) {
//        // kode untuk pengambilan Intent
//        return new Intent(activity, TestActivity.class);
//    }
}