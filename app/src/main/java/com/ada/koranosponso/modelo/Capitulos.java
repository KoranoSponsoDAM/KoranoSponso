package com.ada.koranosponso.modelo;

/**
 * Created by abelinchon on 07/06/2017.
 */

public class Capitulos {
    String nombre;
    Integer capi;

    public Capitulos(String nombre, Integer capi) {
        this.nombre = nombre;
        this.capi = capi;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCapi() {
        return capi;
    }
}
