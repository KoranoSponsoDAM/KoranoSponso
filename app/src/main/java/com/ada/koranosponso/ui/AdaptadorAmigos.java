package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ada.koranosponso.Interfaces.InfoAmigoInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Amigos;

import java.util.List;

/**
 * Created by Alejandro on 01/06/2017.
 */

public class AdaptadorAmigos
        extends RecyclerView.Adapter<AdaptadorAmigos.ViewHolder>{

    List<Amigos> amigos ;
    Context context;
    private FragmentoAmigosActuales mainFragment;

    public AdaptadorAmigos(List<Amigos> amigos, FragmentoAmigosActuales mainFragment) {
        this.amigos = amigos;
        this.mainFragment = mainFragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nombre;
        public Button btnEliminar;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.texto_amigos_actuales);
            btnEliminar = (Button) v.findViewById(R.id.btnEliminarAmigo);
            v.setOnClickListener(this);
            btnEliminar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnEliminarAmigo:
                    ((InfoAmigoInterface) mainFragment).eliminarAmigo((amigos.get(getAdapterPosition())), getAdapterPosition());

                    break;
                default:
                    ((InfoAmigoInterface) mainFragment).verAmigo((amigos.get(getAdapterPosition())), getAdapterPosition());
                    break;
            }
        }
    }

    @Override
    public AdaptadorAmigos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_actuales_amigos, parent, false);
        context = parent.getContext();
        return new AdaptadorAmigos.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorAmigos.ViewHolder holder, int position) {
        Amigos item = amigos.get(position);
        holder.nombre.setText(item.getNombre());
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }
}
