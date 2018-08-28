/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.entity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author juliano
 */
public class UtilTexto {

    public static String CARACTER_COMILLA = "'";
    /*
     * Toma una cadena y la transforma reemplazando todos los caracteres especiales como tildes, ñ, etc.
     */

    public static String normalizar(String cadena) {
        String cadenaNormalizada = Normalizer.normalize(cadena, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        cadenaNormalizada = pattern.matcher(cadenaNormalizada).replaceAll("");
        pattern = Pattern.compile("[\"]");
        cadenaNormalizada = pattern.matcher(cadenaNormalizada).replaceAll("");
        cadenaNormalizada = cadenaNormalizada.replace("&", "-");
        return cadenaNormalizada;
    }

    /*
     * Elimina el caracter de la cadena recorriendo desde la izquierda
     * Ej:  eliminarCaracterIzquierda(0,000538) -> 000538 = 538 
     */
    public static String eliminarCaracterIzquierda(String caracter, String cadena) {
        StringBuilder cadenaAuxiliar = new StringBuilder(cadena);
        while (cadenaAuxiliar.indexOf(caracter) == 0) {
            cadenaAuxiliar.deleteCharAt(0);
        }
        return cadenaAuxiliar.toString();
    }

    /*
     * Toma la cadena y le agregar el caracter a la izquierda hasta completar numeroCaracteres
     */
    public static String lpad(String cadena, String caracter, int numeroCaracteres) {
        if (cadena == null) {
            cadena = "";
        }
        StringBuilder sb = new StringBuilder(numeroCaracteres);
        for (int i = 0; i < (numeroCaracteres - cadena.length()); i++) {
            sb = sb.append(caracter);
        }
        return sb.toString() + cadena;
    }

    /*
     * Toma la cadena y le agrega el caracter a la derecha hasta completar numeroCaracteres
     */
    public static String rpad(String cadena, String caracter, int numeroCaracteres) {
        if (cadena == null) {
            cadena = "";
        }
        StringBuilder sb = new StringBuilder(numeroCaracteres);
        sb.append(cadena);
        for (int i = 0; i < (numeroCaracteres - cadena.length()); i++) {
            sb = sb.append(caracter);
        }
        return sb.toString();
    }

    /*
     * Toma un vector de cadenas y la convierte en una cadena separada por separador
     */
    public static String arrayToString(String[] lista, String separador) {
        String cadena = "";
        for (int i = 0; i < lista.length; i++) {
            cadena += lista[i] + separador;
        }
        if (cadena.length() > 0) {
            cadena = cadena.substring(0, cadena.length() - 1);
        }
        return cadena;
    }

    /**
     * Method Description: separar nombres y apellidos a partir de una ca
     *
     * @param nombreapellido
     */
    public static List<String> separarNombresApellidos(String nombreapellido) {
        List<String> nombresapellidos = new ArrayList<String>();
        nombreapellido = nombreapellido.replaceAll("  ", " ");
        String[] datos = nombreapellido.split(" ");
        if (nombreapellido.length() > 0) {
            if (datos.length == 2) {
                nombresapellidos.add(datos[0]);
                nombresapellidos.add("");
                nombresapellidos.add("");
                nombresapellidos.add("");
            } else if (datos.length == 3) {
                nombresapellidos.add(datos[0]);
                nombresapellidos.add(datos[1]);
                nombresapellidos.add(datos[2]);
                nombresapellidos.add("");
            } else if (datos.length == 4) {
                nombresapellidos.add(datos[0]);
                nombresapellidos.add(datos[1]);
                nombresapellidos.add(datos[2]);
                nombresapellidos.add(datos[3]);
            } else if (datos.length == 5) {
                nombresapellidos.add(datos[0]);
                nombresapellidos.add(datos[1] + " " + datos[2]);
                nombresapellidos.add(datos[3]);
                nombresapellidos.add(datos[4]);
            } else if (datos.length == 6) {
                nombresapellidos.add(datos[0]);
                nombresapellidos.add(datos[1] + " " + datos[2]);
                nombresapellidos.add(datos[3]);
                nombresapellidos.add(datos[4] + " " + datos[5]);
            } else {
                return null;
            }
        }
        return nombresapellidos;
    }

    public static List<String> transformarLista(final List<?> objects) {
        List<String> lista = new ArrayList<String>();
        for (Object ob : objects) {
            lista.add(ob.toString());
        }
        return lista;
    }

    public static String replazarParametros(String mensajeOriginal, List<String> params) {
        int cont = 0;
        String mensajeNuevo = "";
        for (String parametro : params) {
            String cadena1 = "_" + String.valueOf(cont);
            String cadena2 = parametro;
            mensajeNuevo = mensajeOriginal.replace(cadena1, cadena2);
            cont++;
        }
        return mensajeNuevo;
    }

    public static String cadenaDefecto(final String cadena, final String caracter) {
        if (cadena != null) {
            return (caracter + cadena + caracter).trim();
        }
        return null;
    }

    public static final String capitalizarCadena(String string) {
        String s = "";
        if (string != null) {
            string = string.toLowerCase();
            String[] words = string.split(" ");
            for (String w : words) {
                w = w.toUpperCase().replace(w.toUpperCase().substring(1), w.substring(1).toLowerCase());
                s += w + " ";
            }
            s = s.trim();
        }
        return s;
    }

    public static String obtenerMD5(String clave) throws Exception {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(clave.getBytes("UTF-8"));
            byte s[] = m.digest();
            String md5 = "";
            for (int i = 0; i < s.length; i++) {
                md5 += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
            }
            return md5;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            UtilMSG.addSupportMsg();
            UtilLog.generarLog(null, ex);
            throw ex;
        }
    }
}
