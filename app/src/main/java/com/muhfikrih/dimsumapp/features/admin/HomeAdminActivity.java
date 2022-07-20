package com.muhfikrih.dimsumapp.features.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muhfikrih.dimsumapp.R;
import com.muhfikrih.dimsumapp.adapters.MenuAdapter;
import com.muhfikrih.dimsumapp.adapters.OrderAdapter;
import com.muhfikrih.dimsumapp.features.LoginActivity;
import com.muhfikrih.dimsumapp.helpers.ClickMenuListener;
import com.muhfikrih.dimsumapp.helpers.DataHelper;
import com.muhfikrih.dimsumapp.models.ModelMenu;
import com.muhfikrih.dimsumapp.models.ModelOrder;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdminActivity extends AppCompatActivity {
    @BindView(R.id.tvInfoMenu)
    TextView tvInfoMenu;
    @BindView(R.id.tvInfoOrder)
    TextView tvInfoOrder;
    @BindView(R.id.rcyMenu)
    RecyclerView rcyMenu;
    @BindView(R.id.rcyOrder)
    RecyclerView rcyOder;
    @BindView(R.id.btnLogout)
    ImageView btnLogout;
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
                        DataHelper dataHelper = new DataHelper(HomeAdminActivity.this);
                        dataHelper.deletePref();

                        Intent intent = new Intent(HomeAdminActivity.this, LoginAdminActvitiy.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
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
                if (modelMenus.size() == 0) {
                    tvInfoMenu.setVisibility(View.VISIBLE);
                    rcyMenu.setVisibility(View.GONE);
                } else {
                    Collections.reverse(modelMenus);
                    tvInfoMenu.setVisibility(View.GONE);
                    rcyMenu.setVisibility(View.VISIBLE);

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
                if (modelMenuArrayList.size() == 0) {
                    tvInfoOrder.setVisibility(View.VISIBLE);
                    rcyMenu.setVisibility(View.GONE);
                } else {
                    Collections.reverse(modelMenuArrayList);
                    tvInfoOrder.setVisibility(View.GONE);
                    rcyMenu.setVisibility(View.VISIBLE);

                    adapter = new OrderAdapter(HomeAdminActivity.this, modelMenuArrayList);
                    rcyOder.setLayoutManager(new LinearLayoutManager(HomeAdminActivity.this));
                    rcyOder.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeAdminActivity.this, "Tidak dapat memuat menu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}