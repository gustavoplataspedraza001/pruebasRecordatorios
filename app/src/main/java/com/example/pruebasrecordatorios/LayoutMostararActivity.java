package com.example.pruebasrecordatorios;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;
/** no se usa **/
public class LayoutMostararActivity extends AppCompatActivity {
    Button btnAgregar,btnVer;
    EditText etNombre, etEdad;
    ListView lvSqliteVer;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch stCliente;
    ArrayAdapter customArrayAdapter;
    SqliteClientes sqliteClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_mostarar);
        init();
        listeners();
        mostar(sqliteClientes);

    }
    private void mostar(SqliteClientes sqliteClientes1){
        sqliteClientes1 = new SqliteClientes(LayoutMostararActivity.this);
        customArrayAdapter = new ArrayAdapter<ModeloPersona>(LayoutMostararActivity.this, android.R.layout.simple_list_item_1,sqliteClientes1.obtenerTodos());
        lvSqliteVer.setAdapter(customArrayAdapter);
    }
    private void listeners() {
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNombre.getText().toString().equals("")){
                    etNombre.setError("Llene este campo");
                }else if (etEdad.getText().toString().equals("")){
                    etEdad.setError("Llene este campo");
                }else{
                    ModeloPersona modeloPersona = new ModeloPersona(-1,etNombre.getText().toString(),Integer.parseInt(etEdad.getText().toString()),stCliente.isChecked());
                    SqliteClientes sqliteClientes = new SqliteClientes(LayoutMostararActivity.this);
                    boolean exito = sqliteClientes.agregar(modeloPersona);
                    mostar(sqliteClientes);
                    Toast.makeText(LayoutMostararActivity.this, "Exito = "+exito, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteClientes = new SqliteClientes(LayoutMostararActivity.this);
                List<ModeloPersona> todos = sqliteClientes.obtenerTodos();
                Toast.makeText(LayoutMostararActivity.this, "Funciona ver: "+todos.toString() , Toast.LENGTH_SHORT).show();
                mostar(sqliteClientes);
            }
        });
        lvSqliteVer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModeloPersona modeloPersonaClick= (ModeloPersona) parent.getItemAtPosition(position);
                SqliteClientes sqliteClientes2 = new SqliteClientes(LayoutMostararActivity.this);
                sqliteClientes2.borrar(modeloPersonaClick);
                mostar(sqliteClientes2);
                Toast.makeText(LayoutMostararActivity.this, "Borrado: "+modeloPersonaClick, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        btnAgregar = (Button) findViewById(R.id.btnAddSqlite);
        btnVer = (Button) findViewById(R.id.btnVerSqlite);
        stCliente = (Switch) findViewById(R.id.switch1);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etEdad = (EditText) findViewById(R.id.etEdad);
        lvSqliteVer = (ListView) findViewById(R.id.lstViewSqlite);
    }
}