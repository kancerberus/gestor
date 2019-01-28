/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author franj
 */
@Entity
@Table(name = "programa")
@NamedQueries({
    @NamedQuery(name = "Programa.findAll", query = "SELECT pro FROM Programa pro")})
public class PlanTrabajoPrograma implements Serializable, Cloneable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codEstablecimiento;
    @Basic(optional = false)
    @Column(name = "cod_plan_trabajo")
    private Integer codPlantrabajo;
    @Basic(optional = false)
    @Column(name = "cod_objetivo")
    private Integer codObjetivo;
    @Basic(optional = false)
    @Column(name = "cod_programa")
    private Integer codPrograma;        
    @Column(name = "nombre")
    private String nombre;   
    @Column(name = "peso")
    private Integer peso;   

    public PlanTrabajoPrograma() {
    }

    
    public PlanTrabajoPrograma(int codEstablecimiento, int codPlantrabajo, int codObjetivo, int codPrograma, int peso, String nombre) {
        this.codEstablecimiento = codEstablecimiento;
        this.codPlantrabajo = codPlantrabajo;
        this.codPrograma= codPrograma;
        this.codObjetivo = codObjetivo;
        this.peso = peso;
        this.nombre = nombre;        
    }

    public Integer getCodPlantrabajo() {
        return codPlantrabajo;
    }

    public void setCodPlantrabajo(Integer codPlantrabajo) {
        this.codPlantrabajo = codPlantrabajo;
    }

    public Integer getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(Integer codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(Integer codPrograma) {
        this.codPrograma = codPrograma;
    }

    public Integer getCodObjetivo() {
        return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTrabajoPrograma)) {
            return false;
        }
        PlanTrabajoPrograma other = (PlanTrabajoPrograma) object;
        if ((this.codPrograma == null && other.codPrograma != null) || (this.codPrograma != null && !this.codPrograma.equals(other.codPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.Programa[ cod_programa=" + codPrograma + " ]";
    }
    
}
