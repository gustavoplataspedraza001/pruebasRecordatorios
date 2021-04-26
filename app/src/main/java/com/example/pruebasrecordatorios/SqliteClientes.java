package com.example.pruebasrecordatorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteClientes extends SQLiteOpenHelper {
    public static final String TABLA = "CLIENTES";
    public static final String COLUMNA_ID = "ID";
    public static final String COLUMNA_NOMBRE_CLIENTE = "NOMBRE_CLIENTE";
    public static final String COLUMNA_EDAD_CLIENTE = "EDAD_CLIENTE";
    public static final String COLUMNA_ACTIVO_CLIENTE = "ACTIVO_CLIENTE";
    public SqliteClientes(@Nullable Context context) {
        super(context, "clientes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTabla = "CREATE TABLE " + TABLA + " ("+ COLUMNA_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +  COLUMNA_NOMBRE_CLIENTE + " , " + COLUMNA_EDAD_CLIENTE +" , "+ COLUMNA_ACTIVO_CLIENTE+" )";
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean agregar (ModeloPersona modeloPersona){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMNA_NOMBRE_CLIENTE, modeloPersona.getNombre());
        cv.put(COLUMNA_EDAD_CLIENTE, modeloPersona.getEdad());
        cv.put(COLUMNA_ACTIVO_CLIENTE,modeloPersona.isActivo());
        long insertar = db.insert(TABLA,null,cv);
        if (insertar == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean borrar(ModeloPersona modeloPersona){
        SQLiteDatabase db = this.getWritableDatabase();
        String consultaString = "DELETE FROM " + TABLA + " WHERE " + COLUMNA_ID + " = " + modeloPersona.getId();
        Cursor cursor = db.rawQuery(consultaString,null);
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
    public List<ModeloPersona> obtenerTodos(){
        List<ModeloPersona> regresarLista = new ArrayList<>();
        String consultaString = "SELECT * FROM "+ TABLA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaString, null);
        if (cursor.moveToNext()){
            do{
                int IDCliente = cursor.getInt(0);
                String nombreCliente = cursor.getString(1);
                int edad = cursor.getInt(2);
                boolean clienteActivo = cursor.getInt(3) == 1 ? true : false;

                ModeloPersona modeloPersona = new ModeloPersona(IDCliente,nombreCliente,edad,clienteActivo);
                regresarLista.add(modeloPersona);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return regresarLista;
    }
    public boolean actualizar(int id){
        return true;
    }
}
