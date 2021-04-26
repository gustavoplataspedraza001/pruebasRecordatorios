package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class VerificadorActivity extends AppCompatActivity {
    ImageView btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificador);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        listeners();
    }

    private void listeners() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn2.getVisibility() == View.VISIBLE) {
                    Intent intent = new Intent(VerificadorActivity.this, ScannerActivity.class);
                    startActivity(intent);
                }else{
                    btn2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void init() {
        btn1 = (ImageView) findViewById(R.id.imageViewCamera);
        btn2 = (ImageView) findViewById(R.id.imageViewTexto);
    }
}