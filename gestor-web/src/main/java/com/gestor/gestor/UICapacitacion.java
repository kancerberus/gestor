/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.controller.GestorGeneral;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.controlador.GestorEvaluacionCapacitacion;
import com.gestor.gestor.controlador.GestorEvaluacionPlanAccion;
import com.gestor.modelo.Sesion;
import com.gestor.publico.controlador.GestorEstablecimiento;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiCapacitacion")
@SessionScoped
public class UICapacitacion {

    private SeccionDetalleItems sdiSeleccionado;
    private List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles = new ArrayList<>();

    private Boolean modificarActivo = Boolean.FALSE;

    public void limpiar() {
        modificarActivo = Boolean.FALSE;
        UtilJSF.setBean("evaluacionCapacitacionDetalle", new EvaluacionCapacitacionDetalle(), UtilJSF.SESSION_SCOPE);

    }

    public void modificarPlanAccion() {
        EvaluacionCapacitacionDetalle epad = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("varCapacitacionDetalle");
        evaluacionCapacitacionDetalles.remove(epad);
        modificarActivo = Boolean.TRUE;
        UtilJSF.setBean("evaluacionPlanAccionDetalle", epad, UtilJSF.SESSION_SCOPE);
    }

    public void eliminarCapacitacion() {
        try {
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();
            EvaluacionCapacitacionDetalle ecd = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("varCapacitacionDetalle");
            ecd.setDocumentoUsuario(documentoUsuario);
            ecd.setEstado(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ELIMINADO);

            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            if (gestorEvaluacionCapacitacion.actualizarEstadoEvaluacionCapacitacionDetalle(ecd) <= 0) {
                UtilMSG.addErrorMsg("Eliminar Capacitación", "No se pudo eliminar la capacitación, por favor intente de nuevo.");
            } else {
                evaluacionCapacitacionDetalles.remove(ecd);
            }

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilLog.generarLog(this.getClass(), e);
                UtilMSG.addSupportMsg();
            }
        }

    }

    public void procesarCapacitacionModificar() {
        try {
            EvaluacionCapacitacionDetalle ecd = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("evaluacionCapacitacionDetalle");;
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            gestorEvaluacionCapacitacion.actualizarEvaluacionCapacitacionDetalle(ecd);

            evaluacionCapacitacionDetalles = new ArrayList<>();
            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));
            
            UtilJSF.setBean("evaluacionCapacitacionDetalle", new EvaluacionCapacitacionDetalle(), UtilJSF.SESSION_SCOPE);

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public void procesarCapacitacion() {
        try {
            EvaluacionCapacitacionDetalle ecd = (EvaluacionCapacitacionDetalle) UtilJSF.getBean("evaluacionCapacitacionDetalle");;
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();

            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            ecd = gestorEvaluacionCapacitacion.validarEvaluacionCapacitacionDetalle(ecd);

            Long codCapacitacion = gestorEvaluacionCapacitacion.consultarEvaluacionCapacitacion(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(), App.EVALUACION_CAPACITACION_ESTADO_ABIERTO);

            if (codCapacitacion == null) {
                codCapacitacion = gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_CAPACITACION_COD_CAPACITACION_SEQ);
            }

            EvaluacionCapacitacion ec = new EvaluacionCapacitacion(new EvaluacionCapacitacionPK(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(), codCapacitacion
            ), documentoUsuario, App.EVALUACION_CAPACITACION_ESTADO_ABIERTO);

            ecd.setEvaluacionCapacitacionDetallePK(new EvaluacionCapacitacionDetallePK(ec.getEvaluacionCapacitacionPK().getCodEvaluacion(),
                    ec.getEvaluacionCapacitacionPK().getCodigoEstablecimiento(), ec.getEvaluacionCapacitacionPK().getCodCapacitacion(), 0));
            ecd.setEstado(App.EVALUACION_CAPACITACION_DETALLE_ESTADO_ABIERTO);

            ecd.setCodCiclo(sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo());
            ecd.setCodSeccion(sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion());
            ecd.setCodDetalle(sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle());
            ecd.setCodItem(sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem());
            ecd.setDocumentoUsuario(documentoUsuario);

            ec.setEvaluacionCapacitacionDetalle(ecd);

            gestorEvaluacionCapacitacion.procesarCapacitacion(ec);
            UtilJSF.setBean("evaluacionCapacitacionDetalle", new EvaluacionCapacitacionDetalle(), UtilJSF.SESSION_SCOPE);
            UtilMSG.addSuccessMsg("Capacitación Guardada", "Se almaceno la capacitación satisfactoriamente.");
            evaluacionCapacitacionDetalles = new ArrayList<>();
            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addErrorMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public void mostrarDialogoCapacitacion() {
        try {
            this.sdiSeleccionado = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
            GestorEvaluacionCapacitacion gestorEvaluacionCapacitacion = new GestorEvaluacionCapacitacion();
            evaluacionCapacitacionDetalles = new ArrayList<>();
            evaluacionCapacitacionDetalles.addAll(gestorEvaluacionCapacitacion.cargarListaEvaluacionCapacitacionDetalle(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

            Dialogo dialogo = new Dialogo("dialogos/capacitacion.xhtml", "Capactiación", "clip", Dialogo.WIDTH_80);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    /**
     * @return the evaluacionCapacitacionDetalles
     */
    public List<EvaluacionCapacitacionDetalle> getEvaluacionCapacitacionDetalles() {
        return evaluacionCapacitacionDetalles;
    }

    /**
     * @param evaluacionCapacitacionDetalles the evaluacionCapacitacionDetalles
     * to set
     */
    public void setEvaluacionCapacitacionDetalles(List<EvaluacionCapacitacionDetalle> evaluacionCapacitacionDetalles) {
        this.evaluacionCapacitacionDetalles = evaluacionCapacitacionDetalles;
    }

    /**
     * @return the sdiSeleccionado
     */
    public SeccionDetalleItems getSdiSeleccionado() {
        return sdiSeleccionado;
    }

    /**
     * @param sdiSeleccionado the sdiSeleccionado to set
     */
    public void setSdiSeleccionado(SeccionDetalleItems sdiSeleccionado) {
        this.sdiSeleccionado = sdiSeleccionado;
    }

    /**
     * @return the modificarActivo
     */
    public Boolean getModificarActivo() {
        return modificarActivo;
    }

    /**
     * @param modificarActivo the modificarActivo to set
     */
    public void setModificarActivo(Boolean modificarActivo) {
        this.modificarActivo = modificarActivo;
    }
}
