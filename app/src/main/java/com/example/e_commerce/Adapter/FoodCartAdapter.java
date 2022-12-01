package com.example.e_commerce.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.CartActivity;
import com.example.e_commerce.Activity.ShowDetailActivity;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class FoodCartAdapter extends RecyclerView.Adapter<FoodCartAdapter.ViewHolder> {
    ArrayList<Food> Foods;
    LocalCache localCache;
    Context context;

    public FoodCartAdapter(Context context, ArrayList<Food> Foods) {
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

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNumberInCart = Foods.get(position).getNumberInCart();
                ++currentNumberInCart;
                Foods.get(position).setNumberInCart(currentNumberInCart);
                holder.number.setText("Number: " + Integer.toString(currentNumberInCart));
                localCache.deleteFoodList();
                localCache.addFoodList(Foods);
                CartActivity.subTotal += Foods.get(position).getCost();
                CartActivity.subTotalView.setText("$" + Double.toString(CartActivity.subTotal));
                CartActivity.totalView.setText("$" + Double.toString(Math.max(0, CartActivity.subTotal + CartActivity.deliveryCharge - CartActivity.disCount)));
            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.subTotal -= Foods.get(position).getCost();
                CartActivity.subTotalView.setText("$" + Double.toString(CartActivity.subTotal));
                CartActivity.totalView.setText("$" + Double.toString(Math.max(0, CartActivity.subTotal + CartActivity.deliveryCharge - CartActivity.disCount)));

                int currentNumberInCart = Foods.get(position).getNumberInCart();
                if (currentNumberInCart == 1) {
                    --currentNumberInCart;
                    Foods.remove(position);
                } else {
                    --currentNumberInCart;
                    Foods.get(position).setNumberInCart(currentNumberInCart);
                    holder.number.setText("Number: " + Integer.toString(currentNumberInCart));
                }
                localCache.deleteFoodList();
                localCache.addFoodList(Foods);
                if (Foods.size() == 0) {
                    context.startActivity(new Intent(context, CartActivity.class));
                }
                if (currentNumberInCart == 0)
                    notifyDataSetChanged();
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.subTotal -= Foods.get(position).getCost() * Foods.get(position).getNumberInCart();
                CartActivity.subTotalView.setText("$" + Double.toString(CartActivity.subTotal));
                CartActivity.totalView.setText("$" + Double.toString(Math.max(0, CartActivity.subTotal + CartActivity.deliveryCharge - CartActivity.disCount)));

                Foods.remove(position);
                localCache.deleteFoodList();
                localCache.addFoodList(Foods);
                if (Foods.size() == 0) {
                    context.startActivity(new Intent(context, CartActivity.class));
                }
                notifyDataSetChanged();
            }
        });
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
