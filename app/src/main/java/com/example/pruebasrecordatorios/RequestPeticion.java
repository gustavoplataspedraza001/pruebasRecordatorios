package com.example.pruebasrecordatorios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.FormUrlEncoded;


public class RequestPeticion {

    @SerializedName("p_codigo")
    @Expose
    private String pCodigo;
    @SerializedName("sucursal")
    @Expose
    private String sucursal;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestPeticion() {
    }

    /**
     *
     * @param pCodigo
     * @param sucursal
     */

    public RequestPeticion(String pCodigo, String sucursal) {
        super();
        this.pCodigo = pCodigo;
        this.sucursal = sucursal;
    }

    public String getPCodigo() {
        return pCodigo;
    }

    public void setPCodigo(String pCodigo) {
        this.pCodigo = pCodigo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

}