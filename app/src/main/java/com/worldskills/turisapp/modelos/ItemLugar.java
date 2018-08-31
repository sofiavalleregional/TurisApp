package com.worldskills.turisapp.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.GET;

public class ItemLugar {


    @SerializedName("id")
    @Expose
    private int ID;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("ubicacion")
    @Expose
    private String ubicacion;

    @SerializedName("descripcioncorta")
    @Expose
    private String descripcionCorta;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("urlimagen")
    @Expose
    private String urlImagen;

    @SerializedName("longitud")
    @Expose
    private double longitud;

    @SerializedName("latitud")
    @Expose
    private double latitud;


    @SerializedName("tipolugar")
    @Expose
    private String tipoLugar;


    //AAA
    public int getID() {
        return ID;
    }
    //BBB
    public void setID(int id) {
        this.ID = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }
}
