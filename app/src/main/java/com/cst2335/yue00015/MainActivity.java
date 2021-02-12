package com.cst2335.yue00015;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private EditText enterEmail;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginbutton);
        enterEmail = findViewById(R.id.Email_ph);
        pref = getSharedPreferences("email", Context.MODE_PRIVATE);
        String emaildef = pref.getString("email", "");
        enterEmail.setText(emaildef);

        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        loginButton.setOnClickListener(click ->
        {
            goToProfile.putExtra("email", enterEmail.getText().toString());
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
        SharedPreferences.Editor editorEmail = pref.edit();
        String emailText = enterEmail.getText().toString();
        editorEmail.putString("email", emailText);
        editorEmail.commit();
    }
}

