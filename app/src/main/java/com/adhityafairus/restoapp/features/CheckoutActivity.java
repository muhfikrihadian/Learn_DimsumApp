package com.example.restoapp.features;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restoapp.R;
import com.example.restoapp.features.admin.AddProductActivity;
import com.example.restoapp.models.ModelMenu;
import com.example.restoapp.models.ModelOrder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {
    @BindView(R.id.ivBtnBack)
    ImageView ivBtnBack;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvQty)
    TextView tvQty;
    @BindView(R.id.tvTotalOld)
    TextView tvTotalOld;
    @BindView(R.id.tvSubtotal)
    TextView tvSubtotal;
    @BindView(R.id.tvOngkir)
    TextView tvOngkir;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.btnOrder)
    Button btnOrder;

    double doubleOngkir = 5000;
    private DatabaseReference database;
    ProgressDialog progressDialog;
    Integer ongkir = 5000;
    ModelOrder modelOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        setup();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
            }
        });
    }

    void setup() {
        database = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        if (intent != null) {
            String datas = intent.getStringExtra("DataOrder");
            modelOrder = new Gson().fromJson(datas, ModelOrder.class);
            Integer totalOld = modelOrder.getTotal();
            double doubleHargaOld = (double) totalOld;

            tvName.setText(modelOrder.getNama());
            tvQty.setText(modelOrder.getQty() + " X");
            tvTotalOld.setText(formatRupiah(doubleHargaOld));
            tvSubtotal.setText(formatRupiah(doubleHargaOld));
            tvOngkir.setText(formatRupiah(doubleOngkir));

            Integer total = totalOld += ongkir;
            double doubleHargaNew = (double) total;
            tvTotal.setText(formatRupiah(doubleHargaNew));

            Picasso.with(CheckoutActivity.this).load(modelOrder.getFoto()).into(ivImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    ivImage.setImageResource(R.drawable.ic_breakfast);
                }
            });
        } else {
            Toast.makeText(CheckoutActivity.this, "Maaf saat ini menu tidak tersedia untuk dipesan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }


    void order() {
        progressDialog = new ProgressDialog(CheckoutActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        database.child("order").push().setValue(modelOrder).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Intent intent = new Intent(CheckoutActivity.this, InfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}