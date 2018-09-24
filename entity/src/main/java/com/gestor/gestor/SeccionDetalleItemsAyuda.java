/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.App;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "seccion_detalle_items_ayuda")
@NamedQueries({
    @NamedQuery(name = "SeccionDetalleItemsAyuda.findAll", query = "SELECT s FROM SeccionDetalleItemsAyuda s")})
public class SeccionDetalleItemsAyuda implements Serializable,Cloneable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SeccionDetalleItemsAyudaPK seccionDetalleItemsAyudaPK;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "definicion")
    private String definicion;
    @Column(name = "numeral")
    private String numeral;
    
    
    protected Ciclo ciclo;    

    public SeccionDetalleItemsAyuda() {
    }

    public SeccionDetalleItemsAyuda(SeccionDetalleItemsAyudaPK seccionDetalleItemsAyudaPK) {
        this.seccionDetalleItemsAyudaPK = seccionDetalleItemsAyudaPK;
    }

    public SeccionDetalleItemsAyuda(String codCiclo, int codSeccion, int codDetalle, int codItem, int codAyuda) {
        this.seccionDetalleItemsAyudaPK = new SeccionDetalleItemsAyudaPK(codCiclo, codSeccion, codDetalle, codItem, codAyuda);
    }

    public SeccionDetalleItemsAyuda(String codCiclo, int codSeccion, int codDetalle, int codItem, int codAyuda, String nombre, String definicion) {
        this.seccionDetalleItemsAyudaPK = new SeccionDetalleItemsAyudaPK(codCiclo, codSeccion, codDetalle, codItem, codAyuda);
        this.nombre = nombre;
        this.definicion = definicion;
    }

    public SeccionDetalleItemsAyuda(SeccionDetalleItemsAyudaPK seccionDetalleItemsAyudaPK, String nombre, String definicion, String numeral) {
        this.seccionDetalleItemsAyudaPK = seccionDetalleItemsAyudaPK;
        this.nombre = nombre;
        this.definicion = definicion;
        this.numeral= numeral;
        
    }
    
    public SeccionDetalleItemsAyuda(Ciclo ciclo, String codCiclo, int codSeccion, int codDetalle, int codItem){        
        this.ciclo= new Ciclo(codCiclo, nombre);
    }
 

    public SeccionDetalleItemsAyudaPK getSeccionDetalleItemsAyudaPK() {
        return seccionDetalleItemsAyudaPK;
    }

    public void setSeccionDetalleItemsAyudaPK(SeccionDetalleItemsAyudaPK seccionDetalleItemsAyudaPK) {
        this.seccionDetalleItemsAyudaPK = seccionDetalleItemsAyudaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDefinicion() {
        return definicion;
    }
    
    public String getNumeralNombre() {
        return numeral + App.NUMERAL_SEPARADOR + nombre;
    }
    
        /**
     * @return the numeral
     */
    public String getNumeral() {
        return numeral;
    }

    /**
     * @param numeral the numeral to set
     */
    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seccionDetalleItemsAyudaPK != null ? seccionDetalleItemsAyudaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeccionDetalleItemsAyuda)) {
            return false;
        }
        SeccionDetalleItemsAyuda other = (SeccionDetalleItemsAyuda) object;
        if ((this.seccionDetalleItemsAyudaPK == null && other.seccionDetalleItemsAyudaPK != null) || (this.seccionDetalleItemsAyudaPK != null && !this.seccionDetalleItemsAyudaPK.equals(other.seccionDetalleItemsAyudaPK))) {
            return false;
        }
        return true;
    }

    /*@Override
    public String toString() {
        return "com.gestor.gestor.SeccionDetalleItemsAyuda[ seccionDetalleItemsAyudaPK=" + seccionDetalleItemsAyudaPK + " ]";
    }*/

}
