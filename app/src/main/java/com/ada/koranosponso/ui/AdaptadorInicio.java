package com.ada.koranosponso.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Pelicula;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Adaptador para mostrar las comidas más pedidas en la sección "Inicio"
 */
public class AdaptadorInicio
        extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder> {
    List<Pelicula> PELICULAS_POPULARES;


    /*public AdaptadorInicio(String nombre, String descripcion, int idDrawable) {
        PELICULAS_POPULARES.add(new Pelicula(nombre, descripcion, idDrawable ));
    }*/

    public AdaptadorInicio(List<Pelicula> peliculasPopulares) {
        PELICULAS_POPULARES = peliculasPopulares;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageButton imagen;
        public TextView nombre;
        public TextView descripcion;
        public ViewHolder(View v) {
            super(v);
            descripcion = (TextView) v.findViewById(R.id.descripcion );
            nombre = (TextView) v.findViewById(R.id.titulo_pelicula );
            imagen = (ImageButton) v.findViewById(R.id.miniatura_pelicula);
        }
    }

    @Override
    public int getItemCount() {
        return PELICULAS_POPULARES.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Pelicula item = PELICULAS_POPULARES.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load("http://koranosponso.000webhostapp.com/imagenes/"+item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.descripcion.setText(item.getDecripcion());
    }


}