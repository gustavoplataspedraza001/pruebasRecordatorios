package com.example.pruebasrecordatorios.SqlLm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteUsuarios extends SQLiteOpenHelper {
    private static final int version_BD = 1;
    private static final String NOMBRE_TABLA = "users";
    private static final String NOMBRE = "nombre";
    private static final String PASS = "pass";
    /*separador de usuarios y departamentos(columnas solamente)*/
    private static final String NOMBRE_TABLA_DEPARTAMENTO = "departamentos";
    private static final String NOMBRE_DEPARTAMENTO = "nombre_departamento";
    private static final String DEPARTAMENTO_REALIZADO = "realizado";
    /*separador de tareas y departamentos(columnas solamente)*/
    private static final String NOMBRE_TABLA_TAREA = "tareas";
    private static final String ID_DEPARTAMENTO = "ID_DEPARTAMENTO";
    private static final String NOMBRE_TAREA = "nombre_tarea";
    private static final String TAREA_REALIZADA = "realizada";
    /*---------------------------------------------------*/
    private static final String ID_USUARIO = "ID";
    public SqliteUsuarios(@Nullable Context context)
    {
        super(context, "lmDB.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablaUsuarios = "CREATE TABLE users ( ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, pass TEXT)";
        String tablaDepartamentos = "CREATE TABLE departamentos(ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre_departamento TEXT, realizado TEXT,identificador TEXT)";
        String tablaTareas = "CREATE TABLE tareas (ID INTEGER PRIMARY KEY AUTOINCREMENT,ID_DEPARTAMENTO TEXT, nombre_tarea TEXT, realizada TEXT)";
        String tablaFecha = "CREATE TABLE fecha(ID INTEGER PRIMARY KEY AUTOINCREMENT, fecha_guardada TEXT, validar TEXT)";
        db.execSQL(tablaFecha);
        db.execSQL(tablaUsuarios);
        db.execSQL(tablaDepartamentos);
        db.execSQL(tablaTareas);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String tabla = "CREATE TABLE users ( ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, pass TEXT)";
        String tablaDepartamentos = "CREATE TABLE departamentos(ID INTEGER PRIMARY KEY AUTOINCREMENT, nombre_departamento TEXT, realizado TEXT,identificador TEXT)";
        String tablaTareas = "CREATE TABLE tareas (ID INTEGER PRIMARY KEY AUTOINCREMENT,ID_DEPARTAMENTO TEXT, nombre_tarea TEXT, realizada TEXT)";
        String tableFecha = "CREATE TABLE fecha (ID INTEGER PRIMARY KEY AUTOINCREMENT, fecha_guardada TEXT, validar TEXT)";
        db.execSQL("DROP TABLE IF EXISTS lamision.fecha");
        db.execSQL("DROP TABLE IF EXISTS lamision.users");
        db.execSQL("DROP TABLE IF EXISTS lamision.departamentos");
        db.execSQL("DROP TABLE IF EXISTS lamision.tareas");
        db.execSQL(tableFecha);
        db.execSQL(tabla);
        db.execSQL(tablaDepartamentos);
        db.execSQL(tablaTareas);
    }
    public void terminarTurno(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM departamentos");
        db.execSQL("DELETE FROM tareas");
    }
    public void borrarUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM users");
    }
    public boolean actualizarFecha(String fechaGuardar){
        String query = "SELECT * FROM fecha WHERE validar = ?";
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery(query, new String[]{"validado"});
        ContentValues cv2 = new ContentValues();
        cv2.put("fecha_guardada",fechaGuardar);
        if (cursor.moveToFirst()){
            long update = db2.update("fecha",cv2, "validar = ?", new String[]{"validado"});
            if (update == -1){
                return false;
            }else{
                return true;
            }
        }else{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("validar","validado");
            cv.put("fecha_guardada",fechaGuardar);
            long insert = db.insert("fecha", null,cv);
            if (insert == -1){
                return false;
            }else{
                return true;
            }
        }
    }
    public boolean insertarTarea( String id_departamento,String nombre_tarea,String realizada){
        String query = "SELECT * FROM "+ NOMBRE_TABLA_TAREA + " WHERE nombre_tarea = ? AND id_departamento = ?";
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery(query, new String[]{nombre_tarea, String.valueOf(id_departamento)});
        ContentValues cv2 = new ContentValues();
        cv2.put(ID_DEPARTAMENTO,id_departamento);
        cv2.put(NOMBRE_TAREA,nombre_tarea);
        cv2.put(TAREA_REALIZADA,realizada);
        if (cursor.moveToFirst()){
            long update = db2.update(NOMBRE_TABLA_TAREA,cv2, "nombre_tarea = ?", new String[]{nombre_tarea});
            if (update == -1){
                return false;
            }else{
                return true;
            }
        }else{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(ID_DEPARTAMENTO,id_departamento);
            cv.put(NOMBRE_TAREA,nombre_tarea);
            cv.put(TAREA_REALIZADA,realizada);
            long insert = db.insert(NOMBRE_TABLA_TAREA, null,cv);
            if (insert == -1){
                return false;
            }else{
                return true;
            }
        }
    }
    public boolean insertarDepartamento(String nombre_departamento,String realizado,String identificador){
        String query = "SELECT * FROM "+ NOMBRE_TABLA_DEPARTAMENTO + " WHERE nombre_departamento = ?";
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery(query,new String[]{nombre_departamento});
        ContentValues cv2 = new ContentValues();
        cv2.put(NOMBRE_DEPARTAMENTO,nombre_departamento);
        cv2.put(DEPARTAMENTO_REALIZADO,realizado);
        if (cursor.moveToFirst()){
            long update = db2.update(NOMBRE_TABLA_DEPARTAMENTO, cv2,"nombre_departamento = ?", new String[]{nombre_departamento});
            if (update == -1){
                return false;
            }else{
                return true;
            }
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(NOMBRE_DEPARTAMENTO,nombre_departamento);
            cv.put(DEPARTAMENTO_REALIZADO,realizado);
            cv.put("identificador",identificador);
            long insert = db.insert(NOMBRE_TABLA_DEPARTAMENTO,null,cv);
            if (insert == -1){
                return false;
            }else{
                return true;
            }
        }
    }
    public boolean validarExistencias(){
        String query = "SELECT * FROM "+ NOMBRE_TABLA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }
    public boolean insertar(String id, String nombre,String pass) {
        String query = "SELECT * FROM "+ NOMBRE_TABLA + " WHERE nombre = ?";
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery(query,new String[]{nombre});
        ContentValues cv2 = new ContentValues();
        cv2.put(NOMBRE,nombre);
        cv2.put(PASS,pass);
        if (cursor.moveToFirst()){
            long update = db2.update(NOMBRE_TABLA, cv2,"nombre = ?", new String[]{nombre});
            if (update == -1){
                return false;
            }else{
                return true;
            }
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(NOMBRE,nombre);
            cv.put(PASS,pass);
            long insert = db.insert(NOMBRE_TABLA,null,cv);
            if (insert == -1){
                return false;
            }else{
                return true;
            }
        }
    }
    public Cursor getEveryone(){
        String query = "SELECT * FROM "+ NOMBRE_TABLA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public Cursor todosDeptos(String nombre_depto){
        String queryDeptos = "SELECT * FROM "+ NOMBRE_TABLA_DEPARTAMENTO + " WHERE nombre_departamento = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryDeptos,new String[]{nombre_depto});
        return cursor;
    }
    public boolean actualizarDepto(String realizado, String id_depto){
        SQLiteDatabase db2 = this.getReadableDatabase();
        ContentValues cv2 = new ContentValues();
        cv2.put(DEPARTAMENTO_REALIZADO,realizado);
        long update = db2.update(NOMBRE_TABLA_DEPARTAMENTO, cv2,"identificador = ?", new String[]{id_depto});
        if (update == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean actualizarTarea(){
        return false;
    }
    public Cursor filtrarDeptos(String nombre_depto,String identificador){
        String query = "SELECT * FROM "+ NOMBRE_TABLA_DEPARTAMENTO + " WHERE nombre_departamento = ? AND realizado = ? AND identificador = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{nombre_depto,"si",identificador});
        return cursor;
    }
    public Cursor filtrarTareas(String nombre_tarea,String id_depto){
        String query = "SELECT * FROM "+ NOMBRE_TABLA_TAREA + " WHERE nombre_tarea = ? AND ID_DEPARTAMENTO = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{nombre_tarea,id_depto});
        return cursor;
    }
    public Cursor filtrarFecha(String fechaActual){
        String query = "SELECT * FROM fecha WHERE fecha_guardada = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{fechaActual});
        return cursor;
    }
    public Cursor todosTareas(){
        String query = "SELECT * FROM " + NOMBRE_TABLA_TAREA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public Cursor todosTareas2(String id_depto){
        String query = "SELECT * FROM " + NOMBRE_TABLA_TAREA + " WHERE ID_DEPARTAMENTO = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{id_depto});
        return cursor;
    }
}
