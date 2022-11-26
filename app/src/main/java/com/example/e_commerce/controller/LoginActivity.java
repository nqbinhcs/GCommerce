package com.example.e_commerce.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.widget.EditText;

import com.example.e_commerce.R;

public class LoginActivity extends AppCompatActivity {

    EditText account, password;
    boolean visibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = findViewById(R.id.Account);
        password = findViewById(R.id.Password);


        password.setOnTouchListener(new View.OnTouchListener() {
            //final int DRAWABLE_LEFT = 0;
            // final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            //final int DRAWABLE_BOTTOM = 3;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!visibility)
                        {
                            visibility = true;
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                        else
                        {
                            visibility = false;
                            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        return true;
                    }
                }
                return false;
            }
        });


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

    public void Account(View view) {
    }


    public void Password(View view) {

    }
}