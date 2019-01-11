/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

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
@Table(name = "tecnica_capacitacion")
@NamedQueries({
    @NamedQuery(name = "TecnicaCapacitacion.findAll", query = "SELECT tc FROM TecnicaCapacitacion tc")})
public class TecnicaCapacitacion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tecnica")
    private Integer codtecnica;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public TecnicaCapacitacion() {
    }

    
    public TecnicaCapacitacion(int codtecnica, String nombre) {
        this.codtecnica= codtecnica;
        this.nombre = nombre;
        
    }

    public Integer getCodTecnica() {
        return codtecnica;
    }

    public void setCodTecnica(Integer codtecnica) {
        this.codtecnica = codtecnica;
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
        if (!(object instanceof TecnicaCapacitacion)) {
            return false;
        }
        TecnicaCapacitacion other = (TecnicaCapacitacion) object;
        if ((this.codtecnica == null && other.codtecnica != null) || (this.codtecnica != null && !this.codtecnica.equals(other.codtecnica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.TecnicaCapacitacion[ cod_tecnica=" + codtecnica + " ]";
    }
    
}
