package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alex on 21/05/2017.
 */

public class Pelicula implements Serializable{

    private String nombre, descripcion, idDrawable, userP, tokenP;

    public Pelicula(String nombre, String descripcion, String idDrawable) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idDrawable = idDrawable;
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


}
