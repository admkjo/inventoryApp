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
import com.sabonay.sirs.ejb.entities.CustomerGroup;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = CustomerGroup.class)
public class CustomerGroupConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CustomerGroup cty = SirsDataSource.dataSource().customerGroupFind(Integer.parseInt(value));
        return cty;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        CustomerGroup obj = (CustomerGroup) value;
        return String.valueOf(obj.getGroupId());
    }
}
