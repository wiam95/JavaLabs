package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;



public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    static ImageButton mImageButton;


    EditText name, email;
    ImageButton camera;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        view = findViewById(R.id.profile_layout);

        name = (EditText)findViewById(R.id.enterName);
        email = (EditText)findViewById(R.id.enterEmail);
        //camera = (ImageButton)findViewById(R.id.camera);
        mImageButton = (ImageButton)findViewById(R.id.camera);


        //Next three lines are to obtain email from previous page
        Intent fromMain = getIntent();
        String mail = fromMain.getStringExtra("EMAIL");
        email.setText(mail);


        //Runs the camera once clicked on
        mImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            //Calls method given by prof in lab
            dispatchTakePictureIntent();


            }
        });


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }
}
