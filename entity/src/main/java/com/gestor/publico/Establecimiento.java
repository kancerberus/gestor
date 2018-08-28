/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "establecimiento")
@NamedQueries({
    @NamedQuery(name = "Establecimiento.findAll", query = "SELECT e FROM Establecimiento e")})
public class Establecimiento implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_establecimiento")
    private Integer codigoEstablecimiento;
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "nit")
    private String nit;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "fecha_cierre_diario")
    @Temporal(TemporalType.DATE)
    private Date fechaCierreDiario;
    @Basic(optional = false)
    @Column(name = "tipo_establecimiento")
    private String tipoEstablecimiento;
    @JoinColumn(name = "codigo_municipio", referencedColumnName = "codigo_municipio")
    @ManyToOne(optional = false)
    private Municipios municipios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "establecimiento")
    private List<RelUsuariosEstablecimiento> relUsuariosEstablecimientoList;

    public Establecimiento() {
    }

    public Establecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public Establecimiento(int codigoEstablecimiento, String nombre) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.nombre = nombre;
    }

    public Establecimiento(Integer codigoEstablecimiento, String nombre, String nit, String direccion, String telefono, String correo) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    

    public Establecimiento(int codigoEstablecimiento, String nit, Date fechaCierreDiario, String tipoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.nit = nit;
        this.fechaCierreDiario = fechaCierreDiario;
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public Integer getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaCierreDiario() {
        return fechaCierreDiario;
    }

    public void setFechaCierreDiario(Date fechaCierreDiario) {
        this.fechaCierreDiario = fechaCierreDiario;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
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
        hash += (codigoEstablecimiento != null ? codigoEstablecimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Establecimiento)) {
            return false;
        }
        Establecimiento other = (Establecimiento) object;
        if ((this.codigoEstablecimiento == null && other.codigoEstablecimiento != null) || (this.codigoEstablecimiento != null && !this.codigoEstablecimiento.equals(other.codigoEstablecimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestor.publico.Establecimiento[ codigoEstablecimiento=" + codigoEstablecimiento + " ]";
    }

    /**
     * @return the municipios
     */
    public Municipios getMunicipios() {
        return municipios;
    }

    /**
     * @param municipios the municipios to set
     */
    public void setMunicipios(Municipios municipios) {
        this.municipios = municipios;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
