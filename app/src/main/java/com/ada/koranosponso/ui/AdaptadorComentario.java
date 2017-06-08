package com.ada.koranosponso.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.EliminarComentarioInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Comentario;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.List;
/**
 * Created by Alex on 02/06/2017.
 */


/**
 * Adaptador para poblar la lista de direcciones de la sección "Mi Cuenta"
 */
public class AdaptadorComentario
        extends RecyclerView.Adapter<AdaptadorComentario.ViewHolder> {


    List<Comentario> comentarios;
    Context context;
    InfoPelicula mainFragment;

    public AdaptadorComentario(){}

    public AdaptadorComentario(List<Comentario> comentarios, InfoPelicula mainFragment) {
        this.comentarios = comentarios;
        this.mainFragment = mainFragment;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        // Campos respectivos de un item
        public TextView nombre, fecha, texto;
        public ImageView imagenP;
        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.txtUsuario);
            texto = (TextView) v.findViewById(R.id.txtComentario);
            fecha = (TextView) v.findViewById(R.id.txtFecha);
            imagenP = (ImageView) v.findViewById(R.id.comenPerfil);
            v.setOnLongClickListener((View.OnLongClickListener) this);
        }

        @Override
        public boolean onLongClick(View v) {
            ((EliminarComentarioInterface) mainFragment).eliminarComentarios((comentarios.get(getAdapterPosition())), getAdapterPosition());
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comentario, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Comentario item = comentarios.get(i);
        viewHolder.nombre.setText(item.getUsername());
        viewHolder.fecha.setText(item.getFecha());
        viewHolder.texto.setText(item.getTexto());
        if(!item.getImagen().isEmpty()) {
            Glide.with(viewHolder.itemView.getContext())
                    .load(Constantes.IMAGENES_PERFIL + item.getImagen())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.imagenP);
        }else{
            Glide.with(viewHolder.itemView.getContext())
                    .load(Constantes.IMAGENES_PERFIL + "defectou.png")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.imagenP);
        }
    }

}
