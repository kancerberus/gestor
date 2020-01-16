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
@Table(name = "elementos_botiquin")
@NamedQueries({
    @NamedQuery(name = "ElementosBotiquin.findAll", query = "SELECT eb FROM ElementosBotiquin eb")})
public class ElementosBotiquin implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_elemento")
    private Integer codElemento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "unidades")
    private String unidades;

    public ElementosBotiquin() {
    }

    public ElementosBotiquin(Integer codElemento, String nombre, String unidades) {
        this.codElemento = codElemento;
        this.nombre = nombre;
        this.unidades = unidades;
    }
    
    

    public Integer getCodElemento() {
        return codElemento;
    }

    public void setCodElemento(Integer codElemento) {
        this.codElemento = codElemento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElementosBotiquin)) {
            return false;
        }
        ElementosBotiquin other = (ElementosBotiquin) object;
        if ((this.codElemento == null && other.codElemento != null) || (this.codElemento != null && !this.codElemento.equals(other.codElemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.ElementosBotiquin[ cod_elemento=" + codElemento + " ]";
    }
    
}
