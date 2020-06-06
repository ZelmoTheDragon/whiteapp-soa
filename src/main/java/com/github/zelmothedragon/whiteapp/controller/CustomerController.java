package com.github.zelmothedragon.whiteapp.controller;

import com.github.zelmothedragon.whiteapp.model.entity.Customer;
import com.github.zelmothedragon.whiteapp.service.CustomerService;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Contrôleur web spécifique à une entité métier.
 *
 * @author MOSELLE Maxime
 */
@Named
@ViewScoped
public class CustomerController extends AbstractController<Customer, CustomerService>
        implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     *
     */
    public CustomerController() {
        super(null, null);
        // Ne pas appeler explicitement.
    }

    /**
     * Constructeur d'injection.Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     *
     * @param entity Entité persistante
     * @param service Service métier
     */
    @Inject
    public CustomerController(
            final Customer entity,
            final CustomerService service) {

        super(entity, service);
        // Ne pas appeler explicitement.
    }

    /**
     * Vérifier l'existance de l'adresse de courriel dans la persistance.
     *
     * @param email Adresse de courriel
     * @return La valeur <code>true</code> si cette adresse est déjà utilisée,
     * sinon la valeur <code>false</code> est retournée
     */
    public boolean emailExists(final String email) {
        return service.emailExists(email);
    }

}
