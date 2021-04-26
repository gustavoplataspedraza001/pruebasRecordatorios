package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class VerPDFActivity extends AppCompatActivity {

    private PDFView pdfView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_p_d_f);
        pdfView = (PDFView) findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Toast.makeText(this, "Si", Toast.LENGTH_SHORT).show();
            file = new File(bundle.getString("path",""));
        }
        pdfView.fromFile(file)
        .enableSwipe(true)
        .swipeHorizontal(false)
        .enableDoubletap(true)
        .enableAntialiasing(true)
        .load();
    }
}