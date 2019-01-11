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
@Table(name = "motivo_correccion")
@NamedQueries({
    @NamedQuery(name = "MotivoCorreccion.findAll", query = "SELECT mc FROM MotivoCorreccion mc")})
public class MotivoCorreccion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_motivo_correccion")
    private Integer codmotivocorreccion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public MotivoCorreccion() {
    }

    
    public MotivoCorreccion(Integer codmotivocorreccion, String nombre) {
        this.codmotivocorreccion= codmotivocorreccion;
        this.nombre = nombre;
        
    }

    public Integer getCodMotivocorreccion() {
        return codmotivocorreccion;
    }

    public void setCodMotivocorreccion(Integer codmotivocorreccion) {
        this.codmotivocorreccion = codmotivocorreccion;
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
        if (!(object instanceof MotivoCorreccion)) {
            return false;
        }
        MotivoCorreccion other = (MotivoCorreccion) object;
        if ((this.codmotivocorreccion == null && other.codmotivocorreccion != null) || (this.codmotivocorreccion != null && !this.codmotivocorreccion.equals(other.codmotivocorreccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.MotivoCorreccion[ cod_motivo_correccion=" + codmotivocorreccion + " ]";
    }
    
}
