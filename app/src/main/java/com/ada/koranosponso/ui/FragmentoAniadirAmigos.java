package com.ada.koranosponso.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Interfaces.AgregarAmigoInterface;
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

public class FragmentoAniadirAmigos extends Fragment implements AgregarAmigoInterface {
    View view;
    private String username, idUsuario, idUsuarioA,  userF, tokenF, imagen;
    private int id_usuarioA;
    private AdaptadorBuscarAmigos adaptador;
    private RecyclerView reciclador;
    private ProgressDialog pd;
    private LinearLayoutManager layoutManager;
    public static ArrayList<Amigos> amigos;
    public EditText inputSearch;

    public FragmentoAniadirAmigos() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_aniadir_amigos, container, false);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                FragmentoAniadirAmigos.this.adaptador.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
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
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.MOSTRAR_USUARIOS);
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
                            imagen = usuarios.getJSONObject(i).getString("imagen");
                            amigos.add(i,new Amigos(username, id_usuarioA, imagen));
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
        adaptador = new AdaptadorBuscarAmigos(amigos, this);
        reciclador.setAdapter(adaptador);
    }

    @Override
    public void agregarAmigo(Amigos amigo, int position) {

        adaptador.notifyDataSetChanged();
        idUsuarioA = String.valueOf(amigo.getIdUsuario());
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userF);
        hashMap.put(Constantes.KEY_TOKEN, tokenF);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        hashMap.put(Constantes.KEY_IDUSUARIOA, idUsuarioA);
        RestAPIWebServices res = new RestAPIWebServices(this.getActivity(), hashMap, Urls.SOLICITUD_AMIGO);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);

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
