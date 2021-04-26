package com.example.pruebasrecordatorios.retrofit;

import com.example.pruebasrecordatorios.retrofit.responses.ResponseDepartamentos;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseLogin;
import com.example.pruebasrecordatorios.retrofit.responses.ResponsePeticion;
import com.example.pruebasrecordatorios.retrofit.responses.ResponsePeticionInventarios;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseTareas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface ServicioPrueba {

    @FormUrlEncoded
    @POST("webService/verificar_precio.php")
    Call<List<ResponsePeticion>> peticion(@Field("p_codigo") String p_codigo,
                                          @Field("sucursal") String sucursal);

    @FormUrlEncoded
    @POST("webService/login.php")
    Call<List<ResponseLogin>> peticionLogin(@Field("usuario") String usuario,
                                            @Field("pass") String pass);
    @FormUrlEncoded
    @POST("webService/veridicadorInventarios.php")
    Call<List<ResponsePeticionInventarios>> peticionInventarios(@Field("artc_articulo") String artc_articulo,
                                                                @Field("fecha_inicio") String fecha_inicio,
                                                                @Field("fecha_fin") String fecha_fin,
                                                                @Field("sucursal") String sucursal);
    @Multipart
    @POST("webService/mEvidenciaCierre/guardar_evidencia.php")
    Call<ResponsePeticion> peticionSubirFoto();

    @FormUrlEncoded
    @POST("webService/consulta_departamento.php")
    Call<List<ResponseDepartamentos>> peticionDepartamentos(@Field("sucursal")String sucursal);

    @FormUrlEncoded
    @POST("webService/listar_tareas.php")
    Call<List<ResponseTareas>> peticionTareas(@Field("id_depto")String id_depto,
                                             @Field("sucursal")String sucursal);
}
