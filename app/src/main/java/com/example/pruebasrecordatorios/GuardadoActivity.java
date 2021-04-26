package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**no se usa*/
public class GuardadoActivity extends AppCompatActivity {

    Button btnInicioPruebas2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardado);
        init();
        listeners();
    }

    private void listeners() {
        btnInicioPruebas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuardadoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btnInicioPruebas2 = (Button) findViewById(R.id.btnInicioPruebas2);
    }
}