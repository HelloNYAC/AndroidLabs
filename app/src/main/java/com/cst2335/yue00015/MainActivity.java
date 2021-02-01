package com.cst2335.yue00015;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button clickBtn;
    private Switch switchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        clickBtn = (Button)findViewById(R.id.btn_1);
        clickBtn.setOnClickListener((v) -> {
                Toast.makeText(this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show();
                });

        switchbtn = (Switch)findViewById(R.id.switch_1);
        switchbtn.setOnCheckedChangeListener((c, f)-> {
            Snackbar.make(switchbtn, getResources().getString(R.string.swc_msg), Snackbar.LENGTH_SHORT).show();
        });
    }
}