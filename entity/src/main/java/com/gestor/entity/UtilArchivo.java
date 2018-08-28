/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author juliano
 */
public class UtilArchivo {

    public static void guardarStream(String destino, InputStream input) throws IOException {
        File f = new File(destino);
        OutputStream out = new FileOutputStream(f);
        byte[] bytes = new byte[1024];
        while (input.read(bytes) > -1) {
            out.write(bytes);
        }
        input.close();
        out.flush();
        out.close();
    }

    public static void borrar(String origen) throws IOException {
        File file = new File(origen);
        file.delete();
    }

    public static void copiar(String origen, String destino) throws IOException {
        InputStream in = new FileInputStream(origen);
        guardarStream(destino, in);
    }

}
