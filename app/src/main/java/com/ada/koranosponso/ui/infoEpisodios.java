package com.ada.koranosponso.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.LoadPeliculaInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
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

/**
 * Created by abelinchon on 07/06/2017.
 */

public class infoEpisodios extends AppCompatActivity implements LoadPeliculaInterface {

    private TextView descripcion, titulo;
    private ImageButton imagen;
    private ImageView ImageFavorito;
    private String rutaImagen,userF, tokenF, idPelicula, idUsuario;
    private int fav;
    private SmallBang mSmallBang;
    private AdaptadorEpisodios adaptador;
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private static List<Pelicula> listaEpisodios;
    private Context context;
    private String nombreEpi, descripcionEpi, userC, tokenC, idDrawableEpi, urlEpi;
    private int idPeliculaEpi;
    private Pelicula pelicula;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.item_comentario);
        setContentView(R.layout.actividad_episodios);
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
        descripcion = (TextView)findViewById(R.id.descripcionEpi);
        titulo = (TextView)findViewById(R.id.txtTituloEpi);
        imagen = (ImageButton) findViewById(R.id.ibImagenEpi);
        ImageFavorito = (ImageView) findViewById(R.id.ImageFavoritoEpi);
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
        pelicula = (Pelicula) getIntent().getExtras().getSerializable("peliculas");
        idPelicula = String.valueOf(pelicula.getIdPelicula());
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userF = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userF);
        tokenF = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenF);
        idUsuario = String.valueOf(sharedPreferences.getString(Constantes.IDUSUARIO_SHARED_PREF, idUsuario));
        inicializarFavorito();
        inicializarEpisodios();
        titulo.setText(pelicula.getNombre());
        descripcion.setText(pelicula.getDescripcion());
        rutaImagen = pelicula.getIdDrawable();
        Glide.with(this)
                .load(Constantes.IMAGENES+pelicula.getIdDrawable())
                .centerCrop()
                .into(imagen);
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
        hashMap.put(Constantes.KEY_IDCOMENTARIO, idUsuario);

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

    private void inicializarEpisodios() {
        listaEpisodios = new ArrayList<Pelicula>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDPELICULA, idPelicula);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap,  Urls.VER_CAPITULOS);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;
                JSONArray episodios;
                try {
                    json = new JSONObject(response);
                    episodios = json.getJSONArray("capitulos");
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        for(int i = 0; i < episodios.length(); i++) {
                            nombreEpi = episodios.getJSONObject(i).getString("nombre");
                            descripcionEpi = episodios.getJSONObject(i).getString("descripcion");
                            idDrawableEpi = episodios.getJSONObject(i).getString("imagen");
                            urlEpi = episodios.getJSONObject(i).getString("url");
                            idPeliculaEpi = episodios.getJSONObject(i).getInt("id_pelicula");
                            listaEpisodios.add(new Pelicula(idPeliculaEpi, nombreEpi, descripcionEpi, idDrawableEpi, urlEpi));
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
        adaptador = new AdaptadorEpisodios(listaEpisodios, this);
        reciclador.setAdapter(adaptador);
    }

    public void verPelicula(Pelicula peliculas, int position) {
        Intent intent = new Intent(this, InfoPelicula.class);
        intent.putExtra("username", userF);
        intent.putExtra("token", tokenF);
        intent.putExtra("peliculas", peliculas);
        startActivity(intent);
    }
}
