/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.controller;

import com.gestor.entity.UtilLog;
import java.util.Properties;

/**
 *
 * @author juliano
 */
public class Propiedades {

    private static Propiedades instancia;
    private Properties propiedades;

    private Propiedades() {
        GestorPropiedades gestorPropiedades = new GestorPropiedades();
        try {
            propiedades = gestorPropiedades.cargarPropiedades();
        } catch (Exception e) {
            UtilLog.generarLog(this.getClass(), e);
        }
    }

    /**
     * @return the instancia
     */
    public static Propiedades getInstancia() {
        if (instancia == null) {
            synchronized (Propiedades.class) {
                instancia = new Propiedades();
            }
        }
        return instancia;
    }

    public Properties getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Properties propiedades) {
        this.propiedades = propiedades;
    }
}
