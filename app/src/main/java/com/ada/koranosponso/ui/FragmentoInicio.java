package com.ada.koranosponso.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Pelicula;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.ada.koranosponso.modelo.Pelicula.PELICULAS_POPULARES;


/**
 * Fragmento para la sección de "Inicio"
 */
public class FragmentoInicio extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicio adaptador;
    private static JSONObject json;
    private ProgressDialog pd;
    //static List<Pelicula> PELICULAS_POPULARES = new ArrayList<Pelicula>();
    private String nombre, descripcion, userP, tokenP, idDrawable;
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
        //mostrarPeliculas();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userP = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userP);
        tokenP = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenP);

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


                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        nombre = json.getString("nombre");
                        descripcion = json.getString("descripcion");
                        idDrawable = json.getString("imagen");
                        pd.dismiss();
                        PELICULAS_POPULARES.add(new Pelicula(nombre, descripcion, idDrawable ));
                        adaptador = new AdaptadorInicio(PELICULAS_POPULARES);
                        reciclador.setAdapter(adaptador);
                        return view;
                    } else {
                        pd.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }

                return view;
            }

        });
        return view;
    }

    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this.getActivity());
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();
    }

}

