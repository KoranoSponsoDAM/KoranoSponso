package com.ada.koranosponso.modelo;

import java.io.Serializable;

/**
 * Created by Alex on 02/06/2017.
 */

public class Comentario implements Serializable {
    String texto, fecha, username, imagen;
    int id_usuario, id_comentario;

    public Comentario(int id_comentario, int id_usuario, String username, String texto, String fecha, String imagen) {
        this.id_comentario = id_comentario;
        this.id_usuario = id_usuario;
        this.username = username;
        this.texto = texto;
        this.fecha = fecha;
        this.imagen = imagen;
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

    public String getImagen() {
        return imagen;
    }
}
