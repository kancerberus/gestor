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
@Table(name = "fuente_hallazgo")
@NamedQueries({
    @NamedQuery(name = "FuenteHallazgo.findAll", query = "SELECT fh FROM FuenteHallazgo fh")})
public class FuenteHallazgo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_fuente_hallazgo")
    private Integer codFuentehallazgo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public FuenteHallazgo() {
    }

    public FuenteHallazgo(Integer codFuentehallazgo, String nombre) {
        this.codFuentehallazgo = codFuentehallazgo;
        this.nombre = nombre;
    }

    public Integer getCodFuentehallazgo() {
        return codFuentehallazgo;
    }

    public void setCodFuentehallazgo(Integer codFuentehallazgo) {
        this.codFuentehallazgo = codFuentehallazgo;
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
        if (!(object instanceof FuenteHallazgo)) {
            return false;
        }
        FuenteHallazgo other = (FuenteHallazgo) object;
        if ((this.codFuentehallazgo == null && other.codFuentehallazgo != null) || (this.codFuentehallazgo != null && !this.codFuentehallazgo.equals(other.codFuentehallazgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.FuenteHallazgo[ cod_fuente_hallazgo=" + codFuentehallazgo + " ]";
    }
    
}
