package com.muhfikrih.dimsumapp.features;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muhfikrih.dimsumapp.R;
import com.muhfikrih.dimsumapp.models.ModelMenu;
import com.muhfikrih.dimsumapp.models.ModelOrder;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProductActivity extends AppCompatActivity {
    @BindView(R.id.ivBtnBack)
    ImageView ivBtnBack;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvQty)
    TextView tvQty;
    @BindView(R.id.ivMinus)
    ImageView ivMinus;
    @BindView(R.id.ivPlus)
    ImageView ivPlus;
    @BindView(R.id.btnOrder)
    Button btnOder;

    ModelMenu data;
    Integer harga = 0, qty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        setup();
    }

    void setup() {
        Intent intent = getIntent();
        if (intent != null) {
            String datas = intent.getStringExtra("Data");
            data = new Gson().fromJson(datas, ModelMenu.class);
            Log.e("Data", datas);
            Picasso.with(DetailProductActivity.this).load(data.getFoto()).into(ivImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    ivImage.setImageResource(R.drawable.ic_breakfast);
                }
            });
            double doubleHarga = (double) data.getHarga();
            tvName.setText(data.getNama());
            tvDesc.setText(data.getDeskripsi());
            tvPrice.setText(formatRupiah(doubleHarga));
            harga = data.getHarga();
            qty = 1;

            ivBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            btnOder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long unixTime = System.currentTimeMillis() / 1000L;
                    String id = Long.toString(unixTime);
                    ModelOrder modelOrder = new ModelOrder(id, data.getNama(), data.getHarga(), qty, harga, data.getFoto());
                    Intent intent = new Intent(DetailProductActivity.this, CheckoutActivity.class);
                    intent.putExtra("DataOrder", new Gson().toJson(modelOrder));
                    startActivity(intent);
                }
            });
            ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer qtyOld = Integer.valueOf(tvQty.getText().toString());
                    if (data == null) {
                        Toast.makeText(DetailProductActivity.this, "Maaf tidak bisa merubah jumlah makanan", Toast.LENGTH_SHORT).show();
                    } else if (qtyOld <= 1) {
                        Toast.makeText(DetailProductActivity.this, "Anda telah mencapai batas minimum pemesanan", Toast.LENGTH_SHORT).show();
                    } else {
                        qty = qtyOld -= 1;
                        Integer hargaOld = data.getHarga();
                        harga = hargaOld * qty;
                        tvQty.setText(String.valueOf(qty));
                        double doubleHarga = (double) harga;
                        tvPrice.setText(formatRupiah(doubleHarga));
                    }
                }
            });
            ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer qtyOld = Integer.valueOf(tvQty.getText().toString());
                    if (data == null) {
                        Toast.makeText(DetailProductActivity.this, "Maaf tidak bisa merubah jumlah makanan", Toast.LENGTH_SHORT).show();
                    } else {
                        qty = qtyOld += 1;
                        Integer hargaOld = data.getHarga();
                        harga = hargaOld * qty;
                        tvQty.setText(String.valueOf(qty));
                        double doubleHarga = (double) harga;
                        tvPrice.setText(formatRupiah(doubleHarga));
                    }
                }
            });
        } else {
            Toast.makeText(DetailProductActivity.this, "Maaf saat ini menu tidak tersedia untuk dipesan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}