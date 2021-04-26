package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class PruebaCargaActivity extends AppCompatActivity {
    LottieAnimationView ltAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_carga);
        init();
    }

    public void ocultar(View v){
        //ltAnim.setVisibility(View.GONE);
        ltAnim.cancelAnimation();
    }
    private void init() {
        ltAnim = (LottieAnimationView) findViewById(R.id.ltAnim);
    }
}