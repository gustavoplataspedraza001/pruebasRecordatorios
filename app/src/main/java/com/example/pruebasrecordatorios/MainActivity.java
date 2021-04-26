package com.example.pruebasrecordatorios;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebasrecordatorios.SqlLm.SqliteUsuarios;
import com.example.pruebasrecordatorios.retrofit.ClienteConexion;
import com.example.pruebasrecordatorios.retrofit.ServicioPrueba;
import com.example.pruebasrecordatorios.retrofit.constantes;
import com.example.pruebasrecordatorios.retrofit.responses.ResponseLogin;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.pruebasrecordatorios.retrofit.constantes.passEncriptado;
public class MainActivity extends AppCompatActivity {
    private Button btnInicio;
    private TextInputEditText txtPass,txtUsuario;
    /***clases retrofit**/
    ClienteConexion clienteConexion;
    ServicioPrueba servicioPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        initRetrofit();
        listeners();
    }
    private void initRetrofit() {
        clienteConexion = ClienteConexion.getInstancia();
        servicioPrueba = clienteConexion.getServicioPrueba();
    }
    private void peticionRetrofit(String usuario,String pass){

        if (usuario.isEmpty() || pass.isEmpty()){
            if (usuario.isEmpty()){
                txtUsuario.setError("Este campo está vacío");
            }else if (pass.isEmpty()){
                txtPass.setError("Este campo está vacío");
            }
        }else {
            Call<List<ResponseLogin>> responsePeticionCall = servicioPrueba.peticionLogin(usuario, pass);
            responsePeticionCall.enqueue(new Callback<List<ResponseLogin>>() {
                @Override
                public void onResponse(Call<List<ResponseLogin>> call, retrofit2.Response<List<ResponseLogin>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().get(0).getAutenticado().equals("SI")) {
                            /****/
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                            Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                            intent.putExtra("idUsuario", response.body().get(0).getId());
                            intent.putExtra("nombrePersona", response.body().get(0).getNombrePersona());
                            intent.putExtra("nombreUsuario", response.body().get(0).getNombreUsuario());
                            intent.putExtra("sucursal", response.body().get(0).getIdSede());
                            intent.putExtra("tipoLogin","primeraVez");
                            SqliteUsuarios sqliteUsuarios = new SqliteUsuarios(getApplicationContext());
                            sqliteUsuarios.borrarUsers();
                            sqliteUsuarios.insertar("",usuario,pass);
                            startActivity(intent, options.toBundle());
                            Activity activity = MainActivity.this;
                            activity.finish();
                        } else {
                            txtPass.setError("Usuario o contraseña incorrectos");
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error inesperado" + response.code(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ResponseLogin>> call, Throwable t) {
                    Log.e("ZZZ", t.getMessage());
                }
            });
        }
    }
    private void listeners() {
        btnInicio.setOnClickListener(view -> {
            String usuario = txtUsuario.getText().toString();
            String pass = txtPass.getText().toString();
            peticionRetrofit(usuario,pass);
        });
    }
    private void init() {
        btnInicio = (Button) findViewById(R.id.btnInicioPruebas);
        txtUsuario = (TextInputEditText) findViewById(R.id.txtUsuarioReal);
        txtPass = (TextInputEditText) findViewById(R.id.txtPassReal);
    }
}