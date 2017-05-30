package com.ada.koranosponso.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.ada.koranosponso.R;
import com.ada.koranosponso.modelo.Amigos;

import java.util.ArrayList;

public class FragmentoAniadirAmigos extends Fragment implements SearchView.OnQueryTextListener {
    View view;
    private String nombre, direccion, favorito;
    private AdaptadorBuscarAmigos adaptador;
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    public static ArrayList<Amigos> amigo;

    public FragmentoAniadirAmigos() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragmento_aniadir_amigos, container, false);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        return view;
    }

    public void buscarAmigos(String cadena){
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        //Aqui señor puto
        crearAdaptardor();
    }

    public void crearAdaptardor(){
        adaptador = new AdaptadorBuscarAmigos(amigo, this);
        reciclador.setAdapter(adaptador);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        buscarAmigos(s);
        return false;
    }
}
