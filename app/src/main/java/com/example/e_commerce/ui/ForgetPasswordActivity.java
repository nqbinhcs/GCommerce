package com.example.e_commerce.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_commerce.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    public void ForgotPasswordBack(View view) {
        Intent backLoginIntent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        ForgetPasswordActivity.this.startActivity(backLoginIntent);
    }
}