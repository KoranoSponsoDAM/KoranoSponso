package com.ada.koranosponso.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Pelicula;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;


public class InfoPelicula extends AppCompatActivity {
    TextView descripcion, titulo;
    ImageButton imagen;
    ImageView ImageFavorito;
    String rutaImagen, url ,userF, tokenF, idPelicula;

    private boolean fav=false;
    private SmallBang mSmallBang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void inicializaElementos(){
        descripcion = (TextView)findViewById(R.id.descripcion);
        titulo = (TextView)findViewById(R.id.txtTitulo);
        imagen = (ImageButton) findViewById(R.id.ibImagen);
        ImageFavorito = (ImageView) findViewById(R.id.ImageFavorito);
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

    public void like(View view){
        if(fav) {
            ImageFavorito.setImageResource(R.drawable.heart);
        }else{
            ImageFavorito.setImageResource(R.drawable.heart_red);
        }
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
                if(fav){
                    fav = false;
                    ImageFavorito.setImageResource(R.drawable.heart);
                }else{
                    fav = true;
                    ImageFavorito.setImageResource(R.drawable.heart_red);
                }
            }
        });
    }

    public void btnFavorito() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userF = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userF);
        tokenF = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenF);
        //showProgressDialog("CARGANDO", "");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDPELICULA, idPelicula);

        RestAPIWebServices res = new RestAPIWebServices(this, hashMap,  Urls.DAR_FAVORTIO);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);
                    JSONArray peliculas;

                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {


                        // pd.dismiss();
                    } else {
                        //pd.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //pd.dismiss();
                }

                return null;
            }

        });


    }
}
