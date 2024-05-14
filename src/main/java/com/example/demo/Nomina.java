package com.example.demo;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Nomina {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private double totalNomina;
    private List<ComponenteNomina> componentes;

    public Nomina(List<Presupuesto> presupuestos) {
        componentes = new ArrayList<>();
        for (Presupuesto presupuesto : presupuestos) {
            componentes.add(new ComponenteNomina(presupuesto));
        }
    }

    public Nomina() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotalNomina() {
        return totalNomina;
    }

    public void setTotalNomina(double totalNomina) {
        this.totalNomina = totalNomina;
    }

    public List<ComponenteNomina> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteNomina> componentes) {
        this.componentes = componentes;
    }

    public void addComponente(ComponenteNomina componente) {
        this.componentes.add(componente);
    }
}
