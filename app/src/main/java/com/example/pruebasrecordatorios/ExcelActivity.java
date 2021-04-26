package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;
/**no se usa**/
public class ExcelActivity extends AppCompatActivity {
    Button btnAbrir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        Toast.makeText(this, getIntent().getExtras().getString("nombrePersona").toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, getIntent().getExtras().getString("nombreUsuario").toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, getIntent().getExtras().getString("sucursal").toString(), Toast.LENGTH_SHORT).show();
        /*init();
        listeners();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    20);
        }*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://200.1.1.178/sysMision/webService/verificar_precio.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Log.e("ZZZ", jsonArray.toString());
                            Log.e("ZZZ", jsonObject.getString("artc_descripcion"));
                            //txtUsuario.setText(jsonObject.getString("artc_descripcion"));
                            //txtPass.setText(jsonObject.getString("artc_precioVenta"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ExcelActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new Hashtable<String, String>();
                params.put("p_codigo", "201");
                params.put("sucursal", "Misión Díaz Ordaz");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ExcelActivity.this);
        requestQueue.add(stringRequest);
    }

    private void listeners() {
        btnAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory() + "/Download/"+"prueba.xlsx");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
    }
}