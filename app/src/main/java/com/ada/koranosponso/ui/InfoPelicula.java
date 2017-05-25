package com.ada.koranosponso.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Pelicula;
import com.bumptech.glide.Glide;

public class InfoPelicula extends AppCompatActivity {
    TextView descripcion, titulo;
    ImageButton imagen;
    String rutaImagen, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pelicula);
        rellenarElementos();
        agregarToolbar();
    }

    public void inicializaElementos(){
        descripcion = (TextView)findViewById(R.id.descripcion);
        titulo = (TextView)findViewById(R.id.txtTitulo);
        imagen = (ImageButton) findViewById(R.id.ibImagen);
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void rellenarElementos(){
        inicializaElementos();
        Pelicula pelicula = (Pelicula) getIntent().getExtras().getSerializable("peliculas");
        titulo.setText(pelicula.getNombre());
        descripcion.setText(pelicula.getDescripcion());
        rutaImagen = pelicula.getIdDrawable();
        url = pelicula.getUrl();
        Glide.with(this)
                .load("http://koranosponso.000webhostapp.com/imagenes/"+rutaImagen)
                .centerCrop()
                .into(imagen);

    }

    public void reproducir(View view) {
        Intent intent = new Intent(this, reproductoVideo.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
