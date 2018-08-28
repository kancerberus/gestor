/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.publico.Establecimiento;
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
                    + " telefono, correo, tipo_establecimiento"
                    + " FROM establecimiento"
                    + " WHERE codigo_establecimiento=" + codigo
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                establecimiento.setCodigoEstablecimiento(rs.getInt("codigo_establecimiento"));
                establecimiento.setNombre(rs.getString("nombre"));
                establecimiento.setTipoEstablecimiento(rs.getString("tipo_establecimiento"));
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
                    + " E.telefono, E.correo, M.nombre AS nom_municipio"
                    + " FROM establecimiento E"
                    + " JOIN municipios M USING (codigo_municipio)"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                Establecimiento e = new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre"));
                e.setMunicipios(new Municipios(rs.getString("codigo_municipio"), rs.getString("nom_municipio")));
                e.setNit(rs.getString("nit"));
                e.setDireccion(rs.getString("direccion"));
                e.setTelefono(rs.getString("telefono"));
                e.setCorreo(rs.getString("correo"));
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

    public List<?> cargarListaEstablecimientosUsuario(String documentoUsuario) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Establecimiento> listaEstablecimientos = new ArrayList<>();
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT E.codigo_establecimiento, E.nombre, E.nit, E.direccion, E.telefono, E.correo, E.fecha_cierre_diario, E.tipo_establecimiento"
                    + " FROM rel_usuarios_establecimiento"
                    + " JOIN establecimiento E USING (codigo_establecimiento)"
                    + " WHERE documento_usuario='" + documentoUsuario + "'"
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                listaEstablecimientos.add(new Establecimiento(rs.getInt("codigo_establecimiento"), rs.getString("nombre"), rs.getString("nit"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("correo")));
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

    public void insertarEstablecimiento(Establecimiento establecimiento) throws SQLException {
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "INSERT INTO establecimiento("
                    + " codigo_establecimiento, codigo_municipio, nombre, nit, direccion, "
                    + " telefono, correo, fecha_cierre_diario, tipo_establecimiento)"
                    + " VALUES (" + establecimiento.getCodigoEstablecimiento() + ", '" + establecimiento.getMunicipios().getCodigoMunicipio() + "',"
                    + " '" + establecimiento.getNombre() + "', '" + establecimiento.getNit() + "', '" + establecimiento.getDireccion() + "',"
                    + " '" + establecimiento.getTelefono() + "', '" + establecimiento.getCorreo() + "', current_date-1, 'I')"
                    + " ON CONFLICT (codigo_establecimiento) DO UPDATE"
                    + " SET codigo_municipio=EXCLUDED.codigo_municipio, nombre=EXCLUDED.nombre, nit=EXCLUDED.nit, "
                    + " direccion=EXCLUDED.direccion, telefono=EXCLUDED.telefono, correo=EXCLUDED.correo, fecha_cierre_diario=EXCLUDED.fecha_cierre_diario, tipo_establecimiento=EXCLUDED.tipo_establecimiento"
            );
            consulta.actualizar(sql);
        } finally {
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
}
