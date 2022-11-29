package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

public class SettingActivity extends AppCompatActivity {
    private TextView logOutBtn, nameView, emailView, editProfileView, myOrderView;
    private LocalCache localCache;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        addEventOnClick();
    }

    public void BackEvent(View view) {
        finish();
    }

    private void addEventOnClick() {
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localCache.deleteUser();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });
        editProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, EditProfileActivity.class));
            }
        });
    }

    private void initView() {
        localCache = new LocalCache(SettingActivity.this, "Local cache");
        logOutBtn = (TextView) findViewById(R.id.setting_logout);
        user = localCache.loadUser();
        nameView = (TextView) findViewById(R.id.setting_name);
        emailView = (TextView) findViewById(R.id.setting_email);
        editProfileView = (TextView) findViewById(R.id.setting_edit_profile);
        myOrderView = (TextView) findViewById(R.id.setting_my_orders);

        nameView.setText(user.getName());
        emailView.setText(user.getEmail());
    }


}
