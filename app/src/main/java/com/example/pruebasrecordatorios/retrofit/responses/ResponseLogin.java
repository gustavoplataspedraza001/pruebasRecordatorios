package com.example.pruebasrecordatorios.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("autenticado")
    @Expose
    private String autenticado;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nombreUsuario")
    @Expose
    private String nombreUsuario;
    @SerializedName("nombrePersona")
    @Expose
    private String nombrePersona;
    @SerializedName("idSede")
    @Expose
    private String idSede;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseLogin() {
    }

    /**
     *
     * @param autenticado
     * @param nombrePersona
     * @param id
     * @param nombreUsuario
     * @param idSede
     */
    public ResponseLogin(String autenticado, String id, String nombreUsuario, String nombrePersona, String idSede) {
        super();
        this.autenticado = autenticado;
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombrePersona = nombrePersona;
        this.idSede = idSede;
    }

    public String getAutenticado() {
        return autenticado;
    }

    public void setAutenticado(String autenticado) {
        this.autenticado = autenticado;
    }

    public ResponseLogin withAutenticado(String autenticado) {
        this.autenticado = autenticado;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResponseLogin withId(String id) {
        this.id = id;
        return this;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public ResponseLogin withNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        return this;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public ResponseLogin withNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
        return this;
    }

    public String getIdSede() {
        return idSede;
    }

    public void setIdSede(String idSede) {
        this.idSede = idSede;
    }

    public ResponseLogin withIdSede(String idSede) {
        this.idSede = idSede;
        return this;
    }

}