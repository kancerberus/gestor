/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "plan_seccion_detalle_item")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalleItem.findAll", query = "SELECT p FROM PlanSeccionDetalleItem p")})
public class PlanSeccionDetalleItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetalleItemPK planSeccionDetalleItemPK;
    @Column(name = "numeral")
    private String numeral;
    @Column(name = "nombre")
    private String nombre;
    
    private List<PlanSeccionDetalleItemTexto> planSeccionDetalleItemTextoList;
    private List<PlanSeccionDetalleItemAdjuntos> planSeccionDetalleItemAdjuntosList;

    public PlanSeccionDetalleItem() {
    }

    public PlanSeccionDetalleItem(PlanSeccionDetalleItemPK planSeccionDetalleItemPK, String numeral, String nombre) {
        this.planSeccionDetalleItemPK = planSeccionDetalleItemPK;
        this.numeral = numeral;
        this.nombre = nombre;
    }
    
    

    public PlanSeccionDetalleItem(PlanSeccionDetalleItemPK planSeccionDetalleItemPK) {
        this.planSeccionDetalleItemPK = planSeccionDetalleItemPK;
    }

    public PlanSeccionDetalleItem(int codTitulo, int codigoEstablecimiento, int codSeccion, int codSeccionDetalle, int codSeccionDetalleItem) {
        this.planSeccionDetalleItemPK = new PlanSeccionDetalleItemPK(codTitulo, codigoEstablecimiento, codSeccion, codSeccionDetalle, codSeccionDetalleItem);
    }

    public PlanSeccionDetalleItemPK getPlanSeccionDetalleItemPK() {
        return planSeccionDetalleItemPK;
    }

    public void setPlanSeccionDetalleItemPK(PlanSeccionDetalleItemPK planSeccionDetalleItemPK) {
        this.planSeccionDetalleItemPK = planSeccionDetalleItemPK;
    }

    public String getNumeral() {
        return numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planSeccionDetalleItemPK != null ? planSeccionDetalleItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalleItem)) {
            return false;
        }
        PlanSeccionDetalleItem other = (PlanSeccionDetalleItem) object;
        if ((this.planSeccionDetalleItemPK == null && other.planSeccionDetalleItemPK != null) || (this.planSeccionDetalleItemPK != null && !this.planSeccionDetalleItemPK.equals(other.planSeccionDetalleItemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalleItem[ planSeccionDetalleItemPK=" + planSeccionDetalleItemPK + " ]";
    }

    /**
     * @return the planSeccionDetalleItemTextoList
     */
    public List<PlanSeccionDetalleItemTexto> getPlanSeccionDetalleItemTextoList() {
        return planSeccionDetalleItemTextoList;
    }

    /**
     * @param planSeccionDetalleItemTextoList the planSeccionDetalleItemTextoList to set
     */
    public void setPlanSeccionDetalleItemTextoList(List<PlanSeccionDetalleItemTexto> planSeccionDetalleItemTextoList) {
        this.planSeccionDetalleItemTextoList = planSeccionDetalleItemTextoList;
    }

    /**
     * @return the planSeccionDetalleItemAdjuntosList
     */
    public List<PlanSeccionDetalleItemAdjuntos> getPlanSeccionDetalleItemAdjuntosList() {
        return planSeccionDetalleItemAdjuntosList;
    }

    /**
     * @param planSeccionDetalleItemAdjuntosList the planSeccionDetalleItemAdjuntosList to set
     */
    public void setPlanSeccionDetalleItemAdjuntosList(List<PlanSeccionDetalleItemAdjuntos> planSeccionDetalleItemAdjuntosList) {
        this.planSeccionDetalleItemAdjuntosList = planSeccionDetalleItemAdjuntosList;
    }
    
    public String getDescripcionAdjuntos() {
        String descripcion = null;
        if (planSeccionDetalleItemAdjuntosList != null) {
            for (PlanSeccionDetalleItemAdjuntos psdia : planSeccionDetalleItemAdjuntosList) {
                descripcion = psdia.getDescripcion();
                if (descripcion != null && !descripcion.equalsIgnoreCase("")) {
                    break;
                }
            }
        }
        return descripcion;
    }

    public String getTituloAdjuntos() {
        String titulo = null;
        if (planSeccionDetalleItemAdjuntosList != null) {
            for (PlanSeccionDetalleItemAdjuntos psdia : planSeccionDetalleItemAdjuntosList) {
                titulo = psdia.getTitulo();
                if (titulo != null && !titulo.equalsIgnoreCase("")) {
                    break;
                }
            }
        }
        return titulo;
    }

   
    
}
