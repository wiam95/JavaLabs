package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    Button login;
    CheckBox cb;
    Switch sw;
    EditText email, password;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.main_layout);

        //Set up the buttons
        login = (Button)findViewById(R.id.Login);;
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);


        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("ReserveName", "");
        EditText typeField = findViewById(R.id.email);
        typeField.setText(savedString);

        //Click listener (once user clicks login)
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Goes to next page
                Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

                //Next line is to pass email to the next page
                goToProfile.putExtra("EMAIL", email.getText().toString());
                startActivity(goToProfile); //startActivityForResult(nextPage, result) if you expect data back

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Main Activity", "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Main Activity", "In onPause()");
        saveSharedPrefs(email.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //Saves the data to file
    private void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ReserveName", stringToSave);
        editor.commit();
    }
}
