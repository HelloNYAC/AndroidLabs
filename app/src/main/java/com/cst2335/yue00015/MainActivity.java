package com.cst2335.yue00015;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button clickBtn;
    private Switch switchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        clickBtn = (Button)findViewById(R.id.btn_1);
        clickBtn.setOnClickListener((v) -> {
                Toast.makeText(this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show();
                });

        switchBtn = (Switch)findViewById(R.id.switch_1);
        switchBtn.setOnCheckedChangeListener((CompoundButton switchBtn, boolean b)-> {
            if(b == true){
                Snackbar
                        .make(switchBtn, getResources().getString(R.string.swc_msg1), Snackbar.LENGTH_SHORT)
                        .setAction("Undo", click->switchBtn.setChecked(!b))
                        .show();
            }else{
                Snackbar
                        .make(switchBtn, getResources().getString(R.string.swc_msg2), Snackbar.LENGTH_SHORT)
                        .setAction("Undo", click->switchBtn.setChecked(!b))
                        .show();
            }
        });

    }
}