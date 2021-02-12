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

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private ImageButton mImageButton;
    private EditText emailText;
    private ArrayList<String> list = new ArrayList<>(Arrays.asList());
//    private MyListAdapter myAdapter;
    private ListView myList;
    private Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        chat = findViewById(R.id.chatbutton);
        Intent GoToChat = new Intent(ProfileActivity.this, ChatRoomActivity.class);
        chat.setOnClickListener(click -> {
            startActivity(GoToChat);
        });
        Log.e(ACTIVITY_NAME, "In function" + "onCreate");
        setContentView(R.layout.activity_profile);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: " + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function: " + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: " + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function: " + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: " + "onDestroy");
    }

}


//        myList = findViewById(R.id.list_item);
//        myList.setAdapter(myAdapter);
        }

//        class MyListAdapter extends BaseAdapter {
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return list.get(position);
//            }
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//
//        }

//    }
