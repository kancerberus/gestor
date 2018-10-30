/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
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
@Table(name = "plan_seccion_detalle_item_texto")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalleItemTexto.findAll", query = "SELECT p FROM PlanSeccionDetalleItemTexto p")})
public class PlanSeccionDetalleItemTexto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetalleItemTextoPK planSeccionDetalleItemTextoPK;
    @Column(name = "texto")
    private String texto;

    public PlanSeccionDetalleItemTexto() {
    }

    public PlanSeccionDetalleItemTexto(PlanSeccionDetalleItemTextoPK planSeccionDetalleItemTextoPK, String texto) {
        this.planSeccionDetalleItemTextoPK = planSeccionDetalleItemTextoPK;
        this.texto = texto;
    }
    
    
    
    public PlanSeccionDetalleItemTexto(PlanSeccionDetalleItemTextoPK planSeccionDetalleItemTextoPK) {
        this.planSeccionDetalleItemTextoPK = planSeccionDetalleItemTextoPK;
    }

    public PlanSeccionDetalleItemTexto(int codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem, int codSeccionDetalleItemTexto) {
        this.planSeccionDetalleItemTextoPK = new PlanSeccionDetalleItemTextoPK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionDetalle, codSeccionDetalleItem, codSeccionDetalleItemTexto);
    }

    public PlanSeccionDetalleItemTextoPK getPlanSeccionDetalleItemTextoPK() {
        return planSeccionDetalleItemTextoPK;
    }

    public void setPlanSeccionDetalleItemTextoPK(PlanSeccionDetalleItemTextoPK planSeccionDetalleItemTextoPK) {
        this.planSeccionDetalleItemTextoPK = planSeccionDetalleItemTextoPK;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionDetalleItemTextoPK != null ? planSeccionDetalleItemTextoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleItemTexto)) {
            return false;
        }
        PlanSeccionDetalleItemTexto other = (PlanSeccionDetalleItemTexto) object;
        if ((this.planSeccionDetalleItemTextoPK == null && other.planSeccionDetalleItemTextoPK != null) || (this.planSeccionDetalleItemTextoPK != null && !this.planSeccionDetalleItemTextoPK.equals(other.planSeccionDetalleItemTextoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleItemTexto[ planSeccionDetalleItemTextoPK=" + planSeccionDetalleItemTextoPK + " ]";
    }
    
}
