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
@Table(name = "plan_seccion_detalle")
@NamedQueries({
    @NamedQuery(name = "PlanSeccionDetalle.findAll", query = "SELECT p FROM PlanSeccionDetalle p")})
public class PlanSeccionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanSeccionDetallePK planSeccionDetallePK;
    @Column(name = "numeral")
    private String numeral;
    @Column(name = "nombre")
    private String nombre;
    
    private List<PlanSeccionDetalleTexto> planSeccionDetalleTextoList;
    private List<PlanSeccionDetalleAdjuntos> planSeccionDetalleAdjuntosList;
    
    private List<PlanSeccionDetalleItem> planSeccionDetalleItemList;

    public PlanSeccionDetalle() {
    }

    public PlanSeccionDetalle(PlanSeccionDetallePK planSeccionDetallePK, String numeral, String nombre) {
        this.planSeccionDetallePK = planSeccionDetallePK;
        this.numeral = numeral;
        this.nombre = nombre;
    }
    
    

    public PlanSeccionDetalle(PlanSeccionDetallePK planSeccionDetallePK) {
        this.planSeccionDetallePK = planSeccionDetallePK;
    }

    public PlanSeccionDetalle(short codigoEstablecimiento, int codTitulo, int codSeccion, int codSeccionDetalle) {
        this.planSeccionDetallePK = new PlanSeccionDetallePK(codigoEstablecimiento, codTitulo, codSeccion, codSeccionDetalle);
    }

    public PlanSeccionDetallePK getPlanSeccionDetallePK() {
        return planSeccionDetallePK;
    }

    public void setPlanSeccionDetallePK(PlanSeccionDetallePK planSeccionDetallePK) {
        this.planSeccionDetallePK = planSeccionDetallePK;
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
        hash += (planSeccionDetallePK != null ? planSeccionDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanSeccionDetalle)) {
            return false;
        }
        PlanSeccionDetalle other = (PlanSeccionDetalle) object;
        if ((this.planSeccionDetallePK == null && other.planSeccionDetallePK != null) || (this.planSeccionDetallePK != null && !this.planSeccionDetallePK.equals(other.planSeccionDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanSeccionDetalle[ planSeccionDetallePK=" + planSeccionDetallePK + " ]";
    }

    /**
     * @return the planSeccionDetalleTextoList
     */
    public List<PlanSeccionDetalleTexto> getPlanSeccionDetalleTextoList() {
        return planSeccionDetalleTextoList;
    }

    /**
     * @param planSeccionDetalleTextoList the planSeccionDetalleTextoList to set
     */
    public void setPlanSeccionDetalleTextoList(List<PlanSeccionDetalleTexto> planSeccionDetalleTextoList) {
        this.planSeccionDetalleTextoList = planSeccionDetalleTextoList;
    }
    
    public String getDescripcionAdjuntos() {
        String descripcion = null;
        if (planSeccionDetalleAdjuntosList != null) {
            for (PlanSeccionDetalleAdjuntos psda : planSeccionDetalleAdjuntosList) {
                descripcion = psda.getDescripcion();
                if (descripcion != null && !descripcion.equalsIgnoreCase("")) {
                    break;
                }
            }
        }
        return descripcion;
    }

    public String getTituloAdjuntos() {
        String titulo = null;
        if (planSeccionDetalleAdjuntosList != null) {
            for (PlanSeccionDetalleAdjuntos psda : planSeccionDetalleAdjuntosList) {
                titulo = psda.getTitulo();
                if (titulo != null && !titulo.equalsIgnoreCase("")) {
                    break;
                }
            }
        }
        return titulo;
    }

    /**
     * @return the planSeccionDetalleAdjuntosList
     */
    public List<PlanSeccionDetalleAdjuntos> getPlanSeccionDetalleAdjuntosList() {
        return planSeccionDetalleAdjuntosList;
    }

    /**
     * @param planSeccionDetalleAdjuntosList the planSeccionDetalleAdjuntosList to set
     */
    public void setPlanSeccionDetalleAdjuntosList(List<PlanSeccionDetalleAdjuntos> planSeccionDetalleAdjuntosList) {
        this.planSeccionDetalleAdjuntosList = planSeccionDetalleAdjuntosList;
    }

    /**
     * @return the planSeccionDetalleItemList
     */
    public List<PlanSeccionDetalleItem> getPlanSeccionDetalleItemList() {
        return planSeccionDetalleItemList;
    }

    /**
     * @param planSeccionDetalleItemList the planSeccionDetalleItemList to set
     */
    public void setPlanSeccionDetalleItemList(List<PlanSeccionDetalleItem> planSeccionDetalleItemList) {
        this.planSeccionDetalleItemList = planSeccionDetalleItemList;
    }
    
}
