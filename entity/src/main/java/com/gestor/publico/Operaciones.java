/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "operaciones")
@NamedQueries({
    @NamedQuery(name = "Operaciones.findAll", query = "SELECT o FROM Operaciones o")})
public class Operaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_operacion")
    private Integer codOperacion;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "operaciones")
    private List<Privilegios> privilegiosList;

    public Operaciones() {
    }

    public Operaciones(Integer codOperacion) {
        this.codOperacion = codOperacion;
    }

    public Integer getCodOperacion() {
        return codOperacion;
    }

    public void setCodOperacion(Integer codOperacion) {
        this.codOperacion = codOperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Privilegios> getPrivilegiosList() {
        return privilegiosList;
    }

    public void setPrivilegiosList(List<Privilegios> privilegiosList) {
        this.privilegiosList = privilegiosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codOperacion != null ? codOperacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operaciones)) {
            return false;
        }
        Operaciones other = (Operaciones) object;
        if ((this.codOperacion == null && other.codOperacion != null) || (this.codOperacion != null && !this.codOperacion.equals(other.codOperacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.Operaciones[ codOperacion=" + codOperacion + " ]";
    }
    
}
