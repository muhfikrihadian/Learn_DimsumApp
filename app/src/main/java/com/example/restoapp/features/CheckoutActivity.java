package com.example.restoapp.features;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restoapp.R;
import com.example.restoapp.models.ModelOrder;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

    Integer ongkir = 5000;

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
                Intent intent = new Intent(CheckoutActivity.this, InfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    void setup() {
        Intent intent = getIntent();
        if (intent != null) {
            String datas = intent.getStringExtra("DataOrder");
            ModelOrder modelOrder = new Gson().fromJson(datas, ModelOrder.class);
            Integer totalOld = modelOrder.getTotal();

            tvName.setText(modelOrder.getNama());
            tvQty.setText(String.valueOf(modelOrder.getQty()));
            tvTotalOld.setText(String.valueOf(totalOld));
            tvSubtotal.setText(String.valueOf(totalOld));
            tvOngkir.setText(String.valueOf(ongkir));

            Integer total = totalOld += ongkir;
            tvTotal.setText(String.valueOf(total));

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
}