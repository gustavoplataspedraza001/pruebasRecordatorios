package com.example.pruebasrecordatorios.ui.nuevoFragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebasrecordatorios.PruebaInventarioActivity;
import com.example.pruebasrecordatorios.R;
import com.example.pruebasrecordatorios.ScannerActivity;
import com.example.pruebasrecordatorios.retrofit.constantes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


public class NuevoFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    TextView fechaInicio, fechaFinal;
    EditText textoCodigo;
    Button btnCargarInventario;
    String validarFecha = "";
    ImageButton btnEscaner;
    Spinner spinnerSucursales;
    SimpleDateFormat sdfInicio = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfFinal = new SimpleDateFormat("dd/MM/yyyy");
    Date sInicio;
    Date sFinal;
    private static String iniciado = "";
    private static String fin = "";
    private static final String[] sucursales = {"Diaz Ordaz","Arboledas","Villegas","Allende","La Petaca"};
    @Override
    public void onResume() {
        super.onResume();
        if (!constantes.BARCODE.equals("")) {
            textoCodigo.setText(constantes.BARCODE);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nuevo_fragment, container, false);
        init(view);
        listeners();
        extras();
        return view;
    }
    private void extras() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinnercustom,sucursales);
        adapter.setDropDownViewResource(R.layout.spinnercustom);
        spinnerSucursales.setAdapter(adapter);
    }

    private void listeners() {
        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarFecha = "inicio";
                showDatePicker();
            }
        });
        fechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarFecha = "final";
                showDatePicker();
            }
        });
        btnEscaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });
        btnCargarInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fechaInicio.getText().toString().equals("dd/mm/aaaa") || fechaFinal.getText().toString().equals("dd/mm/aaaa") ||   textoCodigo.getText().toString().equals("")){
                    Toasty.warning(getContext(), "Uno de los campos está vacío", Toast.LENGTH_SHORT).show();
                }else{
                    if (sInicio.getTime()>sFinal.getTime()) {
                        Toasty.error(getContext(), "Esta introduciendo las fechas incorrectamente", Toast.LENGTH_SHORT).show();
                        Drawable img = getContext().getResources().getDrawable(R.drawable.ic_error);
                        img.setBounds(0, 0, 60, 60);
                        fechaInicio.setCompoundDrawables(img, null, null, null);
                        fechaFinal.setCompoundDrawables(img, null, null, null);
                    }else{
                        Intent intent = new Intent(getContext(), PruebaInventarioActivity.class);

                        intent.putExtra("codigo", textoCodigo.getText().toString());
                        intent.putExtra("fechaInicio", iniciado);
                        intent.putExtra("fechaFin",fin);
                        intent.putExtra("fechaInicioM",fechaInicio.getText().toString());
                        intent.putExtra("fechaFinM",fechaFinal.getText().toString());
                        String sucursal ="";
                        if (spinnerSucursales.getSelectedItem() == "Diaz Ordaz"){
                            intent.putExtra("sucursal","1");
                            sucursal = "1";
                        }else if (spinnerSucursales.getSelectedItem() == "Arboledas") {
                            intent.putExtra("sucursal","2");
                        }else if (spinnerSucursales.getSelectedItem() == "Villegas") {
                            intent.putExtra("sucursal","3");
                        }else if (spinnerSucursales.getSelectedItem() == "Allende") {
                            intent.putExtra("sucursal","4");
                        }else if (spinnerSucursales.getSelectedItem() == "La Petaca") {
                            intent.putExtra("sucursal","5");
                        }
                        startActivity(intent);
                    }

                }
            }
        });
    }

    private void init(View view) {
        fechaInicio = (TextView) view.findViewById(R.id.edtFechaInicial);
        fechaFinal  = (TextView) view.findViewById(R.id.edtFechaFinal);
        textoCodigo = (EditText) view.findViewById(R.id.edtInventarioVCodigo);
        btnEscaner = (ImageButton) view.findViewById(R.id.imgBtnEscanerInventario);
        spinnerSucursales = (Spinner) view.findViewById(R.id.spinnerSucursales);
        btnCargarInventario = (Button) view.findViewById(R.id.btnCargarInventario);
    }

    private void escanear(){
        Intent intent = new Intent(getContext(), ScannerActivity.class);
        intent.putExtra("accion","verificadorInventario");
        startActivity(intent);
    }

    private void showDatePicker() {
        Calendar calendar = new GregorianCalendar(Locale.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get((Calendar.MONTH));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), NuevoFragment.this, year, month, day);
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if (validarFecha.equals("inicio")){
            fechaInicio.setText(day+"/"+(month+1)+"/"+year);
            iniciado = year+"-"+(month+1)+"-"+day;
            String fi = day+"/"+(month+1)+"/"+year;
            try {
                sInicio = sdfInicio.parse(fi);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(validarFecha.equals("final")){
            fechaFinal.setText(day+"/"+(month+1)+"/"+year);
            fin = year+"-"+(month+1)+"-"+day;
            String fe = day+"/"+(month+1)+"/"+year;
            try {
                sFinal = sdfFinal.parse(fe);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}