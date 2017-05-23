package com.ada.koranosponso;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 09/01/2017.
 */

public class CrearBD extends SQLiteOpenHelper {
    public static final String NOMBREBD = ".sdb";
    public static final int VERSION = 1;
    public static final String NOMBRE_TABLA = "usuarios";
    public static final String USUARIO = "usuario";
    public static final String PASS = "pass";

    public CrearBD(Context context) {
        super(context, NOMBREBD, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists usuarios(usuario text primary key not null, pass text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
