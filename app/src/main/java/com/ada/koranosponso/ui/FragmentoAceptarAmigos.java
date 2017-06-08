package com.ada.koranosponso.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.ResponderSolicitudInterface;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Amigos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class FragmentoAceptarAmigos extends Fragment implements ResponderSolicitudInterface {
    View view;
    private String username, direccion = " ", idUsuario, idUsuarioA, userF, tokenF, imagen = "";
    private int id_usuarioA;
    private AdaptadorAceptarAmigos adaptador;
    private RecyclerView reciclador;
    private ProgressDialog pd;
    private LinearLayoutManager layoutManager;
    private static ArrayList<Amigos> amigos;
    private ActividadPrincipal ap;

    public FragmentoAceptarAmigos() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.actividad_principal, container, false);
        view = inflater.inflate(R.layout.fragmento_aceptar_amigos, container, false);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userF = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userF);
        tokenF = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenF);
        idUsuario = String.valueOf(sharedPreferences.getString(Constantes.IDUSUARIO_SHARED_PREF, idUsuario));
        amigos = new ArrayList<Amigos>();
        showProgressDialog("CARGANDO", "");
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.VER_SOLICITUD);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);
                    JSONArray usuarios;

                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        usuarios = json.getJSONArray("usuarios");
                        for(int i = 0; i < usuarios.length(); i++) {
                            username = usuarios.getJSONObject(i).getString("username");
                            id_usuarioA = usuarios.getJSONObject(i).getInt("id_usuario");
                            amigos.add(i,new Amigos(username, id_usuarioA, imagen, direccion));
                        }
                        crearAdaptardor();
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

    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this.getActivity());
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();

    }

    public void crearAdaptardor(){
        adaptador = new AdaptadorAceptarAmigos(amigos, this);
        reciclador.setAdapter(adaptador);
    }

    @Override
    public void aceptarAmigo(Amigos amigos, int position) {
        final HashMap<String, String> hashMap = new HashMap<>();
        idUsuarioA = String.valueOf(amigos.getIdUsuario());
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        hashMap.put(Constantes.KEY_IDUSUARIOA, idUsuarioA);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.ACEPTAR_SOLICITUD);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    JSONArray usuarios;
                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {

                    } else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

        });
    }

    @Override
    public void denegarAmigo(Amigos amigos, int position) {
        idUsuarioA = String.valueOf(amigos.getIdUsuario());
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        hashMap.put(Constantes.KEY_IDUSUARIOA, idUsuarioA);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.DENEGAR_SOLICITUD);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    JSONArray usuarios;
                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {

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
