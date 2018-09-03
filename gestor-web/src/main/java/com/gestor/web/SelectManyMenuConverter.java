/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.web;

import com.gestor.modelo.Sesion;
import com.gestor.publico.Establecimiento;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;


/**
 *
 * @author juliano
 */
@FacesConverter("establecimientoConverter")
public class SelectManyMenuConverter implements Converter {

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
//                ThemeService service = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
                Sesion sesion = (Sesion) fc.getExternalContext().getSessionMap().get("sesion");
                for (Establecimiento e : sesion.getEstablecimientoList()) {
                    if(e.getCodigoEstablecimiento().toString().equalsIgnoreCase(value)){
                        return e;
                    }
                }
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Establecimiento) object).getCodigoEstablecimiento());
        } else {
            return null;
        }
    }
}
