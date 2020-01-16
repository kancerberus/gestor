/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.inspecciones;

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
@Table(name = "motivo_no_uso")
@NamedQueries({
    @NamedQuery(name = "MotivoNoUso.findAll", query = "SELECT mnu FROM MotivoNoUso mnu")})
public class MotivoNoUso implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_mot_no_uso")
    private Integer codMotNoUso;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;    

    public MotivoNoUso() {
    }

    public MotivoNoUso(Integer codMotNoUso, String nombre) {
        this.codMotNoUso = codMotNoUso;
        this.nombre = nombre;
    }

    public Integer getCodMotNoUso() {
        return codMotNoUso;
    }

    public void setCodMotNoUso(Integer codMotNoUso) {
        this.codMotNoUso = codMotNoUso;
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
        if (!(object instanceof MotivoNoUso)) {
            return false;
        }
        MotivoNoUso other = (MotivoNoUso) object;
        if ((this.codMotNoUso == null && other.codMotNoUso != null) || (this.codMotNoUso != null && !this.codMotNoUso.equals(other.codMotNoUso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.inspecciones.MotivoNoUso[ cod_mot_no_uso=" + codMotNoUso + " ]";
    }
    
}
