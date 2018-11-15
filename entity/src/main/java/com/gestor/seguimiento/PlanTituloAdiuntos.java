/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.gestor.AdjuntosCategoria;
import com.gestor.publico.EvaluacionAdjuntos;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Julian D Osorio G
 */
@Entity
@Table(name = "plan_titulo_adiuntos")
@NamedQueries({
    @NamedQuery(name = "PlanTituloAdiuntos.findAll", query = "SELECT p FROM PlanTituloAdiuntos p")})
public class PlanTituloAdiuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanTituloAdiuntosPK planTituloAdiuntosPK;
    @Basic(optional = false)
    @Column(name = "cod_categoria")
    private int codCategoria;
    @Basic(optional = false)
    @Column(name = "cod_categoria_tipo")
    private int codCategoriaTipo;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "documento")
    private String documento;

    private EvaluacionAdjuntos evaluacionAdjuntos;
    private AdjuntosCategoria adjuntosCategoria = new AdjuntosCategoria();
    

    public PlanTituloAdiuntos() {
        adjuntosCategoria = new AdjuntosCategoria();
    }

    public PlanTituloAdiuntos(PlanTituloAdiuntosPK planTituloAdiuntosPK) {
        this.planTituloAdiuntosPK = planTituloAdiuntosPK;
    }

    public PlanTituloAdiuntos(PlanTituloAdiuntosPK planTituloAdiuntosPK, int codCategoria, int codCategoriaTipo) {
        this.planTituloAdiuntosPK = planTituloAdiuntosPK;
        this.codCategoria = codCategoria;
        this.codCategoriaTipo = codCategoriaTipo;
    }

    public PlanTituloAdiuntos(PlanTituloAdiuntosPK planTituloAdiuntosPK, int codCategoria, int codCategoriaTipo, String titulo, String descripcion, String documento) {
        this.planTituloAdiuntosPK = planTituloAdiuntosPK;
        this.codCategoria = codCategoria;
        this.codCategoriaTipo = codCategoriaTipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.documento = documento;
    }

    public PlanTituloAdiuntos(short codigoEstablecimiento, int codTituloAdjunto, int codTitulo) {
        this.planTituloAdiuntosPK = new PlanTituloAdiuntosPK(codigoEstablecimiento, codTituloAdjunto, codTitulo);
    }

    public PlanTituloAdiuntosPK getPlanTituloAdiuntosPK() {
        return planTituloAdiuntosPK;
    }

    public void setPlanTituloAdiuntosPK(PlanTituloAdiuntosPK planTituloAdiuntosPK) {
        this.planTituloAdiuntosPK = planTituloAdiuntosPK;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public int getCodCategoriaTipo() {
        return codCategoriaTipo;
    }

    public void setCodCategoriaTipo(int codCategoriaTipo) {
        this.codCategoriaTipo = codCategoriaTipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public AdjuntosCategoria getAdjuntosCategoria() {
        return adjuntosCategoria;
    }

    public void setAdjuntosCategoria(AdjuntosCategoria adjuntosCategoria) {
        this.adjuntosCategoria = adjuntosCategoria;
    }    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planTituloAdiuntosPK != null ? planTituloAdiuntosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanTituloAdiuntos)) {
            return false;
        }
        PlanTituloAdiuntos other = (PlanTituloAdiuntos) object;
        if ((this.planTituloAdiuntosPK == null && other.planTituloAdiuntosPK != null) || (this.planTituloAdiuntosPK != null && !this.planTituloAdiuntosPK.equals(other.planTituloAdiuntosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanTituloAdiuntos[ planTituloAdiuntosPK=" + planTituloAdiuntosPK + " ]";
    }

    /**
     * @return the evaluacionAdjuntos
     */
    public EvaluacionAdjuntos getEvaluacionAdjuntos() {
        return evaluacionAdjuntos;
    }

    /**
     * @param evaluacionAdjuntos the evaluacionAdjuntos to set
     */
    public void setEvaluacionAdjuntos(EvaluacionAdjuntos evaluacionAdjuntos) {
        this.evaluacionAdjuntos = evaluacionAdjuntos;
    }

}
