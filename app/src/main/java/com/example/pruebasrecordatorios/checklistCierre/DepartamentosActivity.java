package com.example.pruebasrecordatorios.checklistCierre;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebasrecordatorios.DrawerActivity;
import com.example.pruebasrecordatorios.R;
import com.example.pruebasrecordatorios.SqlLm.SqliteUsuarios;
import com.example.pruebasrecordatorios.expandableList.AdapterExpandable;
import com.example.pruebasrecordatorios.expandableList.ExpandableListDataPump;
import com.example.pruebasrecordatorios.retrofit.ClienteConexion;
import com.example.pruebasrecordatorios.retrofit.ServicioPrueba;
import com.example.pruebasrecordatorios.retrofit.constantes;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseDepartamentos;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseTareas;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

import static java.lang.Thread.sleep;
public class DepartamentosActivity extends AppCompatActivity {
    ImageView imgDepartamentoRegresar;
    Button btnTerminarTurno;
    ListView lv_dptos;
    /**cliente Retrofit**/
    ClienteConexion clienteConexion;
    ServicioPrueba servicioPrueba;
    /**instancia slqite**/
    SqliteUsuarios sqliteUsuarios = new SqliteUsuarios(this);
    /**Variables globales**/
    int validar = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamentos);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        initRetrofit();
        listeners();
        getDptos();
        cargarFecha();
    }
    private void cargarFecha() {
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        Cursor respuesta = sqliteUsuarios.filtrarFecha(date_n);
        if (!respuesta.moveToFirst()){
            sqliteUsuarios.terminarTurno();
            boolean consulta = sqliteUsuarios.actualizarFecha(date_n);
            Intent intent = new Intent(this, DepartamentosActivity.class);
            startActivityForResult(intent,0);
            if (consulta){
                Toasty.info(this, "Comenzó un nuevo turno,\npuede hacer un checklist nuevo", Toast.LENGTH_LONG).show();
            }
        }else if (respuesta.moveToFirst() && validar == 0){
        }
    }
    private void listeners() {
        imgDepartamentoRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DepartamentosActivity.this, DrawerActivity.class);
                startActivity(intent);
            }
        });
        lv_dptos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View view1 = lv_dptos.getChildAt(position);
                TextView idDpto = view1.findViewById(R.id.txtIdDpto);
                TextView listTitulo = view1.findViewById(R.id.listTitulo);
                Intent intent = new Intent(DepartamentosActivity.this,TareasActivity.class);
                intent.putExtra("idDpto", idDpto.getText().toString());
                intent.putExtra("listTitulo", listTitulo.getText().toString());
                startActivity(intent);
            }
        });
        btnTerminarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFecha();
            }
        });
    }
    private void init() {
        imgDepartamentoRegresar = (ImageView) findViewById(R.id.imgDepartamentoRegresar);
        lv_dptos = (ListView) findViewById(R.id.lv_departamentos);
        btnTerminarTurno = (Button) findViewById(R.id.btnTerminarTurno);
    }
    private void getDptos(){
        Call<List<ResponseDepartamentos>> responsePeticionCall = servicioPrueba.peticionDepartamentos(constantes.sucursal);
        responsePeticionCall.enqueue(new Callback<List<ResponseDepartamentos>>() {
            @Override
            public void onResponse(Call<List<ResponseDepartamentos>> call, retrofit2.Response<List<ResponseDepartamentos>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().get(0).getID().equals("")) {
                        ArrayList<String> listItems = new ArrayList<String>();
                        List<HashMap<String, String>> aLista2 = new ArrayList<HashMap<String, String>>();
                        for (int i = 0; i < response.body().size(); i++) {
                            String id = response.body().get(i).getID();
                            String nombre = response.body().get(i).getNombre();
                            /****/
                            Cursor respuestaDpto = sqliteUsuarios.filtrarDeptos(nombre,id);
                            /****/
                            if (!respuestaDpto.moveToFirst()){
                                listItems.add(nombre);
                                listItems.add(id);
                                //apartado custom para lo visual en el caso de los departamentos
                                HashMap<String, String> hm2 = new HashMap<String, String>();
                                hm2.put("listTitulo", nombre);
                                hm2.put("txtIdDpto", id);
                                aLista2.add(hm2);
                                validar ++;
                            }
                        }
                        if (validar == 0){
                            if (constantes.VALIDARCHECKLIST == "") {
                                Toast.makeText(DepartamentosActivity.this, "Terminó todas las tareas", Toast.LENGTH_SHORT).show();
                                btnTerminarTurno.setVisibility(View.VISIBLE);
                                constantes.VALIDARCHECKLIST = "yaVisto";
                            }else{
                                btnTerminarTurno.setVisibility(View.VISIBLE);
                            }
                        }
                        String[] de2 = {"listTitulo", "txtIdDpto"};
                        int[] para2 = {R.id.listTitulo, R.id.txtIdDpto};
                        SimpleAdapter simpleAdapter1 = new SimpleAdapter(getBaseContext(), aLista2, R.layout.list_group, de2, para2);
                        lv_dptos.setAdapter(simpleAdapter1);
                        /****/
                    }
                     else {
                        Toast.makeText(DepartamentosActivity.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                     }
                } else{
                    Toast.makeText(DepartamentosActivity.this, "Error inesperado" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseDepartamentos>> call, Throwable t) {
                Log.e("ZZZ", t.getMessage());
            }
        });
    }
    private void initRetrofit() {
        clienteConexion = ClienteConexion.getInstancia();
        servicioPrueba = clienteConexion.getServicioPrueba();
    }
}