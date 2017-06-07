package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ada.koranosponso.Interfaces.EliminarComentarioInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Capitulos;
import com.ada.koranosponso.modelo.Comentario;

import java.util.List;

/**
 * Created by abelinchon on 07/06/2017.
 */

public class AdaptadorEpisodios
        extends RecyclerView.Adapter<AdaptadorEpisodios.ViewHolder> {

    List<Capitulos> capitulos;
    Context context;
    infoEpisodios mainFragment;

    public AdaptadorEpisodios() {}

    public AdaptadorEpisodios(List<Capitulos> capitulos, infoEpisodios mainFragment) {
        this.capitulos = capitulos;
        this.mainFragment = mainFragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre, capi;
        public Button btnPublicar;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.tvTitulo);
            capi = (TextView) v.findViewById(R.id.tvNumCapi);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Aactivity InfoPelicula(arrayFiltradoConCapi,posicion)
        }
    }

    @Override
    public AdaptadorEpisodios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_episodios, parent, false);
        context = parent.getContext();
        return new AdaptadorEpisodios.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorEpisodios.ViewHolder holder, int position) {
        Capitulos item = capitulos.get(position);
        holder.nombre.setText(item.getNombre());
        holder.capi.setText(item.getCapi());
    }

    @Override
    public int getItemCount() {
        return capitulos.size();
    }
}
