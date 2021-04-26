package com.example.pruebasrecordatorios.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseTareas {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseTareas() {
    }

    /**
     *
     * @param iD
     * @param nombre
     */
    public ResponseTareas(String iD, String nombre) {
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

    public ResponseTareas withID(String iD) {
        this.iD = iD;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ResponseTareas withNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

}