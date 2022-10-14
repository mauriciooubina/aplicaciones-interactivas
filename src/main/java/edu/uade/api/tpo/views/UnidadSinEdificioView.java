package edu.uade.api.tpo.views;

public class UnidadSinEdificioView {

    private int id;
    private String piso;
    private String numero;
    private boolean habitado;

    public UnidadSinEdificioView() {}

    public UnidadSinEdificioView(int id, String piso, String numero, boolean habitado) {
        this.id = id;
        this.piso = piso;
        this.numero = numero;
        this.habitado = habitado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isHabitado() {
        return habitado;
    }

    public void setHabitado(boolean habitado) {
        this.habitado = habitado;
    }

    public String toString() {
        return piso + " " + numero;
    }
}
