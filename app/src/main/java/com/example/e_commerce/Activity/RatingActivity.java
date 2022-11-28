package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends AppCompatActivity {
    private final static String[] RATING_DESCRIPTION = {"Very Bad", "Bad", "No problem", "Great", "Very good"};

    int currentRating = 0;
    List<ImageView> listStar = new ArrayList<>();
    TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        getView();
        addEventListener();
        addStarEvent();
    }

    private void addEventListener() {
        Button submitButton = (Button) findViewById(R.id.submit_rating);
        Button skipButton = (Button) findViewById(R.id.skip_rating);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText reviewView = (EditText) findViewById(R.id.review_rating);
                String review = reviewView.getText().toString();
                Log.d("Review", review);
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RatingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getView() {
        listStar.add((ImageView)findViewById(R.id.star1_rating));
        listStar.add((ImageView)findViewById(R.id.star2_rating));
        listStar.add((ImageView)findViewById(R.id.star3_rating));
        listStar.add((ImageView)findViewById(R.id.star4_rating));
        listStar.add((ImageView)findViewById(R.id.star5_rating));
        ratingText = (TextView) findViewById(R.id.text_rating);
    }

    private void reDrawRating() {
        for (int i = 0; i < listStar.size(); ++i)
            listStar.get(i).setImageResource(i <= currentRating ? R.drawable.ic_baseline_star_24_active : R.drawable.ic_baseline_star_24_inactive);
        ratingText.setText(RATING_DESCRIPTION[currentRating]);
    }

    private void addStarEvent() {
        listStar.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = 0;
                reDrawRating();
            }
        });
        listStar.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = 1;
                reDrawRating();
            }
        });
        listStar.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = 2;
                reDrawRating();
            }
        });
        listStar.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = 3;
                reDrawRating();
            }
        });
        listStar.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = 4;
                reDrawRating();
            }
        });
    }
}
