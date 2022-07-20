package com.muhfikrih.dimsumapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.muhfikrih.dimsumapp.R;
import com.muhfikrih.dimsumapp.models.ModelOrder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MenuViewHolder> {
    ArrayList<ModelOrder> dataList;
    Context context;

    public OrderAdapter(Context context, ArrayList<ModelOrder> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_order, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        final ModelOrder data = dataList.get(position);
        holder.tvName.setText(dataList.get(position).getName());
        holder.tvQty.setText(String.valueOf(dataList.get(position).getQty()));
        holder.tvProduct.setText(dataList.get(position).getNama());
        double harga = (double) dataList.get(position).getTotal();
        holder.tvTotal.setText(formatRupiah(harga));

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailProductActivity.class);
//                intent.putExtra("Data", new Gson().toJson(data));
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQty, tvProduct, tvTotal;
        LinearLayout layoutItem;

        public MenuViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvQty = (TextView) itemView.findViewById(R.id.tvQty);
            tvProduct = (TextView) itemView.findViewById(R.id.tvProduct);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layoutItem);
        }
    }
}
