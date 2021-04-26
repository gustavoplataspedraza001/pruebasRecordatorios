package com.example.pruebasrecordatorios.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pruebasrecordatorios.MostrarActivity;
import com.example.pruebasrecordatorios.MostrarPrecioActivity;
import com.example.pruebasrecordatorios.PruebaInventarioActivity;
import com.example.pruebasrecordatorios.R;
import com.example.pruebasrecordatorios.ScannerActivity;
import com.example.pruebasrecordatorios.VerificadorActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class SlideshowFragment extends Fragment {

    ImageView btn1,btn2;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        init(view);
        listeners();
        return view;
    }

    private void listeners() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ScannerActivity.class);
                    intent.putExtra("accion","no");
                    startActivity(intent);
            }
        });
        btn2.setOnClickListener(view ->{
            dialogo();
        });
    }

    private void init(View view) {
        btn1 = (ImageView) view.findViewById(R.id.imageViewCamera);
        btn2 = (ImageView) view.findViewById(R.id.imageViewTexto);
    }
    public void dialogo(){
        LayoutInflater inflater = SlideshowFragment.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogmostrar,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("      Ingresar código\n      manualmente");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.descarga);
        builder.setView(view).setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText) view.findViewById(R.id.txtNumeroCodigoMostrar);
                String validador = editText.getText().toString();
                if (validador.equals("")){
                    Toasty.warning(getContext(), "El campo de código está vacío", Toast.LENGTH_SHORT).show();
                    dialogo();
                }else{
                    Intent intent = new Intent(getContext(), MostrarPrecioActivity.class);
                    intent.putExtra("intentData",editText.getText().toString());
                    startActivityForResult(intent,0);
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}