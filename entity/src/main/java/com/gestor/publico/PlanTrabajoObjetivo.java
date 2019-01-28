/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "plan_trabajo_objetivo")
@NamedQueries({
    @NamedQuery(name = "PlanTrabajoObjetivo.findAll", query = "SELECT obj FROM PlanTrabajoObjetivo obj")})
public class PlanTrabajoObjetivo implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
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
      
    @Column(name = "nombre")
    private String nombre;    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plan_trabajo_objetivo")
    private List<PlanTrabajoPrograma> programaList = new ArrayList<>();
    private PlanTrabajoPrograma programa = new PlanTrabajoPrograma();
       
    public PlanTrabajoObjetivo() {        
    }

    public PlanTrabajoObjetivo(Integer codEstablecimiento, Integer codPlantrabajo, Integer codObjetivo, String nombre) {
        this.codEstablecimiento = codEstablecimiento;
        this.codObjetivo = codObjetivo;
        this.codPlantrabajo = codPlantrabajo;
        this.nombre = nombre;
    }    

    public Integer getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(Integer codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public Integer getCodPlantrabajo() {
        return codPlantrabajo;
    }

    public void setCodPlantrabajo(Integer codPlantrabajo) {
        this.codPlantrabajo = codPlantrabajo;
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

    public List<PlanTrabajoPrograma> getProgramaList() {
        return programaList;
    }

    public void setProgramaList(List<PlanTrabajoPrograma> programaList) {
        this.programaList = programaList;
    }

    public PlanTrabajoPrograma getPrograma() {
        return programa;
    }

    public void setPrograma(PlanTrabajoPrograma programa) {
        this.programa = programa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codObjetivo != null ? codObjetivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTrabajoObjetivo)) {
            return false;
        }
        PlanTrabajoObjetivo other = (PlanTrabajoObjetivo) object;
        if ((this.codObjetivo == null && other.codObjetivo != null) || (this.codObjetivo != null && !this.codObjetivo.equals(other.codObjetivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.PlanTrabajoObjetivo[ codObjetivo=" + codObjetivo + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
