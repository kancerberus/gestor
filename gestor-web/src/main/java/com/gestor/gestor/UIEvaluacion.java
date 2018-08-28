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
import com.gestor.gestor.controlador.GestorCiclo;
import com.gestor.gestor.controlador.GestorEvaluacion;
import com.gestor.gestor.controlador.GestorSeccionDetalleItems;
import com.gestor.gestor.controlador.GestorSeccionDetalleItemsAyuda;
import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.Lista;
import com.gestor.publico.controlador.GestorLista;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiEvaluacion")
@SessionScoped
public class UIEvaluacion {

    public static final String COMPONENTES_REFRESCAR = "dialog";

    private List<Evaluacion> evaluacionList = new ArrayList<>();
    private List<SeccionDetalleItems> resumenEvaluacionList;
    private Establecimiento establecimiento;
    private Lista seccionDetalleItemsOpcionesLista;
    private List<SeccionDetalleItemsAyuda> seccionDetalleItemsAyudas = new ArrayList<>();

    private boolean guardarActivo = false;
    private boolean nuevoActivo = true;
    private boolean eliminarActivo = false;
    private boolean consultarActivo = false;
    private boolean cancelarActivo = false;

    private boolean finalizarActivo = false;

    public void mostrarAyudaItem() {
        try {
            SeccionDetalleItems sdi = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
            GestorSeccionDetalleItemsAyuda gestorSeccionDetalleItemsAyuda = new GestorSeccionDetalleItemsAyuda();
            seccionDetalleItemsAyudas = new ArrayList<>();
            seccionDetalleItemsAyudas.addAll(
                    gestorSeccionDetalleItemsAyuda.cargarSeccionDetalleItemsAyudas(
                            new SeccionDetalleItemsAyudaPK(
                                    sdi.getSeccionDetalleItemsPK().getCodCiclo(), sdi.getSeccionDetalleItemsPK().getCodSeccion(), sdi.getSeccionDetalleItemsPK().getCodDetalle(), sdi.getSeccionDetalleItemsPK().getCodItem()
                            )
                    )
            );
            if (seccionDetalleItemsAyudas != null && !seccionDetalleItemsAyudas.isEmpty()) {
                Dialogo dialogo = new Dialogo("dialogos/ayuda-item.xhtml", "Indicaciones", "clip", Dialogo.WIDTH_80);
                UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
                UtilJSF.execute("PF('dialog').show();");
            } else {
                UtilMSG.addWarningMsg("El ítem no tiene configurado criterios de verificación");
            }
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public void seleccionarEstablecimiento() {
        try {
            Establecimiento e = (Establecimiento) UtilJSF.getBean("varEstablecimiento");
            this.establecimiento = (Establecimiento) e.clone();
        } catch (CloneNotSupportedException ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public String nuevo() {
        try {
            Dialogo dialogo = new Dialogo("dialogos/nuevo.xhtml", "Nueva Evaluación", "clip", "80%");
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return "";
    }

    @PostConstruct
    public void init() {
        try {
            this.seccionDetalleItemsOpcionesLista = new GestorLista().cargarLista(App.LISTA_SECCION_DETALLE_ITEMS_OPCIONES);
            UtilJSF.setBean("evaluacion", new Evaluacion(), UtilJSF.SESSION_SCOPE);
            UtilJSF.setBean("evaluacionAdjuntos", new EvaluacionAdjuntos(), UtilJSF.SESSION_SCOPE);
            UtilJSF.setBean("evaluacionPlanAccionDetalle", new EvaluacionPlanAccionDetalle(), UtilJSF.SESSION_SCOPE);
            UtilJSF.setBean("evaluacionCapacitacionDetalle", new EvaluacionCapacitacionDetalle(), UtilJSF.SESSION_SCOPE);

        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public String continuarEvaluacion() {
        try {
            Sesion sesion = (Sesion) UtilJSF.getBean("sesion");

            GestorCiclo gestorCiclos = new GestorCiclo();
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            GestorSeccionDetalleItems gestorSeccionDetalleItems = new GestorSeccionDetalleItems();

            Evaluacion e = (Evaluacion) UtilJSF.getBean("varEvaluacion");

            e.setCiclos(sesion.getCiclos());
            e.setEvaluacionPuntajesList(gestorEvaluacion.cargarEvaluacionPuntajes(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion()));

            List<String> evaluacionPuntajesItems = new ArrayList<>();
            for (EvaluacionPuntajes ep : e.getEvaluacionPuntajesList()) {
                evaluacionPuntajesItems.add(ep.getDescripcion());
            }

            for (Ciclo c : e.getCiclos()) {
                c.setEvaluacion(e);
                for (Seccion s : c.getSeccionList()) {
                    s.setCiclo(c);
                    for (SeccionDetalle sd : s.getSeccionDetalleList()) {
                        sd.setSeccion(s);
                        for (SeccionDetalleItems sdi : sd.getSeccionDetalleItemsList()) {
                            sdi.setSeccionDetalle(sd);
                            sdi.setEvaluacionPuntajesItems(evaluacionPuntajesItems);

                            EvaluacionPuntajeSeccionDetalleCombos epsc = new EvaluacionPuntajeSeccionDetalleCombos(
                                    e.getEvaluacionPK().getCodEvaluacion(), e.getEvaluacionPK().getCodigoEstablecimiento(),
                                    sdi.getSeccionDetalleItemsPK().getCodCiclo(), sdi.getSeccionDetalleItemsPK().getCodSeccion(), sdi.getSeccionDetalleItemsPK().getCodDetalle(), sdi.getSeccionDetalleItemsPK().getCodItem());
                            sdi.getEvaluacionPuntajes().setDescripcion(gestorSeccionDetalleItems.cargarDescripcionEvaluacionPuntajes(epsc.getEvaluacionPuntajeSeccionDetalleCombosPK()));
                        }
                    }
                }
            }

            this.nuevoActivo = Boolean.FALSE;
            this.guardarActivo = this.cancelarActivo = Boolean.TRUE;

            UtilJSF.setBean("evaluacion", e, UtilJSF.SESSION_SCOPE);

            return ("/gestor/evaluacion.xhtml?faces-redirect=true");
        } catch (Exception e) {
        }

        return "";
    }

    public String cargarSeccion() {
        Ciclo c = (Ciclo) UtilJSF.getBean("varCiclo");
        UtilJSF.setBean("ciclo", c, UtilJSF.SESSION_SCOPE);
        UtilJSF.setBean("seccion", new Seccion(), UtilJSF.SESSION_SCOPE);
        UtilJSF.setBean("seccionDetalle", new SeccionDetalle(), UtilJSF.SESSION_SCOPE);
        return ("/gestor/seccion.xhtml?faces-redirect=true");
    }

    public String cargarSeccionDetalle() {
        Seccion s = (Seccion) UtilJSF.getBean("varSeccion");
        UtilJSF.setBean("seccion", s, UtilJSF.SESSION_SCOPE);
        return ("/gestor/seccion-detalle.xhtml?faces-redirect=true");
    }

    public String cargarSeccionDetalleItems() {
        SeccionDetalle sd = (SeccionDetalle) UtilJSF.getBean("varSeccionDetalle");
        UtilJSF.setBean("seccionDetalle", sd, UtilJSF.SESSION_SCOPE);
        return ("/gestor/seccion-detalle-items.xhtml?faces-redirect=true");
    }

    public void guardarSeccionDetalleItems() {
        SeccionDetalleItems sdi = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
//        System.out.println("d=>" + sdi.getEvaluacionPuntajes().getDescripcion());
        try {
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            GestorSeccionDetalleItems gestorSeccionDetalleItems = new GestorSeccionDetalleItems();
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            EvaluacionPuntajes evaluacionPuntajes = gestorEvaluacion.cargarEvaluacionPuntajes(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion(), sdi.getEvaluacionPuntajes().getDescripcion());
            EvaluacionPuntajeSeccionDetalleCombos epsc = new EvaluacionPuntajeSeccionDetalleCombos(
                    e.getEvaluacionPK().getCodEvaluacion(), e.getEvaluacionPK().getCodigoEstablecimiento(), evaluacionPuntajes.getEvaluacionPuntajesPK().getCodPuntaje(),
                    sdi.getSeccionDetalleItemsPK().getCodCiclo(), sdi.getSeccionDetalleItemsPK().getCodSeccion(), sdi.getSeccionDetalleItemsPK().getCodDetalle(), sdi.getSeccionDetalleItemsPK().getCodItem()
            );
            gestorSeccionDetalleItems.upsertEvaluacionPuntajeSeccionDetalleCombos(epsc);

        } catch (Exception e) {
            UtilMSG.addErrorMsg(UtilMSG.getSupportMsg());
            UtilLog.generarLog(this.getClass(), e);
        }

    }

    public void cancelarProcesarEvaluacion() {
        this.establecimiento = new Establecimiento();
    }

    public void finalizarEvaluacion() {
        if (getAvanceEvaluacion() < 100) {
            UtilMSG.addWarningMsg("Se debe completar la evalución para finalizarla.");
            return;
        }
        try {
            Evaluacion evaluacion = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            gestorEvaluacion.finalizarEvaluacion(evaluacion);
            this.finalizarActivo = Boolean.FALSE;
            this.evaluacionList = null;
            UtilMSG.addSuccessMsg("Evaluación finalizada correctamente.");
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    public String procesarEvaluacion() {
        try {
            Sesion sesion = (Sesion) UtilJSF.getBean("sesion");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorCiclo gestorCiclos = new GestorCiclo();

            Evaluacion evaluacion = (Evaluacion) UtilJSF.getBean("evaluacion");
            if (evaluacion.getFecha() == null) {
                evaluacion.setFecha(new Date());
            }
            Evaluacion e = new Evaluacion(new EvaluacionPK(gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_COD_EVALUACION_SEQ), sesion.getEstablecimiento().getCodigoEstablecimiento()),
                    sesion.getUsuarios().getUsuariosPK().getDocumentoUsuario(), new Date(), new Date(), App.EVALUACION_ESTADO_ABIERTO);

            e.setFecha(evaluacion.getFecha());
            e.setEvaluacionPuntajesList(gestorEvaluacion.generarEvaluacionPuntajes(sesion.getEstablecimiento().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion(), sesion.getPuntajesList()));
            e = gestorEvaluacion.validarEvaluacion(e);
            gestorEvaluacion.procesarEvaluacion(e);

            e.setUsuarios(sesion.getUsuarios());
            e.setEstablecimiento(sesion.getEstablecimiento());
            e.setCiclos(sesion.getCiclos());

            List<String> evaluacionPuntajesItems = new ArrayList<>();
            for (EvaluacionPuntajes ep : e.getEvaluacionPuntajesList()) {
                evaluacionPuntajesItems.add(ep.getDescripcion());
            }

            for (Ciclo c : e.getCiclos()) {
                c.setEvaluacion(e);
                for (Seccion s : c.getSeccionList()) {
                    s.setCiclo(c);
                    for (SeccionDetalle sd : s.getSeccionDetalleList()) {
                        sd.setSeccion(s);
                        for (SeccionDetalleItems sdi : sd.getSeccionDetalleItemsList()) {
                            sdi.setSeccionDetalle(sd);
                            sdi.setEvaluacionPuntajesItems(evaluacionPuntajesItems);
                        }
                    }
                }
            }

            UtilMSG.addSuccessMsg("Auto-evaluación creada, código: " + e.getEvaluacionPK().getCodEvaluacion());
            UtilJSF.setBean("evaluacion", e, UtilJSF.SESSION_SCOPE);

            this.nuevoActivo = Boolean.FALSE;
            this.guardarActivo = this.cancelarActivo = Boolean.TRUE;

            return ("/gestor/evaluacion.xhtml?faces-redirect=true");
        } catch (Exception e) {
            if (UtilLog.causaControlada(e)) {
            } else {
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        return "";
    }

    /**
     * @return the evaluacionList
     */
    public List<Evaluacion> getEvaluacionList() {
        if (evaluacionList == null || evaluacionList.isEmpty()) {
            try {
                Sesion s = (Sesion) UtilJSF.getBean("sesion");
                GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
                evaluacionList = gestorEvaluacion.cargarEvaluacionList(s.getEstablecimiento().getCodigoEstablecimiento(), s.getConfiguracion().get(App.CONFIGURACION_MOSTRAR_EVALUACIONES).toString());
            } catch (Exception e) {
                UtilLog.generarLog(this.getClass(), e);
            }
        }
        return evaluacionList;
    }

    /**
     * @param evaluacionList the evaluacionList to set
     */
    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    public void consultar() {
    }

    public String guardar() {
        try {
            this.resumenEvaluacionList = new ArrayList<>();
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            for (Ciclo c : e.getCiclos()) {
                c.setEvaluacion(e);
                for (Seccion s : c.getSeccionList()) {
                    s.setCiclo(c);
                    for (SeccionDetalle sd : s.getSeccionDetalleList()) {
                        sd.setSeccion(s);
                        for (SeccionDetalleItems sdi : sd.getSeccionDetalleItemsList()) {
                            sdi.setSeccionDetalle(sd);
                            if (sdi.getEvaluacionPuntajes() != null && sdi.getEvaluacionPuntajes().getDescripcion() != null && !sdi.getEvaluacionPuntajes().getDescripcion().equalsIgnoreCase("")) {
                                sdi.setEvaluacionPuntajes(gestorEvaluacion.cargarEvaluacionPuntajes(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion(), sdi.getEvaluacionPuntajes().getDescripcion()));
                            }
                            resumenEvaluacionList.add((SeccionDetalleItems) sdi.clone());
                        }
                    }
                }
            }

            if (this.getAvanceEvaluacion() >= 100) {
                this.finalizarActivo = Boolean.TRUE;
            } else {
                UtilMSG.addWarningMsg("La evaluación no se puede finalizar hasta completarla.");
            }

            return ("/gestor/evaluacion-guardar.xhtml?faces-redirect=true");
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
        return "";
    }

    public String cancelar() {
        evaluacionList.clear();
        this.nuevoActivo = Boolean.TRUE;
        this.guardarActivo = Boolean.FALSE;
        UtilJSF.setBean("evaluacion", new Evaluacion(), UtilJSF.SESSION_SCOPE);
        return ("/gestor/evaluaciones.xhtml?faces-redirect=true");
    }

    public void eliminar() {
    }

    public String volverEvaluacion() {
        return ("/gestor/evaluacion.xhtml?faces-redirect=true");
    }

    /**
     * @return the guardarActivo
     */
    public boolean isGuardarActivo() {
        return guardarActivo;
    }

    /**
     * @param guardarActivo the guardarActivo to set
     */
    public void setGuardarActivo(boolean guardarActivo) {
        this.guardarActivo = guardarActivo;
    }

    /**
     * @return the nuevoActivo
     */
    public boolean isNuevoActivo() {
        return nuevoActivo;
    }

    /**
     * @param nuevoActivo the nuevoActivo to set
     */
    public void setNuevoActivo(boolean nuevoActivo) {
        this.nuevoActivo = nuevoActivo;
    }

    /**
     * @return the eliminarActivo
     */
    public boolean isEliminarActivo() {
        return eliminarActivo;
    }

    /**
     * @param eliminarActivo the eliminarActivo to set
     */
    public void setEliminarActivo(boolean eliminarActivo) {
        this.eliminarActivo = eliminarActivo;
    }

    /**
     * @return the consultarActivo
     */
    public boolean isConsultarActivo() {
        return consultarActivo;
    }

    /**
     * @param consultarActivo the consultarActivo to set
     */
    public void setConsultarActivo(boolean consultarActivo) {
        this.consultarActivo = consultarActivo;
    }

    public String getComponentesRefrescar() {
        return COMPONENTES_REFRESCAR;
    }

    /**
     * @return the cancelarActivo
     */
    public boolean isCancelarActivo() {
        return cancelarActivo;
    }

    /**
     * @param cancelarActivo the cancelarActivo to set
     */
    public void setCancelarActivo(boolean cancelarActivo) {
        this.cancelarActivo = cancelarActivo;
    }

    public Integer getAvanceEvaluacion() {
        try {
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avanceEvaluacion(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion());
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return Integer.MIN_VALUE;
    }

    public Integer getAvanceEvaluacionPlanear() {
        try {
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avanceEvaluacionCiclo(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion(), App.COD_CICLO_PLANEAR);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return Integer.MIN_VALUE;
    }

    public Integer getAvanceEvaluacionHacer() {
        try {
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avanceEvaluacionCiclo(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion(), App.COD_CICLO_HACER);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return Integer.MIN_VALUE;
    }

    public Integer getAvanceEvaluacionVerificar() {
        try {
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avanceEvaluacionCiclo(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion(), App.COD_CICLO_VERIFICAR);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return Integer.MIN_VALUE;
    }

    public Integer getAvanceEvaluacionActuar() {
        try {
            Evaluacion e = (Evaluacion) UtilJSF.getBean("evaluacion");
            GestorEvaluacion gestorEvaluacion = new GestorEvaluacion();
            return gestorEvaluacion.avanceEvaluacionCiclo(e.getEvaluacionPK().getCodigoEstablecimiento(), e.getEvaluacionPK().getCodEvaluacion(), App.COD_CICLO_ACTUAR);
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
        return Integer.MIN_VALUE;
    }

    /**
     * @return the resumenEvaluacionList
     */
    public List<SeccionDetalleItems> getResumenEvaluacionList() {
        return resumenEvaluacionList;
    }

    /**
     * @param resumenEvaluacionList the resumenEvaluacionList to set
     */
    public void setResumenEvaluacionList(List<SeccionDetalleItems> resumenEvaluacionList) {
        this.resumenEvaluacionList = resumenEvaluacionList;
    }

    /**
     * @return the finalizarActivo
     */
    public boolean isFinalizarActivo() {
        return finalizarActivo;
    }

    /**
     * @param finalizarActivo the finalizarActivo to set
     */
    public void setFinalizarActivo(boolean finalizarActivo) {
        this.finalizarActivo = finalizarActivo;
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
     * @return the seccionDetalleItemsOpcionesLista
     */
    public Lista getSeccionDetalleItemsOpcionesLista() {
        return seccionDetalleItemsOpcionesLista;
    }

    /**
     * @param seccionDetalleItemsOpcionesLista the
     * seccionDetalleItemsOpcionesLista to set
     */
    public void setSeccionDetalleItemsOpcionesLista(Lista seccionDetalleItemsOpcionesLista) {
        this.seccionDetalleItemsOpcionesLista = seccionDetalleItemsOpcionesLista;
    }

    /**
     * @return the seccionDetalleItemsAyudas
     */
    public List<SeccionDetalleItemsAyuda> getSeccionDetalleItemsAyudas() {
        return seccionDetalleItemsAyudas;
    }

    /**
     * @param seccionDetalleItemsAyudas the seccionDetalleItemsAyudas to set
     */
    public void setSeccionDetalleItemsAyudas(List<SeccionDetalleItemsAyuda> seccionDetalleItemsAyudas) {
        this.seccionDetalleItemsAyudas = seccionDetalleItemsAyudas;
    }

}
