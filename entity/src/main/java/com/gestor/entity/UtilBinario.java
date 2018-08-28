/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.entity;

/**
 *
 * @author juliano
 */
public class UtilBinario {

    public static int PERMISO_EJECUTAR = 1;
    public static int PERMISO_CREAR = 2;
    public static int PERMISO_MODIFICAR = 4;
    public static int PERMISO_ELIMINAR = 8;
    public static int PERMISO_IMPRIMIR = 16;
    public static int PERMISO_PLANO = 32;
    public static int PERMISO_AUTORIZADO = 64;

    /**
     * calcula si el usuario tiene el permiso asignado desde el SIMA.
     *
     * @param codPermiso variable estatica.
     * @param permiso permiso que tiene asignado el usuario en el SIMA.
     * @return boolean
     * @throws java.lang.Exception
     */
    public static boolean permisoBinario(int codPermiso, int permiso) throws Exception {
        int contador = 0, residuo;
        int potencia = (int) Math.pow(2, contador);
        boolean res = false;
        while (potencia <= codPermiso) {
            residuo = permiso % 2;
            res = residuo == 1;
            permiso = permiso / 2;
            contador++;
            potencia = (int) Math.pow(2, contador);
        }
        return res;
    }
}
