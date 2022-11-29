package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

import org.w3c.dom.Text;

public class EditProfileActivity extends AppCompatActivity {
    static final int REQUEST_GET_MAP_LOCATION = 3112;

    TextView emailView, locationView;
    EditText shopNameView, nameView;
    LocalCache localCache;
    User user;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_MAP_LOCATION && resultCode == Activity.RESULT_OK) {
            double latitude = Double.parseDouble(data.getStringExtra("latitude"));
            double longitude = Double.parseDouble(data.getStringExtra("longitude"));
//            Log.d("RegisAct", Double.toString(latitude));
//            Log.d("RegisAct", Double.toString(longitude));
            locationView.setText('[' + Double.toString(latitude) + ',' + Double.toString(longitude) + ']');
        }
    }

    private void initView() {
        // get View
        emailView = (TextView) findViewById(R.id.edit_email);
        nameView = (EditText) findViewById(R.id.edit_name);
        locationView = (TextView) findViewById(R.id.edit_location);
        shopNameView = (EditText) findViewById(R.id.edit_shopname);

        // set text
        localCache = new LocalCache(EditProfileActivity.this, "Local cache");
        user = localCache.loadUser();
        emailView.setText(user.getEmail());
        nameView.setText(user.getName());
    }
}