package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ada.koranosponso.Interfaces.ResponderSolicitudInterface;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nombre;
        public Button btnAceptarAmigo, btnDenegarAmigo;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.texto_nombre_añadir);
            btnAceptarAmigo = (Button) v.findViewById(R.id.btnAceptarAmigo);
            btnDenegarAmigo = (Button) v.findViewById(R.id.btnDenegarAmigo);
            btnAceptarAmigo.setOnClickListener(this);
            btnDenegarAmigo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAceptarAmigo:
                    ((ResponderSolicitudInterface) mainFragment).aceptarAmigo((amigos.get(getAdapterPosition())), getAdapterPosition());
                    amigos.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;
                case R.id.btnDenegarAmigo:
                    ((ResponderSolicitudInterface) mainFragment).denegarAmigo((amigos.get(getAdapterPosition())), getAdapterPosition());
                    amigos.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
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
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

}
