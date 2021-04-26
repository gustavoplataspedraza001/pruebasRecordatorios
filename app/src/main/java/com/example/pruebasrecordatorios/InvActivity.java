package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
/** no se usa**/
public class InvActivity extends AppCompatActivity {

    TextView btn1,btn2;
    Button btnRedir;
    ImageButton btnScan;
    EditText txtInsertarTexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv);
        init();
        listeners();
    }

    private void listeners() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setText("12/03/2021");
                btn2.setText("");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setText("14/03/2021");
                btn1.setText("");
            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtInsertarTexto.setText("201");
            }
        });
        btnRedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvActivity.this, PruebaInventarioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btn1 = (TextView) findViewById(R.id.edtFechaInicial);
        btn2 = (TextView) findViewById(R.id.edtFechaFinal);
        btnScan = (ImageButton) findViewById(R.id.imgBtnEscanerInventario);
        txtInsertarTexto = (EditText) findViewById(R.id.edtInventarioVCodigo);
        btnRedir = (Button) findViewById(R.id.btnCargarInventario);
    }
}