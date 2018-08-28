/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.entity.App;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "seccion_detalle_items")
@NamedQueries({
    @NamedQuery(name = "SeccionDetalleItems.findAll", query = "SELECT s FROM SeccionDetalleItems s")
})
public class SeccionDetalleItems implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SeccionDetalleItemsPK seccionDetalleItemsPK;

    private String nombre;

    @Column(name = "detalle")
    private String detalle;
    @Column(name = "peso")
    private Double peso;

    private Boolean activo;
    private String imagen;
    private Integer orden;

    @ManyToMany(mappedBy = "seccionDetalleItemsList")
    private List<EvaluacionPuntajes> evaluacionPuntajesList;
    @JoinColumns({
        @JoinColumn(name = "cod_seccion", referencedColumnName = "cod_seccion", insertable = false, updatable = false),
        @JoinColumn(name = "cod_detalle", referencedColumnName = "cod_detalle", insertable = false, updatable = false),
        @JoinColumn(name = "cod_ciclo", referencedColumnName = "cod_ciclo", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SeccionDetalle seccionDetalle;
    private EvaluacionPuntajes evaluacionPuntajes = new EvaluacionPuntajes(new EvaluacionPuntajesPK());
    private List<String> evaluacionPuntajesItems;
    
    private String numeral;

    public SeccionDetalleItems() {
    }

    public SeccionDetalleItems(SeccionDetalleItemsPK seccionDetalleItemsPK) {
        this.seccionDetalleItemsPK = seccionDetalleItemsPK;
    }

    public SeccionDetalleItems(String codCiclo, int codSeccion, int codDetalle, int codItem) {
        this.seccionDetalleItemsPK = new SeccionDetalleItemsPK(codCiclo, codSeccion, codDetalle, codItem);
    }

    public SeccionDetalleItems(SeccionDetalleItemsPK seccionDetalleItemsPK, String nombre, String detalle, Double peso, Boolean activo, String imagen, Integer orden) {
        this.seccionDetalleItemsPK = seccionDetalleItemsPK;
        this.nombre = nombre;
        this.detalle = detalle;
        this.peso = peso;
        this.activo = activo;
        this.imagen = imagen;
        this.orden = orden;
    }

    public SeccionDetalleItemsPK getSeccionDetalleItemsPK() {
        return seccionDetalleItemsPK;
    }

    public void setSeccionDetalleItemsPK(SeccionDetalleItemsPK seccionDetalleItemsPK) {
        this.seccionDetalleItemsPK = seccionDetalleItemsPK;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public List<EvaluacionPuntajes> getEvaluacionPuntajesList() {
        return evaluacionPuntajesList;
    }

    public void setEvaluacionPuntajesList(List<EvaluacionPuntajes> evaluacionPuntajesList) {
        this.evaluacionPuntajesList = evaluacionPuntajesList;
    }

    public SeccionDetalle getSeccionDetalle() {
        return seccionDetalle;
    }

    public void setSeccionDetalle(SeccionDetalle seccionDetalle) {
        this.seccionDetalle = seccionDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seccionDetalleItemsPK != null ? seccionDetalleItemsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeccionDetalleItems)) {
            return false;
        }
        SeccionDetalleItems other = (SeccionDetalleItems) object;
        if ((this.seccionDetalleItemsPK == null && other.seccionDetalleItemsPK != null) || (this.seccionDetalleItemsPK != null && !this.seccionDetalleItemsPK.equals(other.seccionDetalleItemsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.SeccionDetalleItems[ seccionDetalleItemsPK=" + seccionDetalleItemsPK + " ]";
    }

    /**
     * @return the peso
     */
    public Double getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * @return the evaluacionPuntajes
     */
    public EvaluacionPuntajes getEvaluacionPuntajes() {
        return evaluacionPuntajes;
    }

    /**
     * @param evaluacionPuntajes the evaluacionPuntajes to set
     */
    public void setEvaluacionPuntajes(EvaluacionPuntajes evaluacionPuntajes) {
        this.evaluacionPuntajes = evaluacionPuntajes;
    }

    /**
     * @return the evaluacionPuntajesItems
     */
    public List<String> getEvaluacionPuntajesItems() {
        return evaluacionPuntajesItems;
    }

    /**
     * @param evaluacionPuntajesItems the evaluacionPuntajesItems to set
     */
    public void setEvaluacionPuntajesItems(List<String> evaluacionPuntajesItems) {
        this.evaluacionPuntajesItems = evaluacionPuntajesItems;
    }
    
    public String getNumeralNombre() {
        return numeral + App.NUMERAL_SEPARADOR + nombre;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
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
    
    

}
