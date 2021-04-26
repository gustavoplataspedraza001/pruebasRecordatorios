package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pruebasrecordatorios.SqlLm.SqliteUsuarios;
import com.example.pruebasrecordatorios.retrofit.ClienteConexion;
import com.example.pruebasrecordatorios.retrofit.ServicioPrueba;
import com.example.pruebasrecordatorios.retrofit.constantes;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseLogin;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class LoadingActivity extends AppCompatActivity {


    ClienteConexion clienteConexion;
    ServicioPrueba servicioPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initRetrofit();
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        SqliteUsuarios sqliteUsuarios = new SqliteUsuarios(getApplicationContext());
        Cursor respuesta = sqliteUsuarios.getEveryone();
        String usuario = "";
        String pass = "";
        while (respuesta.moveToNext()){
            usuario = respuesta.getString(1);
            pass = respuesta.getString(2);
        }
            if (!usuario.isEmpty() && !pass.isEmpty()){
                peticionRetrofit(usuario,pass);
            }else{
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                Activity activity = LoadingActivity.this;
                activity.finish();
            }
    }

    private void initRetrofit() {
        clienteConexion = ClienteConexion.getInstancia();
        servicioPrueba = clienteConexion.getServicioPrueba();
    }

    private void peticionRetrofit(String usuario,String pass){
        Call<List<ResponseLogin>> responsePeticionCall = servicioPrueba.peticionLogin(usuario, pass);
        responsePeticionCall.enqueue(new Callback<List<ResponseLogin>>() {
            @Override
            public void onResponse(Call<List<ResponseLogin>> call, retrofit2.Response<List<ResponseLogin>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).getAutenticado().equals("SI")) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoadingActivity.this);
                        Intent intent = new Intent(LoadingActivity.this, DrawerActivity.class);
                        intent.putExtra("idUsuario", response.body().get(0).getId());
                        intent.putExtra("nombrePersona", response.body().get(0).getNombrePersona());
                        intent.putExtra("nombreUsuario", response.body().get(0).getNombreUsuario());
                        intent.putExtra("sucursal", response.body().get(0).getIdSede());
                        intent.putExtra("tipoLogin","guardado");
                        String passCifrado = "";
                        try {
                            //passCifrado = ClassCypher.cifrar(pass);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        intent.putExtra("passCifrado", passCifrado);
                        startActivity(intent, options.toBundle());
                        finish();
                    } else {
                        Toasty.error(LoadingActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.error(LoadingActivity.this, "Error inesperado" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseLogin>> call, Throwable t) {
                Toasty.error(LoadingActivity.this,"Error en la petición",Toasty.LENGTH_LONG).show();
            }
        });
    }

}