package com.github.zelmothedragon.whiteapp.controller;

import com.github.zelmothedragon.whiteapp.service.AbstractService;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.enterprise.inject.spi.CDI;

/**
 * Classe mère pour les contrôleurs web.
 *
 * @param <E> Type de l'entité
 * @param <S> Type de service métier
 * @author MOSELLE Maxime
 */
public abstract class AbstractController<E, S extends AbstractService<E, ?>> {

    /**
     * Service métier.
     */
    protected final S service;

    /**
     * Formulaire de données.
     */
    protected E entity;

    /**
     * Constructeur d'injection.
     *
     * @param entity Entité persistante
     * @param service Service métier
     */
    protected AbstractController(final E entity, final S service) {
        this.entity = entity;
        this.service = service;
        // Ne pas appeler explicitement.
    }

    public void save() {
        service.save(entity);
        resetForm();
    }

    public void edit(final E entity) {
        this.entity = entity;
    }

    public void remove(final E entity) {
        service.remove(entity);
    }

    public List<E> find() {
        return service.find();
    }

    public boolean contains() {
        return service.contains(entity);
    }

    protected void resetForm() {
        var paramType = (ParameterizedType) getClass().getGenericSuperclass();
        var formType = (Class<E>) paramType.getActualTypeArguments()[0];
        entity = CDI.current().select(formType).get();
    }

    // ------------------------------
    // Accesseurs & Muttateurs
    // ------------------------------
    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

}
