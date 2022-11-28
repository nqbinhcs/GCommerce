package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class FullPackageDetailActivity extends AppCompatActivity {

    ImageView backArrow;
    RecyclerView productRecView;
    ProductAdapter productAdapter;
    List<Product> listProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_package_detail);

        addBackEvent();
        createListProduct();
    }

    private void addBackEvent() {
        backArrow = (ImageView) findViewById(R.id.back_arrow_full_package_detail);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullPackageDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createListProduct() {
        productRecView = findViewById(R.id.recycle_full_package_detail);
        productRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        listProduct.add(new Product("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300", "Hũ nước mắm", 20000, 1, "Shopê"));
        listProduct.add(new Product("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300", "Hũ nước mắm2", 20000, 1, "Shopê"));
        listProduct.add(new Product("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300", "Hũ nước mắm3", 20000, 1, "Shopê"));
        listProduct.add(new Product("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300", "Hũ nước mắm4", 20000, 1, "Shopê"));
        listProduct.add(new Product("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300", "Hũ nước mắm4", 20000, 1, "Shopê"));
        listProduct.add(new Product("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300", "Hũ nước mắm4", 20000, 1, "Shopê"));
        listProduct.add(new Product("https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300", "Hũ nước mắm4", 20000, 1, "Shopê"));

        productAdapter = new ProductAdapter(this, listProduct);
        productRecView.setAdapter(productAdapter);
    }
}