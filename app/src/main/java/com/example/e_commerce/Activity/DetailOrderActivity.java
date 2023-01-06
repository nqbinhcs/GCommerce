package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailOrderActivity extends AppCompatActivity {
    TextView orderIdView, dateView, timeView, subTotalView, deliView, discountView, totalView;
    String inCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailorder);
        initView();

    }

    private void initView() {
        String userEmail = getIntent().getStringExtra("UserEmail");
        String orderId = getIntent().getStringExtra("OrderId");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String subTotal = getIntent().getStringExtra("subTotal");
        String disCount = getIntent().getStringExtra("disCount");
        String deliveryCharge = getIntent().getStringExtra("deliveryCharge");
        String total = getIntent().getStringExtra("total");

        inCart = getIntent().getStringExtra("type");

        ImageView backArrow = (ImageView) findViewById(R.id.back_arrow_detail_order);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inCart.equals("cart")) {
                    Intent intent = new Intent(DetailOrderActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });

        orderIdView = (TextView) findViewById(R.id.orderId);
        dateView = (TextView) findViewById(R.id.date);
        timeView = (TextView) findViewById(R.id.time);
        subTotalView = (TextView) findViewById(R.id.subtotal);
        deliView = (TextView) findViewById(R.id.deliverycharge);
        discountView = (TextView) findViewById(R.id.discount);
        totalView = (TextView) findViewById(R.id.total);

        orderIdView.setText("Order #" + orderId);
        dateView.setText(date);
        timeView.setText(time);
        subTotalView.setText("$" + subTotal);
        deliView.setText("$" + deliveryCharge);
        discountView.setText("$" + disCount);
        totalView.setText("$" + total);

        Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailOrderActivity.this, RatingActivity.class));
            }
        });

        Button showFullBtn = (Button) findViewById(R.id.showfull);
        showFullBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailOrderActivity.this, FullPackageDetailActivity.class);
                intent.putExtra("OrderId", orderId);
                startActivity(intent);
            }
        });

        Button contactBtn = (Button) findViewById(R.id.cottactbtn);
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailOrderActivity.this, ChatActivity.class);
                intent.putExtra("UserEmail", userEmail);
                startActivity(intent);
            }
        });

        if (!inCart.equals("cart")) {
            confirmBtn.setVisibility(View.GONE);
            //contactBtn.setVisibility(View.GONE);
        }

    }

}
