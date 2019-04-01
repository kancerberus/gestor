/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author juliano
 */
@Embeddable
public class RelCargosEstablecimiento implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_cargo")
    private Integer codCargo;
    @Column(name = "cod_funcion")
    private Integer codFuncion;
    @Column(name = "diligenciado")
    private Boolean diligenciado;
    
    private Funciones funciones = new Funciones();
    private Cargos cargos= new Cargos();

    public RelCargosEstablecimiento() {
    }

    public RelCargosEstablecimiento(Integer codigoEstablecimiento, Integer codCargo, Integer codFuncion, Boolean diligenciado) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.codCargo = codCargo;
        this.codFuncion = codFuncion;
        this.diligenciado = diligenciado;
    }

    public Funciones getFunciones() {
        return funciones;
    }

    public void setFunciones(Funciones funciones) {
        this.funciones = funciones;
    }

    public Cargos getCargos() {
        return cargos;
    }

    public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(Integer codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Integer getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(Integer codCargo) {
        this.codCargo = codCargo;
    }

    public Integer getCodFuncion() {
        return codFuncion;
    }

    public void setCodFuncion(Integer codFuncion) {
        this.codFuncion = codFuncion;
    }

    public Boolean getDiligenciado() {
        return diligenciado;
    }

    public void setDiligenciado(Boolean diligenciado) {
        this.diligenciado = diligenciado;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoEstablecimiento;
        hash += (codCargo != null ? codCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelCargosEstablecimiento)) {
            return false;
        }
        RelCargosEstablecimiento other = (RelCargosEstablecimiento) object;
        if (this.codigoEstablecimiento != other.codigoEstablecimiento) {
            return false;
        }
        if ((this.codCargo == null && other.codCargo != null) || (this.codCargo != null && !this.codCargo.equals(other.codCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.RelCargosEstablecimiento[ codigoEstablecimiento=" + codigoEstablecimiento + ", codCargo=" + codCargo + " ]";
    }
    
}
