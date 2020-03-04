package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherForecast extends AppCompatActivity {


    ImageView weatherImg;
    TextView currTemp, minTemp, maxTemp, UVRating;
    ProgressBar pb;

    int counter = 0;

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
        progBar(pb);

    }

    //Obtains the weather from the website
    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        String currTemp, minTemp, maxTemp, UVRating;
        Bitmap weatherImg;

        @Override
        protected String doInBackground(String... strings) {

            try {

                String link = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

                //create a URL object of what server to contact:
                URL url = new URL(link);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == 200) {

                    //wait for data:
                    InputStream response = urlConnection.getInputStream();

                    StringBuffer sb = new StringBuffer();
                    BufferedReader br = new BufferedReader(new InputStreamReader(response));

                    String line = br.readLine();

                    while(line != null) {
                        //Print out the line here before continuing (add this)
                        line = br.readLine();

                    }

                }

            }
            catch (Exception e)
            {

            }

            return "Done";
        }


        public void onProgressUpdate(Integer ... args)
        {

        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            Log.i("HTTP", fromDoInBackground);
        }


    }

    //Code to run the progressBar
    public void progBar(ProgressBar pb) {

        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);

                if(counter == 100)
                    t.cancel();

            }
        };

        t.schedule(tt, 0, 100);

    }


}
