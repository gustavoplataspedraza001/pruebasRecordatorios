 package com.example.pruebasrecordatorios.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePeticion {

    @SerializedName("existe")
    @Expose
    private String existe;
    @SerializedName("artc_descripcion")
    @Expose
    private String artcDescripcion;
    @SerializedName("artc_precioVenta")
    @Expose
    private String artcPrecioVenta;
    @SerializedName("artc_precioOferta")
    @Expose
    private String artcPrecioOferta;
    @SerializedName("ofrt_diasRestantes")
    @Expose
    private Object ofrtDiasRestantes;
    @SerializedName("ofrt_ahorroPesos")
    @Expose
    private String ofrtAhorroPesos;
    @SerializedName("ofrt_vigenciaFecha")
    @Expose
    private Object ofrtVigenciaFecha;
    @SerializedName("ofrt_folio")
    @Expose
    private String ofrtFolio;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponsePeticion() {
    }

    /**
     *
     * @param ofrtVigenciaFecha
     * @param ofrtDiasRestantes
     * @param existe
     * @param artcPrecioVenta
     * @param artcPrecioOferta
     * @param ofrtAhorroPesos
     * @param ofrtFolio
     * @param artcDescripcion
     */
    public ResponsePeticion(String existe, String artcDescripcion, String artcPrecioVenta, String artcPrecioOferta, Object ofrtDiasRestantes, String ofrtAhorroPesos, Object ofrtVigenciaFecha, String ofrtFolio) {
        super();
        this.existe = existe;
        this.artcDescripcion = artcDescripcion;
        this.artcPrecioVenta = artcPrecioVenta;
        this.artcPrecioOferta = artcPrecioOferta;
        this.ofrtDiasRestantes = ofrtDiasRestantes;
        this.ofrtAhorroPesos = ofrtAhorroPesos;
        this.ofrtVigenciaFecha = ofrtVigenciaFecha;
        this.ofrtFolio = ofrtFolio;
    }

    public String getExiste() {
        return existe;
    }

    public void setExiste(String existe) {
        this.existe = existe;
    }

    public ResponsePeticion withExiste(String existe) {
        this.existe = existe;
        return this;
    }

    public String getArtcDescripcion() {
        return artcDescripcion;
    }

    public void setArtcDescripcion(String artcDescripcion) {
        this.artcDescripcion = artcDescripcion;
    }

    public ResponsePeticion withArtcDescripcion(String artcDescripcion) {
        this.artcDescripcion = artcDescripcion;
        return this;
    }

    public String getArtcPrecioVenta() {
        return artcPrecioVenta;
    }

    public void setArtcPrecioVenta(String artcPrecioVenta) {
        this.artcPrecioVenta = artcPrecioVenta;
    }

    public ResponsePeticion withArtcPrecioVenta(String artcPrecioVenta) {
        this.artcPrecioVenta = artcPrecioVenta;
        return this;
    }

    public String getArtcPrecioOferta() {
        return artcPrecioOferta;
    }

    public void setArtcPrecioOferta(String artcPrecioOferta) {
        this.artcPrecioOferta = artcPrecioOferta;
    }

    public ResponsePeticion withArtcPrecioOferta(String artcPrecioOferta) {
        this.artcPrecioOferta = artcPrecioOferta;
        return this;
    }

    public Object getOfrtDiasRestantes() {
        return ofrtDiasRestantes;
    }

    public void setOfrtDiasRestantes(Object ofrtDiasRestantes) {
        this.ofrtDiasRestantes = ofrtDiasRestantes;
    }

    public ResponsePeticion withOfrtDiasRestantes(Object ofrtDiasRestantes) {
        this.ofrtDiasRestantes = ofrtDiasRestantes;
        return this;
    }

    public String getOfrtAhorroPesos() {
        return ofrtAhorroPesos;
    }

    public void setOfrtAhorroPesos(String ofrtAhorroPesos) {
        this.ofrtAhorroPesos = ofrtAhorroPesos;
    }

    public ResponsePeticion withOfrtAhorroPesos(String ofrtAhorroPesos) {
        this.ofrtAhorroPesos = ofrtAhorroPesos;
        return this;
    }

    public Object getOfrtVigenciaFecha() {
        return ofrtVigenciaFecha;
    }

    public void setOfrtVigenciaFecha(Object ofrtVigenciaFecha) {
        this.ofrtVigenciaFecha = ofrtVigenciaFecha;
    }

    public ResponsePeticion withOfrtVigenciaFecha(Object ofrtVigenciaFecha) {
        this.ofrtVigenciaFecha = ofrtVigenciaFecha;
        return this;
    }

    public String getOfrtFolio() {
        return ofrtFolio;
    }

    public void setOfrtFolio(String ofrtFolio) {
        this.ofrtFolio = ofrtFolio;
    }

    public ResponsePeticion withOfrtFolio(String ofrtFolio) {
        this.ofrtFolio = ofrtFolio;
        return this;
    }

}