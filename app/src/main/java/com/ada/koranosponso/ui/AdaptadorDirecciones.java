package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Pelicula;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para poblar la lista de direcciones de la sección "Mi Cuenta"
 */
public class AdaptadorDirecciones
        extends RecyclerView.Adapter<AdaptadorDirecciones.ViewHolder> {


    List<Pelicula> PELICULAS_FAVORITAS;
    Context context;
    private FragmentoDirecciones mainFragment;

    public AdaptadorDirecciones(){}

    public AdaptadorDirecciones(ArrayList<Pelicula> peliculasFavoritas, FragmentoDirecciones mainFragment) {
        PELICULAS_FAVORITAS = peliculasFavoritas;
        this.mainFragment = mainFragment;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.titulo_pelicula );
            imagen = (ImageView) v.findViewById(R.id.miniatura_pelicula);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((LoadPeliculaInterface)mainFragment).verPelicula(( PELICULAS_FAVORITAS.get(getAdapterPosition())), getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return PELICULAS_FAVORITAS.size();
    }

    @Override
    public AdaptadorDirecciones.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_direccion, viewGroup, false);
        context = viewGroup.getContext();
        return new AdaptadorDirecciones.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(AdaptadorDirecciones.ViewHolder viewHolder, int i) {
        Pelicula item = PELICULAS_FAVORITAS.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load("http://koranosponso.000webhostapp.com/imagenes/"+item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
    }

}