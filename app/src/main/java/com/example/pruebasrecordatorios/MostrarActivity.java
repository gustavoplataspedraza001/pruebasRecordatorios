package com.example.pruebasrecordatorios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MostrarActivity extends AppCompatActivity {
    Button btnHoy,btnTodos;
    ListView listView;
    int hora1,minuto1;
    public static final String[] sucursales = {"Recordatorio 1 hola", "Recordatorio 2 otro hola","Recordatorio 3 hola otro","Recordatorio 4 hola hola","Recordatorio 5"};
    public static final String[] quienenvia = {"01/02/2021","25/02/2021","25/02/2021","25/02/2021","25/02/2021"};
    public static final String [] fechas = {"24/02/2021","24/02/2021","24/02/2021","24/02/2021","24/02/2021"};
    public static final String[] sucursalesTodos = {"Recordatorio 1", "Recordatorio 2","Recordatorio 3","Recordatorio 4","Recordatorio 5","Recordatorio 6","Recordatorio 7","Recordatorio 5","Recordatorio 6","Recordatorio 7","Recordatorio 5","Recordatorio 6","Recordatorio 7"};
    public static final String[] quienenviaTodos = {"Quien envía 1", "quien envía 2","quien envía 3","quien envía 4","quien envía 5","quien envía 6","quien envía 7","quien envía 5","quien envía 6","quien envía 7","quien envía 5","quien envía 6","quien envía 7"};
    public static final String [] fechasTodos = {"22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021","22/02/2021"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        init();
        /*String hola = getIntent().getExtras().getString("pruebas");
        Toast.makeText(this, hola, Toast.LENGTH_SHORT).show();*/
        extras(sucursales,quienenvia,fechas);
        listeners();
        getView();
    }

    private void listeners() {
        btnHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHoy.setVisibility(View.GONE);
                btnTodos.setVisibility(View.VISIBLE);
                extras(sucursales,quienenvia,fechas);
            }
        });
        btnTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTodos.setVisibility(View.GONE);
                btnHoy.setVisibility(View.VISIBLE);
                extras(sucursalesTodos,quienenviaTodos,fechasTodos);
            }
        });
        /*String prueba = "01";
        int in2 = Integer.parseInt(prueba);
        Toast.makeText(this,in2+"", Toast.LENGTH_SHORT).show();*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = MostrarActivity.this.getLayoutInflater();
                View view2 = inflater.inflate(R.layout.mi_custom_layout,null);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MostrarActivity.this);
                //builder.setTitle("  Seleccionar opción");
                builder.setCancelable(true);
                //builder.setIcon(R.drawable.ic_settings);
                builder.setView(view2);
                AlertDialog dialog = builder.create();
                dialog.show();
                view2.findViewById(R.id.btnMensajeWp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PackageManager pm=getPackageManager();
                        try {
                            TextView txtTitulo = view.findViewById(R.id.txtGuardado_custom);
                            TextView txtFecha1 = view.findViewById(R.id.txtFecha);
                            TextView txtFechaFinal = view.findViewById(R.id.txtGuardado_custom2);
                            /////////////////
                            String titulo = txtTitulo.getText().toString();
                            String fecha1 = txtFecha1.getText().toString();
                            Intent waIntent = new Intent(Intent.ACTION_SEND);
                            waIntent.setType("text/plain");

                            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                            //Check if package exists or not. If not then code
                            //in catch block will be called
                            waIntent.setPackage("com.whatsapp");

                            waIntent.putExtra(Intent.EXTRA_TEXT, "Asunto: "+titulo +"\n"+"Fecha: "+ fecha1);
                            startActivity(Intent.createChooser(waIntent, "Compartir con:"));
                        }catch (ActivityNotFoundException | PackageManager.NameNotFoundException ex){
                            ex.printStackTrace();
                            Toast.makeText(MostrarActivity.this, "No tiene Whatsapp instalado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                view2.findViewById(R.id.btnAgregarAgenda).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView txtTitulo = view.findViewById(R.id.txtGuardado_custom);
                        TextView txtFecha1 = view.findViewById(R.id.txtFecha);
                        TextView txtFechaFinal = view.findViewById(R.id.txtGuardado_custom2);
                        /////////////////
                        String titulo = txtTitulo.getText().toString();
                        String fecha1 = txtFecha1.getText().toString();
                        ////////////////
                        StringBuilder anio1 = new StringBuilder();
                        StringBuilder mes1 = new StringBuilder();
                        StringBuilder dia1 = new StringBuilder();
                        //////////////
                        for (int i = 0; i <= 9;i++){
                            if (i < 2){
                                dia1.append(fecha1.charAt(i));
                            }else if(i > 2 && i < 5){
                                mes1.append(fecha1.charAt(i));
                            }else if (i>5){
                                anio1.append(fecha1.charAt(i));
                            }
                        }
                        TimePickerDialog timePickerDialog = new TimePickerDialog(MostrarActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        hora1 = hourOfDay;
                                        minuto1 = minute;

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(0,0,0,hora1,minuto1);
                                        //Toast.makeText(MostrarActivity.this, hora1+"++"+minuto1, Toast.LENGTH_SHORT).show();
                                        Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                        calIntent.setData(CalendarContract.Events.CONTENT_URI);
                                        calIntent.putExtra(CalendarContract.Events.TITLE,titulo);
                                        GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(anio1.toString()), (Integer.parseInt(mes1.toString()) - 1), Integer.parseInt(dia1.toString()),hora1,minuto1);
                                        GregorianCalendar calDateEnd = new GregorianCalendar(Integer.parseInt(anio1.toString()), ( Integer.parseInt(mes1.toString())  - 1), Integer.parseInt(dia1.toString()),(hora1+1),minuto1);

                                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                                calDate.getTimeInMillis());
                                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                                calDateEnd.getTimeInMillis());

                                        startActivity(calIntent);
                                    }
                                },12,0,true);
                        timePickerDialog.updateTime(hora1,minuto1);
                        timePickerDialog.setMessage("pruebas de texto");
                        timePickerDialog.getWindow().getAttributes().windowAnimations = R.style.MyCustomTheme;
                        timePickerDialog.show();
                    }
                });

            }
        });
    }

    private void getView() {

    }
    private void extras(String[] sc,String[] quienExtra,String[] fechasExtra) {
        int imgUri = R.drawable.ic_calendar;
        List<HashMap<String,String>> aLista = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i<sc.length; i++){
            String sucursal = sc[i];
            String quien = quienExtra[i];
            String fechaenvia = fechasExtra[i];
            HashMap<String,String> hm = new HashMap<String,String>();
            hm.put("txtGuardado_custom",sucursal);
            hm.put("txtFecha",fechaenvia);
            hm.put("txtGuardado_custom2",quien);
            aLista.add(hm);
        }
        String[] de = {"txtGuardado_custom","txtFecha","txtGuardado_custom2"};
        int[] para = {R.id.txtGuardado_custom,R.id.txtFecha,R.id.txtGuardado_custom2};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),aLista,R.layout.list,de,para);
        listView.setAdapter(simpleAdapter);
    }
    private void init() {
        listView = (ListView) findViewById(R.id.listView1);
        btnHoy = (Button) findViewById(R.id.btnCambiarHoy);
        btnTodos = (Button) findViewById(R.id.btnCambiarTodos);
        //calendarView = (CalendarView) findViewById(R.id.calendario);
    }
    public void msgEnviarTexto(){
        PackageManager pm= getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, "texto plano");
            startActivity(Intent.createChooser(waIntent, "Compartir con:"));
        }catch (ActivityNotFoundException | PackageManager.NameNotFoundException ex){
            ex.printStackTrace();
            Toast.makeText(this, "No tiene Whatsapp instalado", Toast.LENGTH_SHORT).show();
        }
    }
    public void dialogo(){

    }
}