package com.example.demo;

public class ComponenteNomina {

    private Presupuesto presupuesto;
    private TipoComponenteNomina tipoComponenteNomina;
    private double valor;

    public ComponenteNomina(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ComponenteNomina() {
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public TipoComponenteNomina getTipoComponenteNomina() {
        return tipoComponenteNomina;
    }

    public void setTipoComponenteNomina(TipoComponenteNomina tipoComponenteNomina) {
        this.tipoComponenteNomina = tipoComponenteNomina;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
