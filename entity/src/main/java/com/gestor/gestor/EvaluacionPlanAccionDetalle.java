/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.publico.Establecimiento;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Basic;
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
@Table(name = "evaluacion_plan_accion_detalle")
@NamedQueries({
    @NamedQuery(name = "EvaluacionPlanAccionDetalle.findAll", query = "SELECT e FROM EvaluacionPlanAccionDetalle e")
})
@ManagedBean
@SessionScoped
public class EvaluacionPlanAccionDetalle implements Serializable {

    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_DOCUMENTO_USUARIO = "U.DOCUMENTO_USUARIO IN (?)";
    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_ESTADO = "EPAD.ESTADO IN (?)";
    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_LIMITE_600 = "LIMIT 600";

    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_ESTABLECIMIENTO = "EPAD.codigo_establecimiento IN (?)";
    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_EVALUACION = "EPAD.cod_evaluacion IN (?)";
    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_COD_CICLO = "EPAD.cod_ciclo IN (?)";
    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_RESPONSABLE = "EPAD.responsable ILIKE (?)";
    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_FECHA_REGISTRO_GTE = "EPAD.fecha_registro::DATE >= ?";
    public static String EVALUACION_PLAN_ACCION_DETALLE_CONDICION_FECHA_REGISTRO_LTE = "EPAD.fecha_registro::DATE <= ?";
    
    public static String EVALUACION_PLAN_ACCION_DETALLE_ESTADO_CERRADO = "C";
    

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK;
    @Column(name = "cod_ciclo")
    private String codCiclo;
    @Column(name = "cod_seccion")
    private Integer codSeccion;
    @Column(name = "cod_detalle")
    private Integer codDetalle;
    @Column(name = "cod_item")
    private Integer codItem;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_plazo")
    private Date fechaPlazo;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    private String documentoUsuario;
    
    private Responsable responsable = new Responsable();

    private Usuarios usuarios;

    private Date fechaRegistro;
    
    
    
    

    private SeccionDetalleItems seccionDetalleItems;
    private Evaluacion evaluacion;
    private Establecimiento establecimiento;
    private List<EvaluacionPlanAccionDetalleNotas> evaluacionPlanAccionDetalleNotasList;
    private EvaluacionPlanAccionDetalleNotas evaluacionPlanAccionDetalleNotas;

    public EvaluacionPlanAccionDetalle() {
    }

    public EvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK) {
        this.evaluacionPlanAccionDetallePK = evaluacionPlanAccionDetallePK;
    }

    public EvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK, String estado) {
        this.evaluacionPlanAccionDetallePK = evaluacionPlanAccionDetallePK;
        this.estado = estado;
    }

    public EvaluacionPlanAccionDetalle(Long codEvaluacion, int codigoEstablecimiento, Long codPlan, Long codPlanDetalle) {
        this.evaluacionPlanAccionDetallePK = new EvaluacionPlanAccionDetallePK(codEvaluacion, codigoEstablecimiento, codPlan, codPlanDetalle);
    }

    public EvaluacionPlanAccionDetalle(EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK, String codCiclo, Integer codSeccion, Integer codDetalle, Integer codItem, String nombre, String descripcion, String estado, Usuarios usuarios, Date fechaRegistro, Date fechaPlazo) {
        this.evaluacionPlanAccionDetallePK = evaluacionPlanAccionDetallePK;
        this.codCiclo = codCiclo;
        this.codSeccion = codSeccion;
        this.codDetalle = codDetalle;
        this.codItem = codItem;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuarios = usuarios;
        this.fechaRegistro = fechaRegistro;
        this.fechaPlazo = fechaPlazo;
    }

    public EvaluacionPlanAccionDetallePK getEvaluacionPlanAccionDetallePK() {
        return evaluacionPlanAccionDetallePK;
    }

    public void setEvaluacionPlanAccionDetallePK(EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK) {
        this.evaluacionPlanAccionDetallePK = evaluacionPlanAccionDetallePK;
    }

    public void setEvaluacionPlanAccionDetallePK(EvaluacionPlanAccionDetallePK evaluacionPlanAccionDetallePK, String estado) {
        this.evaluacionPlanAccionDetallePK = evaluacionPlanAccionDetallePK;
    }

    public String getCodCiclo() {
        return codCiclo;
    }

    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
    }

    public Integer getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(Integer codSeccion) {
        this.codSeccion = codSeccion;
    }

    public Integer getCodDetalle() {
        return codDetalle;
    }

    public void setCodDetalle(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    public Integer getCodItem() {
        return codItem;
    }

    public void setCodItem(Integer codItem) {
        this.codItem = codItem;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionPlanAccionDetallePK != null ? evaluacionPlanAccionDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPlanAccionDetalle)) {
            return false;
        }
        EvaluacionPlanAccionDetalle other = (EvaluacionPlanAccionDetalle) object;
        return !((this.evaluacionPlanAccionDetallePK == null && other.evaluacionPlanAccionDetallePK != null) || (this.evaluacionPlanAccionDetallePK != null && !this.evaluacionPlanAccionDetallePK.equals(other.evaluacionPlanAccionDetallePK)));
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionPlanAccionDetalle[ evaluacionPlanAccionDetallePK=" + evaluacionPlanAccionDetallePK + " ]";
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaPlazo() {
        return fechaPlazo;
    }

    public void setFechaPlazo(Date fechaPlazo) {
        this.fechaPlazo = fechaPlazo;
    }

    /**
     * @return the usuarios
     */
    public Usuarios getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the documentoUsuario
     */
    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    /**
     * @param documentoUsuario the documentoUsuario to set
     */
    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    /**
     * @return the seccionDetalleItems
     */
    public SeccionDetalleItems getSeccionDetalleItems() {
        return seccionDetalleItems;
    }

    /**
     * @param seccionDetalleItems the seccionDetalleItems to set
     */
    public void setSeccionDetalleItems(SeccionDetalleItems seccionDetalleItems) {
        this.seccionDetalleItems = seccionDetalleItems;
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
     * @return the evaluacionPlanAccionDetalleNotasList
     */
    public List<EvaluacionPlanAccionDetalleNotas> getEvaluacionPlanAccionDetalleNotasList() {
        return evaluacionPlanAccionDetalleNotasList;
    }

    /**
     * @param evaluacionPlanAccionDetalleNotasList the evaluacionPlanAccionDetalleNotasList to set
     */
    public void setEvaluacionPlanAccionDetalleNotasList(List<EvaluacionPlanAccionDetalleNotas> evaluacionPlanAccionDetalleNotasList) {
        this.evaluacionPlanAccionDetalleNotasList = evaluacionPlanAccionDetalleNotasList;
    }

    /**
     * @return the evaluacionPlanAccionDetalleNotas
     */
    public EvaluacionPlanAccionDetalleNotas getEvaluacionPlanAccionDetalleNotas() {
        return evaluacionPlanAccionDetalleNotas;
    }

    /**
     * @param evaluacionPlanAccionDetalleNotas the evaluacionPlanAccionDetalleNotas to set
     */
    public void setEvaluacionPlanAccionDetalleNotas(EvaluacionPlanAccionDetalleNotas evaluacionPlanAccionDetalleNotas) {
        this.evaluacionPlanAccionDetalleNotas = evaluacionPlanAccionDetalleNotas;
    }

    /**
     * @return the responsable
     */
    public Responsable getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

}
