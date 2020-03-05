package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherForecast extends AppCompatActivity {

    ArrayList<String> data = new ArrayList<String>();

    ImageView weatherImg;
    TextView currTemp, minTemp, maxTemp, UVRating;
    ProgressBar pb;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImg = (ImageView) findViewById(R.id.weatherImg);

        currTemp = (TextView)findViewById(R.id.currentTemp);
        minTemp = (TextView) findViewById(R.id.minTemp);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        UVRating = (TextView) findViewById(R.id.UVRating);
        pb = (ProgressBar) findViewById(R.id.progressBar);


        ForecastQuery weatherReq = new ForecastQuery();
        weatherReq.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

        pb.setMax(100);
        progBar(pb);

    }

    //Obtains the weather from the website
    class ForecastQuery extends AsyncTask<String, Integer, String> {

        String curr, min, max, UV;
        Bitmap weatherImg;

        @Override
        protected String doInBackground(String... strings) {

            try {

                String link = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

                //create a URL object of what server to contact:
                URL url = new URL(link);
                //URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");


                //From part 3, slide 20
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("Weather")) {
                            //If you get here, then you are pointing to a <Weather> start tag
                            //String outlook = xpp.getAttributeValue(null,    "outlook");
                            // String windy = xpp.getAttributeValue(null, "windy");
                            parameter = xpp.getAttributeValue(null, "icon");
                        } else if (xpp.getName().equals("Temperature")) {
                            xpp.next(); //move the pointer from the opening tag to the TEXT event
                            parameter = xpp.getText(); // this will return  20

                            curr = xpp.getAttributeValue(null, "value");
                            //data.add(curr);
                            min = xpp.getAttributeValue(null, "min");
                            //data.add(min);
                            max = xpp.getAttributeValue(null, "max");
                            //data.add(max);
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }

            return "Done";
        }


        public void onProgressUpdate(Integer... args) {
            //publishProgress(25);
            //publishProgress(50);
            //publishProgress(75);
        }

        //Type3
        public void onPostExecute(String fromDoInBackground) {
            Log.i("HTTP", fromDoInBackground);

            //data.add(curr);
            //currTemp.setText(data.get(0));
            //minTemp.setText(data.get(1));
            //maxTemp.setText(data.get(2));
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

                if (counter == 100)
                    t.cancel();

            }
        };

        t.schedule(tt, 0, 100);

    }


    public class WeatherDB extends SQLiteOpenHelper {

        protected final static String DATABASE_NAME = "ContactsDB";
        protected final static int VERSION_NUM = 1;

        public final static String TABLE_NAME = "WeatherDB";

        public final static String COL_1 = "NAME";
        public final static String COL_2 = "DATA";

        public WeatherDB(Context ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_1 + " text,"
                    + COL_2 + " text);");  // add or remove columns
        }

        //this function gets called if the database version on your device is lower than VERSION_NUM
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   //Drop the old table:
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            //Create the new table:
            onCreate(db);
        }

        //this function gets called if the database version on your device is higher than VERSION_NUM
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {   //Drop the old table:
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            //Create the new table:
            onCreate(db);
        }

        //Add data to the table
        public long insertMessage(String msg, int type) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COL_1, msg);
            contentValues.put(COL_2, type);

            long result = db.insert("WeatherDB", null, contentValues);

            return result;
        }

        public void AddData(String item) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_2, item);

        }

        public Cursor getData() {

            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor data = db.rawQuery(query, null);

            return data;
        }

    }
}
