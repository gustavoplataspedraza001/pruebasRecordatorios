package com.example.pruebasrecordatorios.ui.gallery;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pruebasrecordatorios.MostrarActivity;
import com.example.pruebasrecordatorios.R;


import android.content.pm.PackageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class GalleryFragment extends Fragment {
    Button btnCambiar;
    ListView listView;
    JSONArray jsonArrays;
    int horas, minutos;
    public static String filtro = "hoy";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        new cargarList().execute();
        init(view);
        listeners();
        return view;
    }
    private void listeners() {
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filtro == "hoy"){
                    btnCambiar.setText("Hoy");
                    new cargarList().execute();
                    filtro = "todos";
                }else{
                    btnCambiar.setText("Todos");
                    new cargarList().execute();
                    filtro = "hoy";
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater layoutInflater = GalleryFragment.this.getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.mi_custom_layout,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();
                view1.findViewById(R.id.btnMensajeWp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PackageManager pm = getContext().getPackageManager();
                        try {
                            TextView textView = view.findViewById(R.id.txtGuardado_custom);
                            TextView textFecha1 = view.findViewById(R.id.txtFecha);
                            TextView textFechaFinal = view.findViewById(R.id.txtGuardado_custom2);
                            /////////////
                            String titulo = textView.getText().toString();
                            String fecha1 = textFecha1.getText().toString();
                            Intent wpIntent = new Intent(Intent.ACTION_SEND);
                            wpIntent.setType("text/plain");

                            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                            wpIntent.setPackage("com.whatsapp");

                            wpIntent.putExtra(Intent.EXTRA_TEXT, "Asunto: "+titulo+"\nFecha: "+fecha1);
                            startActivity(Intent.createChooser(wpIntent,"Share "));
                        }catch (ActivityNotFoundException | PackageManager.NameNotFoundException ex){
                            ex.getMessage();
                        }
                    }
                });
                view1.findViewById(R.id.btnAgregarAgenda).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView txttitulo = view.findViewById(R.id.txtGuardado_custom2);
                        TextView txtFechaInicio = view.findViewById(R.id.txtFecha);
                        ////////////////////
                        String titulo = txttitulo.getText().toString();
                        String fecha = txtFechaInicio.getText().toString();
                        ////////////
                        StringBuilder anio = new StringBuilder();
                        StringBuilder mes = new StringBuilder();
                        StringBuilder dia = new StringBuilder();
                        /////////////////
                        for (int i = 0; i <= 9; i++){
                            if (i < 2){
                                dia.append(fecha.charAt(i));
                            }else if (i > 2 && i < 5){
                                mes.append(fecha.charAt(i));
                            }else if (i > 5){
                                anio.append(fecha.charAt(i));
                            }
                        }
                        ////////////
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        horas = hourOfDay;
                                        minutos = minute;
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(0,0,0,horas,minutos);
                                        Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                        calIntent.setData(CalendarContract.Events.CONTENT_URI);
                                        calIntent.putExtra(CalendarContract.Events.TITLE,titulo);
                                        GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(anio.toString()), (Integer.parseInt(mes.toString()) - 1), Integer.parseInt(dia.toString())
                                                ,horas,minutos);
                                        GregorianCalendar calDateEnd = new GregorianCalendar(Integer.parseInt(anio.toString()), ( Integer.parseInt(mes.toString())  - 1), (Integer.parseInt(dia.toString())+1)
                                                ,(horas + 1),minutos);

                                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                                calDate.getTimeInMillis());
                                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                                calDateEnd.getTimeInMillis());
                                        startActivity(calIntent);
                                    }
                                },12,0,true);

                        timePickerDialog.updateTime(horas,minutos);
                        timePickerDialog.setMessage("Definir la hora de recordatorio");
                        timePickerDialog.show();
                        ///////////////////////////////////////
                    }
                });
            }
        });
    }


    private String extras() {
        //int imgUri = R.drawable.ic_calendar;
        List<HashMap<String,String>> aLista = new ArrayList<HashMap<String, String>>();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        int contador = 0;
        for (int j = 0; j< jsonArrays.length();j++){
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArrays.getJSONObject(j);
                SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date fecha2 = df2.parse(jsonObject.getString("fecha_inicio"));
                Date fecha = df.parse(formattedDate);
                if (fecha2.getTime()<fecha.getTime()){
                    contador = contador+1;
                }
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
        if (jsonArrays.length() == 0 || contador == jsonArrays.length()){
            btnCambiar.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            //txtNotificacion.setVisibility(View.VISIBLE);
            Toasty.info(getContext(), "No tiene eventos programados en los siguientes días, ni hoy", Toasty.LENGTH_LONG).show();
        }else{
            for (int i = 0; i<jsonArrays.length(); i++){
                try {
                    JSONObject jsonObject = jsonArrays.getJSONObject(i);
                    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                    Date fecha2 = df2.parse(jsonObject.getString("fecha_inicio"));
                    Date fecha = df.parse(formattedDate);
                    if (filtro == "hoy" && jsonObject.getString("fecha_inicio").equals(formattedDate)){
                        if (jsonObject.getString("titulo").length() > 10) {
                            String tituloCompleto = jsonObject.getString("titulo").toString();
                            StringBuilder mitad1 = new StringBuilder();
                            StringBuilder mitad2 = new StringBuilder();
                            for (int i1 = 0; i1 < tituloCompleto.length(); i1++){
                                if (i1 < 25){
                                    mitad1.append(tituloCompleto.charAt(i1));
                                }else{
                                    mitad2.append(tituloCompleto.charAt(i1));
                                }
                            }
                            HashMap<String,String> hm = new HashMap<String,String>();
                            hm.put("txtGuardado_custom2",mitad1 +"\n"+ mitad2);
                            hm.put("txtFecha",jsonObject.getString("fecha_inicio"));
                            hm.put("txtGuardado_custom","Final: "+jsonObject.getString("fecha_fin"));
                            aLista.add(hm);
                        }else{
                            HashMap<String,String> hm = new HashMap<String,String>();
                            hm.put("txtGuardado_custom2",jsonObject.getString("titulo"));
                            hm.put("txtFecha",jsonObject.getString("fecha_inicio"));
                            hm.put("txtGuardado_custom","Final: "+jsonObject.getString("fecha_fin"));
                            aLista.add(hm);
                        }
                    }else if (filtro == "todos" && fecha2.getTime() >= fecha.getTime()){
                        if (jsonObject.getString("titulo").length() > 10) {
                            String tituloCompleto = jsonObject.getString("titulo").toString();
                            StringBuilder mitad1 = new StringBuilder();
                            StringBuilder mitad2 = new StringBuilder();
                            for (int i1 = 0; i1 < tituloCompleto.length(); i1++){
                                if (i1<25){
                                    mitad1.append(tituloCompleto.charAt(i1));
                                }else{
                                    mitad2.append(tituloCompleto.charAt(i1));
                                }

                            }
                            HashMap<String,String> hm = new HashMap<String,String>();
                            hm.put("txtGuardado_custom2",mitad1 +"\n"+ mitad2);
                            hm.put("txtFecha",jsonObject.getString("fecha_inicio"));
                            hm.put("txtGuardado_custom","Final: "+jsonObject.getString("fecha_fin"));
                            aLista.add(hm);
                        }else{
                            HashMap<String,String> hm = new HashMap<String,String>();
                            hm.put("txtGuardado_custom2",jsonObject.getString("titulo"));
                            hm.put("txtFecha",jsonObject.getString("fecha_inicio"));
                            hm.put("txtGuardado_custom","Final: "+jsonObject.getString("fecha_fin"));
                            aLista.add(hm);
                        }
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
            String[] de = {"txtGuardado_custom","txtFecha","txtGuardado_custom2"};
            int[] para = {R.id.txtGuardado_custom,R.id.txtFecha,R.id.txtGuardado_custom2};
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getBaseContext(),aLista,R.layout.list,de,para);
            listView.setAdapter(simpleAdapter);
        }
        return null;
    }

    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.listView1);
        btnCambiar = (Button) view.findViewById(R.id.btnCambiar);;
        //txtNotificacion = (TextView) view.findViewById(R.id.txtNotificacion);
        //imgRegresar = (ImageButton) view.findViewById(R.id.btnRegresarRecordatorio);
    }
    public class cargarList extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String resultado = "";
            try {
                resultado = cargarListN();
                try {
                    jsonArrays = new JSONArray(resultado);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            extras();
        }
    }
    public static String cargarListN() throws IOException{
        URL url = null;
        try {
            url = new URL("http://200.1.1.178/sysMision/webService/listarAgenda.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String resultado = null;
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("id_usuario","170");
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

}