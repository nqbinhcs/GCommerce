package com.example.e_commerce.Adaptor;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Domain.FoodDomain;
import com.example.e_commerce.R;

import java.util.ArrayList;


public class PopularAdaptor extends RecyclerView.Adapter<PopularAdaptor.ViewHolder> {
    ArrayList<FoodDomain> foodDomains;

    public PopularAdaptor(ArrayList<FoodDomain> foodDomains) {
        this.foodDomains = foodDomains;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);

        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.popularTitle.setText(foodDomains.get(position).getTitle());
        holder.popularFee.setText(String.valueOf(foodDomains.get(position).getFee()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
        if(drawableResourceId==0) {
            drawableResourceId = holder.itemView.getContext().getResources().getIdentifier("food", "drawable", holder.itemView.getContext().getPackageName());
        }
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.popularPic);

//        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), FoodByCategoryActivity.class);
//                intent.putExtra("category", categoryDomains.get(position).getName());
//                intent.putExtra("categoryID", categoryDomains.get(position).getId());
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
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
