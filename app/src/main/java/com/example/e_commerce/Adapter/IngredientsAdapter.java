package com.example.e_commerce.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Model.ExtendedIngredient;
import com.example.e_commerce.Model.Ingredient;
import com.example.e_commerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder>{
    Context context;
    List<ExtendedIngredient> list;

    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_ingredients, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.textView_ingred_name.setText(list.get(position).getName());
        holder.textView_ingred_name.setSelected(true);
        holder.textView_ingred_quantity.setText(list.get(position).original);
        holder.textView_ingred_quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.imageView_ingred);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class IngredientsViewHolder extends RecyclerView.ViewHolder {

    TextView textView_ingred_quantity, textView_ingred_name;
    ImageView imageView_ingred;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_ingred_quantity = itemView.findViewById(R.id.textView_ingred_quantity);
        textView_ingred_name = itemView.findViewById(R.id.textView_ingred_name);
        imageView_ingred = itemView.findViewById(R.id.imageView_ingred);
    }
}
