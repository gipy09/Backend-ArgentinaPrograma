package com.porfolio.Porfolio.Dto;

import javax.validation.constraints.NotBlank;

public class dtohys {

    @NotBlank
    private String nombreS;
    @NotBlank
    private int porcentaje;

    public dtohys() {
    }

    public dtohys(String nombre, int porcentaje) {
        this.nombreS = nombre;
        this.porcentaje = porcentaje;
    }

    public String getNombreS() {
        return nombreS;
    }

    public void setNombreS(String nombreS) {
        this.nombreS = nombreS;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
}
