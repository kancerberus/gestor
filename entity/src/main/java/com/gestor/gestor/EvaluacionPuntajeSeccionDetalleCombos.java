/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import java.io.Serializable;
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
@Table(name = "evaluacion_puntaje_seccion_detalle_combos")
@NamedQueries({
    @NamedQuery(name = "EvaluacionPuntajeSeccionDetalleCombos.findAll", query = "SELECT e FROM EvaluacionPuntajeSeccionDetalleCombos e")})
public class EvaluacionPuntajeSeccionDetalleCombos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionPuntajeSeccionDetalleCombosPK evaluacionPuntajeSeccionDetalleCombosPK;

    public EvaluacionPuntajeSeccionDetalleCombos() {
    }

    public EvaluacionPuntajeSeccionDetalleCombos(EvaluacionPuntajeSeccionDetalleCombosPK evaluacionPuntajeSeccionDetalleCombosPK) {
        this.evaluacionPuntajeSeccionDetalleCombosPK = evaluacionPuntajeSeccionDetalleCombosPK;
    }

    public EvaluacionPuntajeSeccionDetalleCombos(Long codEvaluacion, int codigoEstablecimiento, String codPuntaje, String codCiclo, int codSeccion, int codDetalle, int codItem) {
        this.evaluacionPuntajeSeccionDetalleCombosPK = new EvaluacionPuntajeSeccionDetalleCombosPK(codEvaluacion, codigoEstablecimiento, codPuntaje, codCiclo, codSeccion, codDetalle, codItem);
    }
    
    public EvaluacionPuntajeSeccionDetalleCombos(Long codEvaluacion, int codigoEstablecimiento, String codCiclo, int codSeccion, int codDetalle, int codItem) {
        this.evaluacionPuntajeSeccionDetalleCombosPK = new EvaluacionPuntajeSeccionDetalleCombosPK(codEvaluacion, codigoEstablecimiento, codCiclo, codSeccion, codDetalle, codItem);
    }

    public EvaluacionPuntajeSeccionDetalleCombosPK getEvaluacionPuntajeSeccionDetalleCombosPK() {
        return evaluacionPuntajeSeccionDetalleCombosPK;
    }

    public void setEvaluacionPuntajeSeccionDetalleCombosPK(EvaluacionPuntajeSeccionDetalleCombosPK evaluacionPuntajeSeccionDetalleCombosPK) {
        this.evaluacionPuntajeSeccionDetalleCombosPK = evaluacionPuntajeSeccionDetalleCombosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionPuntajeSeccionDetalleCombosPK != null ? evaluacionPuntajeSeccionDetalleCombosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPuntajeSeccionDetalleCombos)) {
            return false;
        }
        EvaluacionPuntajeSeccionDetalleCombos other = (EvaluacionPuntajeSeccionDetalleCombos) object;
        if ((this.evaluacionPuntajeSeccionDetalleCombosPK == null && other.evaluacionPuntajeSeccionDetalleCombosPK != null) || (this.evaluacionPuntajeSeccionDetalleCombosPK != null && !this.evaluacionPuntajeSeccionDetalleCombosPK.equals(other.evaluacionPuntajeSeccionDetalleCombosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPuntajeSeccionDetalleCombos[ evaluacionPuntajeSeccionDetalleCombosPK=" + evaluacionPuntajeSeccionDetalleCombosPK + " ]";
    }
    
}
