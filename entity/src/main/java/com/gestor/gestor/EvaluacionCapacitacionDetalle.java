/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import com.gestor.seguimiento.PlanCapacitacion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "evaluacion_capacitacion_detalle")
@NamedQueries({
    @NamedQuery(name = "EvaluacionCapacitacionDetalle.findAll", query = "SELECT e FROM EvaluacionCapacitacionDetalle e")
})
@ManagedBean
@SessionScoped
public class EvaluacionCapacitacionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_DOCUMENTO_USUARIO = "U.DOCUMENTO_USUARIO IN (?)";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_ESTADO = "ECD.ESTADO IN (?)";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_COD_ESTABLECIMIENTO = "ECD.codigo_establecimiento IN (?)";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_COD_EVALUACION = "ECD.cod_evaluacion IN (?)";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_COD_CICLO = "ECD.cod_ciclo IN (?)";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_RESPONSABLE = "ECD.responsable ILIKE (?)";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_FECHA_REGISTRO_GTE = "ECD.fecha_registro::DATE >= ?";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_FECHA_REGISTRO_LTE = "ECD.fecha_registro::DATE <= ?";
    public static String EVALUACION_CAPACITACION_DETALLE_CONDICION_FECHA_FINALIZADO_LTE = "ECD.fecha_finalizado::DATE <= ?";
    
    public static String EVALUACION_CAPACITACION_DETALLE_ESTADO_CERRADO = "C";
    
    
    @EmbeddedId
    protected EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK;

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
    @Basic(optional = false)    
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_finalizado")
    private Date fechaFinalizado;  
    @Column(name = "fecha_plazo")
    private Date fechaPlazo;  
    
    private Integer diasRestantes;

    
    @JoinColumns({
        @JoinColumn(name = "cod_evaluacion", referencedColumnName = "cod_evaluacion", insertable = false, updatable = false),
        @JoinColumn(name = "codigo_establecimiento", referencedColumnName = "codigo_establecimiento", insertable = false, updatable = false),
        @JoinColumn(name = "cod_capacitacion", referencedColumnName = "cod_capacitacion", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private EvaluacionCapacitacion evaluacionCapacitacion;

    private String documentoUsuario;
    private Responsable responsable = new Responsable();    
    private CentroTrabajo centrotrabajo = new CentroTrabajo();
    private Modalidad modalidad = new Modalidad();
    private TecnicaCapacitacion tecnica = new TecnicaCapacitacion();
    private Facilitador facilitador = new Facilitador();
    private Dirigida dirigida = new Dirigida();
    private Recursos recursos = new Recursos();
    private PlanCapacitacion plancapacitacion= new PlanCapacitacion();
    
    private Usuarios usuarios = new Usuarios();

    private Date fechaRegistro;

    private SeccionDetalleItems seccionDetalleItems;
    private Evaluacion evaluacion;
    private Establecimiento establecimiento;
    private List<EvaluacionCapacitacionDetalleNotas> evaluacionCapacitacionDetalleNotasList;
    private EvaluacionCapacitacionDetalleNotas evaluacionCapacitacionDetalleNotas;
    

    public EvaluacionCapacitacionDetalle() {
    }

    public EvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
    }

    public EvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK, String estado) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
        this.estado = estado;
    }

    public EvaluacionCapacitacionDetalle(Long codEvaluacion, int codigoEstablecimiento, Long codCapacitacion, Long codCapacitacionDetalle) {
        this.evaluacionCapacitacionDetallePK = new EvaluacionCapacitacionDetallePK(codEvaluacion, codigoEstablecimiento, codCapacitacion, codCapacitacionDetalle);
    }

    public EvaluacionCapacitacionDetalle(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK, String codCiclo, int codSeccion, int codDetalle, int codItem, String nombre, String descripcion,
            String estado, Usuarios usuarios, java.sql.Date fechaRegistro, Date fechaPlazo, Date fechaFinalizado) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
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
        this.fechaFinalizado = fechaFinalizado;         
        
    }

    public Integer getDiasRestantes() {
        return diasRestantes;
    }

    public void setDiasRestantes(Integer diasRestantes) {
        this.diasRestantes = diasRestantes;
    }
    
    public PlanCapacitacion getPlancapacitacion() {
        return plancapacitacion;
    }

    public void setPlancapacitacion(PlanCapacitacion plancapacitacion) {
        this.plancapacitacion = plancapacitacion;
    }

    public CentroTrabajo getCentrotrabajo() {
        return centrotrabajo;
    }

    public void setCentrotrabajo(CentroTrabajo centrotrabajo) {
        this.centrotrabajo = centrotrabajo;
    }

    public Date getFechaFinalizado() {
        return fechaFinalizado;
    }

    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    public EvaluacionCapacitacionDetallePK getEvaluacionCapacitacionDetallePK() {
        return evaluacionCapacitacionDetallePK;
    }

    public void setEvaluacionCapacitacionDetallePK(EvaluacionCapacitacionDetallePK evaluacionCapacitacionDetallePK) {
        this.evaluacionCapacitacionDetallePK = evaluacionCapacitacionDetallePK;
    }    

    public TecnicaCapacitacion getTecnica() {
        return tecnica;
    }

    public void setTecnica(TecnicaCapacitacion tecnica) {
        this.tecnica = tecnica;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Facilitador getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(Facilitador facilitador) {
        this.facilitador = facilitador;
    }

    public Dirigida getDirigida() {
        return dirigida;
    }

    public void setDirigida(Dirigida dirigida) {
        this.dirigida = dirigida;
    }

    public Recursos getRecursos() {
        return recursos;
    }

    public void setRecursos(Recursos recursos) {
        this.recursos = recursos;
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

    public EvaluacionCapacitacion getEvaluacionCapacitacion() {
        return evaluacionCapacitacion;
    }

    public void setEvaluacionCapacitacion(EvaluacionCapacitacion evaluacionCapacitacion) {
        this.evaluacionCapacitacion = evaluacionCapacitacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionCapacitacionDetallePK != null ? evaluacionCapacitacionDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitacionDetalle)) {
            return false;
        }
        EvaluacionCapacitacionDetalle other = (EvaluacionCapacitacionDetalle) object;
        if ((this.evaluacionCapacitacionDetallePK == null && other.evaluacionCapacitacionDetallePK != null) || (this.evaluacionCapacitacionDetallePK != null && !this.evaluacionCapacitacionDetallePK.equals(other.evaluacionCapacitacionDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.gestor.EvaluacionCapacitacionDetalle[ evaluacionCapacitacionDetallePK=" + evaluacionCapacitacionDetallePK + " ]";
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
     * @return the codCiclo
     */
    public String getCodCiclo() {
        return codCiclo;
    }

    /**
     * @param codCiclo the codCiclo to set
     */
    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
    }

    /**
     * @return the codSeccion
     */
    public Integer getCodSeccion() {
        return codSeccion;
    }

    /**
     * @param codSeccion the codSeccion to set
     */
    public void setCodSeccion(Integer codSeccion) {
        this.codSeccion = codSeccion;
    }

    /**
     * @return the codDetalle
     */
    public Integer getCodDetalle() {
        return codDetalle;
    }

    /**
     * @param codDetalle the codDetalle to set
     */
    public void setCodDetalle(Integer codDetalle) {
        this.codDetalle = codDetalle;
    }

    /**
     * @return the codItem
     */
    public Integer getCodItem() {
        return codItem;
    }

    /**
     * @param codItem the codItem to set
     */
    public void setCodItem(Integer codItem) {
        this.codItem = codItem;
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
     * @return the evaluacionCapacitacionDetalleNotasList
     */
    public List<EvaluacionCapacitacionDetalleNotas> getEvaluacionCapacitacionDetalleNotasList() {
        return evaluacionCapacitacionDetalleNotasList;
    }

    /**
     * @param evaluacionCapacitacionDetalleNotasList the evaluacionCapacitacionDetalleNotasList to set
     */
    public void setEvaluacionCapacitacionDetalleNotasList(List<EvaluacionCapacitacionDetalleNotas> evaluacionCapacitacionDetalleNotasList) {
        this.evaluacionCapacitacionDetalleNotasList = evaluacionCapacitacionDetalleNotasList;
    }

    /**
     * @return the evaluacionCapacitacionDetalleNotas
     */
    public EvaluacionCapacitacionDetalleNotas getEvaluacionCapacitacionDetalleNotas() {
        return evaluacionCapacitacionDetalleNotas;
    }

    /**
     * @param evaluacionCapacitacionDetalleNotas the evaluacionCapacitacionDetalleNotas to set
     */
    public void setEvaluacionCapacitacionDetalleNotas(EvaluacionCapacitacionDetalleNotas evaluacionCapacitacionDetalleNotas) {
        this.evaluacionCapacitacionDetalleNotas = evaluacionCapacitacionDetalleNotas;
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
