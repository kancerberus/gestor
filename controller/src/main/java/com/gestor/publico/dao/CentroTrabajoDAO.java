/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.publico.CentroTrabajo;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Municipios;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.Consulta;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Julian
 */
public class CentroTrabajoDAO {

    private Connection conexion;

    public CentroTrabajoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<?> cargarListaCentrostrabajo(int codEstablecimiento) throws SQLException {        
        ResultSet rs = null;
        
        Consulta consulta = null;        
        List<CentroTrabajo> listaCentrosTrabajo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_centrotrabajo, nombre, nit, estado "                    
                    + " FROM centro_trabajo"
                    + " WHERE codigo_establecimiento= '"+codEstablecimiento+"'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                CentroTrabajo ct= new CentroTrabajo(rs.getInt("codigo_establecimiento"), rs.getInt("cod_centrotrabajo"), rs.getString("nombre") , rs.getString("nit"), rs.getBoolean("estado"));                
                ct.setCodCentrotrabajo(rs.getInt("cod_centrotrabajo"));
                ct.setNombre(rs.getString("nombre")); 
                ct.setNit(rs.getString("nit"));
                ct.setEstado(rs.getBoolean("estado"));
                listaCentrosTrabajo.add(ct);
            }
            
            return listaCentrosTrabajo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }      
    
    public List<?> cargarListaCentrostrabajoactivos(int codEstablecimiento) throws SQLException {        
        ResultSet rs = null;
        
        Consulta consulta = null;        
        List<CentroTrabajo> listaCentrosTrabajo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_centrotrabajo, nombre, nit, estado "                    
                    + " FROM centro_trabajo"
                    + " WHERE codigo_establecimiento= '"+codEstablecimiento+"' AND estado='true'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                CentroTrabajo ct= new CentroTrabajo(rs.getInt("codigo_establecimiento"), rs.getInt("cod_centrotrabajo"), rs.getString("nombre") , rs.getString("nit"), rs.getBoolean("estado"));                
                ct.setCodCentrotrabajo(rs.getInt("cod_centrotrabajo"));
                ct.setNombre(rs.getString("nombre")); 
                ct.setNit(rs.getString("nit"));
                ct.setEstado(rs.getBoolean("estado"));
                listaCentrosTrabajo.add(ct);
            }
            
            return listaCentrosTrabajo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public List<?> cargarListaCentrosTrabajoVulnerabilidades(int codEstablecimiento) throws SQLException {        
        ResultSet rs = null;
        
        Consulta consulta = null;        
        List<CentroTrabajo> listaCentrosTrabajo = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento,cod_centrotrabajo, nombre, nit, estado " +
                    "FROM centro_trabajo " +
                    "WHERE estado='true' AND codigo_establecimiento='"+codEstablecimiento+"' AND cod_centrotrabajo NOT IN (Select cod_centro_trabajo " +
                    "FROM plemergencias.plan_emergencia pe " +
                    "WHERE pe.codigo_establecimiento='"+codEstablecimiento+"')"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                CentroTrabajo ct= new CentroTrabajo(rs.getInt("codigo_establecimiento"), rs.getInt("cod_centrotrabajo"), rs.getString("nombre") , rs.getString("nit"), rs.getBoolean("estado"));                
                ct.setCodCentrotrabajo(rs.getInt("cod_centrotrabajo"));
                ct.setNombre(rs.getString("nombre")); 
                ct.setNit(rs.getString("nit"));
                ct.setEstado(rs.getBoolean("estado"));
                listaCentrosTrabajo.add(ct);
            }
            
            return listaCentrosTrabajo;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public void insertarCentro(CentroTrabajo centro) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO public.centro_trabajo "
                    + " ( cod_centrotrabajo, codigo_establecimiento, nombre, nit, estado )"
                    + " VALUES ('" + centro.getCodCentrotrabajo() + "', '" + centro.getCodigoEstablecimiento() + "', '"+centro.getNombre()+"', '" + centro.getNit() + "','"+centro.getEstado()+"') "                    
                    + " ON CONFLICT (cod_centrotrabajo, codigo_establecimiento) DO UPDATE "
                    + " SET nombre=EXCLUDED.nombre, nit=EXCLUDED.nit, estado=EXCLUDED.estado"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarCentro(CentroTrabajo centro) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE "
                    + " FROM public.centro_trabajo"
                    + " WHERE cod_centrotrabajo='"+centro.getCodCentrotrabajo()+"' AND codigo_establecimiento='"+centro.getCodigoEstablecimiento()+"'"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }       
       
    }
    
}
