package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alejandro on 30/05/2017.
 */

public class Amigos implements Serializable {
    private String nombre, direccion, favorito;

    public Amigos(String nombre, String direccion, String favorito) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.favorito = favorito;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFavorito() {
        return favorito;
    }
}
