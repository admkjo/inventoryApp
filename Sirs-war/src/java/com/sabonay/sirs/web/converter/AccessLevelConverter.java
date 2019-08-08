/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.converter;

/**
 *
 * @author bekstech
 */
import com.sabonay.sirs.ejb.entities.Accesslevel;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Accesslevel.class)
public class AccessLevelConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Accesslevel cty = null; //SirsDataSource.dataSource().accessLevelFind(value);
        return cty;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Accesslevel obj = (Accesslevel) value;
        return obj.getAcid();
    }
}
