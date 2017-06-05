package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alex on 02/06/2017.
 */

public class Comentario implements Serializable {
    String texto, fecha, username;
    int id_usuario, id_comentario;

    public Comentario(int id_comentario, int id_usuario, String username, String texto, String fecha) {
        this.id_comentario = id_comentario;
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

    public int getIdUsuario() {
        return id_usuario;
    }

    public int getIdComentario() {
        return id_comentario;
    }
}
