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

import com.example.e_commerce.Model.InstructionsResponse;
import com.example.e_commerce.R;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionViewHolder>{
    Context context;
    List<InstructionsResponse> list;

    public InstructionsAdapter(Context context, List<InstructionsResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        holder.textView_instruction_name.setText(list.get(position).name);
        holder.recycler_instruction_steps.setHasFixedSize(true);
        holder.recycler_instruction_steps.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        InstructionStepsAdapter instructionStepsAdapter = new InstructionStepsAdapter(context, list.get(position).steps);
        holder.recycler_instruction_steps.setAdapter(instructionStepsAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionViewHolder extends RecyclerView.ViewHolder {
    TextView textView_instruction_name;
    RecyclerView recycler_instruction_steps;
    public InstructionViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instruction_name = itemView.findViewById(R.id.textView_instruction_name);
        recycler_instruction_steps = itemView.findViewById(R.id.recycler_instruction_steps);
    }
}
