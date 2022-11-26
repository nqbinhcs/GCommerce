package com.example.e_commerce.controller;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;

public class RegisterationActivity extends AppCompatActivity {

    Button signUp;
    TextView signIn;
    EditText account, password, confirmPassword;
    boolean visibility = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        signIn = findViewById(R.id.SignIn);
        signUp = findViewById(R.id.SignUp);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        account = findViewById(R.id.Account);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            //final int DRAWABLE_LEFT = 0;
            // final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            //final int DRAWABLE_BOTTOM = 3;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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


        confirmPassword.setOnTouchListener(new View.OnTouchListener() {
            //final int DRAWABLE_LEFT = 0;
            // final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            //final int DRAWABLE_BOTTOM = 3;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!visibility)
                        {
                            visibility = true;
                            confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                        else
                        {
                            visibility = false;
                            confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void createUser() {
        String userName = account.getText().toString();
        String userPassword = password.getText().toString();
        String userConfirmPassword = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userConfirmPassword)) {
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }


        if (userPassword.length() < 6)
        {
            Toast.makeText(this, "Password length must be greater than 6 letter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPassword.equals(userConfirmPassword))
        {
            Toast.makeText(this, "Confirm password must be the same with password!", Toast.LENGTH_SHORT).show();
            return;
        }

    }


    public void RegiterationBack(View view) {
        Intent backLoginIntent = new Intent(RegisterationActivity.this, LoginActivity.class);
        RegisterationActivity.this.startActivity(backLoginIntent);
    }

    public void SignIn(View view) {
        Intent signIn = new Intent(RegisterationActivity.this, LoginActivity.class);
        RegisterationActivity.this.startActivity(signIn);
    }

    public void Account(View view) {
    }

    public void Password(View view) {

    }

    public void ConfirmPassword(View view) {

    }
}