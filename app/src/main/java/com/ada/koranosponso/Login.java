package com.ada.koranosponso;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private CrearBD crearBD;
    private EditText etUsu;
    private EditText etPass;
    private CheckBox chRec;
    private final static String SETTING_USER = "setting_user";
    private final static String SETTING_PASS = "setting_pass";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        crearBD = new CrearBD(this);
        etUsu = (EditText) findViewById(R.id.etUsu);
        etPass = (EditText) findViewById(R.id.etPass);
        chRec = (CheckBox) findViewById(R.id.chRecordar);
        sharedPreferences = getApplicationContext().getSharedPreferences("daniel.martin.garcia.veterinario", Context.MODE_PRIVATE);
        String user, pass;
        user = sharedPreferences.getString(SETTING_USER,"");
        pass = sharedPreferences.getString(SETTING_PASS,"");
        if(user!="") {
            etUsu.setText(user);
            etPass.setText(pass);
            chRec.setChecked(true);
            onEntrar(etUsu);
        }
    }

    public void onEntrar(View view){
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
    }
}
