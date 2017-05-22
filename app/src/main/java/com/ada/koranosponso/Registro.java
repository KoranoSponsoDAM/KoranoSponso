package com.ada.koranosponso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog pd;
    private EditText etUser, etPassword, etPasswordConfirm, etEmail;
    static String username, password, passwordConfirm, email;
    Button btnRegistro;
    private static JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnRegistro) {
            newUser();
        }
    }

    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this);
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();
    }

    public void newUser() {
        username = etUser.getText().toString();
        password = etPassword.getText().toString();
        passwordConfirm = etPasswordConfirm.getText().toString();
        email = etEmail.getText().toString();
        if (username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || email.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.vacio, Toast.LENGTH_SHORT).show();
        } else if (password.equals(passwordConfirm)) {
            showProgressDialog("CARGANDO", "Ingresando...");
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(Constantes.KEY_USER, username);
            hashMap.put(Constantes.KEY_PASSWORD, password);
            hashMap.put(Constantes.KEY_EMAIL, email);
            RestAPIWebServices res = new RestAPIWebServices(this, hashMap, Urls.REGISTER);
            res.responseApi(new RestAPIWebServices.VolleyCallback() {
                @Override
                public View onSuccess(String response) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);


                        //If we are getting success from server
                        if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {


                            Toast.makeText(Registro.this, json.getString("message"), Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            Intent intent = new Intent(Registro.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Registro.this, json.getString("message"), Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                        //pd.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }else{
            Toast.makeText(Registro.this, R.string.errorPass, Toast.LENGTH_LONG).show();
        }
    }


    public void onBackPressed() {
        finish();
    }//Esto es para volver a la actividad anterior, esa login()MIRAR
}
