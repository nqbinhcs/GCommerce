package com.example.e_commerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.Model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.productList = list;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
//        Glide.with(context).load(product.getImg_url()).into(holder.imageView);
        holder.shop.setText(productList.get(position).getShop());
        holder.cost.setText(productList.get(position).getCost());
        holder.name.setText(productList.get(position).getName());
        holder.amount.setText(productList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, shop, cost, amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_carditem);
            shop = itemView.findViewById(R.id.shop_carditem);
            cost = itemView.findViewById(R.id.cost_carditem);
            name = itemView.findViewById(R.id.name_carditem);
            amount = itemView.findViewById(R.id.number_carditem);
        }
    }
}
