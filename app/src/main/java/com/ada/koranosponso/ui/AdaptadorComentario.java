package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Comentario;


import java.util.List;
/**
 * Created by Alex on 02/06/2017.
 */


/**
 * Adaptador para poblar la lista de direcciones de la sección "Mi Cuenta"
 */
public class AdaptadorComentario
        extends RecyclerView.Adapter<AdaptadorComentario.ViewHolder> {


    List<Comentario> comentarios;
    Context context;
    InfoPelicula mainFragment;

    public AdaptadorComentario(){}

    public AdaptadorComentario(List<Comentario> comentarios, InfoPelicula mainFragment) {
        this.comentarios = comentarios;
        this.mainFragment = mainFragment;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre, fecha, texto;
        public Button btnPublicar;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.txtUsuario);
            texto = (TextView) v.findViewById(R.id.txtComentario);
            fecha = (TextView) v.findViewById(R.id.txtFecha);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    @Override
    public AdaptadorComentario.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comentario, viewGroup, false);
        context = viewGroup.getContext();
        return new AdaptadorComentario.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(AdaptadorComentario.ViewHolder viewHolder, int i) {
        Comentario item = comentarios.get(i);
        viewHolder.nombre.setText(item.getUsername());
        viewHolder.fecha.setText(item.getFecha());
        viewHolder.texto.setText(item.getTexto());
    }

}
