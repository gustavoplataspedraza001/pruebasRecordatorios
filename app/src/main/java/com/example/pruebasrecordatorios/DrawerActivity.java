package com.example.pruebasrecordatorios;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebasrecordatorios.SqlLm.SqliteUsuarios;
import com.example.pruebasrecordatorios.retrofit.constantes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DrawerActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static String sucursalCercana = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DrawerActivity.this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        permisos();
        FirebaseMessaging.getInstance().subscribeToTopic(constantes.ID_USUARIO);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        TextView txtnombrePersona = view.findViewById(R.id.txtNombrePersona);
        TextView txtnombreUsuario = view.findViewById(R.id.txtNombreUsuario);
        if (constantes.NOMBRE_USUARIO == "" && constantes.NOMBRE_PERSONA =="" && constantes.sucursal == "" && constantes.ID_USUARIO == ""){
            constantes.NOMBRE_PERSONA = getIntent().getExtras().getString("nombrePersona");
            constantes.NOMBRE_USUARIO = getIntent().getExtras().getString("nombreUsuario");
            constantes.sucursal = getIntent().getExtras().getString("sucursal");
            constantes.ID_USUARIO = getIntent().getExtras().getString("idUsuario");
        }
        txtnombrePersona.setText(constantes.NOMBRE_PERSONA);
        txtnombreUsuario.setText(constantes.NOMBRE_USUARIO);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculo();
                String sucursal ="";
                switch (constantes.sucursal){
                    case "1":
                        sucursal = "Diaz Ordaz";
                        break;
                    case"2":
                        sucursal ="Arboledas";
                        break;
                    case"3":
                        sucursal = "Villegas";
                        break;
                    case"4":
                        sucursal= "Allende";
                        break;
                    case"5":
                        sucursal = "La Petaca";
                        break;
                }
                Snackbar.make(view, "Su sucursal es: "+sucursal+"\n"+
                        "Sucursal más cercana: "+ sucursalCercana, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_nuevoFragment, R.id.cerrar_sesion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public void calculo() {
        List<String> latitudes = new ArrayList<String>();
        List<String> longitudes = new ArrayList<String>();
        List<Double> resultados = new ArrayList<Double>();
        latitudes.add("24.866480");
        latitudes.add("24.866480");
        latitudes.add("24.857111045966377");
        latitudes.add("25.275262699127797");
        latitudes.add("24.866827940989218");
        longitudes.add("-99.573220");
        longitudes.add("-99.56197103070741");
        longitudes.add("-99.53911145741185");
        longitudes.add("-100.0179759094733");
        longitudes.add("-99.5584868016967");
        for (int i = 0; i < longitudes.size(); i++){
            double resultado = calcularDistancia(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i)));
            resultados.add(resultado);
        }
        int sucursalPosicion= 0;
        for (int j = 0; j<resultados.size();j++){
            if (resultados.get(j) == Collections.min(resultados)){
               sucursalPosicion = j;
            }
        }
        switch (sucursalPosicion){
            case 0:
                sucursalCercana = "Diaz Ordaz";
                break;
            case 1:
                sucursalCercana ="Arboledas";
                break;
            case 2:
                sucursalCercana = "Villegas";
                break;
            case 3:
                sucursalCercana = "Allende";
                break;
            case 4:
                sucursalCercana = "La Petaca";
                break;
        }
    }
    private double calcularDistancia(double latitud,double longitud){
        Location locationA = new Location("punto A");
        locationA.setLatitude(Double.parseDouble(constantes.LATITUD));
        locationA.setLongitude(Double.parseDouble(constantes.LONGITUD));
        Location locationB = new Location("punto B");
        locationB.setLatitude(latitud);
        locationB.setLongitude(longitud);
        float distancia = locationA.distanceTo(locationB);
        return distancia;
    }
    private void permisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1000);
        }else{
            validarStatus();
        }
    }
    /**dejar así, se puede llegar a necesitar*/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }*/
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void cerrar_s(MenuItem menuItem) {
        Intent intent = new Intent(DrawerActivity.this, MainActivity.class);
        startActivityForResult(intent, 0);
        Activity activity = DrawerActivity.this;
        SqliteUsuarios sqliteUsuarios = new SqliteUsuarios(getApplicationContext());
        sqliteUsuarios.borrarUsers();
        activity.finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults[0] >= 0) {
            validarStatus();
        } else if (requestCode == 1000 && grantResults[0] < 0) {
            permisos();
        }
    }
    private void validarStatus() {
        LocationManager locationManager = (LocationManager) DrawerActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean status2 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (status || status2) {
            obtener_ubicacion();
        } else {
            Toasty.info(this, "Encienda la ubicación de su dispositivo, por favor", Toasty.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }
    /**Cambiar al entregar avances**/
    private void obtener_ubicacion() {
        final ProgressDialog loading = ProgressDialog.show(this, "Obteniendo su ubicación...", "Espere un momento", true, false);
        if (!constantes.LATITUD.equals("") && !constantes.LONGITUD.equals("")) {
            loading.dismiss();
        }
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String latitud = String.valueOf(location.getLatitude());
                String longitud = String.valueOf(location.getLongitude());
                if (latitud != "" && longitud != "") {
                    loading.dismiss();
                    constantes.LATITUD = latitud;
                    constantes.LONGITUD = longitud;
                    locationManager.removeUpdates(this);
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) {
                obtener_ubicacion();
            }
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                validarStatus();
            }
        };
        /**aunque los permisos no estén definidos o mas bien sus variables no se deben borrar se siguen activando pese a no estar
         * usandose las variables**/
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permiso2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean status2 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean status3 = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
        if (status) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else if (status2) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } else {
            validarStatus();
        }
    }
    private void getCurrentLocation() {
        final ProgressDialog loading = ProgressDialog.show(this,"Obteniendo su ubicación...","Espere un momento por favor...",true,false);
        if(constantes.LATITUD != "" && constantes.LONGITUD != ""){ loading.dismiss(); }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,}, 1000);
        } else {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            boolean status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean status2 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (status){
                LocationRequest locationRequest = new LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(1000)
                        .setFastestInterval(100)
                        .setNumUpdates(1);
                LocationCallback locationCallback = new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        Location location1 = locationResult.getLastLocation();
                        constantes.LATITUD = String.valueOf(location1.getLatitude());
                        constantes.LONGITUD = String.valueOf(location1.getLongitude());
                        if(constantes.LATITUD != "" && constantes.LONGITUD != ""){
                            loading.dismiss();
                        }
                    }
                };
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.getMainLooper());
            }else if (status2){
                LocationRequest locationRequest = new LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                        .setInterval(1000)
                        .setFastestInterval(100)
                        .setNumUpdates(1);
                LocationCallback locationCallback = new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        Location location1 = locationResult.getLastLocation();
                        constantes.LATITUD = String.valueOf(location1.getLatitude());
                        constantes.LONGITUD = String.valueOf(location1.getLongitude());
                        if(constantes.LATITUD != "" && constantes.LONGITUD != ""){
                            loading.dismiss();
                        }
                    }
                };
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.getMainLooper());
            }
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        validarStatus();
    }
}