/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.controller;

import java.util.Properties;

/**
 *
 * @author juliano
 */
public class GestorPropiedades {

    public Properties cargarPropiedades() throws Exception {
        Properties p = new Properties();
        try {
//    p.setProperty("urlbd", "jdbc:postgresql://localhost:5432/gestor");
            p.setProperty("urlbd", "jdbc:postgresql://192.168.1.9:5432/gestor");
            p.setProperty("controlador", "org.postgresql.Driver");
            p.setProperty("usuario", "postgres");
//    p.setProperty("clave", "postgres");
            p.setProperty("rutaAdjunto", "c:\\gestor\\archivos");
            p.setProperty("rutaTemporal", "c:\\gestor\\tmp");
            p.setProperty("rutaEliminado", "c:\\gestor\\eliminados");            
            p.setProperty("guardarLogos", "D:\\gestor\\gestor-web\\target\\gestor-web-1.0\\resources\\imagenes\\establecimientos");    
            p.setProperty("clave", "1234");

        } catch (Exception e) {
            throw e;
        }
        return p;
    }
}
