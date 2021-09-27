package com.example.restoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restoapp.R;
import com.example.restoapp.features.DetailProductActivity;
import com.example.restoapp.helpers.ClickItemListener;
import com.example.restoapp.helpers.ClickMenuListener;
import com.example.restoapp.models.ModelMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    public static final Integer TipeUser = 1, TipeAdmin = 2;
    ArrayList<ModelMenu> dataList;
    Context context;
    Integer tipe = 0;
    ClickMenuListener clickMenuListener;


    public MenuAdapter(Context context, ArrayList<ModelMenu> dataList, Integer tipe) {
        this.dataList = dataList;
        this.context = context;
        this.tipe = tipe;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        final ModelMenu data = dataList.get(position);
        if (tipe == TipeUser) {
            holder.btnDelete.setVisibility(View.GONE);
        } else {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.layoutItem.getLayoutParams().width = 300;
            holder.layoutItem.requestLayout();
        }

        holder.tvName.setText(dataList.get(position).getNama());
        double harga = (double) dataList.get(position).getHarga();
        holder.tvHarga.setText(formatRupiah(harga));
        Picasso.with(context).load(dataList.get(position).getFoto()).into(holder.ivImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                holder.ivImage.setImageResource(R.drawable.ic_breakfast);
            }
        });
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipe == TipeUser) {
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    intent.putExtra("Data", new Gson().toJson(data));
                    context.startActivity(intent);
                } else {

                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMenuListener.onClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public void setOnClickItemListener(ClickMenuListener clickMenuListener){
        this.clickMenuListener = clickMenuListener;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvHarga;
        ImageView ivImage, btnDelete;
        LinearLayout layoutItem;

        public MenuViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvHarga = (TextView) itemView.findViewById(R.id.tvHarga);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layoutItem);
        }
    }
}
