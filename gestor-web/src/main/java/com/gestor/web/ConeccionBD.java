/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author franj
 */
public class ConeccionBD {
    Connection conn;        

    public ConeccionBD() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gestor", "postgres", "1234");
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
}
