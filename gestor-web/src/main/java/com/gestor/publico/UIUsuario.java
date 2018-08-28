/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import com.gestor.controller.GestorGeneral;
import com.gestor.entity.App;
import com.gestor.entity.Dialogo;
import com.gestor.entity.UtilJSF;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilMSG;
import com.gestor.gestor.Evaluacion;
import com.gestor.gestor.controlador.GestorPuntajes;
import com.gestor.modelo.Sesion;
import com.gestor.publico.controlador.GestorEstablecimiento;
import com.gestor.publico.controlador.GestorConfiguracion;
import com.gestor.publico.controlador.GestorLista;
import com.gestor.publico.controlador.GestorUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Julian
 */
@ManagedBean(name = "uiUsuario")
@SessionScoped

public class UIUsuario {

    public static final String DIALOGO_CREAR = "dialogoNuevoUsuario";
    public static final String COMPONENTES_REFRESCAR = "formNuevoUsuario";
    private List<Roles> itemsRoles;
    private List<Establecimiento> itemsEstablecimiento;
    private List<String> establecimientosDisponibles = new ArrayList<>();
    private List<String> establecimientosAsignados = new ArrayList<>();
    private DualListModel<String> itemsDualEstablecimientos = new DualListModel<>(establecimientosDisponibles, establecimientosAsignados);
    private String usuarioBuscar;
    private String usuario;
    private String clave;
    private Establecimiento establecimiento;

    private boolean guardarActivo = false;
    private boolean nuevoActivo = true;
    private boolean eliminarActivo = false;
    private boolean cancelarActivo = false;
    private boolean consultarActivo = false;

    public void cancelar() {
    }

    public UIUsuario() {
        this.itemsEstablecimiento = new ArrayList<>();
        this.itemsRoles = new ArrayList<>();
        this.cargarRoles();
        this.establecimiento = new Establecimiento();

    }

    public String getDialogoCrearNuevo() {
        return DIALOGO_CREAR;
    }

    public String getComponentesRefrescar() {
        return COMPONENTES_REFRESCAR;
    }

    public String registrarEstablecimiento() {
        return ("/registro.xhtml?faces-redirect=true");
    }

    /**
     * Cargar los datos del usuario.
     *
     * @param establecimiento
     * @param usuario
     *
     */
    private Usuarios cargarDatosUsuario(Establecimiento establecimiento, Usuarios usuario) throws Exception {
        GestorUsuario gestorUsuario = new GestorUsuario();
        return gestorUsuario.cargarDatosUsuario(establecimiento, usuario, App.USUARIOS_FILTRO_USUARIO);
    }

    /**
     * Cargar los datos del usuario.
     *
     * @param establecimiento
     * @param usuario
     *
     */
    private Usuarios cargarDatosUsuario(Usuarios usuario) throws Exception {
        GestorUsuario gestorUsuario = new GestorUsuario();
        return gestorUsuario.cargarDatosUsuario(usuario, App.USUARIOS_FILTRO_USUARIO);
    }

    public void cargarEstablecimientosUsuario(String usuario) {
        try {
            GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();
            GestorUsuario gestorUsuario = new GestorUsuario();
            Usuarios u = (Usuarios) new Usuarios().clone();
            u.setUsuario(usuario);
            u = gestorUsuario.cargarDatosUsuario(u, App.USUARIOS_FILTRO_USUARIO);
            this.itemsEstablecimiento = (List<Establecimiento>) gestorEstablecimiento.cargarListaEstablecimientosUsuario(u.getUsuariosPK().getDocumentoUsuario());
        } catch (Exception ex) {
            UtilMSG.addErrorMsg(ex.getMessage());
            UtilLog.generarLog(this.getClass(), ex);
        }
    }

    public String validarUsuario() throws Exception {
        Sesion sesion = new Sesion();
        Usuarios usuarios = new Usuarios();

        Establecimiento e;
        boolean usuarioValido;
        try {
            GestorUsuario gestorUsuario = new GestorUsuario();
            GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();
            GestorConfiguracion gestorConfiguracion = new GestorConfiguracion();
            GestorGeneral gestorGeneral = new GestorGeneral();
            GestorPuntajes gestorPuntajes = new GestorPuntajes();
            GestorLista gestorLista = new GestorLista();

            usuarios.setUsuario(usuario);
            usuarios.setClave(clave);
            usuarios.setEstablecimiento(establecimiento);

            usuarioValido = gestorUsuario.validarUsuario(usuarios.getUsuario(), usuarios.getClave());
            if (usuarioValido) {

                usuarios = gestorUsuario.cargarDatosUsuario(usuarios, App.USUARIOS_FILTRO_USUARIO);
                usuarios.setListaEstablecimientos((List<Establecimiento>) gestorEstablecimiento.cargarListaEstablecimientosUsuario(usuarios.getUsuariosPK().getDocumentoUsuario()));

                for (Establecimiento i : usuarios.getListaEstablecimientos()) {
                    usuarios.setEstablecimiento(i);
                    break;
                }
                e = this.cargarEstablecimiento(usuarios.getEstablecimiento().getCodigoEstablecimiento());

                sesion.setUsuarios((Usuarios) usuarios.clone());
                sesion.setEstablecimiento(e);
                sesion.setConfiguracion(gestorConfiguracion.cargarConfiguracion());
                sesion.setCiclos(gestorGeneral.cargarCiclosEvaluacion());
                sesion.setPuntajesList(gestorPuntajes.cargarPuntajes(e.getCodigoEstablecimiento()));
                sesion.setEstablecimientoList(usuarios.getListaEstablecimientos());
                sesion.setListaVigenciaArchivos(gestorLista.cargarLista(App.LISTA_VIGENCIA_ARCHIVOS));
                
                usuarios = new Usuarios();
                UtilJSF.setBean("usuarios", usuarios, UtilJSF.SESSION_SCOPE);
                UtilJSF.setBean("sesion", sesion, UtilJSF.SESSION_SCOPE);

                UtilJSF.setBean("dialogo", new Dialogo(), UtilJSF.SESSION_SCOPE);
                UtilJSF.setBean("evaluacion", new Evaluacion(), UtilJSF.SESSION_SCOPE);

                return ("/inicio/principal.xhtml?faces-redirect=true");
            } else {
                UtilMSG.addWarningMsg("Usuario o clave invalida.");
            }
        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg(ex.getMessage());
            } else {
                UtilLog.generarLog(this.getClass(), ex);
                UtilMSG.addErrorMsg("errorPersistencia");
            }
        }
        return "";
    }

    public List<String> autoCompletaUsuario(String query) {
        List<String> resultado = new ArrayList<>();
        try {
            Establecimiento establecimiento = (Establecimiento) ((Sesion) UtilJSF.getBean("sesion")).getEstablecimiento();
            GestorUsuario gestorUsuario = new GestorUsuario();
            resultado.addAll(gestorUsuario.autoCompletaUsuario(establecimiento, query));

        } catch (Exception ex) {
            UtilMSG.addErrorMsg("Error al consultar el usuario");
            UtilLog.generarLog(this.getClass(), ex);
        }
        return resultado;
    }

    public void cargarUsuario() {

        try {
            Usuarios usuario = (Usuarios) UtilJSF.getBean("usuarios");
            Establecimiento establecimiento = (Establecimiento) ((Sesion) UtilJSF.getBean("sesion")).getEstablecimiento();
            if (usuarioBuscar == null || !usuarioBuscar.contains("-")) {
                UtilMSG.addWarningMsg("Usuario no encontrado.");
            }
            usuario.setUsuario(usuarioBuscar.split("-")[0].trim());
            usuario = this.cargarDatosUsuario(establecimiento, usuario);
            for (Roles r : itemsRoles) {
                if (usuario.getRoles() != null && usuario.getRoles().getCodigoRol() == r.getCodigoRol()) {
                    usuario.setRoles(r);
                }
            }
            GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();

            this.establecimientosDisponibles = this.transformarLista(gestorEstablecimiento.cargarListaEstablecimientos());
            this.establecimientosAsignados = this.transformarLista(gestorEstablecimiento.cargarListaEstablecimientosUsuario(usuario.getUsuariosPK().getDocumentoUsuario()));
            this.itemsDualEstablecimientos = new DualListModel<>((List<String>) this.removerElementosAsignados(establecimientosDisponibles, establecimientosAsignados), establecimientosAsignados);

            UtilJSF.setBean("usuarios", usuario, UtilJSF.SESSION_SCOPE);
            this.guardarActivo = true;
        } catch (Exception ex) {
            UtilMSG.addErrorMsg("Error al cargar el usuario");
            UtilLog.generarLog(this.getClass(), ex);
        }

    }

    public List<String> transformarLista(final List<?> objects) {
        List<String> lista = new ArrayList<>();
        for (Object ob : objects) {
            Establecimiento e = (Establecimiento) ob;
            lista.add(e.getNombre());
        }
        return lista;
    }

    public List<?> removerElementosAsignados(List<?> disponibles, List<?> asignados) {
        CopyOnWriteArrayList origen = new CopyOnWriteArrayList(disponibles);
        CopyOnWriteArrayList destino = new CopyOnWriteArrayList(asignados);
        for (Object obj1 : origen) {
            for (Object obj2 : destino) {
                if (obj1.equals(obj2)) {
                    origen.remove(obj2);
                }
            }
        }
        return new ArrayList(origen);
    }

    public String cerrarSesion() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) externalContext.getSession(true)).invalidate();
        return ("/ingreso.xhtml?faces-redirect=true");
    }

    private Establecimiento cargarEstablecimiento(int codigo) throws Exception {
        Establecimiento establecimiento;
        GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();
        establecimiento = gestorEstablecimiento.cargarEstablecimiento(codigo);
        return establecimiento;
    }

    private List<Establecimiento> cargarEstablecimientosAsignados() throws Exception {
        GestorEstablecimiento gestorEstablecimiento = new GestorEstablecimiento();
        List<Establecimiento> listaEstablecimientosAsignados = new ArrayList<>();
        List<Establecimiento> listaEstablecimientos = (List<Establecimiento>) gestorEstablecimiento.cargarListaEstablecimientos();
        List<String> asignados = this.itemsDualEstablecimientos.getTarget();

        for (Establecimiento obe : listaEstablecimientos) {
            for (String e : asignados) {
                if (obe.getNombre().equalsIgnoreCase(e)) {
                    listaEstablecimientosAsignados.add(obe);
                }
            }
        }
        return listaEstablecimientosAsignados;
    }

    public String volverUsuario() {
        return ("/usuario/usuarios.xhtml?faces-redirect=false");
    }

    public String nuevo() {
        UtilJSF.setBean("usuarios", new Usuarios(), UtilJSF.SESSION_SCOPE);
        return ("/usuario/usuarios-nuevo.xhtml?faces-redirect=false");
    }

    public void nuevoCrear() {
        Usuarios usuario = (Usuarios) UtilJSF.getBean("usuarios");
        Establecimiento establecimiento = (Establecimiento) ((Sesion) UtilJSF.getBean("sesion")).getEstablecimiento();
        try {
            GestorUsuario gestorUsuario = new GestorUsuario();
            if (gestorUsuario.existeDocumentoUsuario(usuario)) {
                throw new Exception("El documento de identificación ya existe por favor valide.", UtilLog.TW_VALIDACION);
            }
            if (gestorUsuario.existeUsuario(usuario)) {
                throw new Exception("El usuario ya existe por favor valide.", UtilLog.TW_VALIDACION);
            }
            usuario = gestorUsuario.validarUsuarioNuevo(usuario);
            gestorUsuario.almacenarUsuario(establecimiento, usuario);
            usuario = new Usuarios();
            this.usuarioBuscar = null;
            UtilJSF.setBean("usuarios", usuario, UtilJSF.SESSION_SCOPE);
            UtilMSG.addSuccessMsg("Usuario creado");
            guardarActivo = false;
        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg(ex.getMessage());
            } else {
                UtilMSG.addErrorMsg("Ocurrio una excepción al crear el usuario");
                UtilLog.generarLog(this.getClass(), ex);
            }
        }
    }

    public void guardar() {
        Usuarios usuario = (Usuarios) UtilJSF.getBean("usuarios");
        Establecimiento establecimiento = (Establecimiento) ((Sesion) UtilJSF.getBean("sesion")).getEstablecimiento();
        try {
            if (usuario.getRoles() == null || usuario.getRoles().getCodigoRol() == null
                    || usuario.getRoles().getCodigoRol() == 0) {
                UtilMSG.addWarningMsg("Por favor seleccione el rol del usuario.");
                return;
            }

            GestorUsuario gestorUsuario = new GestorUsuario();
            usuario.setListaEstablecimientos(this.cargarEstablecimientosAsignados());

            if (usuario.getListaEstablecimientos() == null || usuario.getListaEstablecimientos().isEmpty()) {
                UtilMSG.addWarningMsg("Es necesario asignar por lo menos una empresa, para almacenar el rol del usuario.");
            }

            gestorUsuario.almacenarUsuario(establecimiento, usuario);
            gestorUsuario.almacenarEstablecimientosUsuario(usuario);
            gestorUsuario.almacenarRolUsuario(establecimiento, usuario);

            usuario = new Usuarios();
            this.usuarioBuscar = null;
            UtilJSF.setBean("usuarios", usuario, UtilJSF.SESSION_SCOPE);
            UtilMSG.addSuccessMsg("Usuario modificado correctamente");
            guardarActivo = false;
        } catch (Exception ex) {
            if (UtilLog.causaControlada(ex)) {
                UtilMSG.addWarningMsg(ex.getMessage());
            } else {
                UtilMSG.addErrorMsg("Ocurrio una excepción al modificar usuario");
                UtilLog.generarLog(this.getClass(), ex);
            }
        }
    }

    public void eliminar() {
        UtilMSG.addWarningMsg("No se permite eliminar usuario, por favor inactivelo.");
    }

    /**
     * @return the itemsRoles
     */
    public List<Roles> getItemsRoles() {
        return itemsRoles;
    }

    /**
     * @param itemsRoles the itemsRoles to set
     */
    public void setItemsRoles(List<Roles> itemsRoles) {
        this.itemsRoles = itemsRoles;
    }

    private void cargarRoles() {
        try {
            GestorUsuario gestorUsuario = new GestorUsuario();
            this.itemsRoles = gestorUsuario.cargarRoles();
        } catch (Exception ex) {

        }
    }

    /**
     * @return the itemsDualEstablecimientos
     */
    public DualListModel<String> getItemsDualEstablecimientos() {
        return itemsDualEstablecimientos;
    }

    /**
     * @param itemsDualEstablecimientos the itemsDualEstablecimientos to set
     */
    public void setItemsDualEstablecimientos(DualListModel<String> itemsDualEstablecimientos) {
        this.itemsDualEstablecimientos = itemsDualEstablecimientos;
    }

    /**
     * @return the usuarioBuscar
     */
    public String getUsuarioBuscar() {
        return usuarioBuscar;
    }

    /**
     * @param usuarioBuscar the usuarioBuscar to set
     */
    public void setUsuarioBuscar(String usuarioBuscar) {
        this.usuarioBuscar = usuarioBuscar;
    }

    /**
     * @return the itemsEstablecimiento
     */
    public List<Establecimiento> getItemsEstablecimiento() {
        return itemsEstablecimiento;
    }

    /**
     * @param itemsEstablecimiento the itemsEstablecimiento to set
     */
    public void setItemsEstablecimiento(List<Establecimiento> itemsEstablecimiento) {
        this.itemsEstablecimiento = itemsEstablecimiento;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
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

}
