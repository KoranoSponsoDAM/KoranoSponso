package com.ada.koranosponso.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.LoadPeliculaInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Amigos;
import com.ada.koranosponso.modelo.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ada.koranosponso.R.string.username;

public class InfoAmigo extends AppCompatActivity implements LoadPeliculaInterface {

    TextView nombreU;
    private String userF, tokenF,id_usuarioA, id_usuario, descripcion, idDrawable, url, nombreP;
    private int idPelicula;
    private static List<Pelicula> videosFA;
    private LinearLayoutManager layoutManager;
    private RecyclerView reciclador;
    private AdaptadorInfoAmigo adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_amigo);
        reciclador = (RecyclerView) this.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(this);
        reciclador.setLayoutManager(layoutManager);
        rellenarDatos();
    }

    public void inicializarElementos(){
        nombreU = (TextView) findViewById(R.id.texto_nombre_añadir);
    }


    public void rellenarDatos(){
        inicializarElementos();
        Amigos amigo = (Amigos) getIntent().getExtras().getSerializable("amigo");
        nombreU.setText(amigo.getNombre());
        id_usuarioA = String.valueOf(amigo.getIdUsuario());
        //CONSULTAR WEBSERVICE
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userF = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userF);
        tokenF = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenF);
        id_usuario = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, id_usuario);
        videosFA = new ArrayList<Pelicula>();
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDUSUARIOA, id_usuario);
        hashMap.put(Constantes.KEY_IDUSUARIOA, id_usuarioA);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap, Urls.MOSTRAR_FAVORITOS_AMIGO);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);
                    JSONArray videos;

                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        videos = json.getJSONArray("videos");
                        for(int i = 0; i < videos.length(); i++) {
                            nombreP = videos.getJSONObject(i).getString("nombre");
                            descripcion = videos.getJSONObject(i).getString("descripcion");
                            idDrawable = videos.getJSONObject(i).getString("imagen");
                            url = videos.getJSONObject(i).getString("url");
                            idPelicula = videos.getJSONObject(i).getInt("id_pelicula");
                            videosFA.add(i,new Pelicula(idPelicula, nombreP, descripcion, idDrawable, url));
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

    public void crearAdaptardor(){
        adaptador = new AdaptadorInfoAmigo(videosFA, this);
        reciclador.setAdapter(adaptador);
    }

    @Override
    public void verPelicula(Pelicula peliculas, int position) {
        Intent intent = new Intent(this, InfoPelicula.class);
        intent.putExtra("username", userF);
        intent.putExtra("token", tokenF);
        intent.putExtra("peliculas", peliculas);
        startActivity(intent);
    }

}
