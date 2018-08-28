/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author juliano
 */
@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")})
@ManagedBean
@SessionScoped
public class Usuarios implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuariosPK usuariosPK;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "clave")
    private String clave;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "correo")
    private String correo;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "fecha_retiro")
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private List<RelUsuariosEstablecimiento> relUsuariosEstablecimientoList;
    private Roles roles;
    private String claveMd5;
    private List<Establecimiento> listaEstablecimientos;
    private Establecimiento establecimiento;

    public Usuarios() {
        this.usuariosPK = new UsuariosPK();
    }

    public Usuarios(UsuariosPK usuariosPK) {
        this.usuariosPK = usuariosPK;
    }

    public Usuarios(String documentoUsuario) {
        this.usuariosPK = new UsuariosPK(documentoUsuario);
    }

    public Usuarios(UsuariosPK usuariosPK, String nombre, String apellido) {
        this.usuariosPK = usuariosPK;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Usuarios(UsuariosPK usuariosPK, String nombre, String apellido, String usuario) {
        this.usuariosPK = usuariosPK;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
    }

    public UsuariosPK getUsuariosPK() {
        return usuariosPK;
    }

    public void setUsuariosPK(UsuariosPK usuariosPK) {
        this.usuariosPK = usuariosPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public List<RelUsuariosEstablecimiento> getRelUsuariosEstablecimientoList() {
        return relUsuariosEstablecimientoList;
    }

    public void setRelUsuariosEstablecimientoList(List<RelUsuariosEstablecimiento> relUsuariosEstablecimientoList) {
        this.relUsuariosEstablecimientoList = relUsuariosEstablecimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariosPK != null ? usuariosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuariosPK == null && other.usuariosPK != null) || (this.usuariosPK != null && !this.usuariosPK.equals(other.usuariosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.Usuarios[ usuariosPK=" + usuariosPK + " ]";
    }

    /**
     * @return the roles
     */
    public Roles getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    /**
     * @return the claveMd5
     */
    public String getClaveMd5() {
        return claveMd5;
    }

    /**
     * @param claveMd5 the claveMd5 to set
     */
    public void setClaveMd5(String claveMd5) {
        this.claveMd5 = claveMd5;
    }

    /**
     * @return the listaEstablecimientos
     */
    public List<Establecimiento> getListaEstablecimientos() {
        return listaEstablecimientos;
    }

    /**
     * @param listaEstablecimientos the listaEstablecimientos to set
     */
    public void setListaEstablecimientos(List<Establecimiento> listaEstablecimientos) {
        this.listaEstablecimientos = listaEstablecimientos;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
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

}
