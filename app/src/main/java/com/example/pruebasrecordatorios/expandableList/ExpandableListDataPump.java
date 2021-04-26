package com.example.pruebasrecordatorios.expandableList;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.pruebasrecordatorios.DrawerActivity;
import com.example.pruebasrecordatorios.MainActivity;
import com.example.pruebasrecordatorios.SqlLm.SqliteUsuarios;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseLogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(Context context){

        HashMap<String,List<String>> expandableListDetail = new HashMap<String,List<String>>();
        List<String> dptos = new ArrayList<String>();
        dptos.add("Tortillería");
        dptos.add("Panadería 2");
        dptos.add("Carnicería 2");


        List<String> tortilleria = new ArrayList<String>();
        tortilleria.add("Maquina");
        tortilleria.add("Horno");
        tortilleria.add("Elemento");

        List<String> panaderia = new ArrayList<String>();
        panaderia.add("Brazil");
        panaderia.add("Spain");
        panaderia.add("Germany");
        panaderia.add("Netherlands");
        panaderia.add("Italy");

        List<String> carniceria = new ArrayList<String>();
        carniceria.add("Cortadora");
        carniceria.add("Equipo");
        carniceria.add("Botes");

        expandableListDetail.put(dptos.get(0), tortilleria);
        expandableListDetail.put(dptos.get(1),panaderia);
        expandableListDetail.put(dptos.get(2),carniceria);
        return expandableListDetail;
    }
}
