package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    static final int REQUEST_GET_MAP_LOCATION = 3112;

    TextView emailView, locationView;
    EditText shopNameView, nameView;
    LocalCache localCache;
    User user;
    Button saveChangesBtn;

    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initView();
        addEventOnClick();
    }

    public void BackEvent(View view) {
        finish();
    }

    private void addEventOnClick() {
        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, LocationQueryActivity.class);
                startActivityForResult(intent, REQUEST_GET_MAP_LOCATION);
            }
        });

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setShop_name(shopNameView.getText().toString());
                user.setLat(latitude);
                user.setLng(longitude);
                user.setName(nameView.getText().toString());

                FirebaseFirestore.getInstance()
                        .collection("User")
                        .whereEqualTo("email", user.getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        document.getReference()
                                                .update("name", user.getName(),
                                                        "latitude", Double.toString(user.getLat()),
                                                        "longitude", Double.toString(user.getLng()),
                                                        "shop_name", user.getShop_name())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            localCache.deleteUser();
                                                            localCache.addUser(user);
                                                            Toast.makeText(EditProfileActivity.this, "Update profile successfully!", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            Toast.makeText(EditProfileActivity.this, "Error to edit your profile, please try again!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    Toast.makeText(EditProfileActivity.this, "Error to edit your profile, please try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_MAP_LOCATION && resultCode == Activity.RESULT_OK) {
            latitude = Double.parseDouble(data.getStringExtra("latitude"));
            longitude = Double.parseDouble(data.getStringExtra("longitude"));
            locationView.setText('[' + Double.toString(latitude) + ',' + Double.toString(longitude) + ']');
        }
    }

    private void initView() {
        // get View
        emailView = (TextView) findViewById(R.id.edit_email);
        nameView = (EditText) findViewById(R.id.edit_name);
        locationView = (TextView) findViewById(R.id.edit_location);
        shopNameView = (EditText) findViewById(R.id.edit_shopname);
        saveChangesBtn = (Button) findViewById(R.id.saveChanges);

        // set text
        localCache = new LocalCache(EditProfileActivity.this, "Local cache");
        user = localCache.loadUser();
        emailView.setText(user.getEmail());
        nameView.setText(user.getName());
        locationView.setText('[' + Double.toString(user.getLat()) + ',' + Double.toString(user.getLng()) + ']');
        shopNameView.setText(user.getShop_name());
    }
}