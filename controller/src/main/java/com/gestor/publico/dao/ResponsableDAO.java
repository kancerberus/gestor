/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;
import com.gestor.publico.Responsable;
import com.gestor.publico.Establecimiento;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author franj
 */
public class ResponsableDAO {
    
    private Connection conexion;

    public ResponsableDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public Responsable cargarResponsable(String cedula) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            Responsable responsable = new Responsable();
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cedula, nombres, apellidos, fk_cod_establecimiento, correo, telefono "                    
                    + " FROM responsable"
                    + " WHERE cedula=" + cedula
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                responsable.setCedula(rs.getString("cedula"));
                responsable.setNombres(rs.getString("nombres"));
                responsable.setApellidos(rs.getString("apellidos"));                
                responsable.setTelefono(rs.getString("telefono"));                
                responsable.setCorreo(rs.getString("correo"));                
                responsable.getEstablecimiento().setCodigoEstablecimiento(rs.getInt("codigo_establecimiento"));                
            }
            return responsable;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List<?> cargarListaResponsable() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Responsable> listaResponsable = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT R.cedula, E.codigo_establecimiento, R.nombres, R.apellidos, R.correo, R.telefono, R.estado, "                    
                    + "E.nombre as nomestablecimiento "
                    + "FROM responsable R"
                    + " INNER JOIN establecimiento E ON (E.codigo_establecimiento=R.codigo_establecimiento)"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Responsable res = new Responsable(rs.getString("cedula"), rs.getString("nombres"));
                res.setEstablecimiento(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nomestablecimiento")));                                
                res.setNombres(rs.getString("nombres"));
                res.setApellidos(rs.getString("apellidos"));
                res.setTelefono(rs.getString("telefono"));
                res.setCorreo(rs.getString("correo"));
                res.setEstado(rs.getBoolean("estado"));
                listaResponsable.add(res);
            }
            return listaResponsable;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public void insertarResponsable(Responsable responsable) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO responsable "
                    + " (cedula, nombres, apellidos, correo, telefono, codigo_establecimiento, estado )"
                    + " VALUES ('" + responsable.getCedula()+"', '"+responsable.getNombres()+ "', '"+responsable.getApellidos()+ "', "
                    + " '" + responsable.getCorreo()+ "', '" + responsable.getTelefono()+ "', "        
                    + " '" + responsable.getEstablecimiento().getCodigoEstablecimiento() + "',"+responsable.getEstado()+")"
                    + " ON CONFLICT (cedula) DO UPDATE"
                    + " SET cedula = EXCLUDED.cedula, codigo_establecimiento=EXCLUDED.codigo_establecimiento, nombres=EXCLUDED.nombres, apellidos=EXCLUDED.apellidos, "
                    + " telefono=EXCLUDED.telefono, correo=EXCLUDED.correo, estado=EXCLUDED.estado"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
}
