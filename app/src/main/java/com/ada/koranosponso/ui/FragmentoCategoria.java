package com.ada.koranosponso.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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


/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Categorías"
 */
public class FragmentoCategoria extends Fragment implements LoadPeliculaInterface, SearchView.OnQueryTextListener {

    private static final String INDICE_SECCION
            = "com.ada.koranosponso.ui.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorCategorias adaptador;
    private String nombre, descripcion, userC, tokenC, idDrawable, url;
    private int idPelicula;
    private static ArrayList<Pelicula> PELICULAS_PELICULAS = new ArrayList<Pelicula>();
    private static ArrayList<Pelicula> PELICULAS_SERIES = new ArrayList<Pelicula>();
    private static ArrayList<Pelicula> PELICULAS_ANIMES = new ArrayList<Pelicula>();
    private boolean cargado = false;

    public static FragmentoCategoria nuevaInstancia(int indiceSeccion) {
        FragmentoCategoria fragment = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userC = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userC);
        tokenC = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenC);
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userC);
        hashMap.put(Constantes.KEY_TOKEN, tokenC);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.MOSTRAR_CATEGORIAS);
        if(!cargado) {
            res.responseApi(new RestAPIWebServices.VolleyCallback() {
                @Override
                public View onSuccess(String response) {
                    JSONObject json = null;

                    try {
                        json = new JSONObject(response);
                        JSONArray peliculas, series, animes;
                        PELICULAS_PELICULAS.clear();
                        PELICULAS_SERIES.clear();
                        PELICULAS_ANIMES.clear();
                        //If we are getting success from server
                        if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                            peliculas = json.getJSONArray("peliculas");
                            series = json.getJSONArray("series");
                            animes = json.getJSONArray("animes");
                            if (peliculas != null) {
                                for (int i = 0; i < peliculas.length(); i++) {
                                    nombre = peliculas.getJSONObject(i).getString("nombre");
                                    descripcion = peliculas.getJSONObject(i).getString("descripcion");
                                    idDrawable = peliculas.getJSONObject(i).getString("imagen");
                                    url = peliculas.getJSONObject(i).getString("url");
                                    idPelicula = peliculas.getJSONObject(i).getInt("id_pelicula");
                                    PELICULAS_PELICULAS.add(new Pelicula(idPelicula, nombre, descripcion, idDrawable, url));
                                }
                            }
                            if (series != null) {
                                for (int i = 0; i < series.length(); i++) {
                                    nombre = series.getJSONObject(i).getString("nombre");
                                    descripcion = series.getJSONObject(i).getString("descripcion");
                                    idDrawable = series.getJSONObject(i).getString("imagen");
                                    url = series.getJSONObject(i).getString("url");
                                    idPelicula = series.getJSONObject(i).getInt("id_pelicula");
                                    PELICULAS_SERIES.add(new Pelicula(idPelicula, nombre, descripcion, idDrawable, url));
                                }
                            }
                            if (animes != null) {
                                for (int i = 0; i < animes.length(); i++) {
                                    nombre = animes.getJSONObject(i).getString("nombre");
                                    descripcion = animes.getJSONObject(i).getString("descripcion");
                                    idDrawable = animes.getJSONObject(i).getString("imagen");
                                    url = animes.getJSONObject(i).getString("url");
                                    idPelicula = animes.getJSONObject(i).getInt("id_pelicula");
                                    PELICULAS_ANIMES.add(new Pelicula(idPelicula, nombre, descripcion, idDrawable, url));
                                }
                            }
                            int indiceSeccion = getArguments().getInt(INDICE_SECCION);
                            crearAdaptardor(indiceSeccion);
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

            });
        }
        cargado = true;

        return view;
    }

    public void crearAdaptardor(int indiceSeccion){
            switch (indiceSeccion) {
                case 0:
                        adaptador = new AdaptadorCategorias(PELICULAS_PELICULAS, this);
                    break;
                case 1:
                        adaptador = new AdaptadorCategorias(PELICULAS_SERIES, this);
                    break;
                case 2:
                        adaptador = new AdaptadorCategorias(PELICULAS_ANIMES, this);
                    break;
            }
        reciclador.setAdapter(adaptador);
    }

    public void verPelicula(Pelicula peliculas, int position) {
        Pelicula p = peliculas;
        Intent intent = new Intent(getActivity(), infoEpisodios.class);
        intent.putExtra("username", userC);
        intent.putExtra("token", tokenC);
        intent.putExtra("peliculas", peliculas);
        startActivity(intent);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Pelicula> filtrada = filter(PELICULAS_PELICULAS, newText);
        return false;
    }

    private ArrayList<Pelicula> filter(ArrayList<Pelicula> pelis, String query) {
        query = query.toLowerCase();
        final ArrayList<Pelicula> filteredModelList = new ArrayList<>();
        for (Pelicula peli : pelis) {
            final String text = peli.getNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(peli);
            }
        }
        return filteredModelList;
    }

}
