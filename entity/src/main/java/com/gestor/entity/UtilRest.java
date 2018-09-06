/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author juliano
 */
public class UtilRest {

    /**
     * Convierte un objeto en Json.
     *
     * @param object
     * @return
     *
     */
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Convierte un objeto en Json.
     *
     * @param object tipo de objeto
     * @param formatoFecha formtado de fecha a utilizar
     * @return json con informacion del objeto transformado
     *
     */
    public static String toJson(Object object, String formatoFecha) {
        Gson gson = new GsonBuilder().setDateFormat(formatoFecha).create();
        return gson.toJson(object);
    }

    /**
     * Convierte una cadena Json en un objeto
     *
     * @param <T> Tipo de objeto
     * @param json Cadena Json
     * @param clase Clase del objeto
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clase) {
        Gson gson = new Gson();
        return gson.fromJson(json, clase);
    }

    /**
     * Convierte una cadena Json en un objeto
     *
     * @param <T>
     * @param json Cadena Json
     * @param clase Clase del objeto
     * @param formatoFecha
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clase, String formatoFecha) {
        Gson gson = new GsonBuilder().setDateFormat(formatoFecha).create();
        return gson.fromJson(json, clase);
    }

    /**
     * Convierte una cadena json a jsonArray.
     *
     * @param json Cadena Json
     * @param object
     * @return
     * @throws org.json.simple.parser.ParseException
     */
    public static String toJsonArray(String json, Object object) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONObject jsonObject = (JSONObject) obj;
        String nombreClase = object.getClass().getSimpleName().substring(0, 1).toLowerCase() + object.getClass().getSimpleName().substring(1);
        if (jsonObject.get(nombreClase).getClass() != null && jsonObject.get(nombreClase).getClass().getName() != null && jsonObject.get(nombreClase).getClass().getName().equals("org.json.simple.JSONArray")) {
            return ((JSONArray) jsonObject.get(nombreClase)).toJSONString();
        } else {
            JSONArray array = new JSONArray();
            array.add(jsonObject.get(nombreClase));
            return array.toJSONString();
        }
    }

//    public static String encode(byte[] imagen) {
//        return new BASE64Encoder().encode(imagen);
//    }
}
