package com.example.pruebasrecordatorios;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
/** no se usa */
public class LgDConsultaActivity extends AppCompatActivity {
    //"http://200.1.1.178/sysMision/webService/login.php"
    private TextView txtRecibidor;
    private Button btnProbar,btnVer;
    private static JSONArray jsonArray;
    int numero = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lg_d_consulta);
        init();
        listeners();
    }
    private class peticionAsincrona extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String resultado = inicio("gplatas","123456789");
                txtRecibidor.setText(resultado);
            } catch (IOException e) {
                e.getMessage();
            }
            return null;
        }
    }
    private void listeners() {
        btnProbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                 /*Thread tr = new Thread(){
                    @Override
                    public void run(){
                        //lblAlertaP.setText("");
                        //String pass = txtPass.getText().toString();
                        String resultado = "";
                        /*if (txtUsuario.getText().toString().isEmpty() || txtPass.getText().toString().isEmpty()){
                            lblAlertaP.setTextColor(Color.RED);
                            lblAlertaP.setText("Uno de los campos está vacío");
                        }else{*/
                        /*try {
                            resultado = inicio("gplatas","123456789");

                        } catch (IOException e) {
                            e.getMessage();
                        }
                        try {
                            jsonArray = new JSONArray(resultado);
                            numero += 1;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //txtRecibidor.setText(resultado);
                            /*try {
                                JSONArray j = new JSONArray(resultado);
                                try {
                                    jsonObject=j.getJSONObject(0);
                                    if (jsonObject.getString("autenticado").equals("SI")){
                                        guardar.id = jsonObject.getString("id");
                                        guardar.nombrePersona = jsonObject.getString("nombrePersona");
                                        guardar.idSucursal = jsonObject.getString("idSede");
                                        guardar.usuario = usuario;
                                        guardar.pass  = pass;
                                        Intent intent = new Intent(v.getContext(), inicio.class);
                                        startActivityForResult(intent,0);
                                    }else{
                                        lblAlertaP.setText("Usuario o contraseña incorrectos");
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/
                        /*Intent intent = new Intent(LgDConsultaActivity.this,MainActivity.class);
                        startActivity(intent);*/
                   /* }
                };
                tr.start();*/
                pruebaConectar();
            }
        });
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtRecibidor.setText(jsonArray.toString()+ "\n veces: "+ numero);
            }
        });
    }

    private void init() {
        txtRecibidor = (TextView) findViewById(R.id.txtRecibidor);
        btnProbar = (Button) findViewById(R.id.btnProbar);
        btnVer = (Button) findViewById(R.id.btnVerConsulta);
    }

    public static String inicio(String usuario, String pass) throws IOException, NetworkOnMainThreadException {
        URL url = null;
        try {
            url = new URL("http://200.1.1.178/sysMision/webService/login.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String resultado = null;
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("usuario", ""+usuario);
        params.put("pass", ""+pass);
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
                    "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length",
                String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();

        while((line=reader.readLine()) != null){
            sb.append(line);
        }
        reader.close();
        resultado = sb.toString();
        return resultado;
    }

    private void pruebaConectar(){
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, "http://200.1.1.178/sysMision/webService/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            txtRecibidor.setText(new JSONArray(response).getJSONObject(0).getString("autenticado"));
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new Hashtable<String,String>();
                parametros.put("usuario","gplatas");
                parametros.put("pass","123456789");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LgDConsultaActivity.this);
        requestQueue.add(stringRequest);
    }
}