package com.example.e_commerce.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.DetailOrderActivity;
import com.example.e_commerce.Activity.FoodByCategoryActivity;
import com.example.e_commerce.Activity.FullPackageDetailActivity;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.R;

import java.util.ArrayList;


public class HistorySaleAdapter extends RecyclerView.Adapter<HistorySaleAdapter.ViewHolder> {
    ArrayList<Order> orderList;
    Context context;

    public HistorySaleAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_sale_item, parent, false);

        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderIdView.setText("Sale #" + orderList.get(position).getId());
        holder.dateView.setText(orderList.get(position).getDate());
        holder.totalView.setText("$" + Double.toString(orderList.get(position).getTotal()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailOrderActivity.class);
                intent.putExtra("OrderId", orderList.get(position).getId());
                intent.putExtra("date", orderList.get(position).getDate());
                intent.putExtra("time", orderList.get(position).getTime());
                intent.putExtra("subTotal", Double.toString(orderList.get(position).getSubtotal()));
                intent.putExtra("disCount", Double.toString(orderList.get(position).getDiscount()));
                intent.putExtra("deliveryCharge", Double.toString(orderList.get(position).getDeliveryCharge()));
                intent.putExtra("total", Double.toString(orderList.get(position).getTotal()));
                intent.putExtra("type", "History order");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdView, dateView, totalView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdView = itemView.findViewById(R.id.orderId);
            dateView = itemView.findViewById(R.id.date);
            totalView = itemView.findViewById(R.id.total);
        }
    }
}
