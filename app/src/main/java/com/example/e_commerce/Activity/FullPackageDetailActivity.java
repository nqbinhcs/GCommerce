package com.example.e_commerce.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;
import com.example.e_commerce.Adapter.FullPackageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FullPackageDetailActivity extends AppCompatActivity {

    ImageView backArrow;
    RecyclerView productRecView;
    FullPackageAdapter fullPackageAdapter;
    ArrayList<Food> listProduct = new ArrayList<>();
    String orderId;
    TextView subTotalView, totalView, deliView, discountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_package_detail);

        orderId = getIntent().getStringExtra("OrderId");
        initView();
        addBackEvent();
        createListProduct();
    }

    private void initView() {
        subTotalView = (TextView) findViewById(R.id.subtotal);
        totalView = (TextView) findViewById(R.id.total);
        deliView = (TextView) findViewById(R.id.deliverycharge);
        discountView = (TextView) findViewById(R.id.discount);
    }

    private void addBackEvent() {
        backArrow = (ImageView) findViewById(R.id.back_arrow_full_package_detail);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void createListProduct() {
        productRecView = findViewById(R.id.recycle_full_package_detail);
        productRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        FirebaseFirestore.getInstance()
                .collection("Order")
                .whereEqualTo("id", orderId)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gson gson = new Gson();
                                ArrayList<String> stringList = gson.fromJson(document.getString("food"), ArrayList.class);
                                for (String string : stringList) {
                                    listProduct.add(gson.fromJson(string, Food.class));
                                }
                                double total = Double.valueOf(document.getString("subTotal")) +
                                        Double.valueOf(document.getString("deliveryCharge")) -
                                        Double.valueOf(document.getString("disCount"));
                                subTotalView.setText("$" + document.getString("subTotal"));
                                deliView.setText("$" + document.getString("deliveryCharge"));
                                discountView.setText("$" + document.getString("disCount"));
                                totalView.setText("$" + Double.toString(total));
                                fullPackageAdapter = new FullPackageAdapter(FullPackageDetailActivity.this, listProduct);
                                productRecView.setAdapter(fullPackageAdapter);
                            }
                        } else {
                            Toast.makeText(FullPackageDetailActivity.this, "Error to query order from database", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}