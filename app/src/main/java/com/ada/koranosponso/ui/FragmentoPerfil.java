package com.ada.koranosponso.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.Login;
import com.ada.koranosponso.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Fragmento para la pestaña "PERFIL" De la sección "Mi Cuenta"
 */
public class FragmentoPerfil extends Fragment{
    TextView username, email;
    View view;
    String userL, emailL;
    ImageButton change, logout;
    SharedPreferences sharedPreferences;
    public FragmentoPerfil() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container ,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_perfil, container, false);
        inicializarUsername();
        change = (ImageButton)view.findViewById(R.id.cambioButton);
        change.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(getActivity(), ActividadCambioPassword.class);
                startActivity(intent);
            }
        });
        logout = (ImageButton)view.findViewById(R.id.logoutButton);
        logout.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                sharedPreferences =  getActivity().getSharedPreferences(Constantes.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    public void inicializarUsername(){
        username = (TextView) view.findViewById(R.id.texto_nombre);
        email = (TextView) view.findViewById(R.id.texto_email);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        String nombre = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userL);
        String correo = sharedPreferences.getString(Constantes.EMAIL_SHARED_PREF, emailL);
        username.setText(nombre);
        email.setText(correo);
    }
}
