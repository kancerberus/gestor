/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.modelo;

import com.gestor.controller.GestorGeneral;
import com.gestor.gestor.Ciclo;
import com.gestor.gestor.Puntajes;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Lista;
import com.gestor.publico.Responsable;
import com.gestor.publico.Usuarios;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author juliano
 */
@ManagedBean
@SessionScoped
public class Sesion {

    private Usuarios usuarios;
    private Establecimiento establecimiento;
    private boolean logueado;
    private HashMap configuracion = new HashMap();
    private HashMap<Integer, Boolean> permisos = new HashMap<>();
    private List<Ciclo> ciclos;
    private List<Puntajes> puntajesList;
    private List<Establecimiento> establecimientoList;       
    private Lista listaVigenciaArchivos;
    private List<Responsable> responsables = new ArrayList<>();
    
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
     * @return the logueado
     */
    public boolean isLogueado() {
        return logueado;
    }

    /**
     * @param logueado the logueado to set
     */
    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }

    /**
     * @return the permisos
     */
    public HashMap<Integer, Boolean> getPermisos() {
        return permisos;
    }

    /**
     * @param permisos the permisos to set
     */
    public void setPermisos(HashMap<Integer, Boolean> permisos) {
        this.permisos = permisos;
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
     * @return the configuracion
     */
    public HashMap getConfiguracion() {
        return configuracion;
    }

    /**
     * @param configuracion the configuracion to set
     */
    public void setConfiguracion(HashMap configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * @return the ciclos
     */
    public List<Ciclo> getCiclos() {
        return ciclos;
    }

    /**
     * @param ciclos the ciclos to set
     */
    public void setCiclos(List<Ciclo> ciclos) {
        this.ciclos = ciclos;
    }

    /**
     * @return the puntajesList
     */
    public List<Puntajes> getPuntajesList() {
        return puntajesList;
    }

    /**
     * @param puntajesList the puntajesList to set
     */
    public void setPuntajesList(List<Puntajes> puntajesList) {
        this.puntajesList = puntajesList;
    }

    /**
     * @return the establecimientoList
     */
    public List<Establecimiento> getEstablecimientoList() {
        return establecimientoList;
    }

    /**
     * @param establecimientoList the establecimientoList to set
     */
    public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
        this.establecimientoList = establecimientoList;
    }

    /**
     * @return the listaVigenciaArchivos
     */
    public Lista getListaVigenciaArchivos() {
        return listaVigenciaArchivos;
    }

    /**
     * @param listaVigenciaArchivos the listaVigenciaArchivos to set
     */
    public void setListaVigenciaArchivos(Lista listaVigenciaArchivos) {
        this.listaVigenciaArchivos = listaVigenciaArchivos;
    }

    /**
     * @return the responsables
     */
    public List<Responsable> getResponsables() {
        return responsables;
    }

    /**
     * @param responsables the responsables to set
     */
    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }
}
