package com.wolfteam20.schedulemobile.data.models;

import java.util.Date;

/**
 * Created by Efrain.Bastidas on 1/8/2018.
 */

public class PeriodoAcademicoDTO {
    private String nombrePeriodo;
    private int idPeriodo;
    private boolean status;
    private Date fechaCreacion;

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}