package com.example.e_commerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.e_commerce.Model.Step;
import com.example.e_commerce.R;

import java.util.List;

public class InstructionStepsAdapter extends RecyclerView.Adapter<StepsViewHolder>{
    Context context;
    List<Step> list;

    public InstructionStepsAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.textView_instruction_step_number.setText(String.valueOf(list.get(position).getNumber())+". ");
        holder.textView_instruction_step_title.setText(list.get(position).getStep());
        holder.recycler_instructions_equipments.setHasFixedSize(true);
        holder.recycler_instructions_equipments.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        InstructionStepItemsAdapter equipmentAdapter = new InstructionStepItemsAdapter(context, list.get(position).getEquipment());
        holder.recycler_instructions_equipments.setAdapter(equipmentAdapter);

        holder.recycler_instructions_ingredients.setHasFixedSize(true);
        holder.recycler_instructions_ingredients.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        InstructionStepIngredientsAdapter ingredientsAdapter = new InstructionStepIngredientsAdapter(context, list.get(position).getIngredients());
        holder.recycler_instructions_ingredients.setAdapter(ingredientsAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class StepsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_instruction_step_title, textView_instruction_step_number;
    RecyclerView recycler_instructions_equipments, recycler_instructions_ingredients;
    public StepsViewHolder(@NonNull View itemView) {
        super(itemView);
        recycler_instructions_ingredients = itemView.findViewById(R.id.recycler_instructions_ingredients);
        recycler_instructions_equipments = itemView.findViewById(R.id.recycler_instructions_equipments);
        textView_instruction_step_title = itemView.findViewById(R.id.textView_instruction_step_title);
        textView_instruction_step_number = itemView.findViewById(R.id.textView_instruction_step_number);
    }
}
