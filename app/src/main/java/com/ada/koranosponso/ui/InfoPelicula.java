package com.ada.koranosponso.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Comentario;
import com.ada.koranosponso.modelo.Pelicula;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;


public class InfoPelicula extends AppCompatActivity {
    private EditText EtTexto;
    private TextView descripcion, titulo;
    private ImageButton imagen;
    private ImageView ImageFavorito;
    private String rutaImagen, url ,userF, tokenF, idPelicula, idUsuario, username, texto, fecha;
    private int  idUsuarioC;
    private int fav;
    private SmallBang mSmallBang;
    private AdaptadorComentario adaptador;
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private static List<Comentario> comentariosP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.item_comentario);
        setContentView(R.layout.activity_info_pelicula);
        rellenarElementos();
        agregarToolbar();
        mSmallBang = SmallBang.attach2Window(this);

        ImageFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(v);
                btnFavorito();
            }
        });


    }

    public void inicializarElementos(){
        reciclador = (RecyclerView) this.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(this);
        reciclador.setLayoutManager(layoutManager);
        descripcion = (TextView)findViewById(R.id.descripcion);
        titulo = (TextView)findViewById(R.id.txtTitulo);
        imagen = (ImageButton) findViewById(R.id.ibImagen);
        ImageFavorito = (ImageView) findViewById(R.id.ImageFavorito);
        EtTexto = (EditText) findViewById(R.id.etComentario);
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(titulo.getText().toString());
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void rellenarElementos(){
        inicializarElementos();
        Pelicula pelicula = (Pelicula) getIntent().getExtras().getSerializable("peliculas");
        idPelicula = String.valueOf(pelicula.getIdPelicula());
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userF = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userF);
        tokenF = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenF);
        idUsuario = String.valueOf(sharedPreferences.getString(Constantes.IDUSUARIO_SHARED_PREF, idUsuario));
        inicializarFavorito();
        inicializarComentarios();
        titulo.setText(pelicula.getNombre());
        descripcion.setText(pelicula.getDescripcion());
        rutaImagen = pelicula.getIdDrawable();
        url = pelicula.getUrl();
        Glide.with(this)
                .load("http://koranosponso.000webhostapp.com/imagenes/"+rutaImagen)
                .centerCrop()
                .into(imagen);
    }

    private void inicializarComentarios() {
        comentariosP = new ArrayList<Comentario>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDPELICULA, idPelicula);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap,  Urls.MOSTRAR_COMENTARIOS);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;
                JSONArray comentarios;
                try {
                    json = new JSONObject(response);
                    comentarios = json.getJSONArray("comentarios");
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        for(int i = 0; i < comentarios.length(); i++) {
                            username = comentarios.getJSONObject(i).getString("username");
                            texto = comentarios.getJSONObject(i).getString("texto");
                            fecha = comentarios.getJSONObject(i).getString("fecha");
                            idUsuarioC = comentarios.getJSONObject(i).getInt("id_usuario");
                            comentariosP.add(i,new Comentario(idUsuarioC, username, texto, fecha));
                        }
                        crearAdaptardor();
                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

        });
    }

    private void crearAdaptardor() {
        adaptador = new AdaptadorComentario(comentariosP, this);
        reciclador.setAdapter(adaptador);
    }

    private void inicializarFavorito() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDPELICULA, idPelicula);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap,  Urls.SABER_FAVORITO);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);
                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        fav = json.getInt("fav");
                        if(fav == 0){
                            ImageFavorito.setImageResource(R.drawable.heart);
                        }else{
                            ImageFavorito.setImageResource(R.drawable.heart_red);
                        }
                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

        });
    }

    public void reproducir(View view) {
        Intent intent = new Intent(this, reproductoVideo.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void like(View view){
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
                if(fav == 1){
                    fav = 0;
                    ImageFavorito.setImageResource(R.drawable.heart);
                }else{
                    fav = 1;
                    ImageFavorito.setImageResource(R.drawable.heart_red);
                }
            }
        });
        if(fav == 1) {
            ImageFavorito.setImageResource(R.drawable.heart);
        }else{
            ImageFavorito.setImageResource(R.drawable.heart_red);
        }
    }

    public void btnFavorito() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDPELICULA, idPelicula);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);

        RestAPIWebServices res = new RestAPIWebServices(this, hashMap,  Urls.DAR_FAVORTIO);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);
                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //pd.dismiss();
                }

                return null;
            }

        });
    }

    public void publicarComentario(View view) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDPELICULA, idPelicula);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        hashMap.put(Constantes.KEY_COMENTARIO, String.valueOf(EtTexto.getText()));
        EtTexto.setText(" ");
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap,  Urls.PUBLICAR_COMENTARIO);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        rellenarElementos();
                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

        });
    }
}
