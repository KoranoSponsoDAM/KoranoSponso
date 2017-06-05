package com.ada.koranosponso.ui;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.ada.koranosponso.modelo.Amigos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    String userL, tokenL, idUsuario;
    NavigationView navigationView;
    Menu menu;
    MenuItem nav_amigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        agregarToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        inicializarAmigos();
    }

    private void agregarToolbar() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        nav_amigos = menu.findItem(R.id.item_amigos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        nav_amigos = menu.findItem(R.id.item_amigos);

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta();
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.item_amigos:
                nav_amigos.setTitle("Amigos");
                fragmentoGenerico = new FragmentoAmigos();
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.item_videos:
                fragmentoGenerico = new FragmentoCategorias();
                setTitle(itemDrawer.getTitle());
                break;
            case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

    }

    public void inicializarAmigos(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        nav_amigos = menu.findItem(R.id.item_amigos);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userL = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userL);
        tokenL = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenL);
        idUsuario = String.valueOf(sharedPreferences.getString(Constantes.IDUSUARIO_SHARED_PREF, idUsuario));
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userL);
        hashMap.put(Constantes.KEY_TOKEN, tokenL);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap, Urls.VER_SOLICITUD);
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
                        if(usuarios.length() != 0){
                            nav_amigos.setTitle("*Amigos");
                            if (navigationView != null) {
                                prepararDrawer(navigationView);
                                // Seleccionar item por defecto
                                seleccionarItem(navigationView.getMenu().getItem(0));
                            }
                        }else{
                            prepararDrawer(navigationView);
                            // Seleccionar item por defecto
                            seleccionarItem(navigationView.getMenu().getItem(0));
                        }
                    } else {
                        prepararDrawer(navigationView);
                        // Seleccionar item por defecto
                        seleccionarItem(navigationView.getMenu().getItem(0));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Inicializamos el nombre de usuario y lo que queramos
                TextView txt = (TextView) findViewById(R.id.txtUsuarioC);
                SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
                String nombre = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userL);
                txt.setText("Nombre de usuario: " + nombre);
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
