package com.ada.koranosponso.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ActividadCambioPassword extends AppCompatActivity{

    private static JSONObject json;
    private ProgressDialog pd;
    private EditText etPass, etNewPass, etConfNewPass;
    static String passwordC, passwordNewC, passwordNewConfC, userC, tokenC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_cambio_password);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        etPass = (EditText) findViewById(R.id.etPassword);
        etNewPass = (EditText) findViewById(R.id.etNewPassword);
        etConfNewPass = (EditText) findViewById(R.id.etConfirmNewPassword);
    }


    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this);
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();
    }

    public static void getJson(String response) {
        try {
            json = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void cambiarContraseña(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userC = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userC);
        tokenC = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenC);
        passwordC= etPass.getText().toString();
        passwordNewC= etNewPass.getText().toString();
        passwordNewConfC= etConfNewPass.getText().toString();
        showProgressDialog("CARGANDO", "Cambiando contraseña...");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userC);
        hashMap.put(Constantes.KEY_PASSWORD, passwordC);
        hashMap.put(Constantes.KEY_NEW_PASSWORD, passwordNewC);
        hashMap.put(Constantes.KEY_NEW_PASSWORD_CONFIRM, passwordNewConfC);
        hashMap.put(Constantes.KEY_TOKEN, tokenC);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap, Urls.CHANGE_PASSWORD);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);


                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {

                        Toast.makeText(ActividadCambioPassword.this, json.getString("message"), Toast.LENGTH_LONG).show();
                        pd.dismiss();


                        //Starting profile activity
                        Intent intent = new Intent(ActividadCambioPassword.this, ActividadPrincipal.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(ActividadCambioPassword.this, json.getString("message"), Toast.LENGTH_LONG).show();
                        pd.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }

                return null;
            }

        });

    }
}
