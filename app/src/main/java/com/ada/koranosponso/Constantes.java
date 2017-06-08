package com.ada.koranosponso;

/**
 * Created by Alex on 30/04/2017.
 */

public class Constantes {
    public static final String API = "http://164.132.116.6/desarrollandolo.occamagenciadigital.com/alex/korano/webservices/metodos";//"http://koranosponso.000webhostapp.com/webservicesftp/metodos";
    public static final String IMAGENES = "http://164.132.116.6/desarrollandolo.occamagenciadigital.com/alex/korano/imagenes/";
    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USER = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_IDPELICULA = "id_pelicula";
    public static final String KEY_IDUSUARIO = "id_usuario";
    public static final String KEY_IDUSUARIOA = "id_usuarioA";
    public static final String KEY_IDCOMENTARIO = "id_comentario";
    public static final String KEY_IMAGEN = "imagen";
    public static final String KEY_COMENTARIO = "comentario";
    public static final String KEY_NEW_PASSWORD = "newpassword";
    public static final String KEY_NEW_PASSWORD_CONFIRM = "newpasswordconfirm";
    public static final String KEY_TOKEN = "token";




    //If server response is equal to this that means login is successful
    public static final String SUCCESS = "OK";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String USER_SHARED_PREF = "user";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String REMEMBER_SHARED_PREF = "remember";
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String TOKEN_SHARED_PREF = "token";
    public static final String IDUSUARIO_SHARED_PREF = "id_usuario";
    public static final String IMAGEN_SHARED_PREF = "imagen";

    public static final String PREF_SAVE = "prefsave";
}
