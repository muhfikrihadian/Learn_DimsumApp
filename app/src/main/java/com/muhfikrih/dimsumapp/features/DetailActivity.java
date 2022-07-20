package com.muhfikrih.dimsumapp.features;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.muhfikrih.dimsumapp.R;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }
}