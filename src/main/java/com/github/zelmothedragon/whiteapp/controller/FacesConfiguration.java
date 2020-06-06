package com.github.zelmothedragon.whiteapp.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

/**
 * Classe de configuration de la technologie <i>JSF</i>.
 * @author MOSELLE Maxime
 */
@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class FacesConfiguration {

    /**
     * Constructeur d'injection.Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public FacesConfiguration() {
        // Ne pas appeler explicitement.
    }

}
