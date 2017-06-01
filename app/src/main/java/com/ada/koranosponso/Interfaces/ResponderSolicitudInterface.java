package com.ada.koranosponso.Interfaces;

import com.ada.koranosponso.modelo.Amigos;

/**
 * Created by Alex on 01/06/2017.
 */

public interface ResponderSolicitudInterface {
    public void aceptarAmigo(Amigos amigos, int position);
    public void denegarAmigo(Amigos amigos, int position);
}
