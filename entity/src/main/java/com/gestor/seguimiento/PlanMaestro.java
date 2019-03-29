/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.seguimiento;

import com.gestor.gestor.Evaluacion;
import com.gestor.publico.Establecimiento;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Julian D Osorio G
 */
@Entity
@Table(name = "plan_maestro")
@NamedQueries({
    @NamedQuery(name = "PlanMaestro.findAll", query = "SELECT p FROM PlanMaestro p ORDER")
})
@ManagedBean (name="planMaestro")
@SessionScoped

public class PlanMaestro implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    public static String PLAN_MAESTRO_CONDICION_COD_ESTABLECIMIENTO = "PM.codigo_establecimiento IN (?)";
    public static String PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_GTE = "PM.fecha_actualiza::DATE >= ?";
    public static String PLAN_MAESTRO_CONDICION_FECHA_ACTUALIZA_LTE = "PM.fecha_actualiza::DATE <= ?";

    @EmbeddedId
    protected PlanMaestroPK planMaestroPK;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_actualiza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualiza;

    private Establecimiento establecimiento;
    private Evaluacion evaluacion;
    private PlanTitulo planTitulo;
    private List<PlanTitulo> planTituloList;

    public PlanMaestro() {
    }

    public PlanMaestro(PlanMaestroPK planMaestroPK) {
        this.planMaestroPK = planMaestroPK;
    }

    public PlanMaestro(PlanMaestroPK planMaestroPK, Date fechaRegistro, Date fechaActualiza) {
        this.planMaestroPK = planMaestroPK;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualiza = fechaActualiza;
    }

    public PlanMaestro(Long codEvaluacion, int codigoEstablecimiento, Long codMaestro) {
        this.planMaestroPK = new PlanMaestroPK(codEvaluacion, codigoEstablecimiento, codMaestro);
    }

    public PlanMaestroPK getPlanMaestroPK() {
        return planMaestroPK;
    }

    public void setPlanMaestroPK(PlanMaestroPK planMaestroPK) {
        this.planMaestroPK = planMaestroPK;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualiza() {
        return fechaActualiza;
    }

    public void setFechaActualiza(Date fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planMaestroPK != null ? planMaestroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMaestro)) {
            return false;
        }
        PlanMaestro other = (PlanMaestro) object;
        if ((this.planMaestroPK == null && other.planMaestroPK != null) || (this.planMaestroPK != null && !this.planMaestroPK.equals(other.planMaestroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.seguimiento.PlanMaestro[ planMaestroPK=" + planMaestroPK + " ]";
    }

    /**
     * @return the establecimiento
     */
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento the establecimiento to set
     */
    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    /**
     * @return the evaluacion
     */
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param evaluacion the evaluacion to set
     */
    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    /**
     * @return the planTitulo
     */
    public PlanTitulo getPlanTitulo() {
        return planTitulo;
    }

    /**
     * @param planTitulo the planTitulo to set
     */
    public void setPlanTitulo(PlanTitulo planTitulo) {
        this.planTitulo = planTitulo;
    }

    /**
     * @return the planTituloList
     */
    public List<PlanTitulo> getPlanTituloList() {
        return planTituloList;
    }

    /**
     * @param planTituloList the planTituloList to set
     */
    public void setPlanTituloList(List<PlanTitulo> planTituloList) {
        this.planTituloList = planTituloList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
