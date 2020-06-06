package com.github.zelmothedragon.whiteapp.service;

import com.github.zelmothedragon.whiteapp.model.repository.AbstractDAO;
import java.util.List;

/**
 * Classe mère pour le traitement générique des services métier.
 *
 * @param <E> Type de l'entité persistante
 * @param <R> Type d'entrepôt d'accès aux données
 * @author MOSELLE Maxime
 */
public abstract class AbstractService<E, R extends AbstractDAO<E>> {

    /**
     * Accès aux données.
     */
    protected final R dao;

    /**
     * Constructeur d'injection.
     *
     * @param dao Accès aux données
     */
    protected AbstractService(final R dao) {
        this.dao = dao;
    }

    public void save(final E entity) {
        dao.add(entity);
    }

    public void remove(final E entity) {
        dao.remove(entity);
    }

    public List<E> find() {
        return dao.get();
    }

    public boolean contains(final E entity) {
        return dao.contains(entity);
    }

}
