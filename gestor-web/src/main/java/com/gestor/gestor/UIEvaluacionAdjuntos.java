/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.gestor;

import com.gestor.controller.GestorGeneral;
import com.gestor.controller.Propiedades;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilArchivo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.controlador.GestorEvaluacionAdjuntos;
import com.gestor.modelo.Sesion;
import com.gestor.publico.EvaluacionAdjuntos;
import com.gestor.publico.EvaluacionAdjuntosPK;
import com.gestor.publico.Lista;
import com.gestor.publico.ListaDetalle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author juliano
 */
@ManagedBean(name = "uiEvaluacionAdjuntos")
@SessionScoped
public class UIEvaluacionAdjuntos {

    private UploadedFile file;
    private SeccionDetalleItems sdiSeleccionado;
    private List<EvaluacionAdjuntos> evaluacionAdjuntosList = new ArrayList<>();
    private String archivoEliminar;
    private StreamedContent fileDownload;
    private Map<Integer, String> itemsVigenciaArchivos = new HashMap<Integer, String>();

    public UIEvaluacionAdjuntos() {
        Lista listaVigenciaArchivos = ((Sesion) UtilJSF.getBean("sesion")).getListaVigenciaArchivos();
        if (listaVigenciaArchivos != null) {
            for (ListaDetalle ld : listaVigenciaArchivos.getListaDetalleList()) {
                itemsVigenciaArchivos.put(ld.getPropiedadInt("valor"), ld.getPropiedad("nombre"));
            }
        } else {
            UtilMSG.addWarningMsg("Lista Vigencia Adjuntos", "No se pudo cargar la lista de vigencia de adjuntos, contacte a soporte para asitirle.");
        }
    }

    public void limpiar() {
        UtilJSF.setBean("evaluacionAdjuntos", new EvaluacionAdjuntos(), UtilJSF.SESSION_SCOPE);
        archivoEliminar = null;
        file = null;
    }

    public void eliminarAdjunto() {
        try {
            EvaluacionAdjuntos ea = (EvaluacionAdjuntos) UtilJSF.getBean("varEvaluacionAdjunto");
            GestorEvaluacionAdjuntos gestorEvaluacionAdjuntos = new GestorEvaluacionAdjuntos();
            evaluacionAdjuntosList.remove(ea);
            Properties p = Propiedades.getInstancia().getPropiedades();
            String ruta = p.getProperty("rutaAdjunto");
            String rutaDestino = ruta + File.separator + App.ADJUNTO_PREFIJO + sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion();
            String rutaEliminado = p.getProperty("rutaEliminado") + File.separator + App.ADJUNTO_PREFIJO + sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion();
            File carpetaEliminado = new File(rutaEliminado);
            if (!carpetaEliminado.exists()) {
                carpetaEliminado.mkdirs();
            }
            String origen = rutaDestino + File.separator + ea.getArchivo();
            UtilArchivo.copiar(origen, rutaEliminado + File.separator + ea.getArchivo());
            ea.setEstado(App.EVALUACION_ESTADO_ELIMINADO);
            gestorEvaluacionAdjuntos.actualizarEstadoEvaluacionAdjuntos(ea);
            UtilArchivo.borrar(origen);
            UtilMSG.addSuccessMsg("Adjunto Eliminado", "Adjunto borrado satisfactoriamente.");

        } catch (IOException ex) {
            UtilMSG.addSupportMsg();
            UtilLog.generarLog(this.getClass(), ex);
        } catch (Exception ex) {
            UtilMSG.addSupportMsg();
            UtilLog.generarLog(this.getClass(), ex);
        }

    }

    public void modificarAdjunto() {
        EvaluacionAdjuntos ea = (EvaluacionAdjuntos) UtilJSF.getBean("varEvaluacionAdjunto");
        evaluacionAdjuntosList.remove(ea);
        archivoEliminar = ea.getArchivo();
        this.file = null;
        UtilJSF.setBean("evaluacionAdjuntos", ea, UtilJSF.SESSION_SCOPE);
    }

    public void adjuntarSoporte() {
        try {
            this.sdiSeleccionado = (SeccionDetalleItems) UtilJSF.getBean("varSeccionDetalleItems");
            evaluacionAdjuntosList = new ArrayList<>();
            GestorEvaluacionAdjuntos gestorEvaluacionAdjuntos = new GestorEvaluacionAdjuntos();
            evaluacionAdjuntosList.addAll(gestorEvaluacionAdjuntos.cargarEvaluacionAdjuntos(new EvaluacionAdjuntosPK(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            )));
            Dialogo dialogo = new Dialogo("dialogos/adjuntar-soporte.xhtml", "Adjuntar Soportes", "clip", Dialogo.WIDTH_AUTO);
            UtilJSF.setBean("dialogo", dialogo, UtilJSF.SESSION_SCOPE);
            UtilJSF.execute("PF('dialog').show();");
        } catch (Exception ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public void cargarAdjunto(FileUploadEvent event) {
        try {
            String ruta = Propiedades.getInstancia().getPropiedades().getProperty("rutaTemporal");
            File carpeta = new File(ruta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            UtilArchivo.guardarStream(ruta + File.separator + event.getFile().getFileName(), event.getFile().getInputstream());
            this.file = event.getFile();
        } catch (IOException ex) {
            UtilLog.generarLog(this.getClass(), ex);
        }

    }

    public void guardarAdjunto() {
        try {
            Sesion sesion = (Sesion) UtilJSF.getBean("sesion");
            EvaluacionAdjuntos evaluacionAdjuntos = (EvaluacionAdjuntos) UtilJSF.getBean("evaluacionAdjuntos");
            GestorGeneral gestorGeneral = new GestorGeneral();

            Long codAdjunto;
            if (evaluacionAdjuntos == null || evaluacionAdjuntos.getEvaluacionAdjuntosPK() == null
                    || evaluacionAdjuntos.getEvaluacionAdjuntosPK().getCodAdjunto() == null || evaluacionAdjuntos.getEvaluacionAdjuntosPK().getCodAdjunto() == 0) {
                codAdjunto = gestorGeneral.nextval(GestorGeneral.GESTOR_EVALUACION_COD_EVALUACION_SEQ);
            } else {
                codAdjunto = evaluacionAdjuntos.getEvaluacionAdjuntosPK().getCodAdjunto();
            }
            evaluacionAdjuntos.setEvaluacionAdjuntosPK(new EvaluacionAdjuntosPK(sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEstablecimiento().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getCodCiclo(), sdiSeleccionado.getSeccionDetalle().getSeccion().getSeccionPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccionDetallePK().getCodDetalle(), sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem(), codAdjunto)
            );
            Boolean procesarAdjunto = Boolean.TRUE;
            if (archivoEliminar != null && !archivoEliminar.equalsIgnoreCase("") && (file == null || file.getFileName() == null || file.getFileName().equalsIgnoreCase(""))) {
                UtilMSG.addErrorMsg("Cargar Adjunto", "Selecciona el archivo a guardar.");
                return;
            }

            if (archivoEliminar != null && !archivoEliminar.equalsIgnoreCase("")) {
                procesarAdjunto = Boolean.FALSE;
            }
            String fileName = codAdjunto + "-" + file.getFileName();
            evaluacionAdjuntos.setArchivo(fileName);
            evaluacionAdjuntos.setExtension(FilenameUtils.getExtension(file.getFileName()));
            evaluacionAdjuntos.setDocumentoUsuario(sesion.getUsuarios().getUsuariosPK().getDocumentoUsuario());

            GestorEvaluacionAdjuntos gestorEvaluacionAdjuntos = new GestorEvaluacionAdjuntos();
            evaluacionAdjuntos.setEstado(App.EVALUACION_ADJUNTOS_ESTADO_ACTIVO);

            evaluacionAdjuntos.setFechaInicioVigencia(gestorGeneral.now());
            evaluacionAdjuntos = gestorEvaluacionAdjuntos.validarEvaluacionAdjuntos(evaluacionAdjuntos);
            evaluacionAdjuntos = gestorEvaluacionAdjuntos.calcularVigenciaAdjunto(evaluacionAdjuntos);

            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaTemporal = p.getProperty("rutaTemporal") + File.separator + file.getFileName();
            if (procesarAdjunto) {
                String ruta = p.getProperty("rutaAdjunto");
                String rutaDestino = ruta + File.separator + App.ADJUNTO_PREFIJO + sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion();

                File carpeta = new File(rutaDestino);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }

                if (archivoEliminar != null && !archivoEliminar.equals("")) {
                    String rutaEliminado = p.getProperty("rutaEliminado") + File.separator + App.ADJUNTO_PREFIJO + sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion();
                    File carpetaEliminado = new File(rutaEliminado);
                    if (!carpetaEliminado.exists()) {
                        carpetaEliminado.mkdirs();
                    }
                    String origen = rutaDestino + File.separator + archivoEliminar;
                    UtilArchivo.copiar(origen, rutaEliminado + File.separator + archivoEliminar);
                    UtilArchivo.borrar(origen);
                }

                rutaDestino += File.separator + fileName;
                UtilArchivo.copiar(rutaTemporal, rutaDestino);
            }

            gestorEvaluacionAdjuntos.procesarEvaluacionAdjuntos(evaluacionAdjuntos);
            if (procesarAdjunto) {
                UtilArchivo.borrar(rutaTemporal);
            }

            if (procesarAdjunto) {
                UtilMSG.addSuccessMsg("Soporte Almacenado", "Adjunto almacenado correctamente.");
            } else {
                UtilMSG.addSuccessMsg("Soporte Actualizo", "Registro actualizado correctamente, sin modificar archivo.");
            }

            UtilJSF.setBean("evaluacionAdjuntos", new EvaluacionAdjuntos(), UtilJSF.SESSION_SCOPE);
            this.file = null;
            this.archivoEliminar = null;
            evaluacionAdjuntosList = new ArrayList<>();
            evaluacionAdjuntosList.addAll(gestorEvaluacionAdjuntos.cargarEvaluacionAdjuntos(new EvaluacionAdjuntosPK(
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion(),
                    sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodigoEstablecimiento(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodCiclo(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodSeccion(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodDetalle(),
                    sdiSeleccionado.getSeccionDetalleItemsPK().getCodItem()
            )));

        } catch (IOException ex) {
            UtilLog.generarLog(this.getClass(), ex);
            UtilMSG.addSupportMsg();
        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg(ex.getCause().getMessage(), ex.getMessage());
            } else {
                UtilLog.generarLog(this.getClass(), ex);
                UtilMSG.addSupportMsg();
            }
        }

    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
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
     * @return the evaluacionAdjuntosList
     */
    public List<EvaluacionAdjuntos> getEvaluacionAdjuntosList() {
        return evaluacionAdjuntosList;
    }

    /**
     * @param evaluacionAdjuntosList the evaluacionAdjuntosList to set
     */
    public void setEvaluacionAdjuntosList(List<EvaluacionAdjuntos> evaluacionAdjuntosList) {
        this.evaluacionAdjuntosList = evaluacionAdjuntosList;
    }

    /**
     * @return the archivoEliminar
     */
    public String getArchivoEliminar() {
        return archivoEliminar;
    }

    /**
     * @param archivoEliminar the archivoEliminar to set
     */
    public void setArchivoEliminar(String archivoEliminar) {
        this.archivoEliminar = archivoEliminar;
    }

    /**
     * @return the fileDownload
     */
    public StreamedContent getFileDownload() {
        String nombreAdjunto = "";
        try {
            EvaluacionAdjuntos ea = (EvaluacionAdjuntos) UtilJSF.getBean("varEvaluacionAdjunto");
            Properties p = Propiedades.getInstancia().getPropiedades();
            String rutaAdjunto = p.getProperty("rutaAdjunto") + File.separator + App.ADJUNTO_PREFIJO + sdiSeleccionado.getSeccionDetalle().getSeccion().getCiclo().getEvaluacion().getEvaluacionPK().getCodEvaluacion();
            nombreAdjunto = ea.getArchivo();
            InputStream stream = new FileInputStream(rutaAdjunto + File.separator + ea.getArchivo());
            fileDownload = new DefaultStreamedContent(stream, null, ea.getArchivoSimple());
        } catch (FileNotFoundException ex) {
            UtilMSG.addErrorMsg("Archivo No Existe", "El adjunto " + nombreAdjunto + ", no fue encontrado. Si el problema persiste contactenos para asistirle.");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return fileDownload;
    }

    /**
     * @return the itemsVigenciaArchivos
     */
    public Map<Integer, String> getItemsVigenciaArchivos() {
        return itemsVigenciaArchivos;
    }

    /**
     * @param itemsVigenciaArchivos the itemsVigenciaArchivos to set
     */
    public void setItemsVigenciaArchivos(Map<Integer, String> itemsVigenciaArchivos) {
        this.itemsVigenciaArchivos = itemsVigenciaArchivos;
    }

}
