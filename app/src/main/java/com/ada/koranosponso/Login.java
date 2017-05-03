package com.ada.koranosponso;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static JSONObject json;

    //private CrearBD crearBD;
    private EditText etUsu;
    private EditText etPass;
    private String user, password;
    static String passwordL, userL;
    private Button btnLogin, btnRegisterL;
    private CheckBox chRec;
    private static ProgressDialog pd;
    //private final static String SETTING_USER = "setting_user";
    //private final static String SETTING_PASS = "setting_pass";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.ada.koranosponso", Context.MODE_PRIVATE);
        inicializarComponentes();
        /*crearBD = new CrearBD(this);
        etUsu = (EditText) findViewById(R.id.etUsu);
        etPass = (EditText) findViewById(R.id.etPass);
        chRec = (CheckBox) findViewById(R.id.chRecordar);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.ada.koranosponso", Context.MODE_PRIVATE);
        String user, pass;
        user = sharedPreferences.getString(SETTING_USER,"");
        pass = sharedPreferences.getString(SETTING_PASS,"");
        if(user!="") {
                etUsu.setText(user);
            etPass.setText(pass);
            chRec.setChecked(true);
            onEntrar(etUsu);
        }*/
    }

    private void inicializarComponentes(){
        etUsu = (EditText) findViewById(R.id.etUsu);
        etPass = (EditText) findViewById(R.id.etPass);
        chRec = (CheckBox) findViewById(R.id.chRecordar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegisterL = (Button) findViewById(R.id.btnRegisterL);
        btnRegisterL.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegisterL:
                registry();
                break;
            /*case R.id.btnRecoveryL:
                recovery();
                break;*/
        }
    }

    public static void getJson(String response) {
        try {
            json = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void registry() {
        Intent i = new Intent(this, Registro.class);

        startActivityForResult(i, 1);
    }

    public void login() {
        userL = etUsu.getText().toString();
        passwordL = etPass.getText().toString();
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.show(this, "LOADING", "Sign on...");
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put(Constantes.KEY_USER,userL);
        hashMap.put(Constantes.KEY_PASSWORD, passwordL);
        RestAPIWebServices res = new RestAPIWebServices(this,hashMap,Urls.LOGIN);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);


                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {

                        /*SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Constantes.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Adding values to editor
                        editor.putBoolean(Constantes.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Constantes.USER_SHARED_PREF, userL);
                        editor.putString(Constantes.TOKEN_SHARED_PREF, json.getString("token"));

                        //Saving values to editor
                        editor.commit();*/

                        Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_LONG).show();

                        pd.dismiss();


                        //Starting profile activity
                        Intent intent = new Intent(Login.this, prueba.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //If the server response is not success
                        //Displaying an error message on toast
                        Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /*public void onEntrar(View view){
        String usuario=etUsu.getText().toString();
        String pass=etPass.getText().toString();
        boolean entrar=false;
        if(!usuario.equals("") && !pass.equals("")) {
            if (registrado(usuario, pass) == true) {
                entrar = true;
            }else{
                verMensaje("Error en las credenciales");
            }
            if (entrar) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(chRec.isChecked()) {
                    editor.putString(SETTING_USER, usuario);
                    editor.putString(SETTING_PASS, pass);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
                //Intent intent = new Intent(this, Main.class);
                //intent.putExtra("USUARIO", etUsu.getText().toString());
                //startActivity(intent);
                finish();
            }
        }else{
            verMensaje("Campos vacíos");
        }
    }

    public boolean registrado(String usuario, String pass){
        SQLiteDatabase bd = crearBD.getReadableDatabase();
        Cursor contenido = bd.rawQuery("select usuario from usuarios where usuario='"+usuario+"' and pass='"+pass+"'", null);
        boolean existe=false;
        if (contenido.moveToNext()){
            existe=true;
        }
        contenido.close();
        bd.close();
        return existe;
    }

    public void verMensaje(String s){
        Context contexto = getApplicationContext();
        Toast toast = Toast.makeText (contexto, s, Toast.LENGTH_LONG);
        toast.show();
    }*/
}
