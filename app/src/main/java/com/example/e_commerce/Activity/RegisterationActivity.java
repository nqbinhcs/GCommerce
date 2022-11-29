package com.example.e_commerce.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegisterationActivity extends AppCompatActivity {

    static final int REQUEST_GET_MAP_LOCATION = 0;

    Button signUp;
    TextView signIn, signUpMessageView, locationView;
    EditText account, password, confirmPassword, nameView;
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
        signUpMessageView = (TextView) findViewById(R.id.signUpMessage);
        nameView = (EditText) findViewById(R.id.name);
        locationView = (TextView) findViewById(R.id.location);

        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterationActivity.this, LocationQueryActivity.class);
//                startActivityForResult(intent, REQUEST_GET_MAP_LOCATION);
                startActivity(intent);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_MAP_LOCATION && resultCode == Activity.RESULT_OK) {
            double latitude = Double.parseDouble(data.getStringExtra("latitude"));
            double longitude = Double.parseDouble(data.getStringExtra("longitude"));
            // do something with B's return values
            Log.d("RegisAct", Double.toString(latitude));
            Log.d("RegisAct", Double.toString(longitude));
            locationView.setText('[' + Double.toString(latitude) + ',' + Double.toString(longitude) + ']');
        }
    }

    private void createUser() {
        String email = account.getText().toString();
        String userPassword = password.getText().toString();
        String userConfirmPassword = confirmPassword.getText().toString();
        String name = nameView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            signUpMessageView.setText("Email cannot be empty!");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            signUpMessageView.setText("Name cannot be empty!");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            signUpMessageView.setText("Email cannot be empty!");
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            signUpMessageView.setText("Password cannot be empty!");
            return;
        }

        if (TextUtils.isEmpty(userConfirmPassword)) {
            signUpMessageView.setText("Confirm password must be the same with password!");
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6)
        {
            signUpMessageView.setText("Confirm password must be the same with password!");
            Toast.makeText(this, "Password length must be greater than 6 letter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPassword.equals(userConfirmPassword))
        {
            signUpMessageView.setText("Confirm password must be the same with password!");
            return;
        }

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signUpMessageView.setText("Sign up successfully!");
                            signUpMessageView.setTextColor(Color.GREEN);
                        } else {
                            signUpMessageView.setText("Email is already exist!");
                            signUpMessageView.setTextColor(Color.RED);
                        }
                    }
                });
        signUpMessageView.setText("Your signup is processing, please wait...");
        signUpMessageView.setTextColor(Color.GREEN);
    }

    public void RegiterationBack(View view) {
        Intent backLoginIntent = new Intent(RegisterationActivity.this, LoginActivity.class);
        RegisterationActivity.this.startActivity(backLoginIntent);
    }

    public void SignIn(View view) {
        Intent signIn = new Intent(RegisterationActivity.this, LoginActivity.class);
        RegisterationActivity.this.startActivity(signIn);
    }
}