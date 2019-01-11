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
    @NamedQuery(name = "FuenteHallazgo.findAll", query = "SELECT fa FROM FuenteHallazgo fa")})
public class FuenteHallazgo implements Serializable, Cloneable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_fuente_hallazgo")
    private Integer codfuentehallazgo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public FuenteHallazgo() {
    }

    
    public FuenteHallazgo(int codfuentehallazgo, String nombre) {
        this.codfuentehallazgo= codfuentehallazgo;
        this.nombre = nombre;
        
    }
    public Integer getCodFuentehallazgo() {
        return codfuentehallazgo;
    }

    public void setCodFuentehallazgo(Integer codfuentehallazgo) {
        this.codfuentehallazgo = codfuentehallazgo;
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
        if ((this.codfuentehallazgo == null && other.codfuentehallazgo != null) || (this.codfuentehallazgo != null && !this.codfuentehallazgo.equals(other.codfuentehallazgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.FuenteHalazgo[ cod_fuente_hallazgo=" + codfuentehallazgo + " ]";
    }
    
}
