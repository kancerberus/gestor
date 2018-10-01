/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
import java.util.ArrayList;
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
 * @author Julian D Osorio G
 */
@Entity
@Table(name = "adjuntos_categoria")
@NamedQueries({
    @NamedQuery(name = "AdjuntosCategoria.findAll", query = "SELECT a FROM AdjuntosCategoria a")})
public class AdjuntosCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_categoria")
    private Integer codCategoria;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "meses_vigencia")
    private int mesesVigencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adjuntosCategoria")
    private List<AdjuntosCategoriaTipo> adjuntosCategoriaTipoList = new ArrayList<>();
    private AdjuntosCategoriaTipo adjuntosCategoriaTipo = new AdjuntosCategoriaTipo();

    public AdjuntosCategoria() {
        adjuntosCategoriaTipo = new AdjuntosCategoriaTipo();
        adjuntosCategoriaTipoList = new ArrayList<>();
    }

    public AdjuntosCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public AdjuntosCategoria(Integer codCategoria, String nombre, int mesesVigencia) {
        this.codCategoria = codCategoria;
        this.nombre = nombre;
        this.mesesVigencia = mesesVigencia;
    }

    public AdjuntosCategoria(Integer codCategoria, String nombre, String descripcion, int mesesVigencia) {
        this.codCategoria = codCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mesesVigencia = mesesVigencia;
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMesesVigencia() {
        return mesesVigencia;
    }

    public void setMesesVigencia(int mesesVigencia) {
        this.mesesVigencia = mesesVigencia;
    }

    public List<AdjuntosCategoriaTipo> getAdjuntosCategoriaTipoList() {
        return adjuntosCategoriaTipoList;
    }

    public void setAdjuntosCategoriaTipoList(List<AdjuntosCategoriaTipo> adjuntosCategoriaTipoList) {
        this.adjuntosCategoriaTipoList = adjuntosCategoriaTipoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCategoria != null ? codCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdjuntosCategoria)) {
            return false;
        }
        AdjuntosCategoria other = (AdjuntosCategoria) object;
        if ((this.codCategoria == null && other.codCategoria != null) || (this.codCategoria != null && !this.codCategoria.equals(other.codCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.AdjuntosCategoria[ codCategoria=" + codCategoria + " ]";
    }

    /**
     * @return the adjuntosCategoriaTipo
     */
    public AdjuntosCategoriaTipo getAdjuntosCategoriaTipo() {
        return adjuntosCategoriaTipo;
    }

    /**
     * @param adjuntosCategoriaTipo the adjuntosCategoriaTipo to set
     */
    public void setAdjuntosCategoriaTipo(AdjuntosCategoriaTipo adjuntosCategoriaTipo) {
        this.adjuntosCategoriaTipo = adjuntosCategoriaTipo;
    }

}
