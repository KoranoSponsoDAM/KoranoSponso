package com.ada.koranosponso.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Pelicula;
import com.bumptech.glide.Glide;

public class InfoPelicula extends AppCompatActivity {
    TextView descripcion, titulo;
    ImageButton imagen;
    String ruta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pelicula);
        rellenarElementos();
    }

    public void inicializaElementos(){
        descripcion = (TextView)findViewById(R.id.descripcion);
        titulo = (TextView)findViewById(R.id.txtTitulo);
        imagen = (ImageButton) findViewById(R.id.ibImagen);
    }

    public void rellenarElementos(){
        inicializaElementos();
        Pelicula pelicula = (Pelicula) getIntent().getExtras().getSerializable("peliculas");
        titulo.setText(pelicula.getNombre());
        descripcion.setText(pelicula.getDescripcion());
        ruta = pelicula.getIdDrawable();
        Glide.with(this)
                .load("http://koranosponso.000webhostapp.com/imagenes/"+ruta)
                .centerCrop()
                .into(imagen);

    }

    public void reproducir(View view) {

    }
}
