package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Amigos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 30/05/2017.
 */

public class AdaptadorBuscarAmigos
        extends RecyclerView.Adapter<AdaptadorBuscarAmigos.ViewHolder>  implements Filterable {


    List<Amigos> amigos ;
    List<Amigos> amigoslist= new ArrayList<>();
    List<Amigos> amigosAux= new ArrayList<>();
    Context context;
    private FragmentoAniadirAmigos mainFragment;

    public AdaptadorBuscarAmigos(List<Amigos> amigos, FragmentoAniadirAmigos mainFragment) {
        this.amigos = amigos;
        this.mainFragment = mainFragment;
        amigosAux = amigos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nombre;
        public Button btnAgregar;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.texto_nombre_amigo);
            btnAgregar = (Button) v.findViewById(R.id.btnAgregarAmigo);
            btnAgregar.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            ((AgregarAmigoInterface) mainFragment).agregarAmigo((amigos.get(getAdapterPosition())), getAdapterPosition());
            btnAgregar.setEnabled(false);
        }
    }

    @Override
    public AdaptadorBuscarAmigos.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_amigos, viewGroup, false);
        context = viewGroup.getContext();
        return new AdaptadorBuscarAmigos.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorBuscarAmigos.ViewHolder viewHolder, int i) {
        Amigos item = amigos.get(i);
        viewHolder.nombre.setText(item.getNombre());
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
