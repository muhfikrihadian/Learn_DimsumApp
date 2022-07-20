package com.muhfikrih.dimsumapp.features;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.muhfikrih.dimsumapp.R;
import com.muhfikrih.dimsumapp.features.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileRegisterActivity extends AppCompatActivity {
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnLater)
    Button btnLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_register);
        ButterKnife.bind(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileRegisterActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileRegisterActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}