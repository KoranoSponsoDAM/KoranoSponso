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
import android.widget.Toast;

import com.ada.koranosponso.Constantes;
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
 * Fragmento para la sección de "Inicio"
 */
public class FragmentoInicio extends Fragment implements LoadPeliculaInterface{
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicio adaptador;
    private static JSONObject json;
    private ProgressDialog pd;
    public static ArrayList<Pelicula> PELICULAS_POPULARES;
    private String nombre, descripcion, userP, tokenP, idDrawable, url;
    //private int idDrawable;

    public FragmentoInicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userP = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userP);
        tokenP = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenP);
        PELICULAS_POPULARES = new ArrayList<Pelicula>();
        showProgressDialog("CARGANDO", "");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userP);
        hashMap.put(Constantes.KEY_TOKEN, tokenP);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.MOSTRAR_PELICULAS);
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
                            PELICULAS_POPULARES.add(i,new Pelicula(nombre, descripcion, idDrawable, url));
                        }
                        crearAdaptador();
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
        return view;
    }

    private void crearAdaptador() {
        adaptador = new AdaptadorInicio(PELICULAS_POPULARES, this);
        reciclador.setAdapter(adaptador);
    }

    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this.getActivity());
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();

    }public void verPelicula(Pelicula peliculas, int position) {
        Pelicula p = peliculas;
        Intent intent = new Intent(getActivity(), InfoPelicula.class);
        intent.putExtra("username", userP);
        intent.putExtra("token", tokenP);
        intent.putExtra("peliculas", peliculas);
        startActivity(intent);
    }

}

