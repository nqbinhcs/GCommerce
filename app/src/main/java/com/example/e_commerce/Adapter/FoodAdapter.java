package com.example.e_commerce.Adapter;

import android.annotation.SuppressLint;
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
import com.example.e_commerce.Activity.ShowDetailActivity;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    ArrayList<Food> Foods;

    public FoodAdapter(ArrayList<Food> Foods) {
        this.Foods = Foods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(Foods.get(position).getTitle());
        holder.description.setText(Foods.get(position).getDescription());

        holder.shop.setText("Shop: " + Foods.get(position).getSeller());

        Glide.with(holder.itemView.getContext())
                .load(Foods.get(position).getImageUrl())
                .into(holder.pic);

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", Foods.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", Foods.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", Foods.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", Foods.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;
        TextView addBtn;
        TextView description;
        TextView rating;
        TextView shop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.foodnameTxT);
            pic = itemView.findViewById(R.id.foodImg);
            addBtn = itemView.findViewById(R.id.foodaddBtn);
            description = itemView.findViewById(R.id.fooddescriptionTxT);
            shop = itemView.findViewById(R.id.shop);
        }
    }

}
