package com.example.pruebasrecordatorios;
/**no se usa **/
public class ModeloPersona {

    private int id;
    private String nombre;
    private int edad;
    private boolean activo;
    ///constructor


    public ModeloPersona(int id, String nombre, int edad, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.activo = activo;
    }

    public ModeloPersona() {
    }
    //tostring si se ocupa para mostrar el contenido dentro de la clase

    @Override
    public String toString() {
        return "ModeloPersona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", activo=" + activo +
                '}';
    }

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
