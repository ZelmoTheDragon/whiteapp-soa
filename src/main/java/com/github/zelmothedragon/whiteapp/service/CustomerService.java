package com.github.zelmothedragon.whiteapp.service;

import com.github.zelmothedragon.whiteapp.model.entity.Customer;
import com.github.zelmothedragon.whiteapp.model.repository.CustomerDAO;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implémentation des services métier spécifiques à un type objet métier.
 *
 * @author MOSELLE Maxime
 */
@LocalBean
@Stateless
public class CustomerService extends AbstractService<Customer, CustomerDAO>
        implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public CustomerService() {
        super(null);
        // Ne pas appeler explicitement.
    }

    /**
     * Constructeur d'injection. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     *
     * @param dao Accès aux données
     */
    @Inject
    public CustomerService(final CustomerDAO dao) {
        super(dao);
        // Ne pas appeler explicitement.
    }

    public boolean emailExists(String email) {
        return dao.emailExists(email);
    }

}
