package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alex on 02/06/2017.
 */

public class Comentario implements Serializable {
    String texto, fecha, username;
    int id_usuario;

    public Comentario(int id_usuario, String username, String texto, String fecha) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.texto = texto;
        this.fecha = fecha;
    }

    public String getUsername() {
        return username;
    }

    public String getTexto() {
        return texto;
    }

    public String getFecha() {
        return fecha;
    }

    public int getId_usuario() {
        return id_usuario;
    }
}
