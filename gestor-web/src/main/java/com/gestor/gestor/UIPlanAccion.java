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
import com.gestor.gestor.controlador.GestorEvaluacionPlanAccion;
import com.gestor.modelo.Sesion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiPlanAccion")
@SessionScoped

public class UIPlanAccion {

    private SeccionDetalleItems sdiSeleccionado;
    private List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles = new ArrayList<>();
    private Boolean modificarActivo = Boolean.FALSE;

    public void limpiar() {
        UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);
        modificarActivo = Boolean.FALSE;
    }

    public void modificarPlanAccion() {
        EvaluacionPlanAccionDetalle epad = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");
        evaluacionPlanAccionDetalles.remove(epad);
        modificarActivo = Boolean.TRUE;
        UtilJSF.setBean("evaluacionPlanAccionDetalle", epad, UtilJSF.SESSION_SCOPE);
        System.out.println("obj" + (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle"));
    }

    public void eliminarPlanAccion() {
        try {
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();
            EvaluacionPlanAccionDetalle epad = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("varPlanAccionDetalle");
            epad.setDocumentoUsuario(documentoUsuario);
            epad.setEstado(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ELIMINADO);

            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            if (gestorEvaluacionPlanAccion.actualizarEstadoEvaluacionPlanAccionDetalle(epad) <= 0) {
                UtilMSG.addErrorMsg("Eliminar Plan Acción", "No se pudo eliminar el plan de acción, por favor intente de nuevo.");
            } else {
                evaluacionPlanAccionDetalles.remove(epad);
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

    public void procesarPlanAccionModificar() {
        try {
            EvaluacionPlanAccionDetalle epd = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle");
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            gestorEvaluacionPlanAccion.actualizarEvaluacionPlanAccionDetalle(epd);

            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));
            
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);

        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
                UtilMSG.addWarningMsg(e.getCause().getMessage(), e.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), e);
            }
        }
    }

    public void procesarPlanAccion() {
        try {
            EvaluacionPlanAccionDetalle epd = (EvaluacionPlanAccionDetalle) UtilJSF.getBean("evaluacionPlanAccionDetalle");
            String documentoUsuario = ((Sesion) UtilJSF.getBean("sesion")).getUsuarios().getUsuariosPK().getDocumentoUsuario();

            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();

            epd = gestorEvaluacionPlanAccion.validarEvaluacionPlanAccionDetalle(epd);

            Long codPlan = gestorEvaluacionPlanAccion.consultarEvaluacionPlanAccion(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(), App.EVALUACION_PLAN_ACCION_ESTADO_ABIERTO);

            if (codPlan == null) {
                codPlan = gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_PLAN_ACCION_COD_PLAN_SEQ);
            }

            EvaluacionPlanAccion ep = new EvaluacionPlanAccion(
                    new EvaluacionPlanAccionPK(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                            sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                            codPlan),
                    App.EVALUACION_PLAN_ACCION_ESTADO_ABIERTO, documentoUsuario, documentoUsuario
            );
            epd.setEvaluacionPlanAccionDetallePK(new EvaluacionPlanAccionDetallePK(ep.getEvaluacionPlanAccionPK().getCodEvaluacion(), ep.getEvaluacionPlanAccionPK().getCodigoEstablecimiento(),
                    ep.getEvaluacionPlanAccionPK().getCodPlan()));
            epd.setEstado(App.EVALUACION_PLAN_ACCION_DETALLE_ESTADO_ABIERTO);

            epd.setCodCiclo(sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo());
            epd.setCodSeccion(sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion());
            epd.setCodDetalle(sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle());
            epd.setCodItem(sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem());
            epd.setDocumentoUsuario(documentoUsuario);

            ep.setEvaluacionPlanAccionDetalle(epd);
            gestorEvaluacionPlanAccion.procesarPlanAccion(ep);
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);
            UtilMSG.addWarningMsg("Plan Acción Guardado", "Se almaceno el plan de acción satisfactoriamente.");
            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg("Validación", ex.getMessage());
            } else {
                UtilMSG.addSupportMsg();
                UtilLog.generarLog(this.getClass(), ex);
            }
        }

    }

    public void mostrarDialogoPlanAccion() {
        try {
            this.sdiSeleccionado = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
            GestorEvaluacionPlanAccion gestorEvaluacionPlanAccion = new GestorEvaluacionPlanAccion();
            evaluacionPlanAccionDetalles = new ArrayList<>();
            evaluacionPlanAccionDetalles.addAll(gestorEvaluacionPlanAccion.cargarListaEvaluacionPlanAccion(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            ));

            Dialogo dialogo = new Dialogo("dialogos/plan-accion.xhtml", "Plan Acción", "clip", Dialogo.WIDTH_80);
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
     * @return the evaluacionPlanAccionDetalles
     */
    public List<EvaluacionPlanAccionDetalle> getEvaluacionPlanAccionDetalles() {
        return evaluacionPlanAccionDetalles;
    }

    /**
     * @param evaluacionPlanAccionDetalles the evaluacionPlanAccionDetalles to
     * set
     */
    public void setEvaluacionPlanAccionDetalles(List<EvaluacionPlanAccionDetalle> evaluacionPlanAccionDetalles) {
        this.evaluacionPlanAccionDetalles = evaluacionPlanAccionDetalles;
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
