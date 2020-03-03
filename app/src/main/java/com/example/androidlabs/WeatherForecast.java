package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WeatherForecast extends AppCompatActivity {


    ImageView weatherImg;
    TextView currTemp, minTemp, maxTemp, UVRating;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImg = (ImageView)findViewById(R.id.weatherImg);

        currTemp = (TextView)findViewById(R.id.currentTemp);
        minTemp = (TextView)findViewById(R.id.minTemp);
        maxTemp = (TextView)findViewById(R.id.maxTemp);
        UVRating = (TextView)findViewById(R.id.UVRating);

        pb = (ProgressBar)findViewById(R.id.progressBar);


        pb.setMax(100);




    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        String currTemp, minTemp, maxTemp, UVRating;
        Bitmap weatherImg;

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }


}
