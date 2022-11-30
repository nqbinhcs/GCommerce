package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.e_commerce.Adapter.FoodAdapter;
import com.example.e_commerce.Detection.customview.OverlayView;
import com.example.e_commerce.Detection.env.Logger;
import com.example.e_commerce.Detection.env.Utils;
import com.example.e_commerce.Detection.tflite.Classifier;
import com.example.e_commerce.Detection.tflite.YoloV5Classifier;
import com.example.e_commerce.Detection.tracking.MultiBoxTracker;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    private static final int REQUEST_ID_VIDEO_CAPTURE = 101;
    private Button buttonImage;
    private Button buttonVideo;

    private VideoView videoView;
    private ImageView imageView;
    private boolean cameraPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getCameraPermission();
        updateUI();
    }

    private void captureImage() {
        if (cameraPermissionGranted) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
        }
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_ID_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        cameraPermissionGranted = false;
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateUI();
    }

    private void updateUI() {
        if (cameraPermissionGranted) {
            captureImage();
        }
    }

    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap cropBitmap = Utils.processBitmap(bitmap, TF_OD_API_INPUT_SIZE);
                searchText = handleResult(cropBitmap);
                Intent intentSearch = new Intent(CameraActivity.this, SearchActivity.class);
                intentSearch.putExtra("Search text", searchText);
                startActivity(intentSearch);
            }
            else if (resultCode == RESULT_CANCELED)
            {
                startActivity(new Intent(CameraActivity.this, HomeActivity.class));
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            }
            else
            {
                startActivity(new Intent(CameraActivity.this, HomeActivity.class));
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static final Logger LOGGER = new Logger();

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;

    public static final int TF_OD_API_INPUT_SIZE = 416;

    private static final boolean TF_OD_API_IS_QUANTIZED = false;

    private static final String TF_OD_API_MODEL_FILE = "yolov5.tflite";

    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/customclasses.txt";

    // Minimum detection confidence to track a detection.
    private static final boolean MAINTAIN_ASPECT = true;
    private Integer sensorOrientation = 90;

    private Classifier detector;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;
    private MultiBoxTracker tracker;
    private OverlayView trackingOverlay;

    protected int previewWidth = 0;
    protected int previewHeight = 0;

    private String searchText;



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100)
//        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//           // Bitmap bitmap = Utils.getBitmapFromAsset(CameraActivity.this, "apple.jpg");
//            Bitmap cropBitmap = Utils.processBitmap(bitmap, TF_OD_API_INPUT_SIZE);
//            searchText = handleResult(cropBitmap);
//            Intent intentSearch = new Intent(CameraActivity.this, SearchActivity.class);
//            intentSearch.putExtra("Search text", searchText);
//            startActivity(intentSearch);
//        }
//    }



    private String handleResult(Bitmap cropBitmap) {

        try {
            detector =
                    YoloV5Classifier.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_IS_QUANTIZED,
                            TF_OD_API_INPUT_SIZE);

        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }

        final List<Classifier.Recognition> results = detector.recognizeImage(cropBitmap);


        String object = "";
        float max_conf = MINIMUM_CONFIDENCE_TF_OD_API;

        for (final Classifier.Recognition result : results) {

            final RectF location = result.getLocation();
            float conf = result.getConfidence();
            if (location != null && conf > max_conf) {
                object = result.getTitle();
            }
        }

        if (object == "")
            return "apple";
        return object;
    }
}