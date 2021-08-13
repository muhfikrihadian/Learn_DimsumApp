package com.example.restoapp.features.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.ivBtnBack)
    ImageView ivBtnBack;
    @BindView(R.id.rcyMenu)
    RecyclerView recyclerView;

    private MenuAdapter adapter;
    private ArrayList<ModelMenu> modelMenuArrayList;
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
        addData();
        adapter = new MenuAdapter(getActivity(), modelMenuArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);
    }

    void addData() {
        modelMenuArrayList = new ArrayList<>();
        modelMenuArrayList.add(new ModelMenu("Dimsum Original", "Dimsum adalah istilah dari bahasa Kantonis dan artinya adalah makanan kecil. Biasanya dim sum dimakan sebagai sarapan atau sarsi.", Tipe_Makanan, 12000, "https://asset.kompas.com/crops/DEDxeclyBPPZ2miRZhxGFJxhHU0=/0x175:800x708/750x500/data/photo/2021/01/18/60053789dc100.jpg"));
        modelMenuArrayList.add(new ModelMenu("Dimsum Bakar Ori", "Dimsum adalah istilah dari bahasa Kantonis dan artinya adalah makanan kecil. Biasanya dim sum dimakan sebagai sarapan atau sarsi.", Tipe_Makanan, 15000, "https://assets-pergikuliner.com/IJcJDdFgTvZLotA5-ONmCN-XBg8=/fit-in/1366x768/smart/filters:no_upscale()/https://assets-pergikuliner.com/uploads/image/picture/1597436/picture-1568864994.jpg"));
        modelMenuArrayList.add(new ModelMenu("Dimsum Mentai", "Dimsum adalah istilah dari bahasa Kantonis dan artinya adalah makanan kecil. Biasanya dim sum dimakan sebagai sarapan atau sarsi.", Tipe_Makanan, 20000, "https://cdn.idntimes.com/content-images/post/20200812/mentai-9957c46529a49e2125cd3714bb4e8dcf.jpg"));
        modelMenuArrayList.add(new ModelMenu("Es Teh Manis", "Teh adalah minuman yang mengandung kafeina, sebuah infusi yang dibuat dengan cara menyeduh daun, pucuk daun, atau tangkai daun yang dikeringkan dari tanaman Camellia sinensis dengan air panas", Tipe_Minuman, 5000, "https://img.qraved.co/v2/image/data/2016/11/03/icedtea_02_small-x.jpg"));
        modelMenuArrayList.add(new ModelMenu("Boba Milk", "Teh susu mutiara atau lebih sering disebut boba adalah sejenis minuman teh susu ditambah dengan mutiara yang terbuat dari tapioka.", Tipe_Minuman, 10000, "https://asset.kompas.com/crops/5075RCk7D4TbptAt2LQJB1k2HoQ=/0x0:1000x667/750x500/data/photo/2019/09/09/5d763bb561af1.jpg"));
        modelMenuArrayList.add(new ModelMenu("Air Mineral", "Air mineral adalah air yang mengandung mineral, baik alami atau buatan.", Tipe_Minuman, 6000, "https://static.bmdstatic.com/pk/product/medium/5eec6f319888c.jpg"));

        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }
}