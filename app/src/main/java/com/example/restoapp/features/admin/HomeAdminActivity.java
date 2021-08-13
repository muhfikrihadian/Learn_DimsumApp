package com.example.restoapp.features.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restoapp.R;
import com.example.restoapp.adapters.OrderAdapter;
import com.example.restoapp.features.LoginActivity;
import com.example.restoapp.models.ModelOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdminActivity extends AppCompatActivity {
    @BindView(R.id.rcyOrder)
    RecyclerView rcyOder;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.btnAddMenu)
    Button btnAddMenu;

    private OrderAdapter adapter;
    private ArrayList<ModelOrder> modelMenuArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        ButterKnife.bind(this);
        setup();
    }

    void setup(){
        addData();
        adapter = new OrderAdapter(this, modelMenuArrayList);
        rcyOder.setLayoutManager(new LinearLayoutManager(this));
        rcyOder.setAdapter(adapter);
    }

    void addData() {
        modelMenuArrayList = new ArrayList<>();
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));
        modelMenuArrayList.add(new ModelOrder("Fikri", "Dimsum Original", 10000, 1, 25000, ""));

        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdminActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeAdminActivity.this).setTitle("Keluar akun").setMessage("Apakah anda yakin keluar dari akun ini ?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeAdminActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(R.mipmap.ic_icon).show();
            }
        });
    }
}