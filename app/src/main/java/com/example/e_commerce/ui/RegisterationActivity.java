package com.example.e_commerce.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_commerce.R;

public class RegisterationActivity extends AppCompatActivity {

    Button signUp;
    TextView signIn;
    EditText account, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        signIn = findViewById(R.id.SignIn);
        signUp = findViewById(R.id.SignUp);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        account = findViewById(R.id.Account);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterationActivity.this, LoginActivity.class));
            }
        });
    }

    public void registerationBack(View view) {
        Intent backLoginIntent = new Intent(RegisterationActivity.this, LoginActivity.class);
        RegisterationActivity.this.startActivity(backLoginIntent);
    }
}