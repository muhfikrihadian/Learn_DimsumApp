package com.example.restoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restoapp.R;
import com.example.restoapp.features.DetailProductActivity;
import com.example.restoapp.models.ModelMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    ArrayList<ModelMenu> dataList;
    Context context;

    public MenuAdapter(Context context, ArrayList<ModelMenu> dataList) {
        this.dataList = dataList;
        this.context = context;
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
        holder.tvName.setText(dataList.get(position).getNama());
        holder.tvHarga.setText("Rp. " + dataList.get(position).getHarga());
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
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("Data", new Gson().toJson(data));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvHarga;
        ImageView ivImage;
        LinearLayout layoutItem;

        public MenuViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvHarga = (TextView) itemView.findViewById(R.id.tvHarga);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layoutItem);
        }
    }
}
