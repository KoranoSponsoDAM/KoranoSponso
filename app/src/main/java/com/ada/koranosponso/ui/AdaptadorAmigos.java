package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.InfoAmigoInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Amigos;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 01/06/2017.
 */

public class AdaptadorAmigos
        extends RecyclerView.Adapter<AdaptadorAmigos.ViewHolder>{

    List<Amigos> amigos ;
    List<Amigos> amigoslist= new ArrayList<>();
    List<Amigos> amigosAux= new ArrayList<>();
    Context context;
    private FragmentoAmigosActuales mainFragment;

    public AdaptadorAmigos(List<Amigos> amigos, FragmentoAmigosActuales mainFragment) {
        this.amigos = amigos;
        this.mainFragment = mainFragment;
        amigosAux = amigos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nombre;
        public Button btnEliminar;
        public ImageView imagenAmigo;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.texto_amigos_actuales);
            btnEliminar = (Button) v.findViewById(R.id.btnEliminarAmigo);
            imagenAmigo = (ImageView) v.findViewById(R.id.imageAmigo);
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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Amigos item = amigos.get(position);
        holder.nombre.setText(item.getNombre());
    }

    @Override
    public int getItemCount() {
        return amigos.size();
    }

    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List filtList = new ArrayList<>();
                amigos = amigosAux;

                if (amigoslist == null) {
                    amigoslist = new ArrayList(amigos);
                }


                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = amigos.size();
                    results.values = amigos;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for(int i=0; i < amigos.size() ; i++) {
                        Amigos data = (Amigos) amigos.get(i);
                        String da = data.getNombre();
                        if (da.toLowerCase().contains(constraint)) {
                            filtList.add(amigos.get(i));
                            amigoslist.add(amigos.get(i));
                        }
                    }

                    results.count = filtList.size();
                    results.values = filtList;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                amigos = (List) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
