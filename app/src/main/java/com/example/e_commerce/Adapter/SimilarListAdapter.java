package com.example.e_commerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Listener.RecipeClickListener;
import com.example.e_commerce.Model.SimilarRecipeResponse;
import com.example.e_commerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarListAdapter extends RecyclerView.Adapter<SimilarViewHolder>{

    Context context;
    List<SimilarRecipeResponse> list;
    RecipeClickListener listener;

    public SimilarListAdapter(Context context, List<SimilarRecipeResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarViewHolder holder, int position) {
        holder.textView_similar_title.setText(list.get(position).title);
        holder.textView_similar_title.setSelected(true);

        Log.d("SIMI", list.get(position).title);
        Log.d("SIMI", String.valueOf(list.get(position).servings));

        holder.textView_like.setText(String.valueOf(list.get(position).servings));
        holder.textView_time.setText(String.valueOf(list.get(position).readyInMinutes));
        Picasso.get().load("https://spoonacular.com/recipeImages/"+list.get(position).id+"-556x370."+list.get(position).imageType)
                .into(holder.imageView_similar);
        holder.similar_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class SimilarViewHolder extends RecyclerView.ViewHolder {
    CardView similar_container;
    TextView textView_similar_title, textView_like, textView_time;
    ImageView imageView_similar;
    public SimilarViewHolder(@NonNull View itemView) {
        super(itemView);
        similar_container = itemView.findViewById(R.id.similar_container);
        textView_similar_title = itemView.findViewById(R.id.textView_similar_title);
        textView_like = itemView.findViewById(R.id.textView_like_similar);
        textView_time = itemView.findViewById(R.id.textView_time_similar);
        imageView_similar = itemView.findViewById(R.id.imageView_similar);
    }
}
