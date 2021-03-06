package com.ada.koranosponso.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.LoadPeliculaInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class FragmentoFavoritos extends Fragment implements LoadPeliculaInterface {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorFavoritos adaptador;
    private static JSONObject json;
    private ProgressDialog pd;
    public static ArrayList<Pelicula> PELICULAS_FAVORITAS;
    private String nombre, descripcion, userF, tokenF, idDrawable, url, idUsuario;
    private int idPelicula;

    public FragmentoFavoritos() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView)view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userF = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userF);
        tokenF = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenF);
        idUsuario = String.valueOf(sharedPreferences.getString(Constantes.IDUSUARIO_SHARED_PREF, idUsuario));
        PELICULAS_FAVORITAS = new ArrayList<Pelicula>();
        listFavoritos();
        return view;
    }

    private void listFavoritos() {
        PELICULAS_FAVORITAS.clear();
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.MOSTRAR_FAVORITOS);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);
                    JSONArray peliculas;

                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        peliculas = json.getJSONArray("peliculas");
                        for(int i = 0; i < peliculas.length(); i++) {
                            nombre = peliculas.getJSONObject(i).getString("nombre");
                            descripcion = peliculas.getJSONObject(i).getString("descripcion");
                            idDrawable = peliculas.getJSONObject(i).getString("imagen");
                            url = peliculas.getJSONObject(i).getString("url");
                            idPelicula = peliculas.getJSONObject(i).getInt("id_pelicula");
                            PELICULAS_FAVORITAS.add(new Pelicula(idPelicula, nombre, descripcion, idDrawable, url));
                        }
                        crearAdaptador();

                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                return null;
            }

        });
    }



    private void crearAdaptador() {

        adaptador = new AdaptadorFavoritos(PELICULAS_FAVORITAS, this);
        reciclador.setAdapter(adaptador);
    }

    public void verPelicula(Pelicula peliculas, int position) {
        Pelicula p = peliculas;
        Intent intent;
        if(p.getUrl().equals("")) {
            intent = new Intent(getActivity(), infoEpisodios.class);
        }else{
            intent = new Intent(getActivity(), infoSoloPeliculas.class);
        }
        intent.putExtra("username", userF);
        intent.putExtra("token", tokenF);
        intent.putExtra("peliculas", peliculas);
        startActivity(intent);
    }
}
