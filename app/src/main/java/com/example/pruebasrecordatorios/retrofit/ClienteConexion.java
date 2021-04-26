package com.example.pruebasrecordatorios.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteConexion {
    private static ClienteConexion instancia = null;
    private ServicioPrueba servicioPrueba;
    private Retrofit retrofit;

    public ClienteConexion(){
        retrofit = new Retrofit.Builder()
                .baseUrl(constantes.API_CONEXION_LM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicioPrueba = retrofit.create(ServicioPrueba.class);
    }
    public static ClienteConexion getInstancia(){
        if (instancia == null){
            instancia = new ClienteConexion();
        }
        return instancia;
    }
    public ServicioPrueba getServicioPrueba(){
        return servicioPrueba;
    }
}
