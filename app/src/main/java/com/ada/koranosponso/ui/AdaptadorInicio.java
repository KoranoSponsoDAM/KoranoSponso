package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.LoadPeliculaInterface;
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
    Context context;
    private FragmentoInicio mainFragment;


    public AdaptadorInicio(List<Pelicula> peliculasPopulares, FragmentoInicio mainFragment) {
        PELICULAS_POPULARES = peliculasPopulares;
        this.mainFragment = mainFragment;
    }
    public AdaptadorInicio(){}

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
            ((LoadPeliculaInterface)mainFragment).verPelicula(( PELICULAS_POPULARES.get(getAdapterPosition())), getAdapterPosition());
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
        context = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Pelicula item = PELICULAS_POPULARES.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(Constantes.IMAGENES+item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
    }


}