package edu.uade.api.tpo.views;

import java.util.List;

public class EdificioConUnidadesView {

    private int codigo;
    private String nombre;
    private String direccion;

    private List<UnidadSinEdificioView> unidades;

    public EdificioConUnidadesView () {}

    public EdificioConUnidadesView(int codigo, String nombre, String direccion, List<UnidadSinEdificioView> unidades) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.unidades=unidades;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<UnidadSinEdificioView> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadSinEdificioView> unidades) {
        this.unidades = unidades;
    }

    public String toString() {
        return codigo + " " + nombre;
    }
}
