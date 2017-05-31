package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Amigos;

import java.util.List;

/**
 * Created by Alejandro on 30/05/2017.
 */

public class AdaptadorBuscarAmigos
        extends RecyclerView.Adapter<AdaptadorBuscarAmigos.ViewHolder>{


    List<Amigos> amigos;
    Context context;
    private FragmentoAniadirAmigos mainFragment;

    public AdaptadorBuscarAmigos(List<Amigos> amigos, FragmentoAniadirAmigos mainFragment) {
        this.amigos = amigos;
        this.mainFragment = mainFragment;
    }

    @Override
    public AdaptadorBuscarAmigos.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragmento_lista_amigos, viewGroup, false);
        context = viewGroup.getContext();
        return new AdaptadorBuscarAmigos.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorBuscarAmigos.ViewHolder viewHolder, int i) {
        Amigos item = amigos.get(i);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.direccion.setText(item.getDireccion());
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nombre, direccion;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.texto_nombre_amigo);
            direccion = (TextView) v.findViewById(R.id.texto_direccion_amigo);
        }
    }

}
