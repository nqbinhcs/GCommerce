package com.example.e_commerce.Adapter;

import android.annotation.SuppressLint;
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
import com.example.e_commerce.Activity.ShowDetailActivity;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.util.ArrayList;


public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<Food> popularFood;

    public PopularAdapter(ArrayList<Food> Foods) {
        this.popularFood = Foods;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);

        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.popularTitle.setText(popularFood.get(position).getTitle());
        holder.popularFee.setText(String.valueOf(popularFood.get(position).getFee()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularFood.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
        if(drawableResourceId==0) {
            drawableResourceId = holder.itemView.getContext().getResources().getIdentifier("food", "drawable", holder.itemView.getContext().getPackageName());
        }
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.popularPic);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", popularFood.get(position));
//                intent.putExtra("category", popularFood.get(position).getTitle());
//                intent.putExtra("categoryID", popularFood.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView popularTitle, popularFee;
        ImageView popularPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popularTitle = itemView.findViewById(R.id.title_pop);
            popularFee = itemView.findViewById(R.id.fee_pop);
            popularPic = itemView.findViewById(R.id.pic_pop);
            mainLayout = itemView.findViewById(R.id.mainLayout_pop);
        }
    }
}
