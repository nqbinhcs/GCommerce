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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterationActivity extends AppCompatActivity {

    Button signUp;
    TextView signIn, signUpMessageView;
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
                    if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - password.getPaddingRight())) {
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
                    if(event.getRawX() >= (confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - confirmPassword.getPaddingRight())) {
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
                            // Add user to firestore
                            Map<String, String> userFirestore = new HashMap<>();
                            userFirestore.put("email", email);
                            userFirestore.put("name", name);
                            userFirestore.put("latitude", "0");
                            userFirestore.put("longitude", "0");
                            userFirestore.put("shop_name", "");
                            FirebaseFirestore.getInstance()
                                    .collection("User")
                                    .add(userFirestore)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("SignupActivity", "Add user " + email + " successfully");
                                            Toast.makeText(RegisterationActivity.this,"Successfully sign up",Toast.LENGTH_SHORT).show();
                                            signUpMessageView.setText("Sign up successfully!");
                                            signUpMessageView.setTextColor(Color.GREEN);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("SignupActivity", "Error adding user", e);
                                        }
                                    });
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