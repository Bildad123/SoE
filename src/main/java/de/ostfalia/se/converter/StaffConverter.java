package de.ostfalia.se.converter;

import de.ostfalia.se.boundary.StaffService;
import de.ostfalia.se.entity.Staff;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(value = "staffConverter", managed = true)
public class StaffConverter implements Converter {
    @Inject
    StaffService ss;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return (ss.findById(Long.parseLong(s)));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return  String.valueOf(((Staff)(o)).getId());
    }
}
