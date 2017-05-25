package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alex on 21/05/2017.
 */

public class Pelicula implements Serializable{

    private String nombre, descripcion, idDrawable, url;

    public Pelicula(String nombre, String descripcion, String idDrawable, String url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idDrawable = idDrawable;
        this.url = url;
    }



    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIdDrawable() {
        return idDrawable;
    }
    public String getUrl() {
        return url;
    }


}
