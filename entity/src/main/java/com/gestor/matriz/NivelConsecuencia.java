/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.matriz;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Table;

/**
 *
 * @author Franciso
 */
@Entity
@Table(name = "NivelConsecuencia")
@NamedQueries({
    @NamedQuery(name = "NivelConsecuencia.findAll", query = "SELECT ncons FROM NivelConsecuencia ncons")})
public class NivelConsecuencia implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_nviel_consec")
    private Integer codNivelConsec;           
    @Column(name = "nombre")
    private String nombre;   
    @Column(name = "valor")
    private Integer valor; 
    @Column(name = "significado")
    private String significado;   
       
    public NivelConsecuencia() {        
    }

    public NivelConsecuencia(Integer codNivelConsec, String nombre, Integer valor, String significado) {
        this.codNivelConsec = codNivelConsec;
        this.nombre = nombre;
        this.valor = valor;
        this.significado = significado;
    }

    public Integer getCodNivelConsec() {
        return codNivelConsec;
    }

    public void setCodNivelConsec(Integer codNivelConsec) {
        this.codNivelConsec = codNivelConsec;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codNivelConsec != null ? codNivelConsec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelConsecuencia)) {
            return false;
        }
        NivelConsecuencia other = (NivelConsecuencia) object;
        if ((this.codNivelConsec == null && other.codNivelConsec != null) || (this.codNivelConsec != null && !this.codNivelConsec.equals(other.codNivelConsec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.matriz.NivelConsecuencia[ codNivelCons=" + codNivelConsec + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
