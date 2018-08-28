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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "roles")
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r")})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_rol")
    private Integer codigoRol;
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "rolesList")
    private List<RelUsuariosEstablecimiento> relUsuariosEstablecimientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private List<Privilegios> privilegiosList;

    public Roles() {
    }

    public Roles(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    public Integer getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<RelUsuariosEstablecimiento> getRelUsuariosEstablecimientoList() {
        return relUsuariosEstablecimientoList;
    }

    public void setRelUsuariosEstablecimientoList(List<RelUsuariosEstablecimiento> relUsuariosEstablecimientoList) {
        this.relUsuariosEstablecimientoList = relUsuariosEstablecimientoList;
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
        hash += (codigoRol != null ? codigoRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.codigoRol == null && other.codigoRol != null) || (this.codigoRol != null && !this.codigoRol.equals(other.codigoRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.Roles[ codigoRol=" + codigoRol + " ]";
    }
    
}
