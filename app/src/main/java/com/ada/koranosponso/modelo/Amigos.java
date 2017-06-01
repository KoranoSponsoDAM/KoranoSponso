package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alejandro on 30/05/2017.
 */

public class Amigos implements Serializable {
    private String nombre, direccion, favorito;
    private int id_usuario;

    public Amigos(String nombre, int id_usuario) {//}, String direccion), String favorito) {
        this.nombre = nombre;
        this.id_usuario = id_usuario;
        //this.direccion = direccion;
        //this.favorito = favorito;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    /*public String getDireccion() {
        return direccion;
    }

    public String getFavorito() {
        return favorito;
    }*/
}
