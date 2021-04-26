package com.example.pruebasrecordatorios;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebasrecordatorios.retrofit.ClienteConexion;
import com.example.pruebasrecordatorios.retrofit.ClienteConexionInventariado;
import com.example.pruebasrecordatorios.retrofit.ServicioPrueba;
import com.example.pruebasrecordatorios.retrofit.constantes;
import com.example.pruebasrecordatorios.retrofit.responses.ResponsePeticion;
import com.example.pruebasrecordatorios.retrofit.responses.ResponsePeticionInventarios;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class PruebaInventarioActivity extends AppCompatActivity {
    ClienteConexionInventariado clienteConexionInventariado;
    ServicioPrueba servicioPrueba;
    TextView textView,textView1,textView2,textView3,textView4,txtTitulo;
    LottieAnimationView ltAnim2;
    Button btnCapturarInventario;
    ImageButton btnRegresar;
    /***/
    String validador = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_inventario);
        init();
        extras();
        iniciarRetrofit();
        peticionRetrofit();
        listeners();
    }
    private void extras() {
        ltAnim2.setVisibility(View.VISIBLE);
        btnCapturarInventario.setVisibility(View.GONE);
    }
    private void listeners() {
        btnRegresar.setOnClickListener(view ->{
            onBackPressed();
        });
        btnCapturarInventario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                btnCapturarInventario.setVisibility(View.INVISIBLE);
                if (verificarPermisos()){
                    captura();
                }else{
                    validador = "1";
                    btnCapturarInventario.setVisibility(View.VISIBLE);
                    Toasty.info(PruebaInventarioActivity.this, "Por favor, active los permisos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 117 && resultCode == RESULT_OK){
            Toasty.info(this, "Compartida", Toast.LENGTH_SHORT).show();
        }else if (requestCode == 117 && resultCode == RESULT_CANCELED){
            Toasty.warning(this, "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }

    private void captura() {
        View view = this.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int barSize = frame.top;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widht = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        Bitmap b = Bitmap.createBitmap(b1,9,barSize,widht-barSize, height- barSize);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.setPackage("com.whatsapp");
        String path = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(),
                b,"Prueba",null);
        Uri imageUri = Uri.parse(path);
        intent.putExtra(Intent.EXTRA_STREAM,imageUri);
        try {
            startActivityForResult(intent,117);
            btnCapturarInventario.setVisibility(View.VISIBLE);
        }catch (android.content.ActivityNotFoundException exception){
            exception.printStackTrace();
            Snackbar.make(view,"No tiene instalado WhatsApp",Snackbar.LENGTH_LONG).show();
        }
    }

    public boolean verificarPermisos(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE},20);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 20 && grantResults[0] >= 0 && validador.equals("1")){
            captura();
        }
    }

    private void init() {
        textView = (TextView) findViewById(R.id.txtInventario);
        textView1 = (TextView) findViewById(R.id.txtcargadoInventario4);
        textView2 = (TextView) findViewById(R.id.txtcargadoInventario1);
        textView3 = (TextView) findViewById(R.id.txtcargadoInventario2);
        textView4 = (TextView) findViewById(R.id.txtcargadoInventario3);
        btnCapturarInventario = (Button) findViewById(R.id.btnCapturarInventario);
        ltAnim2 = (LottieAnimationView) findViewById(R.id.ltAnim2);
        btnRegresar = (ImageButton) findViewById(R.id.imageButton5);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
    }
    private void iniciarRetrofit() {
        clienteConexionInventariado = ClienteConexionInventariado.getInstancia();
        servicioPrueba = clienteConexionInventariado.getServicioPrueba();
    }
    private void peticionRetrofit(){
        RequestPeticion requestPeticion = new RequestPeticion("201","Misión Díaz Ordaz");
        String artc_articulo = getIntent().getExtras().getString("codigo");
        String fecha_inicio = getIntent().getExtras().getString("fechaInicio");
        String fecha_fin = getIntent().getExtras().getString("fechaFin");
        String sucursal = getIntent().getExtras().getString("sucursal");
        Call<List<ResponsePeticionInventarios>> responsePeticionCall = servicioPrueba.peticionInventarios(artc_articulo,fecha_inicio,fecha_fin,sucursal);
        responsePeticionCall.enqueue(new Callback<List<ResponsePeticionInventarios>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<ResponsePeticionInventarios>> call, retrofit2.Response<List<ResponsePeticionInventarios>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).getEXISTE().equals("SI")){
                        ltAnim2.cancelAnimation();
                        ltAnim2.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        btnCapturarInventario.setVisibility(View.VISIBLE);
                        /*textView1.setText(response.body().get(0).getARTCDESCRIPCION());
                        textView2.setText(response.body().get(0).getARTCCOSTO()+"\n"+
                                response.body().get(0).getARTCPRECIOVENTA()+"\n"+
                                response.body().get(0).getARTCPRECIOOFERTA()+"\n"+
                                response.body().get(0).getINVENTARIOINICIAL());
                        textView3.setText(response.body().get(0).getCANTIDADCOMPRA()+"\n"+
                                response.body().get(0).getINVSTRANS()+"\n"+
                                response.body().get(0).getINVEXDEV()+"\n"+
                                response.body().get(0).getINVTOTALENTRADAS()+"\n"+
                                response.body().get(0).getINVENTRADAS());
                        textView4.setText(response.body().get(0).getCANTIDADCOMPRA()+"\n"+
                                response.body().get(0).getINVSTRANS()+"\n"+
                                response.body().get(0).getINVEXDEV()+"\n"+
                                response.body().get(0).getINVTOTALENTRADAS()+"\n"+
                                response.body().get(0).getINVENTRADAS()+"\n"+
                                response.body().get(0).getINVENTRADAS());*/

                        textView1.setText(response.body().get(0).getARTCDESCRIPCION());
                        textView2.setText(
                                response.body().get(0).getARTCCOSTO()+"\n"+
                                        response.body().get(0).getARTCPRECIOVENTA()+"\n"+
                                        response.body().get(0).getARTCPRECIOOFERTA()+"\n"+
                                        response.body().get(0).getINVENTARIOINICIAL());
                        textView3.setText(
                                response.body().get(0).getCANTIDADCOMPRA()+"\n"+
                                        response.body().get(0).getINVETRANS()+"\n"+
                                        response.body().get(0).getINVEXDEV()+"\n"+
                                        response.body().get(0).getINVENTRADAS()+"\n"+
                                        response.body().get(0).getINVTOTALENTRADAS());
                        textView4.setText(
                                response.body().get(0).getINVSALXVE()+"\n"+
                                        response.body().get(0).getINVSTRANS()+"\n"+
                                        response.body().get(0).getINVDEVOLUCIONES()+
                                        "\n"+response.body().get(0).getINVSALIDAS()+"\n"+
                                        response.body().get(0).getINVTOTALSALIDAS()+"\n"+
                                        response.body().get(0).getINVTEORICO());
                        txtTitulo.setText("Del: "+getIntent().getExtras().getString("fechaInicioM")+ " al "+ getIntent().getExtras().getString("fechaFinM"));
                    }else{
                        Toasty.error(PruebaInventarioActivity.this, "No existe el producto", Toast.LENGTH_SHORT).show();
                        constantes.BARCODE = "";
                        onBackPressed();
                    }

                }else{
                    Toasty.error(PruebaInventarioActivity.this, "Error inesperado" + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<ResponsePeticionInventarios>> call, Throwable t) {
                Toasty.error(PruebaInventarioActivity.this,"Fallo en el pedido",Toasty.LENGTH_LONG).show();
            }
        });
    }
}