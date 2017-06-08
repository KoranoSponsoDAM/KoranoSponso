package com.ada.koranosponso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ada.koranosponso.ui.ActividadPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private static JSONObject json;
    private EditText etUsu;
    private EditText etPass;
    static String passwordL, userL;
    private Button btnLogin, btnRegisterL;
    private CheckBox chRec;
    private ProgressDialog pd;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = Login.this.getSharedPreferences(Constantes.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        inicializarComponentes();
        String prefsav = sharedPreferences.getString(Constantes.PREF_SAVE,"");
        if(prefsav!="") {
            Intent intent = new Intent(Login.this, ActividadPrincipal.class);
            startActivity(intent);
            finish();
        }
        focoEditText();


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

    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this);
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();
    }
    public void login() {
        userL = etUsu.getText().toString();
        passwordL = etPass.getText().toString();
        showProgressDialog("CARGANDO", "Ingresando...");
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER,userL);
        hashMap.put(Constantes.KEY_PASSWORD, passwordL);
        RestAPIWebServices res = new RestAPIWebServices(this,hashMap,Urls.LOGIN);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json;
                try {
                    json = new JSONObject(response);


                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        if(chRec.isChecked()) {
                            editor.putString(Constantes.PREF_SAVE, "true");
                            editor.commit();
                        }else{
                            editor.clear();
                            editor.commit();
                        }

                        //Adding values to editor
                        editor.putBoolean(Constantes.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Constantes.USER_SHARED_PREF, userL);
                        editor.putString(Constantes.TOKEN_SHARED_PREF, json.getString("token"));
                        editor.putString(Constantes.EMAIL_SHARED_PREF, json.getString("email"));
                        editor.putString(Constantes.IDUSUARIO_SHARED_PREF, json.getString("id_usuario"));

                        //Saving values to editor
                        editor.commit();

                        //Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_LONG).show();
                        pd.dismiss();


                        //Starting profile activity
                        Intent intent = new Intent(Login.this, ActividadPrincipal.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_LONG).show();
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

    public void focoEditText(){
        etUsu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !etPass.hasFocus()) {
                    hideKeyboard(v);
                }
            }
        });
        etPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void recordarPass(View view) {
        Intent intent = new Intent(Login.this, RecuperarPass.class);
        startActivity(intent);
    }
}
