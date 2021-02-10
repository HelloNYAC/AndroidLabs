package com.cst2335.yue00015;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

//    private ImageButton mImageButton = findViewById(R.id.imgBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSharedPreferences("name", 20);
        Button loginButton = findViewById(R.id.loginbutton);
        EditText inputEmail = findViewById(R.id.Email_ph);
        EditText inputPasswd = findViewById(R.id.Password_ph);

        Intent nextPage = new Intent(this, ProfileActivity.class);
        loginButton.setOnClickListener(click ->
        {
            nextPage.putExtra("typed", inputEmail.getText());
            nextPage.putExtra("typed", inputPasswd.getText());
            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
