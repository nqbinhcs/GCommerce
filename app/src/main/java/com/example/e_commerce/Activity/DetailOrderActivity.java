package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;

public class DetailOrderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailorder);

        ImageView backArrow = (ImageView) findViewById(R.id.back_arrow_detail_order);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
