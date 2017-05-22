package com.ada.koranosponso.modelo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alex on 21/05/2017.
 */

public class Pelicula{

    private static String nombre, descripcion, idDrawable, userP, tokenP;
    //private static int idDrawable;

    public Pelicula(String nombre, String descripcion, String idDrawable) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idDrawable = idDrawable;
    }

    public static final List<Pelicula> PELICULAS_POPULARES = new ArrayList<Pelicula>();
    //public static final List<Comida> COMIDAS_POPULARES = new ArrayList<>();
    /*public static final List<Comida> BEBIDAS = new ArrayList<>();
    public static final List<Comida> POSTRES = new ArrayList<>();
    public static final List<Comida> PLATILLOS = new ArrayList<>();*/

    /*static{
        PELICULAS_POPULARES.add(new Pelicula(nombre, "sdafsdf", R.drawable.rosca ));
        /*PELICULAS_POPULARES.add(new Pelicula(3.2f, "Rosca Herbárea", R.drawable.rosca));
        PELICULAS_POPULARES.add(new Pelicula(12f, "Sushi Extremo", R.drawable.sushi));
        PELICULAS_POPULARES.add(new Pelicula(9, "Sandwich Deli", R.drawable.sandwich));
        PELICULAS_POPULARES.add(new Pelicula(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));*/

        /*PLATILLOS.add(new Comida(5, "Camarones Tismados", R.drawable.camarones));
        PLATILLOS.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.rosca));
        PLATILLOS.add(new Comida(12f, "Sushi Extremo", R.drawable.sushi));
        PLATILLOS.add(new Comida(9, "Sandwich Deli", R.drawable.sandwich));
        PLATILLOS.add(new Comida(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));

        BEBIDAS.add(new Comida(3, "Taza de Café", R.drawable.cafe));
        BEBIDAS.add(new Comida(12, "Coctel Tronchatoro", R.drawable.coctel));
        BEBIDAS.add(new Comida(5, "Jugo Natural", R.drawable.jugo_natural));
        BEBIDAS.add(new Comida(24, "Coctel Jordano", R.drawable.coctel_jordano));
        BEBIDAS.add(new Comida(30, "Botella Vino Tinto Darius", R.drawable.vino_tinto));

        POSTRES.add(new Comida(2, "Postre De Vainilla", R.drawable.postre_vainilla));
        POSTRES.add(new Comida(3, "Flan Celestial", R.drawable.flan_celestial));
        POSTRES.add(new Comida(2.5f, "Cupcake Festival", R.drawable.cupcakes_festival));
        POSTRES.add(new Comida(4, "Pastel De Fresa", R.drawable.pastel_fresa));
        POSTRES.add(new Comida(5, "Muffin Amoroso", R.drawable.muffin_amoroso));*/


    public String getNombre() {
        return nombre;
    }

    public String getDecripcion() {
        return descripcion;
    }

    public String getIdDrawable() {
        return idDrawable;
    }


}
