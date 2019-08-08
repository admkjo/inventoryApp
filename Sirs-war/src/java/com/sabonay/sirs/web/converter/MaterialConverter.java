/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.web.converter;

/**
 *
 * @author emma
 */
import com.sabonay.sirs.ejb.common.SirsDataSource;
import com.sabonay.sirs.ejb.entities.Material;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Material.class)
public class MaterialConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Material cty = SirsDataSource.dataSource().materialFind(Integer.parseInt(value));
        return cty;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Material obj = (Material) value;
        return String.valueOf(obj.getMaterialId());
    }
}
