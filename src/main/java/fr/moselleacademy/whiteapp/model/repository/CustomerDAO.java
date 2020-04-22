package fr.moselleacademy.whiteapp.model.repository;


import fr.moselleacademy.whiteapp.model.entity.Customer;
import fr.moselleacademy.whiteapp.model.entity.Customer_;
import java.io.Serializable;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Entrepôt de données spécifique à un type d'entité persistante.
 *
 * @author MOSELLE Maxime
 */
@Dependent
public class CustomerDAO extends AbstractDAO<Customer> implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur d'injection.Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     *
     * @param em Gestionnaire d'entité
     */
    @Inject
    public CustomerDAO(final EntityManager em) {
        super(em);
        // Ne pas appeler explicitement.
    }

    /**
     * Construire une nouvelle instance de cette classe. Comme cette classe est
     * injectable, il est nécessaire de passer par <i>CDI</i> pour récupérer une
     * instance. Cette méthode de fabrique permet de faciliter cet appel.
     *
     * @return Une nouvelle instance vierge
     */
    public static CustomerDAO of() {
        return CDI.current().select(CustomerDAO.class).get();
    }

    /**
     * Rechercher une entité en fonction de son adresse de courriel.
     *
     * @param email Adresse de courriel
     * @return Une option contenant ou non l'entité.
     */
    public Optional<Customer> findByEmail(final String email) {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(getEntityClass());
        var root = q.from(getEntityClass());
        q.select(root);
        var p = cb.equal(root.get(Customer_.email), email);
        q.where(p);
        return em
                .createQuery(q)
                .getResultList()
                .stream()
                .findAny();
    }

    /**
     * Vérifier l'existance de l'adresse de courriel dans la persistance.
     *
     * @param email Adresse de courriel
     * @return La valeur <code>true</code> si cette adresse est déjà utilisée,
     * sinon la valeur <code>false</code> est retournée
     */
    public boolean emailExists(final String email) {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(Long.class);
        var root = q.from(getEntityClass());
        q.select(cb.count(root));
        var p = cb.equal(root.get(Customer_.email), email);
        q.where(p);
        return em
                .createQuery(q)
                .getSingleResult() == 1L;
    }

}
