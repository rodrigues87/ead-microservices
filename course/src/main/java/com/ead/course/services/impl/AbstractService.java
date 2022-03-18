package com.ead.course.services.impl;

import com.ead.course.controllers.AbstractEntity;
import javassist.tools.rmi.ObjectNotFoundException;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<T> {

    public abstract List<T> findAll();

    public abstract T save(T obj) throws ObjectNotFoundException;

    public abstract T update(T obj) throws ObjectNotFoundException;

    public abstract void delete(UUID id) throws ObjectNotFoundException;

    public abstract T findById(UUID id) throws ObjectNotFoundException;

    public void verifyIfExists(AbstractEntity obj) throws ObjectNotFoundException {

        if (obj.getId() == null) throw new ObjectNotFoundException("Objeto com id não encontrado");

        this.findById(obj.getId());
    }
}
