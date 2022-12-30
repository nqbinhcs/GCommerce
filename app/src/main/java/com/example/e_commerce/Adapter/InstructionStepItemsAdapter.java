package com.example.e_commerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Model.Equipment;
import com.example.e_commerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionStepItemsAdapter extends RecyclerView.Adapter<InstructionStepIngredientsViewHolder>{
    Context context;
    List<Equipment> list;

    public InstructionStepItemsAdapter(Context context, List<Equipment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionStepIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepIngredientsViewHolder holder, int position) {
        holder.textView_instructions_step_item_name.setText(list.get(position).getName());
        holder.textView_instructions_step_item_name.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/"+list.get(position).getImage()).into(holder.imageView_instructions_step_item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionStepItemViewHolder extends RecyclerView.ViewHolder {

    TextView textView_instructions_step_item_name;
    ImageView imageView_instructions_step_item;
    public InstructionStepItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView_instructions_step_item = itemView.findViewById(R.id.imageView_instructions_step_item);
        textView_instructions_step_item_name = itemView.findViewById(R.id.textView_instructions_step_item_name);
    }
}
