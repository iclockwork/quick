package com.ztesoft.res.quick.base.repository;

import java.io.Serializable;

/**
 * BaseHibernateJpaRepository
 *
 * @author: fengwang
 * @date: 2018-2-7 14:58
 * @version: 1.0
 * @since: JDK 1.8
 */
public interface BaseJpaRepository<T extends Entity, ID extends Serializable> {
    /**
     * Method to setup the class type of the Entity for which
     * the DAO works
     *
     * @param clazz
     */
    void setupEntityClass(Class clazz);

    /**
     * Method to insert the new row into config.database table
     *
     * @param object The object entity to be persisted
     */
    T insert(T object);

    /**
     * Method to update an existing row in the config.database table
     *
     * @param object The object entity to be updated
     */
    T update(T object);

    /**
     * Method to insert a new row or update a row if it was
     * already existing in the system.
     *
     * @param object The object entity to be updated
     */
    T insertOrUpdate(T object);

    /**
     * Method to delete an existing row in the config.database table
     *
     * @param object The object entity to be deleted
     */
    void delete(T object);

    /**
     * Method to find a database item by id
     *
     * @param id The id by which the row has to be found
     */
    T findById(ID id);

    /**
     * find
     *
     * @param object object
     */
    T findByExample(T object);
}
