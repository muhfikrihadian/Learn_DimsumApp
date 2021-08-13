package com.example.restoapp.features.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restoapp.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        setup();
    }

    void setup(){
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddProductActivity.this, "Produk berhasil ditambahkan !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}