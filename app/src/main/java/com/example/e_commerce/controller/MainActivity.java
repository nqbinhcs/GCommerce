package com.example.e_commerce.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_commerce.R;
import com.example.e_commerce.model.FirebaseDB;

public class MainActivity extends AppCompatActivity {
//    FirebaseDB db = FirebaseDB.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_activitymain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailOrderActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button_activitymain2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FullPackageDetailActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button_activitymain3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RatingActivity.class);
                startActivity(intent);
            }
        });

        Button button4 = (Button) findViewById(R.id.button_activitymain4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button button5 = (Button) findViewById(R.id.button_activitymain5);
        button5.setText("Navigate to forget password");
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        Button button6 = (Button) findViewById(R.id.button_activitymain6);
        button6.setText("Navigate to register");
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterationActivity.class);
                startActivity(intent);
            }
        });

        Button button7 = (Button) findViewById(R.id.button_activitymain7);
        button7.setText("Navigate to verification");
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                startActivity(intent);
            }
        });

        Button button8 = (Button) findViewById(R.id.button_activitymain8);
        button8.setText("Navigate to setting");
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}