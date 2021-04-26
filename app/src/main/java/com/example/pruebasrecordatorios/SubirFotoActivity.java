package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class SubirFotoActivity extends AppCompatActivity {
    //"http://200.1.1.178/sysMision/webService/mEvidenciaCierre/guardar_evidencia.php"
    Button btnFotoPruebas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);
        init();
        listeners();
    }

    private void listeners() {
        btnFotoPruebas.setOnClickListener(view->{
            Toast.makeText(this, "funciona", Toast.LENGTH_SHORT).show();
        });
    }

    private void init() {
        btnFotoPruebas = (Button) findViewById(R.id.btnFotoPruebas);
    }
}