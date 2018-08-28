/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "rel_usuarios_establecimiento")
@NamedQueries({
    @NamedQuery(name = "RelUsuariosEstablecimiento.findAll", query = "SELECT r FROM RelUsuariosEstablecimiento r")})
public class RelUsuariosEstablecimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RelUsuariosEstablecimientoPK relUsuariosEstablecimientoPK;
    @JoinTable(name = "roles_usuarios", joinColumns = {
        @JoinColumn(name = "codigo_establecimiento", referencedColumnName = "codigo_establecimiento"),
        @JoinColumn(name = "documento_usuario", referencedColumnName = "documento_usuario")}, inverseJoinColumns = {
        @JoinColumn(name = "codigo_rol", referencedColumnName = "codigo_rol")})
    @ManyToMany
    private List<Roles> rolesList;
    @JoinColumn(name = "codigo_establecimiento", referencedColumnName = "codigo_establecimiento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Establecimiento establecimiento;
    @JoinColumn(name = "documento_usuario", referencedColumnName = "documento_usuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public RelUsuariosEstablecimiento() {
    }

    public RelUsuariosEstablecimiento(RelUsuariosEstablecimientoPK relUsuariosEstablecimientoPK) {
        this.relUsuariosEstablecimientoPK = relUsuariosEstablecimientoPK;
    }

    public RelUsuariosEstablecimiento(short codigoEstablecimiento, String documentoUsuario) {
        this.relUsuariosEstablecimientoPK = new RelUsuariosEstablecimientoPK(codigoEstablecimiento, documentoUsuario);
    }

    public RelUsuariosEstablecimientoPK getRelUsuariosEstablecimientoPK() {
        return relUsuariosEstablecimientoPK;
    }

    public void setRelUsuariosEstablecimientoPK(RelUsuariosEstablecimientoPK relUsuariosEstablecimientoPK) {
        this.relUsuariosEstablecimientoPK = relUsuariosEstablecimientoPK;
    }

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relUsuariosEstablecimientoPK != null ? relUsuariosEstablecimientoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelUsuariosEstablecimiento)) {
            return false;
        }
        RelUsuariosEstablecimiento other = (RelUsuariosEstablecimiento) object;
        if ((this.relUsuariosEstablecimientoPK == null && other.relUsuariosEstablecimientoPK != null) || (this.relUsuariosEstablecimientoPK != null && !this.relUsuariosEstablecimientoPK.equals(other.relUsuariosEstablecimientoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.RelUsuariosEstablecimiento[ relUsuariosEstablecimientoPK=" + relUsuariosEstablecimientoPK + " ]";
    }
    
}
