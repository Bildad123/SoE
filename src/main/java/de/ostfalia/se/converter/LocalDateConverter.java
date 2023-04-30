package de.ostfalia.se.converter;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Custom converter class
@FacesConverter(value = "localDateConverter")
public  class LocalDateConverter implements Converter {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern(DATE_PATTERN));
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage("Invalid date format: " + s));
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof LocalDate) {
            return ((LocalDate) o).format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        } else {
            throw new ConverterException(new FacesMessage("Invalid object type: " + o.getClass()));
        }
    }

}
