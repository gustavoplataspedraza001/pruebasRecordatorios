package com.example.pruebasrecordatorios;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
/** no se usa */
public class MessageActivity extends AppCompatActivity {
    private String text;
    EditText et_message;
    Button btnEnviar;
    Uri imagenUri;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
        listeners();
    }

    private void listeners() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = et_message.getText().toString();
                view = v;
                abrirGaleria();
            }
        });
    }

    private void init() {
        et_message = (EditText) findViewById(R.id.et_message);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
    }
    //seleccionar archivos, cualquier tipo
    // para enviar archivos en especifico sería así application/vnd.openxmlformats-officedocument.spreadsheetml.sheet para excel
    // tambien para pdf sería application/pdf

    //                intent.setDataAndType(uri, "application/vnd.ms-excel");
    //                intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    public void abrirGaleria(){
        Intent galeria = new Intent(Intent.ACTION_GET_CONTENT).setType("*/*");
        String[] mimetypes = {"image/*","application/pdf", "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
        galeria.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(galeria,10);
    }
    /*public void abrirGaleria(){
        Intent galeria = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria,10);
    }*/

    //preparar para enviar el arhcivo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 10){
            imagenUri = data.getData();
            //Toast.makeText(this, "Uri: " + imagenUri.toString(), Toast.LENGTH_LONG).show();
            captura(view);
            Log.e("ZZZ","Archivo///formato///extención: "+imagenUri.toString()+"///"+ getContentResolver().getType(imagenUri));
        }
    }
    //enviar el archivo
    public void captura(View view){
        /*PackageManager pm=getPackageManager();
        try {
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");

        PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
        //Check if package exists or not. If not then code
        //in catch block will be called
        waIntent.setPackage("com.whatsapp");

        waIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(waIntent, "Compartir con:"));
        }catch (ActivityNotFoundException | PackageManager.NameNotFoundException ex){
            ex.printStackTrace();
            Toast.makeText(this, "No tiene Whatsapp instalado", Toast.LENGTH_SHORT).show();
        }*/
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.setPackage("com.whatsapp");

        intent.putExtra(Intent.EXTRA_STREAM,imagenUri);
        startActivity(intent);
    }
}