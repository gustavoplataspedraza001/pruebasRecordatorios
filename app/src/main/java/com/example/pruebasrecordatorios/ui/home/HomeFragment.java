package com.example.pruebasrecordatorios.ui.home;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pruebasrecordatorios.R;
import com.example.pruebasrecordatorios.checklistCierre.DepartamentosActivity;
import com.example.pruebasrecordatorios.retrofit.constantes;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {
    Button btnChecklistCierre;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        listeners();
        return view;
    }

    private void listeners() {
        btnChecklistCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redireccionC(v);
            }
        });
    }


    public void redireccionC(View v) {
        String lat ="";
        String lon = "";
        int tolerancia = 0;
        switch (constantes.sucursal){
            case ("1"):
                lat = "24.866480";
                lon = "-99.573220";
                tolerancia = 250;
                break;
            case("2"):
                lat = "24.85799393162605";
                lon ="-99.56197103070741";
                tolerancia  = 250;
                break;
            case("3"):
                lat = "24.857111045966377";
                lon ="-99.53911145741185";
                tolerancia = 250;
                break;
            case("4"):
                lat = "25.275262699127797";
                lon = "-100.0179759094733";
                tolerancia = 250;
                break;
            case("5"):
                lat = "24.866827940989218";
                lon= "-99.5584868016967";
                tolerancia = 250;
                break;
        }
        double resultado = calcularDistancia(Double.parseDouble(lat),Double.parseDouble(lon));
        if (resultado <= tolerancia){;
            Intent intent = new Intent(v.getContext(), DepartamentosActivity.class);
            startActivityForResult(intent, 0);
        }else if(resultado > tolerancia /*|| resultado < 0*/){
            Toast.makeText(getContext(), "Distancia excedida o incorrecta...", Toast.LENGTH_LONG).show();
        }
    }

    private double calcularDistancia(double latitud,double longitud){
        Location locationA = new Location("punto A");
        locationA.setLatitude(Double.parseDouble(constantes.LATITUD));
        locationA.setLongitude(Double.parseDouble(constantes.LONGITUD));
        Location locationB = new Location("punto B");
        locationB.setLatitude(latitud);
        locationB.setLongitude(longitud);
        float distancia = locationA.distanceTo(locationB);
        return distancia;
    }

    private void init(View view) {
        btnChecklistCierre = (Button) view.findViewById(R.id.btnChecklistCierre);
    }
}