package com.github.zelmothedragon.whiteapp.model;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Gestionnaire de ressource injectable.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
public class ContextResource {

    /**
     * Gestionnaire d'entité (injecté par <i>JPA</i>).
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de Java EE.
     */
    public ContextResource() {
        // Ne pas appeler explicitement
    }

    /**
     * Produire une nouvelle instance du gestionnaire d'entité.
     *
     * @return Une nouvelle instance du gestionnaire d'entité
     */
    @Produces
    public EntityManager createEntityManager() {
        return em;
    }

    /**
     * Libérer les ressources du gestionnaire d'entité.
     *
     * @param em Une instance du gestionnaire d'entité
     */
    public void clearEntityManager(@Disposes final EntityManager em) {
        em.clear();
        em.close();
    }

}
