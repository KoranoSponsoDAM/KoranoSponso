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
 * Created by Alejandro on 31/05/2017.
 */

public class AdaptadorAceptarAmigos
        extends RecyclerView.Adapter<AdaptadorAceptarAmigos.ViewHolder>{

    List<Amigos> amigos ;
    Context context;
    private FragmentoAceptarAmigos mainFragment;

    public AdaptadorAceptarAmigos(List<Amigos> amigos, FragmentoAceptarAmigos mainFragment) {
        this.amigos = amigos;
        this.mainFragment = mainFragment;
    }

    @Override
    public AdaptadorAceptarAmigos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aceptar_amigos, parent, false);
        context = parent.getContext();
        return new AdaptadorAceptarAmigos.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorAceptarAmigos.ViewHolder holder, int position) {
        Amigos item = amigos.get(position);
        holder.nombre.setText(item.getNombre());
        holder.direccion.setText(item.getDireccion());
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nombre, direccion;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.texto_nombre_añadir);
            direccion = (TextView) v.findViewById(R.id.texto_direccion_añadir);
        }
    }
}
