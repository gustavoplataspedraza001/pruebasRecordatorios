package com.example.pruebasrecordatorios.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteConexionInventariado {
    private static ClienteConexionInventariado instancia = null;
    private ServicioPrueba servicioPrueba;
    private Retrofit retrofit;

    public ClienteConexionInventariado(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.MINUTES)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.MINUTES)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(constantes.API_CONEXION_LM)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        servicioPrueba = retrofit.create(ServicioPrueba.class);
    }
    public static ClienteConexionInventariado getInstancia(){
        if (instancia == null){
            instancia = new ClienteConexionInventariado();
        }
        return instancia;
    }
    public ServicioPrueba getServicioPrueba(){
        return servicioPrueba;
    }
}
