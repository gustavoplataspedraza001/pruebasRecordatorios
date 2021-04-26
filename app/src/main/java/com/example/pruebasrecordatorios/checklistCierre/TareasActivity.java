package com.example.pruebasrecordatorios.checklistCierre;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebasrecordatorios.R;
import com.example.pruebasrecordatorios.SqlLm.SqliteUsuarios;
import com.example.pruebasrecordatorios.retrofit.ClienteConexion;
import com.example.pruebasrecordatorios.retrofit.ServicioPrueba;
import com.example.pruebasrecordatorios.retrofit.constantes;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseTareas;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class TareasActivity extends AppCompatActivity {
    /**views dentro de activity**/
    private ImageView iv_regresarTareas;
    private TextView txtTituloTareas;
    private ListView lv_tareas;
    /**Retrofit**/
    ClienteConexion clienteConexion;
    ServicioPrueba servicioPrueba;
    /**Variables globales**/
    String id_globalDpto,id_globalIdTarea,globalNombreDpto,globalNombreTarea;
    String nombreReal = "";
    int globalValidar = 0;
    private static int posicion;
    /**Informacion de llenado de lista***/
    SimpleAdapter simpleAdapter1;
    List<HashMap<String, String>> aLista2;
    /***instancia para sqlite**/
    SqliteUsuarios sqliteUsuarios = new SqliteUsuarios(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /**validación inicial con permisos**/
        validarPermisos();
        /**iniciar variables principales con el contenido de los views dentro del activity*/
        init();
        /**se carga sqlite**/
        sqliteCargarDepto();
        /**Generar Listeners para los valores declarados de init, se declaran eventos**/
        listeners();
        /**aqui se hace instancia a las clases de conexión para retrofit**/
        initRetrofit();
        /**se carga información extra**/
        extras();
        /**obtener tareas con retrofit y cargarlas al list view dentro del activity*/
        getTareas();
    }

    private void validarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) !=
        PackageManager.PERMISSION_GRANTED  || ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    20);
        }
    }
    private void init() {
        iv_regresarTareas = (ImageView) findViewById(R.id.iv_backTareas);
        txtTituloTareas = (TextView) findViewById(R.id.txtTituloTareas);
        lv_tareas = (ListView) findViewById(R.id.lv_tareas);
        id_globalDpto = getIntent().getExtras().getString("idDpto");
        globalNombreDpto = getIntent().getExtras().getString("listTitulo");
    }
    private void sqliteCargarDepto() {
        sqliteUsuarios.insertarDepartamento(globalNombreDpto,"no",id_globalDpto);
    }
    private void listeners() {
        iv_regresarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lv_tareas.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView txtTituloTarea = (TextView) view.findViewById(R.id.expandidosItem);
                        TextView txtIdTarea = (TextView) view.findViewById(R.id.id_tareas);
                        id_globalIdTarea = txtIdTarea.getText().toString();
                        globalNombreTarea = txtTituloTarea.getText().toString();
                        posicion = position;
                        validarPermisosTomarPhoto();
                    }
                });
    }
    private void validarPermisosTomarPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED  || ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    20);
        }else{tomarPhoto();}
    }
    private void initRetrofit() {
        clienteConexion = ClienteConexion.getInstancia();
        servicioPrueba = clienteConexion.getServicioPrueba();
    }
    private void extras() {
        String dpto = getIntent().getExtras().getString("listTitulo");
        String tituloActivity = txtTituloTareas.getText().toString();
        txtTituloTareas.setText(String.format("%s: %s", tituloActivity, dpto));
    }
    private void getTareas() {
        Call<List<ResponseTareas>> responsePeticionCall = servicioPrueba.peticionTareas(id_globalDpto, constantes.sucursal);
        responsePeticionCall.enqueue(new Callback<List<ResponseTareas>>() {
            @Override
            public void onResponse(Call<List<ResponseTareas>> call, retrofit2.Response<List<ResponseTareas>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().get(0).getID().equals("")) {
                        ArrayList<String> listItems = new ArrayList<String>();
                        aLista2 = new ArrayList<HashMap<String, String>>();
                        for (int j = 0; j < response.body().size(); j++) {
                            String id = response.body().get(j).getID();
                            String nombre = response.body().get(j).getNombre();
                            /*****/
                            Cursor responseTask = sqliteUsuarios.filtrarTareas(nombre,id_globalDpto);
                            int contador = 0 ;
                            Cursor cs = sqliteUsuarios.todosTareas2(id_globalDpto);
                            while (cs.moveToNext()){
                                contador += 1;
                            }
                            if (responseTask.moveToFirst() && globalValidar == 0 && cs.getCount() == response.body().size() && j == (response.body().size()-1)){
                                sqliteUsuarios.actualizarDepto("si",id_globalDpto);
                                redirectDeptos();
                            }else if (!responseTask.moveToFirst()){
                                /**código sin sqlite**/
                                listItems.add(nombre);
                                listItems.add(id);
                                //apartado custom para lo visual en el caso de los departamentos
                                HashMap<String, String> hm2 = new HashMap<String, String>();
                                hm2.put("listTitulo", nombre);
                                hm2.put("txtIdTareas", id);
                                aLista2.add(hm2);
                                globalValidar ++;
                                /**termina código sin sqlite**/
                            }
                        }
                        String[] de2 = {"listTitulo", "txtIdTareas"};
                        int[] para2 = {R.id.expandidosItem, R.id.id_tareas};
                        simpleAdapter1 = new SimpleAdapter(getBaseContext(), aLista2, R.layout.list_item, de2, para2);
                        lv_tareas.setAdapter(simpleAdapter1);
                    } else {
                        Toasty.error(TareasActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.error(TareasActivity.this, "Error inesperado" + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<ResponseTareas>> call, Throwable t) {
                Toasty.error(TareasActivity.this,"Error en la petición",Toasty.LENGTH_LONG).show();
            }
        });
    }
    private void redirectDeptos() {
        Toast.makeText(TareasActivity.this,"Terminó tareas de: "+globalNombreDpto, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TareasActivity.this, DepartamentosActivity.class);
        startActivity(intent);
    }
    private void tomarPhoto(){
        Intent intent;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String nombreImagen = "";
        File fileImagen =  new File(Environment.getExternalStorageDirectory(),"/LaMision/");
        boolean isCreada = fileImagen.exists();
        if (!isCreada){
            isCreada = fileImagen.mkdirs();
        }
        if (isCreada){
            nombreImagen = (System.currentTimeMillis()+".jpg");
            nombreReal = nombreImagen;
        }
        File imagen = new File(fileImagen,nombreImagen);
        Uri proveedor = FileProvider.getUriForFile(this,"com.example.pruebasecordatorios.fileprovider",imagen);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,proveedor);
        startActivityForResult(intent,1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] >=0){
        }else if (requestCode == 20 && grantResults[0] < 0){
            Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
            +"/LaMision/"+nombreReal);
            subirImagen(bitmap);
        }else if ((resultCode == RESULT_CANCELED && requestCode == 1)|| data == null){
            Toast.makeText(this, "Toma de foto cancelada", Toast.LENGTH_SHORT).show();
        }
    }
    private void subirImagen(Bitmap bitmap) {
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo Imagen...","Espere por favor",true,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://200.1.1.178/sysMision/webService/mEvidenciaCierre/guardar_evidencia.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                  loading.dismiss();
                  aLista2.remove(posicion);
                  simpleAdapter1.notifyDataSetChanged();
                  File file = new File(Environment.getExternalStorageDirectory()+"/LaMision/"+nombreReal);
                  if(file.exists()) {
                      file.delete();
                      Toast.makeText(TareasActivity.this, "borrado", Toast.LENGTH_SHORT).show();
                  }
                  sqliteUsuarios.insertarTarea(id_globalDpto,globalNombreTarea,"si");
                  if (globalValidar >= 1) {
                      globalValidar--;
                  }
                  if(globalValidar == 0) {
                      sqliteUsuarios.actualizarDepto("si",id_globalDpto);
                      redirectDeptos();
                  }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(TareasActivity.this, "Ocurrió un error al subir su imagen", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen = img(bitmap);
                String id_evidencia = id_globalIdTarea;
                String nombre = nombreReal;
                String id_usuario = constantes.ID_USUARIO;
                Map<String,String> params = new Hashtable<String,String>();
                params.put("imagen",imagen);
                params.put("nombre",nombre);
                params.put("folio",id_evidencia);
                params.put("id_usuario",id_usuario);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public String img(Bitmap bmp){
        Bitmap pruebabmp;
        float grados = 90;
        Matrix matrix = new Matrix();
        matrix.setRotate(grados);
        pruebabmp = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pruebabmp.compress(Bitmap.CompressFormat.JPEG,80,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

}