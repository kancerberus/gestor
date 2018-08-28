/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.controlador;

import com.gestor.controller.Gestor;
import com.gestor.entity.UtilCorreo;
import com.gestor.entity.UtilLog;
import com.gestor.entity.UtilTexto;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Roles;
import com.gestor.publico.Usuarios;
import com.gestor.publico.dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Julian
 */
public class GestorUsuario extends Gestor {

    public GestorUsuario() throws Exception {
        super();
    }

    public boolean validarUsuario(String usuario, String clave) throws Exception {
        try {
            this.abrirConexion();
            return new UsuarioDAO(this.conexion).validarUsuario(usuario, clave);
        } finally {
            this.cerrarConexion();
        }
    }

    public ArrayList<Usuarios> cargarListaUsuarios() throws Exception {
        try {
            this.abrirConexion();
            return new UsuarioDAO(this.conexion).cargarListaUsuarios();
        } finally {
            this.cerrarConexion();
        }
    }

    public List<Usuarios> cargarListaUsuariosEstablecimiento(Establecimiento establecimiento) throws Exception {
        try {
            this.abrirConexion();
            return new UsuarioDAO(this.conexion).cargarListaUsuariosEstablecimiento(establecimiento);
        } finally {
            this.cerrarConexion();
        }
    }

    public Usuarios cargarDatosUsuario(Establecimiento establecimiento, Usuarios usuario, final String filtro) throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            usuario = usuarioDAO.cargarDatosUsuario(usuario, filtro);
            usuario = usuarioDAO.cargarRolUsuario(establecimiento, usuario);
            return usuario;
        } finally {
            this.cerrarConexion();
        }
    }

    public Usuarios cargarDatosUsuario(Usuarios usuario, final String filtro) throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            usuario = usuarioDAO.cargarDatosUsuario(usuario, filtro);
            return usuario;
        } finally {
            this.cerrarConexion();
        }
    }

    public Collection<? extends String> autoCompletaUsuario(Establecimiento establecimiento, String query) throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.autoCompletaUsuario(establecimiento, query);
        } finally {
            this.cerrarConexion();
        }
    }

    public void almacenarUsuario(Establecimiento establecimiento, Usuarios usuario) throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            int registrosActualizados = usuarioDAO.actualizarUsuario(establecimiento, usuario);
            if (registrosActualizados == 0) {
                usuario.setActivo(true);
                usuarioDAO.insertarUsuario(establecimiento, usuario);
            }
        } finally {
            this.cerrarConexion();
        }
    }

    public List<Roles> cargarRoles() throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.cargarRoles();
        } finally {
            this.cerrarConexion();
        }
    }

    public void almacenarEstablecimientosUsuario(Usuarios usuario) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            usuarioDAO.eliminarRolesUsuario(usuario);
            usuarioDAO.eliminarEstablecimientosUsuario(usuario);
            for (Establecimiento e : usuario.getListaEstablecimientos()) {
                usuarioDAO.agregarEstablecimientosUsuario(e, usuario);
            }
            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public void almacenarRolUsuario(Establecimiento establecimiento, Usuarios usuario) throws Exception {
        try {
            this.abrirConexion();
            this.inicioTransaccion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);

            for (Establecimiento e : usuario.getListaEstablecimientos()) {
                usuarioDAO.eliminarRolUsuario(e, usuario);
                usuarioDAO.agregarRolUsuario(e, usuario);
            }

            this.finTransaccion();
        } finally {
            this.cerrarConexion();
        }
    }

    public boolean existeDocumentoUsuario(Usuarios usuario) throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.existeDocumentoUsuario(usuario);
        } finally {
            this.cerrarConexion();
        }
    }
    
    public boolean existeUsuario(Usuarios usuario) throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.existeUsuario(usuario);
        } finally {
            this.cerrarConexion();
        }
    }

    public boolean usuarioAutorizadoExamen(String email, String codExamen) throws Exception {
        try {
            this.abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.usuarioAutorizadoExamen(email, codExamen);
        } finally {
            this.cerrarConexion();
        }
    }

    public Usuarios validarUsuarioNuevo(Usuarios usuario) throws Exception {
        if (usuario == null || usuario.getUsuariosPK() == null || usuario.getUsuariosPK().getDocumentoUsuario() == null
                || usuario.getUsuariosPK().getDocumentoUsuario().equalsIgnoreCase("")
                || usuario.getUsuariosPK().getDocumentoUsuario().equalsIgnoreCase("0")) {
            throw new Exception("Ingresa el documento del usuario a crear", UtilLog.TW_VALIDACION);
        }
        if (usuario.getNombre() == null || usuario.getNombre().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el nombre del usuario", UtilLog.TW_VALIDACION);
        }
        if (usuario.getApellido() == null || usuario.getApellido().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el apellido del usuario", UtilLog.TW_VALIDACION);
        }
        if (usuario.getCorreo() == null
                || usuario.getCorreo().equalsIgnoreCase("")
                || !UtilCorreo.validarCorreo(usuario.getCorreo())) {
            throw new Exception("Ingresa un correo valido", UtilLog.TW_VALIDACION);
        }
        if (usuario.getUsuario() == null || usuario.getUsuario().equalsIgnoreCase("")) {
            throw new Exception("Ingresa el usuario a crear", UtilLog.TW_VALIDACION);
        }

        if (usuario.getClave() == null || usuario.getClave().equalsIgnoreCase("")) {
            throw new Exception("Ingresa una clave valida", UtilLog.TW_VALIDACION);
        }

        usuario.setNombre(usuario.getNombre().trim().toUpperCase());
        usuario.setApellido(usuario.getApellido().trim().toUpperCase());
        usuario.setUsuario(usuario.getUsuario().trim().toUpperCase());
        usuario.setActivo(Boolean.TRUE);
        usuario.setFechaIngreso(new Date());
        usuario.setClave(usuario.getClave().trim());
        usuario.setCorreo(usuario.getCorreo().trim().toLowerCase());
        return usuario;
    }
}
