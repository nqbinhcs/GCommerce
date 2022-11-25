package com.example.e_commerce.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import com.example.e_commerce.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void Login(View view) {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(loginIntent);
    }

    public void SignUp(View view) {
        Intent signupIntent = new Intent(LoginActivity.this, RegisterationActivity.class);
        LoginActivity.this.startActivity(signupIntent);
    }

    public void ForgotPassword(View view) {
        Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        LoginActivity.this.startActivity(forgotPasswordIntent);
    }
}