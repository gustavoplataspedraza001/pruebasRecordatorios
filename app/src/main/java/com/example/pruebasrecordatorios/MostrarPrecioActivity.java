package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebasrecordatorios.retrofit.ClienteConexion;
import com.example.pruebasrecordatorios.retrofit.ServicioPrueba;
import com.example.pruebasrecordatorios.retrofit.responses.ResponsePeticion;
import com.example.pruebasrecordatorios.ui.slideshow.SlideshowFragment;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostrarPrecioActivity extends AppCompatActivity {
    TextView txt1, txt2,txt3,txt4,txt5;
    ServicioPrueba servicioPrueba;
    ClienteConexion clienteConexion;
    ImageButton btnImageRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_precio);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        listeners();
        iniciarRetrofit();
        extras();
    }
    private void extras() {
        RequestPeticion requestPeticion = new RequestPeticion("201","Misión Díaz Ordaz");
        String intentData = getIntent().getExtras().getString("intentData");
        String sucursal = "Misión Díaz Ordaz";
        Call<List<ResponsePeticion>> responsePeticionCall = servicioPrueba.peticion(intentData,sucursal);
        responsePeticionCall.enqueue(new Callback<List<ResponsePeticion>>() {
            @Override
            public void onResponse(Call<List<ResponsePeticion>> call, Response<List<ResponsePeticion>> response) {
                if (response.isSuccessful()) {
                    if(response.body().get(0).getExiste().equals("SI")){
                        if (response.body().get(0).getOfrtFolio().isEmpty()){
                            txt1.setText(response.body().get(0).getArtcDescripcion());
                            txt2.setText("$ "+response.body().get(0).getArtcPrecioVenta());
                            txt1.setVisibility(View.VISIBLE);
                            txt2.setVisibility(View.VISIBLE);
                        }else {
                            txt3.setText(response.body().get(0).getArtcDescripcion());
                            txt4.setText("Oferta\n $ "+response.body().get(0).getArtcPrecioOferta());
                            txt5.setText("Precio: $"+response.body().get(0).getArtcPrecioVenta()
                                    +"\nAhorro: $"+response.body().get(0).getOfrtAhorroPesos()
                                    +"\nDias restantes: "+response.body().get(0).getOfrtDiasRestantes()
                                    +"\nVigencia: "+response.body().get(0).getOfrtVigenciaFecha());
                            txt3.setVisibility(View.VISIBLE);
                            txt4.setVisibility(View.VISIBLE);
                            txt5.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toasty.error(MostrarPrecioActivity.this, "No existe el código: " + getIntent().getExtras().getString("intentData"), Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                }else{
                    Toasty.error(MostrarPrecioActivity.this, "Error inesperado: "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<ResponsePeticion>> call, Throwable t) {
                Toasty.error(MostrarPrecioActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listeners() {
        btnImageRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void iniciarRetrofit() {
        clienteConexion = ClienteConexion.getInstancia();
        servicioPrueba = clienteConexion.getServicioPrueba();
    }

    private void init() {
        txt1 = (TextView) findViewById(R.id.text1);
        txt2 = (TextView) findViewById(R.id.txtPrecio);
        txt3 = (TextView) findViewById(R.id.textDes);
        txt4 = (TextView) findViewById(R.id.txtPrecioPruebas);
        txt5 = (TextView) findViewById(R.id.txtDesLong);
        btnImageRegresar = (ImageButton) findViewById(R.id.btnImgRegresar);
    }
}