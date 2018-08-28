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
@Table(name = "transacciones")
@NamedQueries({
    @NamedQuery(name = "Transacciones.findAll", query = "SELECT t FROM Transacciones t")})
public class Transacciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_transaccion")
    private Long codTransaccion;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transacciones")
    private List<Privilegios> privilegiosList;

    public Transacciones() {
    }

    public Transacciones(Long codTransaccion) {
        this.codTransaccion = codTransaccion;
    }

    public Long getCodTransaccion() {
        return codTransaccion;
    }

    public void setCodTransaccion(Long codTransaccion) {
        this.codTransaccion = codTransaccion;
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
        hash += (codTransaccion != null ? codTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transacciones)) {
            return false;
        }
        Transacciones other = (Transacciones) object;
        if ((this.codTransaccion == null && other.codTransaccion != null) || (this.codTransaccion != null && !this.codTransaccion.equals(other.codTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.Transacciones[ codTransaccion=" + codTransaccion + " ]";
    }
    
}
