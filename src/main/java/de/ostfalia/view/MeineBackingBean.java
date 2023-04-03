package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class MeineBackingBean implements Serializable {

    @Inject
    CustomerService cs;

    private static final String KEY_IN_SESSION = "einzelnesElement";
    private List<Customer> meineDatenListe;
    private Customer einzelnesElement;
    private Map<Customer,Boolean> merkerMap = new HashMap<>();

    @PostConstruct
    public void init()
    {
        meineDatenListe = cs.findAll();

        einzelnesElement = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get( KEY_IN_SESSION );
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( KEY_IN_SESSION, null );


    }

    public String neuesElementErstellen()
    {
        meineDatenListe = cs.findAll();
        int idNeu = 0;
        for( Customer me : meineDatenListe ) {
            idNeu = Math.max( idNeu, me.getId().intValue() + 1 );
        }
        einzelnesElement = new Customer("Firstname", "Lastname", "", "", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( KEY_IN_SESSION, einzelnesElement );
        return "einzelnesElementEditieren.xhtml";
    }

    public String einzelnesElementEditieren()
    {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( KEY_IN_SESSION, einzelnesElement );
        return "einzelnesElementEditieren.xhtml";
    }

    public String einzelnesElementSpeichern()
    {
        cs.save( einzelnesElement );
        return "tabelle.xhtml";
    }

    public String markierteElementeLoeschen()
    {
        meineDatenListe = cs.findAll();
        for( Map.Entry<Customer,Boolean> mapEntry : merkerMap.entrySet() ) {
            if( mapEntry.getValue().booleanValue() && !meineDatenListe.remove( mapEntry.getKey() ) ) {
                addFacesMessage( FacesMessage.SEVERITY_ERROR, "Fehler: Datenelement existiert nicht mehr." );
            }
        }
        //MeineEntityDao.writeAll( meineDatenListe );
        merkerMap.clear();
        return null;
    }

    public void addFacesMessage( FacesMessage.Severity severity, String msg )
    {
        FacesMessage facesMsg = new FacesMessage( severity, msg, null );
        FacesContext.getCurrentInstance().addMessage( null, facesMsg );
    }

    public List<Customer> getMeineDatenListe() {
        return meineDatenListe;
    }

    public void setMeineDatenListe(List<Customer> meineDatenListe) {
        this.meineDatenListe = meineDatenListe;
    }

    public Customer getEinzelnesElement() {
        return einzelnesElement;
    }

    public void setEinzelnesElement(Customer einzelnesElement) {
        this.einzelnesElement = einzelnesElement;
    }

    public Map<Customer, Boolean> getMerkerMap() {
        return merkerMap;
    }

    public void setMerkerMap(Map<Customer, Boolean> merkerMap) {
        this.merkerMap = merkerMap;
    }
}