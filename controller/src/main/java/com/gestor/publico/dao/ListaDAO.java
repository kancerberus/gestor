/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.publico.dao;

import com.gestor.publico.Lista;
import com.gestor.publico.ListaDetalle;
import com.gestor.publico.ListaDetallePK;
import com.google.gson.JsonObject;
import conexion.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author juliano
 */
public class ListaDAO {

    private Connection conexion;

    public ListaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Lista cargarLista(String nombre) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_lista, nombre"
                    + " FROM public.lista"
                    + " WHERE nombre='" + nombre + "'"
            );
            rs = consulta.ejecutar(sql);
            Lista lista = null;
            if (rs.next()) {
                lista = new Lista(rs.getInt("cod_lista"), rs.getString("nombre"));
            }
            return lista;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }

    public List<ListaDetalle> cargarListaDetalle(Integer codLista) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_lista, cod_detalle, nombre, activo, valor"
                    + " FROM public.lista_detalle"
                    + " WHERE cod_lista=" + codLista
            );
            rs = consulta.ejecutar(sql);
            List<ListaDetalle> listaDetalles = new ArrayList<>();
            while (rs.next()) {
                ListaDetalle ld = new ListaDetalle(new ListaDetallePK(rs.getInt("cod_lista"), rs.getInt("cod_detalle")), rs.getString("nombre"), rs.getBoolean("activo"), rs.getString("valor"));
                listaDetalles.add(ld);
            }
            return listaDetalles;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public List cargarListaDetalle() throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                    "SELECT cod_lista, cod_detalle, nombre "
                    +"FROM lista_detalle " 
                    +"WHERE cod_detalle=4 OR cod_detalle=3 " 
                    +"ORDER BY cod_detalle"
            );
            rs = consulta.ejecutar(sql);
            ArrayList<ListaDetalle> listadetalles = new ArrayList<>();
            while (rs.next()) {
                ListaDetalle ld = new ListaDetalle(new ListaDetallePK(rs.getInt("cod_lista"), rs.getInt("cod_detalle")) , rs.getString("nombre"), null, null);
                ld.setListaDetallePK(new ListaDetallePK(rs.getInt("cod_lista"), rs.getInt("cod_detalle")) );
                ld.setNombre(rs.getString("nombre"));                
                listadetalles.add(ld);                
            }
            return listadetalles;
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
