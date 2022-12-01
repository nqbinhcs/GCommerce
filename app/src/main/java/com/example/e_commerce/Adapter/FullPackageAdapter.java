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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.CartActivity;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class FullPackageAdapter extends RecyclerView.Adapter<FullPackageAdapter.ViewHolder> {
    ArrayList<Food> Foods;
    LocalCache localCache;
    Context context;

    public FullPackageAdapter(Context context, ArrayList<Food> Foods) {
        this.Foods = Foods;
        this.context = context;
        this.localCache = new LocalCache(context, "Local cache");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_item, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(Foods.get(position).getTitle());
        holder.shop.setText("Shop: " + Foods.get(position).getSeller());
        holder.cost.setText("$" + Double.toString(Foods.get(position).getCost()));
        holder.number.setText("Number: " + Integer.toString(Foods.get(position).getNumberInCart()));
        Glide.with(holder.itemView.getContext())
                .load(Foods.get(position).getImageUrl())
                .into(holder.pic);
        holder.addBtn.setVisibility(View.GONE);
        holder.minusBtn.setVisibility(View.GONE);
        holder.deleteBtn.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return Foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView title, shop, cost, number;
        ImageView addBtn, minusBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.foodnameTxT);
            pic = itemView.findViewById(R.id.foodImg);
            shop = itemView.findViewById(R.id.shop);
            cost = itemView.findViewById(R.id.cost);
            number = itemView.findViewById(R.id.number_item);
            addBtn = itemView.findViewById(R.id.addBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            deleteBtn = itemView.findViewById(R.id.delete_item);
        }
    }

}
