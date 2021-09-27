package com.example.restoapp.features.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoapp.R;
import com.example.restoapp.adapters.MenuAdapter;
import com.example.restoapp.adapters.OrderAdapter;
import com.example.restoapp.features.LoginActivity;
import com.example.restoapp.helpers.ClickMenuListener;
import com.example.restoapp.models.ModelMenu;
import com.example.restoapp.models.ModelOrder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdminActivity extends AppCompatActivity {
    @BindView(R.id.rcyMenu)
    RecyclerView rcyMenu;
    @BindView(R.id.rcyOrder)
    RecyclerView rcyOder;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.btnAddMenu)
    Button btnAddMenu;

    private DatabaseReference database;
    private OrderAdapter adapter;
    private MenuAdapter menuAdapter;
    private ArrayList<ModelOrder> modelMenuArrayList;
    private ArrayList<ModelMenu> modelMenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    void setup() {
        database = FirebaseDatabase.getInstance().getReference();
        modelMenus = new ArrayList<>();
        modelMenuArrayList = new ArrayList<>();
        modelMenus.clear();
        modelMenuArrayList.clear();
        addData();
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

    void addData() {
        database.child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ModelMenu menu = noteDataSnapshot.getValue(ModelMenu.class);
                    menu.setId(noteDataSnapshot.getKey());
                    modelMenus.add(menu);
                }
                menuAdapter = new MenuAdapter(HomeAdminActivity.this, modelMenus, MenuAdapter.TipeAdmin);
                LinearLayoutManager layoutManager = new LinearLayoutManager(HomeAdminActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rcyMenu.setLayoutManager(layoutManager);
                rcyMenu.setAdapter(menuAdapter);

                menuAdapter.setOnClickItemListener(new ClickMenuListener() {
                    @Override
                    public void onClicked(View v, Integer position) {
                        database.child("menu").child(modelMenus.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                HomeAdminActivity.this.recreate();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeAdminActivity.this, "Tidak dapat memuat menu", Toast.LENGTH_SHORT).show();
            }
        });

        database.child("order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ModelOrder order = noteDataSnapshot.getValue(ModelOrder.class);
                    order.setId(noteDataSnapshot.getKey());
                    modelMenuArrayList.add(order);
                }
                adapter = new OrderAdapter(HomeAdminActivity.this, modelMenuArrayList);
                rcyOder.setLayoutManager(new LinearLayoutManager(HomeAdminActivity.this));
                rcyOder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeAdminActivity.this, "Tidak dapat memuat menu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}