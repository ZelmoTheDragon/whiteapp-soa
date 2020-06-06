package com.github.zelmothedragon.whiteapp.view;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Internationalisation du site côté client.
 *
 * @author MOSELLE Maxime
 */
@Named
@SessionScoped
public class Internationalization implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Langue courante.
     */
    private Locale currentLocale;

    /**
     * Constructeur d'injection.Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public Internationalization() {
        this.currentLocale = Locale.FRENCH;
         // Ne pas appeler explicitement.
    }

    /**
     * Changer la langue côté client.
     *
     * @param lang Code de la nouvelle langue
     */
    public void changeLocale(final String lang) {

        switch (lang) {

            case "EN":
            case "en":
                currentLocale = Locale.ENGLISH;
                FacesContext
                        .getCurrentInstance()
                        .getViewRoot()
                        .setLocale(Locale.ENGLISH);
                break;

            case "FR":
            case "fr":
            default:
                currentLocale = Locale.FRENCH;
                FacesContext
                        .getCurrentInstance()
                        .getViewRoot()
                        .setLocale(Locale.FRENCH);
                break;
        }
    }

    // ------------------------------
    // Accesseurs & Muttateurs
    // ------------------------------
    
    public Locale getCurrentLocale() {
        return currentLocale;
    }

}
