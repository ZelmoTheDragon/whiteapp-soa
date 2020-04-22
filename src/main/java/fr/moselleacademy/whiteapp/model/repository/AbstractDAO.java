package fr.moselleacademy.whiteapp.model.repository;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Classe mère pour les opérations de persistance générique. Le nommage des
 * méthodes est inspirées de <code>java.util.Collection</code>.
 *
 * @param <E> Entité persistante.
 * @author MOSELLE Maxime
 */
public abstract class AbstractDAO<E> {

    /**
     * Gestionnaire d'entité.
     */
    protected transient final EntityManager em;

    /**
     * Constructeur d'injection.Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     *
     * @param em Gestionnaire d'entité
     */
    protected AbstractDAO(final EntityManager em) {
        this.em = em;
        // Ne pas appeler explicitement.
    }

    /**
     * Obtenir la classe de l'entité persistance.
     *
     * @return La classe de l'entité persistance
     */
    protected Class<E> getEntityClass() {
        // Récupération du type générique dynamiquement.
        // Cette méthode peut être surchargé par les classes filles
        // pour eviter l'usage de la réflexion.
        var paramType = (ParameterizedType) getClass().getGenericSuperclass();
        var entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
        return entityClass;
    }

    /**
     * Obtenir la valeur de la clef primaire.
     *
     * @param entity Instance de l'entité persistante.
     * @return La valeur de la clef primaire ou <code>null</code> si inexistant
     */
    protected Object getIdentifier(final E entity) {
        return em
                .getEntityManagerFactory()
                .getPersistenceUnitUtil()
                .getIdentifier(entity);
    }

    /**
     * Obtenir le type du méta modèle <i>JPA</i> de la clef primaire.
     *
     * @param entity Entité persistante
     * @return Le type du méta modèle <i>JPA</i> de la clef primaire
     */
    protected SingularAttribute<? super E, ?> getIdentifierType(E entity) {
        var pk = getIdentifier(entity);
        var pkClass = pk.getClass();
        return em
                .getMetamodel()
                .entity(getEntityClass())
                .getId(pkClass);
    }

    /**
     * Vérifier l'existence d'une entité.
     *
     * @param entity Entité persistante
     * @return La valeur <code>true</code> si l'entité existe, sinon la valeur
     * <code>false</code> est retournée
     */
    public boolean contains(final E entity) {
        boolean exists;
        if (em.contains(entity)) {
            // Lecture dans le cache de persistance
            exists = true;
        } else {
            var cb = em.getCriteriaBuilder();
            var query = cb.createQuery(Long.class);
            var root = query.from(getEntityClass());
            query.select(cb.count(root));
            var pk = getIdentifier(entity);
            var pkType = getIdentifierType(entity);
            var predicate = cb.equal(root.get(pkType), pk);
            query.where(predicate);
            var result = em.createQuery(query).getSingleResult();
            exists = result == 1L;
        }
        return exists;
    }

    /**
     * Vérifier l'existence d'une collection d'entités.
     *
     * @param entities Collection d'entités
     * @return La valeur <code>true</code> si toute les entités de la collection
     * existe, sinon la valeur <code>false</code> est retournée
     */
    public boolean containsAll(final Collection<E> entities) {
        return entities.stream().allMatch(this::contains);
    }

    /**
     * Obtenir le nombre d'occurence enregistré.
     *
     * @return Le nombre d'occurence
     */
    public long size() {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(Long.class);
        var root = q.from(getEntityClass());
        q.select(cb.count(root));
        return em.createQuery(q).getSingleResult();
    }

    /**
     * Indiquer si aucune occurence existe.
     *
     * @return La valeur <code>true</code> si aucune occurence existe, sinon la
     * valeur <code>false</code> est retournée
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Chercher toutes les entités enregistrés.
     *
     * @return Une liste des entités persistées
     */
    public List<E> get() {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(getEntityClass());
        var root = q.from(getEntityClass());
        q.select(root);
        return em.createQuery(q).getResultList();
    }

    /**
     * Rechercher une entité en fonction de la clef primaire.
     *
     * @param id Clef primaire
     * @return Une option contenant ou non l'entité correspondante à la clef
     * primaire
     */
    public Optional<E> get(final Object id) {
        // Lecture dans le cache de persistance
        var entity = em.find(getEntityClass(), id);
        return Optional.ofNullable(entity);
    }

    /**
     * Ajouter une nouvelle entité.Si l'entité existe déjà, il sera mise à jour.
     *
     * @param entity Entité persistante.
     * @return L'entité persisté et synchronisé dans le cache de persistance de
     * <i>JPA</i>
     */
    public E add(final E entity) {
        E attachedEntity;
        if (contains(entity)) {
            attachedEntity = em.merge(entity);
        } else {
            em.persist(entity);
            attachedEntity = entity;
        }
        return attachedEntity;
    }

    /**
     * Ajouter une nouvelle collection d'entités.Si les entités existent déjà,
     * il seront mise à jour.
     *
     * @param entities
     * @return Les entités persistés et synchronisés dans le cache de
     * persistance de <i>JPA</i>
     */
    public Collection<E> addAll(final Collection<E> entities) {
        return entities
                .stream()
                .map(this::add)
                .collect(Collectors.toList());
    }

    /**
     * Supprimer une entité.
     *
     * @param entity Entité persistante à supprimer
     */
    public void remove(final E entity) {
        var id = getIdentifier(entity);
        var attachedEntity = em.getReference(getEntityClass(), id);
        em.remove(attachedEntity);
        CDI.current().destroy(attachedEntity);
    }

    /**
     * Supprimer une collection d'entité.
     *
     * @param entities Collection d'entités persistantes à supprimer
     */
    public void removeAll(final Collection<E> entities) {
        entities.forEach(this::remove);
    }

}
