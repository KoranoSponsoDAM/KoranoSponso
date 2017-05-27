package com.ada.koranosponso.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Comida;
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
public class FragmentoCategoria extends Fragment implements LoadPeliculaInterface {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorCategorias adaptador;
    private String nombre, descripcion, userC, tokenC, idDrawable, url;
    public static ArrayList<Pelicula> PELICULAS_PELICULAS;
    public static ArrayList<Pelicula> PELICULAS_SERIES;
    public static ArrayList<Pelicula> PELICULAS_ANIMES;
    private int idPelicula;
    private ProgressDialog pd;

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
        PELICULAS_PELICULAS = new ArrayList<Pelicula>();
        PELICULAS_SERIES = new ArrayList<Pelicula>();
        PELICULAS_ANIMES = new ArrayList<Pelicula>();
        showProgressDialog("CARGANDO", "");
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userC);
        hashMap.put(Constantes.KEY_TOKEN, tokenC);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.MOSTRAR_CATEGORIAS);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);
                    JSONArray peliculas, series, animes;

                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        peliculas = json.getJSONArray("peliculas");
                        series = json.getJSONArray("series");
                        animes = json.getJSONArray("animes");
                            if(peliculas!=null) {
                                for (int i = 0; i < peliculas.length(); i++) {
                                    nombre = peliculas.getJSONObject(i).getString("nombre");
                                    descripcion = peliculas.getJSONObject(i).getString("descripcion");
                                    idDrawable = peliculas.getJSONObject(i).getString("imagen");
                                    url = peliculas.getJSONObject(i).getString("url");
                                    idPelicula = peliculas.getJSONObject(i).getInt("id_pelicula");
                                    PELICULAS_PELICULAS.add(new Pelicula(idPelicula, nombre, descripcion, idDrawable, url));
                                }
                            }
                            if(series!=null) {
                                for (int i = 0; i < series.length(); i++) {
                                    nombre = series.getJSONObject(i).getString("nombre");
                                    descripcion = series.getJSONObject(i).getString("descripcion");
                                    idDrawable = series.getJSONObject(i).getString("imagen");
                                    url = series.getJSONObject(i).getString("url");
                                    idPelicula = series.getJSONObject(i).getInt("id_pelicula");
                                    PELICULAS_SERIES.add(new Pelicula(idPelicula, nombre, descripcion, idDrawable, url));
                                }
                            }
                            if(animes!=null){
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
                        pd.dismiss();
                    } else {
                        pd.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }

                return null;
            }

        });
        pd.dismiss();


        return view;
    }

    private void crearAdaptardor(int indiceSeccion){
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

    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this.getActivity());
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();

    }
    public void verPelicula(Pelicula peliculas, int position) {
        Pelicula p = peliculas;
        Intent intent = new Intent(getActivity(), InfoPelicula.class);
        intent.putExtra("username", userC);
        intent.putExtra("token", tokenC);
        intent.putExtra("peliculas", peliculas);
        startActivity(intent);
    }

}
