package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alejandro on 30/05/2017.
 */

public class Amigos implements Serializable {
    private String nombre, imagen, direccion;
    private int id_usuario;


    public Amigos(String nombre, int id_usuario, String imagen, String direccion) {
        this.nombre = nombre;
        this.id_usuario = id_usuario;
        this.imagen = imagen;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDireccion() {
        return direccion;
    }
}
