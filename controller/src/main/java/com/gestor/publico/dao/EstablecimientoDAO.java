/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.matriz.MatrizRiesgos;
import com.gestor.publico.Cargos;
import com.gestor.publico.Establecimiento;
import com.gestor.publico.Funciones;
import com.gestor.publico.Municipios;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexion.Consulta;

/**
 *
 * @author Julian
 */
public class EstablecimientoDAO {

    private Connection conexion;

    public EstablecimientoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Establecimiento cargarEstablecimiento(int codigo) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            Establecimiento establecimiento = new Establecimiento();
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, codigo_municipio, nombre, nit, direccion, "
                    + " telefono, correo, tipo_establecimiento, logo"
                    + " FROM establecimiento"
                    + " WHERE codigo_establecimiento=" + codigo
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                establecimiento.setCodigoEstablecimiento(rs.getInt("codigo_establecimiento"));
                establecimiento.setNombre(rs.getString("nombre"));
                establecimiento.setTipoEstablecimiento(rs.getString("tipo_establecimiento"));
                establecimiento.setLogo(rs.getString("logo"));
            }
            return establecimiento;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public Integer buscarSisgapp() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento "
                    + " FROM public.establecimiento "
                    + " WHERE nombre='SISGAPP' "
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getInt("codigo_establecimiento");
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void eliminarCargosEstablecimiento(Establecimiento estb) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "DELETE FROM rel_cargos_establecimiento"
                    + " WHERE codigo_establecimiento='" + estb.getCodigoEstablecimiento() + "'"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    @SuppressWarnings("null")
    public void agregarCargosEstablecimiento(Cargos cargos, Establecimiento establecimiento) throws SQLException {        
        Consulta consulta = null;                  
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO rel_cargos_establecimiento("
                    + " codigo_establecimiento, cod_cargo)"
                    + " VALUES (" + establecimiento.getCodigoEstablecimiento() + ", '" + cargos.getCodCargo() + "')"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    

    public String cargarPrefijo(int codigoEstablecimiento) throws SQLException {
        ResultSet rs = null;
        ResultSet rs2 = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, cod_prefijo, fecha_inicial, fecha_final"
                    + " FROM prefijos"
                    + " WHERE codigo_establecimiento=" + codigoEstablecimiento
                    + " AND current_date between fecha_inicial AND fecha_final"
            );
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                return rs.getString("cod_prefijo");
            } else {
                sql = new StringBuilder(
                        "INSERT INTO prefijos("
                        + " codigo_establecimiento, cod_prefijo, fecha_inicial, fecha_final)"
                        + " VALUES (" + codigoEstablecimiento + ", (SELECT ('A'||extract(year from current_timestamp)-2000)::VARCHAR),"
                        + " (SELECT date_trunc('year', current_date)::date), (SELECT date_trunc('year', current_date)::date + 365))"
                        + " RETURNING cod_prefijo;"
                );
                rs2 = consulta.ejecutar(sql);
                rs2.next();
                return rs2.getString("cod_prefijo");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (rs2 != null) {
                rs2.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaEstablecimientos() throws SQLException {        
        ResultSet rs = null;
        
        Consulta consulta = null;        
        List<Establecimiento> listaEstablecimientos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT codigo_establecimiento, codigo_municipio, E.nombre, E.nit, E.direccion, "
                    + " E.telefono, E.correo, M.nombre AS nom_municipio, E.logo"
                    + " FROM establecimiento E"
                    + " JOIN municipios M USING (codigo_municipio)"
                    + " ORDER BY codigo_establecimiento"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Establecimiento e = new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre"));
                e.setMunicipios(new Municipios(rs.getString("codigo_municipio"), rs.getString("nom_municipio")));
                e.setNit(rs.getString("nit"));
                e.setDireccion(rs.getString("direccion"));
                e.setTelefono(rs.getString("telefono"));
                e.setCorreo(rs.getString("correo"));
                e.setLogo(rs.getString("logo"));  
                listaEstablecimientos.add(e);
            }
            
            return listaEstablecimientos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }  
    
    public List<?> cargarListaCargos() throws SQLException {        
        ResultSet rs = null;
        
        Consulta consulta = null;        
        List<Cargos> listaCargos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_cargo, nombre "                    
                    + " FROM cargos car"                    
                    + " ORDER BY nombre ASC"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Cargos car= new Cargos(rs.getInt("cod_cargo"), rs.getString("nombre"));
                car.setCodCargo(rs.getInt("cod_cargo"));                
                car.setNombre(rs.getString("nombre"));                
                listaCargos.add(car);                
            }            
            return listaCargos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaFunciones(Integer codCargo) throws SQLException {        
        ResultSet rs = null;
        
        Consulta consulta = null;        
        List<Funciones> listaFunciones = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_cargo, cod_funcion, nombre "                    
                    + " FROM funciones fun"
                    + " WHERE cod_cargo='"+codCargo+"'"                                
                    + " ORDER BY cod_funcion"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Funciones fun= new Funciones(rs.getInt("cod_cargo"), rs.getInt("cod_funcion"), rs.getString("nombre"));
                fun.setCodCargo(rs.getInt("cod_cargo"));
                fun.setCodFuncion(rs.getInt("cod_funcion"));
                fun.setNombre(rs.getString("nombre"));                
                listaFunciones.add(fun);                
            }            
            return listaFunciones;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    
    public List<?> cargarListaEstablecimientosUsuario(String documentoUsuario) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Establecimiento> listaEstablecimientos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT E.codigo_establecimiento, E.nombre, E.nit, E.direccion, E.telefono, E.correo, E.fecha_cierre_diario, E.tipo_establecimiento, "
                    + " E.logo"
                    + " FROM rel_usuarios_establecimiento"
                    + " JOIN establecimiento E USING (codigo_establecimiento)"
                    + " WHERE documento_usuario='" + documentoUsuario + "'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                listaEstablecimientos.add(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre"), rs.getString("nit"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("correo"), rs.getString("logo")));
            }
            return listaEstablecimientos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaCargosEstablecimiento(Integer codEstablecimiento) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Cargos> listaCargos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT C.cod_cargo, C.nombre"
                    + " FROM rel_cargos_establecimiento"
                    + " JOIN cargos C USING (cod_cargo)"
                    + " WHERE codigo_establecimiento='" + codEstablecimiento + "'"
                    + " GROUP BY C.cod_cargo"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                listaCargos.add(new Cargos(rs.getInt("cod_cargo"), rs.getString("nombre")));
            }
            return listaCargos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    
    

    public void insertarEstablecimiento(Establecimiento establecimiento) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO establecimiento("
                    + " codigo_establecimiento, codigo_municipio, nombre, nit, direccion, "
                    + " telefono, correo, fecha_cierre_diario, tipo_establecimiento, logo)"
                    + " VALUES (" + establecimiento.getCodigoEstablecimiento() + ", '" + establecimiento.getMunicipios().getCodigoMunicipio() + "',"
                    + " '" + establecimiento.getNombre() + "', '" + establecimiento.getNit() + "', '" + establecimiento.getDireccion() + "',"
                    + " '" + establecimiento.getTelefono() + "', '" + establecimiento.getCorreo() + "', current_date-1, 'I', '"+establecimiento.getLogo()+"')"
                    + " ON CONFLICT (codigo_establecimiento) DO UPDATE"
                    + " SET codigo_municipio=EXCLUDED.codigo_municipio, nombre=EXCLUDED.nombre, nit=EXCLUDED.nit, "
                    + " direccion=EXCLUDED.direccion, telefono=EXCLUDED.telefono, correo=EXCLUDED.correo, fecha_cierre_diario=EXCLUDED.fecha_cierre_diario, tipo_establecimiento=EXCLUDED.tipo_establecimiento, logo=EXCLUDED.logo"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarCargos(Cargos cargos) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO cargos("
                    + " cod_cargo, nombre )"
                    + " VALUES (" +cargos.getCodCargo()+",'"+cargos.getNombre()+"')"
                    + " ON CONFLICT (cod_cargo) DO UPDATE"
                    + " SET nombre=EXCLUDED.nombre "                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarFuncion(Funciones funcion) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO funciones("
                    + " cod_cargo, cod_funcion, nombre )"
                    + " VALUES (" +funcion.getCodCargo()+","+funcion.getCodFuncion()+",'"+funcion.getNombre()+"')"
                    + " ON CONFLICT (cod_cargo, cod_funcion) DO UPDATE"
                    + " SET nombre=EXCLUDED.nombre "                    
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<Establecimiento> establecimientoPermitido() throws SQLException{
        ResultSet rs=null;
        Consulta consulta=null;
        List<Establecimiento> listaEstablecimientos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT E.codigo_establecimiento, E.nombre, E.nit, E.direccion, E.telefono, E.correo, E.fecha_cierre_diario, E.tipo_establecimiento, "+
                        " E.logo "+
                        " FROM public.establecimiento E " +
                        "WHERE e.codigo_establecimiento NOT IN "+
                        " (SELECT ev.codigo_establecimiento  " +
                        " FROM gestor.evaluacion ev " +
                        " JOIN public.establecimiento es using (codigo_establecimiento)) "
                        
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                listaEstablecimientos.add(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre"), rs.getString("nit"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("correo"), rs.getString("logo")));
            }
            return listaEstablecimientos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
        
    }
    
    public List<Establecimiento> establecimientosPlanMaestroPermitido() throws SQLException{
        ResultSet rs=null;
        Consulta consulta=null;
        List<Establecimiento> listaEstablecimientos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT E.codigo_establecimiento, E.nombre, E.nit, E.direccion, E.telefono, E.correo, E.fecha_cierre_diario, E.tipo_establecimiento, "+
                        " E.logo "+
                        " FROM public.establecimiento E " +
                        " WHERE e.codigo_establecimiento NOT IN "+
                        " ((SELECT pm.codigo_establecimiento "+
                        " FROM seguimiento.plan_maestro pm JOIN public.establecimiento es using (codigo_establecimiento))) "
                        
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                listaEstablecimientos.add(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre"), rs.getString("nit"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("correo"), rs.getString("logo")));
            }
            return listaEstablecimientos;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
        
    }
    
    
    
    
}
