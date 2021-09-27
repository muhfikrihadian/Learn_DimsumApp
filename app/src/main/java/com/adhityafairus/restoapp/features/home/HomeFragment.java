package com.example.restoapp.features.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoapp.R;
import com.example.restoapp.adapters.MenuAdapter;
import com.example.restoapp.features.DetailActivity;
import com.example.restoapp.features.DetailProductActivity;
import com.example.restoapp.features.LoginActivity;
import com.example.restoapp.helpers.ClickItemListener;
import com.example.restoapp.models.ModelMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.ivBtnBack)
    ImageView ivBtnBack;
    @BindView(R.id.rcyMenu)
    RecyclerView recyclerView;
    @BindView(R.id.etSearch)
    SearchView etSearch;
    @BindView(R.id.btnSearch)
    ImageView btnSearch;
    @BindView(R.id.btnClear)
    ImageView btnClear;
    @BindView(R.id.btnAll)
    LinearLayout btnAll;
    @BindView(R.id.btnFood)
    LinearLayout btnFood;
    @BindView(R.id.btnDrink)
    LinearLayout btnDrink;

    private DatabaseReference database;
    private MenuAdapter adapter, adapterSearch, adapterTipe;
    private ArrayList<ModelMenu> modelMenuArrayList, modelMenuSearch, modelMenuTipe;
    public static final String Tipe_Makanan = "Makanan", Tipe_Minuman = "Minuman";

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setup();
        return view;
    }

    void setup() {
        database = FirebaseDatabase.getInstance().getReference();
        addData();
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logout();
            }
        });
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(adapter);
                btnClear.setVisibility(View.GONE);
                btnSearch.setVisibility(View.VISIBLE);
            }
        });
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTipe("Makanan");
            }
        });
        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTipe("Minuman");
            }
        });
    }

    void addData() {
        modelMenuArrayList = new ArrayList<>();
        modelMenuSearch = new ArrayList<>();
        modelMenuTipe = new ArrayList<>();
        database.child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ModelMenu menu = noteDataSnapshot.getValue(ModelMenu.class);
                    menu.setId(noteDataSnapshot.getKey());
                    modelMenuArrayList.add(menu);
                }
                adapter = new MenuAdapter(getActivity(), modelMenuArrayList, MenuAdapter.TipeUser);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Tidak dapat memuat menu", Toast.LENGTH_SHORT).show();
            }
        });
        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                etSearch.clearFocus();
                searchMenu(query.toLowerCase());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    searchMenu(s.toLowerCase());
                    btnClear.setVisibility(View.VISIBLE);
                    btnSearch.setVisibility(View.GONE);
                } else {
                    recyclerView.setAdapter(adapter);
                    btnClear.setVisibility(View.GONE);
                    btnSearch.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    void logout(){
        new AlertDialog.Builder(getActivity()).setTitle("Keluar akun").setMessage("Apakah anda yakin keluar dari akun ini ?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
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

    private void searchMenu(String menu) {
        modelMenuSearch.clear();
        for (ModelMenu v : modelMenuArrayList) {
            if (v.getNama().toLowerCase().contains(menu)) {
                modelMenuSearch.add(v);
            }
        }
        adapterSearch = new MenuAdapter(getActivity(), modelMenuSearch, MenuAdapter.TipeUser);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapterSearch);
    }

    private void searchTipe(String tipe) {
        modelMenuTipe.clear();
        for (ModelMenu v : modelMenuArrayList) {
            if (v.getTipe().equalsIgnoreCase(tipe)) {
                modelMenuTipe.add(v);
            }
        }
        adapterTipe = new MenuAdapter(getActivity(), modelMenuTipe, MenuAdapter.TipeUser);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapterTipe);
    }
}