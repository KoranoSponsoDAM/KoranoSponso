package com.ada.koranosponso.ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ada.koranosponso.Constantes;
import com.ada.koranosponso.R;
import com.ada.koranosponso.RestAPIWebServices;
import com.ada.koranosponso.Urls;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    String userL, tokenL, idUsuario;
    NavigationView navigationView;
    Menu menu;
    MenuItem nav_amigos;
    ImageView mOptionButton;
    private static String APP_DIRECTORY = "KoranoSponsoApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "KoranoSponsoApp";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private LinearLayout mRlView;
    private String mPath;
    Context context = this;
    SharedPreferences sharedPreferences;
    Bitmap bitmap;
    private int aux = 0;
    private ProgressDialog pd;

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
                            String before = nav_amigos.getTitle().toString();
                            String counter = Integer.toString(usuarios.length());
                            String s = before + "   "+counter+" ";
                            SpannableString sColored = new SpannableString( s );

                            sColored.setSpan(new BackgroundColorSpan( Color.RED ), s.length()-(counter.length()+2), s.length(), 0);
                            sColored.setSpan(new ForegroundColorSpan( Color.WHITE ), s.length()-(counter.length()+2), s.length(), 0);


                            nav_amigos.setTitle(sColored);
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
                mOptionButton = (ImageView) findViewById(R.id.imageView5);
                if(aux == 0){
                    inicializarImagen();
                    aux = 1;
                }

                mRlView = (LinearLayout) findViewById(R.id.ll_view);

                if(mayRequestStoragePermission())//saber si los permisos se han aplicado
                    mOptionButton.setEnabled(true);
                else
                    mOptionButton.setEnabled(false);

                mOptionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showOptions();
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean mayRequestStoragePermission() {//PARA LAS VERSIONES DE API SUPERIORES A 21(PERMISOS)

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)//VER SI LA APL ES MENOR QUE 21
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&//VER SI ESTAN ACEPTADOS LOS PERMISOS
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){//PEDIR PERMISOS
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",//APARECE ESTO SI ALGUNO DE LOS PERMISOS NO SON ACEPTADOS
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {//APARECE ESTE MENSAJE HASTA QUE EL USUARIO LE DE AL OK
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    //ESTOS ES PARA CUANDO HAS ENTRADO UNA VEZ Y NO HAS ACEPTADO
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            }).show();
        }else{
            //ESTO ES PARA LA PRIMERA VEZ
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ActividadPrincipal.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if(option[position] == "Tomar foto"){
                    openCamera();
                }else if(option[position] == "Elegir de galeria"){
                    //ELEGIR IMAGEN DE GALERIA
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");//Seleccionar todas las imagenes de cualquier extension
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        //FILE GUARDA LA RUTA DEL ALMACENAMIENTO INTERNO DEL MÓVIL
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;//PONER NOMBRE A LA FOTO
            String imageName = timestamp.toString() + ".jpg";//TIMESTAMP SOBRE ESCRIBE LA IMAGEN

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;//DONDE QUEREMOS QUE SE GUARDE LA IMAGEN
            //ABRIMOS LA CAMARA
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(mPath)));//EL PROBLEMA ESTA AQUÍ
                startActivityForResult(intent, PHOTO_CODE);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);//GUARDAMOS EL PATH DE LA IMAGEN SOLO CON LA CAMARA
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //SE MANTIENE LA IMAGEN
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,//PARA VER NUESTRAS FOTOS EN LA GALERIA
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });

                    //PARA CONFIGURAR QUE LA IMAGEN SELECCIONADA QUE APARECEZCA EN EL IMAGEVIEW
                   // Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    /*SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constantes.KEY_IMAGEN, mPath);*/
                    //subirImagen(bitmap);
                    //mOptionButton.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    try {
                        //Getting the Bitmap from Gallery
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                        //Setting the Bitmap to ImageView

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmapresize = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.5), (int)(bitmap.getHeight()*0.5), true);
                    subirImagen(bitmapresize, path);

                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //AQUI ES DONDE VAMOS CUANDO LE DAMOS A ACEPTAR CUANDO NOS APRAREZCA LO DE ACEPTAR PERMISOS
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ActividadPrincipal.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        //ESTO ES UNA EXPLICACION PARA EL USUARIO PARA QUE SEPA PARA QUE UTILIZAMOS LOS PERMISOS
        AlertDialog.Builder builder = new AlertDialog.Builder(ActividadPrincipal.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);//ABRE APLICACION DE CONFIGURACIONES
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            //SI LOS PERMISOS NO ESTAN CEPTADOS LE CERRAMOS LA APLICACION
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    public void subirImagen(Bitmap bitmapresize, final Uri path){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapresize.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        String typeImage = "jpg";
        final SharedPreferences sharedPreferences = this.getSharedPreferences(Constantes.SHARED_PREF_NAME, MODE_PRIVATE);
        userL = sharedPreferences.getString(Constantes.USER_SHARED_PREF, userL);
        tokenL = sharedPreferences.getString(Constantes.TOKEN_SHARED_PREF, tokenL);
        idUsuario = String.valueOf(sharedPreferences.getString(Constantes.IDUSUARIO_SHARED_PREF, idUsuario));
        showProgressDialog("CARGANDO", "Modificando foto de perfil...");
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userL);
        hashMap.put(Constantes.KEY_TOKEN, tokenL);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        hashMap.put("type_img", typeImage);
        hashMap.put("base64_string", encodedImage);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap, Urls.SUBIR_IMAGEN);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);

                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        mOptionButton.setImageURI(path);
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

    public void inicializarImagen(){
        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constantes.KEY_USER, userL);
        hashMap.put(Constantes.KEY_TOKEN, tokenL);
        hashMap.put(Constantes.KEY_IDUSUARIO, idUsuario);
        RestAPIWebServices res = new RestAPIWebServices(this, hashMap, Urls.INICILIALIZAR_IMAGEN);
        res.responseApi(new RestAPIWebServices.VolleyCallback() {
            @Override
            public View onSuccess(String response) {
                JSONObject json = null;

                try {
                    json = new JSONObject(response);

                    //If we are getting success from server
                    if (json.getString("res").equalsIgnoreCase(Constantes.SUCCESS)) {
                        String imagenS = json.getString("imagen");
                        cargarImagen(imagenS);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

        });
    }

    private void showProgressDialog(String title, String message){
        pd = new ProgressDialog(this);
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();
    }

    public void cargarImagen(String imagenS) {
         if(!imagenS.isEmpty()) {
             Glide.with(this)
                     .load(Constantes.IMAGENES_PERFIL + imagenS)
                     .diskCacheStrategy(DiskCacheStrategy.NONE)
                     .skipMemoryCache(true)
                     .into(mOptionButton);
         }else{
             Glide.with(this)
                     .load(Constantes.IMAGENES_PERFIL + "defectou.png")
                     .diskCacheStrategy(DiskCacheStrategy.NONE)
                     .skipMemoryCache(true)
                     .into(mOptionButton);
         }

    }

}
