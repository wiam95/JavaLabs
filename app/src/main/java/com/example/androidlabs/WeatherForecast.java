package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

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

        String curr, min, max, UV;
        Bitmap weatherImg;

        @Override
        protected String doInBackground(String... strings) {

            try {

                String link = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

                //create a URL object of what server to contact:
                URL url = new URL(link);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");


                //From part 3, slide 20
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("Weather"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            String outlook = xpp.getAttributeValue(null,    "outlook");
                            String windy = xpp.getAttributeValue(null, "windy");
                            parameter = xpp.getAttributeValue(null, "icon"); //this will run for <Weather windy="paramter"  >
                        }

                        else if(xpp.getName().equals("AMessage"))
                        {
                            parameter = xpp.getAttributeValue(null, "message"); // this will run for <AMessage message="parameter" >
                        }
                        else if(xpp.getName().equals("Weather"))
                        {
                            parameter = xpp.getAttributeValue(null, "outlook"); //this will run for <Weather outlook="parameter"
                            parameter = xpp.getAttributeValue(null, "windy"); //this will run for <Weather windy="paramter"  >
                        }
                        else if(xpp.getName().equals("Temperature"))
                        {
                            xpp.next(); //move the pointer from the opening tag to the TEXT event
                            parameter = xpp.getText(); // this will return  20
                            curr = xpp.getAttributeValue(null, "value"); //this will run for <Weather windy="paramter"  >
                            //publishProgress(25);
                            //publishProgress(50);
                            //publishProgress(75);

                            min = xpp.getAttributeValue(null, "min"); //this will run for <Weather windy="paramter"  >
                            max = xpp.getAttributeValue(null, "max"); //this will run for <Weather windy="paramter"  >
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

            }
            catch (Exception e)
            {
                Log.e("Error", e.getMessage());
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


        //currTemp = curr;
        //minTemp = min;
        //maxTemp = max;

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
