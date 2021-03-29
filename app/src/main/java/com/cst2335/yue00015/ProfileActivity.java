package com.cst2335.yue00015;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private ImageButton mImageButton;
    private EditText emailText;
    private Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(ACTIVITY_NAME, "In function" + "onCreate");

        setContentView(R.layout.activity_profile);

        chat = findViewById(R.id.chatbutton);
        Intent GoToChat = new Intent(ProfileActivity.this, ChatRoomActivity.class);
        chat.setOnClickListener(click -> {
            startActivity(GoToChat);
        });

        Button weather = findViewById(R.id.weatherButton);
        Intent CheckWeather = new Intent(ProfileActivity.this, WeatherForecast.class);
        weather.setOnClickListener(click -> {
            startActivity(CheckWeather);
        });

        Button toolbar = findViewById(R.id.toolbarBtn);
        Intent checkToolbar = new Intent(ProfileActivity.this, TestToolbar.class);
        toolbar.setOnClickListener(click -> {
            startActivity(checkToolbar);
        });

        mImageButton = findViewById(R.id.imgBtn);
        emailText = findViewById(R.id.enter_email);
        mImageButton.setOnClickListener(click -> dispatchTakePictureIntent());

        Intent fromMain = getIntent();
        emailText.setText(fromMain.getStringExtra("email"));
    }


    private void dispatchTakePictureIntent() {
        android.content.Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageButton = findViewById(R.id.imgBtn);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.e(ACTIVITY_NAME, "In function: " + "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e(ACTIVITY_NAME, "In function: " + "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e(ACTIVITY_NAME, "In function: " + "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.e(ACTIVITY_NAME, "In function: " + "onStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e(ACTIVITY_NAME, "In function: " + "onDestroy");
//    }

}



