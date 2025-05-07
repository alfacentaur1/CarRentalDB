package DAO;

import jakarta.persistence.*;

import java.util.List;

public abstract class BaseDao<T, ID> {
    protected final jakarta.persistence.EntityManager entityManager;
    private final Class<T> entityClass;

    public BaseDao(jakarta.persistence.EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public void update(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    public void delete(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        entityManager.getTransaction().commit();
    }
}
