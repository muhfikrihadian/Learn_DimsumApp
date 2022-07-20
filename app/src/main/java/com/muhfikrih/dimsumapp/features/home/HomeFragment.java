package com.muhfikrih.dimsumapp.features.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muhfikrih.dimsumapp.R;
import com.muhfikrih.dimsumapp.adapters.MenuAdapter;
import com.muhfikrih.dimsumapp.features.LoginActivity;
import com.muhfikrih.dimsumapp.helpers.DataHelper;
import com.muhfikrih.dimsumapp.models.ModelMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.btnLogout)
    ImageView btnLogout;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
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

    DataHelper dataHelper;
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
        dataHelper = new DataHelper(getActivity());
        database = FirebaseDatabase.getInstance().getReference();
        tvName.setText(dataHelper.getUsername());
        addData();
        btnLogout.setOnClickListener(new View.OnClickListener() {
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
                changeTipe(btnAll);
            }
        });
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTipe("Makanan");
                changeTipe(btnFood);
            }
        });
        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTipe("Minuman");
                changeTipe(btnDrink);
            }
        });
    }

    void changeTipe(View v){
        if(v == btnAll){
            btnAll.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature_selected));
            btnFood.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature));
            btnDrink.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature));
        }else if(v == btnFood){
            btnAll.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature));
            btnFood.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature_selected));
            btnDrink.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature));
        }else if(v == btnDrink){
            btnFood.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature));
            btnAll.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature));
            btnDrink.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_feature_selected));
        }
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
                if (modelMenuArrayList.size() == 0) {
                    tvInfo.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvInfo.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new MenuAdapter(getActivity(), modelMenuArrayList, MenuAdapter.TipeUser);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    recyclerView.setAdapter(adapter);
                }
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
                dataHelper.deletePref();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
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