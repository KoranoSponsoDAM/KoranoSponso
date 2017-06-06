package com.ada.koranosponso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ada.koranosponso.ui.ActividadPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.view.View.INVISIBLE;

public class RecuperarPass extends AppCompatActivity {
    private EditText email;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);
        agregarToolbar();
    }

    public void enviarEmail(View view) {
        email = (EditText) findViewById(R.id.txtEmail);
        btnEnviar = (Button) findViewById(R.id.btnRecuperar);
        mensajeEmail();
        email.setText(" ");
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void mensajeEmail(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_EMAIL,email.getText().toString());
        RestAPIWebServices res = new RestAPIWebServices(this,hashMap,Urls.ENVIAR_EMAIL);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        Toast.makeText(RecuperarPass.this, json.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RecuperarPass.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RecuperarPass.this, json.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

        });
    }
}
