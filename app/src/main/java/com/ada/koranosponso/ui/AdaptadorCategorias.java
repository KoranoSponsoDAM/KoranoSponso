package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Comida;
import com.ada.koranosponso.modelo.Pelicula;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Adaptador para comidas usadas en la sección "Categorías"
 */
public class AdaptadorCategorias
        extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {


    List<Pelicula> PELICULAS_CATEGORIA;
    Context context;

    private FragmentoCategoria mainFragment;
    public AdaptadorCategorias(List<Pelicula> peliculasPopulares, FragmentoCategoria mainFragment) {
        PELICULAS_CATEGORIA = peliculasPopulares;
        this.mainFragment = mainFragment;
    }

    @Override
    public AdaptadorCategorias.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return PELICULAS_CATEGORIA.size();
    }
    public AdaptadorCategorias(){}

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
            ((LoadPeliculaInterface)mainFragment).verPelicula(( PELICULAS_CATEGORIA.get(getAdapterPosition())), getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(AdaptadorCategorias.ViewHolder viewHolder, int i) {
        Pelicula item = PELICULAS_CATEGORIA.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(Constantes.IMAGENES+item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
    }


}