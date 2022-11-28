package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailView, passwordView;
    boolean visibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = findViewById(R.id.Account);
        passwordView = findViewById(R.id.Password);


        passwordView.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (passwordView.getRight() - passwordView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!visibility)
                        {
                            visibility = true;
                            passwordView.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                        else
                        {
                            visibility = false;
                            passwordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        return true;
                    }
                }
                return false;
            }
        });


    }

    public void Login(View view) {
        String phone = emailView.getText().toString();
        String password = passwordView.getText().toString();
        TextView message = (TextView) findViewById(R.id.loginMessage);
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(phone, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, RatingActivity.class);
                            startActivity(intent);
                        } else {
                            message.setText("Email or password is invalid!");
                            message.setTextColor(Color.RED);
                        }
                    }
                });
        message.setText("Your login is processing, please wait...");
        message.setTextColor(Color.GREEN);
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