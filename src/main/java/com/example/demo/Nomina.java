package com.example.demo;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Nomina {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private double totalNomina;
    private double alquiler;
    private double otrosGastos;
    private double comida;
    private double coche;

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

    public double getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(double alquiler) {
        this.alquiler = alquiler;
    }

    public double getOtrosGastos() {
        return otrosGastos;
    }

    public void setOtrosGastos(double otrosGastos) {
        this.otrosGastos = otrosGastos;
    }

    public double getComida() {
        return comida;
    }

    public void setComida(double comida) {
        this.comida = comida;
    }

    public double getCoche() {
        return coche;
    }

    public void setCoche(double coche) {
        this.coche = coche;
    }

    public double getAhorro() {
        double ahorro = this.totalNomina - this.alquiler - this.coche - this.comida - this.otrosGastos;
        if (ahorro <= 0) {
            return ahorro;
        } else {
            BigDecimal aux = BigDecimal.valueOf(ahorro);
            aux = aux.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
            return aux.doubleValue();
        }
    }

    public double getOcio() {
        if(getAhorro() <= 0) {
            return 0;
        } else {
            BigDecimal ocio = BigDecimal.valueOf(totalNomina - alquiler - coche - comida - otrosGastos - getAhorro());
            ocio = ocio.setScale(2, RoundingMode.HALF_DOWN);
            return ocio.doubleValue();
            }
    }
}
