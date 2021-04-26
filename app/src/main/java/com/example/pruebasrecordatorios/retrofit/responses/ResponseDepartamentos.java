package com.example.pruebasrecordatorios.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDepartamentos {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public ResponseDepartamentos() {
    }
    public ResponseDepartamentos(String iD, String nombre) {
        super();
        this.iD = iD;
        this.nombre = nombre;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public ResponseDepartamentos withID(String iD) {
        this.iD = iD;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ResponseDepartamentos withNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

}