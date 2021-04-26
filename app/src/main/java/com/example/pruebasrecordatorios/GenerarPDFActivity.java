package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**no se usa**/
public class GenerarPDFActivity extends AppCompatActivity {

    Button hola;
    private String[] header = {"Id", "Nombre", "Apellido"};
    private String shortText = "Hola";
    private String longText = "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit, sed do eiusmod tempor " +
            "incididunt ut labore et dolore magna aliqua. ";
    GenerarPDF generarPDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_p_d_f);
        init();
        extras();
        setListeners();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    20);
        }
    }

    public void pdfView (){
        generarPDF.viewPDF();
    }
    private ArrayList<String[]> getClient(){
        ArrayList<String[]> rows = new ArrayList<>();

        rows.add(new String[]{"1","nombre","apellido"});
        rows.add(new String[]{"2","nombre2","apellido2"});
        rows.add(new String[]{"3","nombre3","apellido3"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        rows.add(new String[]{"4","nombre4","apellido4"});
        return rows;
    }
    private void setListeners() {
        hola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfView();
            }
        });
    }

    private void extras() {
        String pruebasTexto = getIntent().getExtras().getString("pruebas");
        hola.setText(pruebasTexto);

        /***/
        generarPDF = new GenerarPDF(getApplicationContext());
        generarPDF.openDocument();
        generarPDF.addMetaData("Clientes", "Ventas", "Marines");
        generarPDF.addTitles("Tienda", "Clientes", "1/03/20021");
        generarPDF.addParagraph(shortText);
        generarPDF.addParagraph(longText);
        generarPDF.createTable(header,getClient());
        generarPDF.closeDocument();
    }

    private void init() {
        hola = (Button) findViewById(R.id.btnVer);
    }
}