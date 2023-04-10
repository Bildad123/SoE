package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Product;
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




    private static final String KEY_IN_SESSION = "einzelnesElement";
    private List<Product> meineDatenListe;
    private Product einzelnesElement;

    @Inject
    ProductService ps;
    private Map<Product,Boolean> merkerMap = new HashMap<>();

    @PostConstruct
    public void init(){
        meineDatenListe = ps.findAll();

        //meineDatenListe = MeineEntityDao.findAll();




    }

    public MeineBackingBean()
    {

        einzelnesElement = (Product) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get( KEY_IN_SESSION );
        System.out.println("einzelnesElement -- init  : " + einzelnesElementSpeichern() );

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( KEY_IN_SESSION, null );
       // meineDatenListe = ps.findAll();

        //meineDatenListe = MeineEntityDao.findAll();

        //einzelnesElement = (Product) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get( KEY_IN_SESSION );
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( KEY_IN_SESSION, null );

        // Die folgenden Zeilen erzeugen eine Daten-Vorbelegung fuer erste einfache Tests:
        /*if( meineDatenListe == null ) {
            meineDatenListe = MeineEntityDao.createTestDaten();
        } */


    }

    public String neuesElementErstellen()
    {
        /*
        meineDatenListe = MeineEntityDao.findAll();
        int idNeu = 0;
        for( MeineEntity me : meineDatenListe ) {
            idNeu = Math.max( idNeu, me.getId().intValue() + 1 );
        }
        einzelnesElement = new MeineEntity( idNeu, null, new Date() );
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( KEY_IN_SESSION, einzelnesElement );

         */
        return "einzelnesElementEditieren.xhtml";
    }

    public String einzelnesElementEditieren()
    {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( KEY_IN_SESSION, einzelnesElement );
        System.out.println("einzelnes Element name: " + einzelnesElement.getName());
        return "productForm.xhtml";
    }

    public String einzelnesElementSpeichern()
    {
        /*
        MeineEntityDao.saveOrUpdateEntity( einzelnesElement );

         */
        return "tabelle.xhtml";
    }

    public String markierteElementeLoeschen()
    {
        /*
        meineDatenListe = MeineEntityDao.findAll();
        for( Map.Entry<MeineEntity,Boolean> mapEntry : merkerMap.entrySet() ) {
            if( mapEntry.getValue().booleanValue() && !meineDatenListe.remove( mapEntry.getKey() ) ) {
                addFacesMessage( FacesMessage.SEVERITY_ERROR, "Fehler: Datenelement existiert nicht mehr." );
            }
        }
        MeineEntityDao.writeAll( meineDatenListe );
        merkerMap.clear();

         */
        return null;
    }

    public void addFacesMessage( FacesMessage.Severity severity, String msg )
    {
        FacesMessage facesMsg = new FacesMessage( severity, msg, null );
        FacesContext.getCurrentInstance().addMessage( null, facesMsg );
    }

    public List<Product>        getMeineDatenListe()  { return meineDatenListe; }
    public Product              getEinzelnesElement() { return einzelnesElement; }
    public Map<Product,Boolean> getMerkerMap()        { return merkerMap; }
    public void setMeineDatenListe(  List<Product> meineDatenListe )  { this.meineDatenListe = meineDatenListe; }
    public void setEinzelnesElement( Product einzelnesElement )       { this.einzelnesElement = einzelnesElement; }
    public void setMerkerMap(        Map<Product,Boolean> merkerMap ) { this.merkerMap = merkerMap; }
}