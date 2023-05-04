package de.ostfalia.se.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class Logout {
    @Inject
    private HttpServletRequest request;
    public String submit() throws ServletException {
        System.out.println("Logout submit called");
        request.logout();
        request.getSession().invalidate();
        return "/login.xhtml?faces-redirect=true";
    }


}
