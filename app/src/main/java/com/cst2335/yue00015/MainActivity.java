package com.cst2335.yue00015;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private EditText inputEmail;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginbutton);
        inputEmail = findViewById(R.id.Email_ph);
        pref = getSharedPreferences("email", Context.MODE_PRIVATE);
        String emaildef = pref.getString("email", "");
        inputEmail.setText(emaildef);

        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        loginButton.setOnClickListener(click ->
        {
            goToProfile.putExtra("email", inputEmail.getText().toString());
            startActivity(goToProfile);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pref = getSharedPreferences("email", Context.MODE_PRIVATE);
        SharedPreferences.Editor editEmail = pref.edit();
        String emailText = inputEmail.getText().toString();
        editEmail.putString("email", emailText);
        editEmail.commit();
    }
}

