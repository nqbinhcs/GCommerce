package com.example.e_commerce.Activity;

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
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_commerce.Adapter.FoodAdapter;
import com.example.e_commerce.Detection.customview.OverlayView;
import com.example.e_commerce.Detection.env.Logger;
import com.example.e_commerce.Detection.env.Utils;
import com.example.e_commerce.Detection.tflite.Classifier;
import com.example.e_commerce.Detection.tflite.YoloV5Classifier;
import com.example.e_commerce.Detection.tracking.MultiBoxTracker;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private static final Logger LOGGER = new Logger();
    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;
    public static final int TF_OD_API_INPUT_SIZE = 416;
    private static final boolean TF_OD_API_IS_QUANTIZED = false;
    private static final String TF_OD_API_MODEL_FILE = "yolov5.tflite";
    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/customclasses.txt";

    private static final int CAMERA_REQUEST = 100;

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

    private Button cameraButton, detectButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[] { Manifest.permission.CAMERA }, CAMERA_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Log.d("CameraActivity", "success");
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Bitmap cropBitmap = Utils.processBitmap(bitmap, TF_OD_API_INPUT_SIZE);

            searchText = handleResult(cropBitmap);


            Intent intentSearch = new Intent(CameraActivity.this, SearchActivity.class);
            intentSearch.putExtra("Search text", searchText);
            startActivity(intentSearch);
        }
    }



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