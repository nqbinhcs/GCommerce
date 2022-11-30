package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityAddProductBinding;
import com.example.e_commerce.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    ActivityAddProductBinding binding;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    LocalCache localCache;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        addOnClickEvent();
    }

    private void initView() {
        localCache = new LocalCache(AddProductActivity.this, "Local cache");
        user = localCache.loadUser();
    }

    private void addOnClickEvent() {
        binding.layoutUploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        if (imageUri == null)
            return;

        if (binding.productCategory.getText().toString().equals("") ||
            binding.productName.getText().toString().equals("") ||
            binding.productDescription.getText().toString().equals("") ||
            binding.productCost.getText().toString().equals("")) {
                Toast.makeText(AddProductActivity.this, "Please fill all field to add product to your shop!",Toast.LENGTH_LONG).show();
                return;
        }

//        if (user.getShop_name() == null || user.getShop_name().length() == 0) {
//            Toast.makeText(AddProductActivity.this,"You should create your shop in your profile first!",Toast.LENGTH_LONG).show();
//            return;
//        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CHINA);
        Date now = new Date();
        String fileName = formatter.format(now) + "_" + user.getEmail();
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        binding.imageUpload.setImageURI(null);
                        binding.imageUpload.setVisibility(View.GONE);
                        binding.selectImageBtn.setVisibility(View.VISIBLE);
                        binding.textimage.setVisibility(View.VISIBLE);

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();

                        Map<String, String> productFirestore = new HashMap<>();
                        productFirestore.put("category", binding.productCategory.getText().toString());
                        productFirestore.put("name", binding.productName.getText().toString());
                        productFirestore.put("description", binding.productDescription.getText().toString());
                        productFirestore.put("cost", binding.productCost.getText().toString());
                        productFirestore.put("seller", user.getName());
                        productFirestore.put("email", user.getEmail());

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                productFirestore.put("imgUrl", uri.toString());
                                FirebaseFirestore.getInstance()
                                        .collection("Product")
                                        .add(productFirestore)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                if (progressDialog.isShowing())
                                                    progressDialog.dismiss();
                                                Toast.makeText(AddProductActivity.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                if (progressDialog.isShowing())
                                                    progressDialog.dismiss();
                                                Toast.makeText(AddProductActivity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            binding.selectImageBtn.setVisibility(View.GONE);
            binding.textimage.setVisibility(View.GONE);
            binding.imageUpload.setVisibility(View.VISIBLE);
            binding.imageUpload.setImageURI(imageUri);


        }
    }

    public void BackEvent(View view) {
        finish();
    }
}