/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.web;

import java.util.Collections;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Esta clase se encarga de realizar la conversion de los objetos en las listas
 * a ser mostradas en los componentes graficos, funciona correctamente para
 * listas de seleccion tipo selectItems
 *
 * @author juliano
 */
@FacesConverter("convertidorGenerico")
public class ConvertidorGenerico implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.equals("")) {
            final int index = Integer.parseInt(value);
            if (index == -1) {
                return null;
            }
            final List<?> objects = getItemsObjects(component);
            return objects.get(index);
        }else{
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final List<?> objects = getItemsObjects(component);
        return String.valueOf(objects.indexOf(value));
    }

    private List<?> getItemsObjects(UIComponent component) {
        List<?> objects = Collections.emptyList();
        for (UIComponent child : component.getChildren()) {
            if (child.getClass() == UISelectItems.class) {
                objects = (List<?>) ((UISelectItems) child).getValue();
            }
        }
        return objects;
    }
}
