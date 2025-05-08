package DAO;

import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class BaseDao<T, ID> {
    protected final EntityManager entityManager;
    private final Class<T> entityClass;

    public BaseDao(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        entityManager.persist(entity); // no transaction begin/commit
    }

    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public void update(T entity) {
        entityManager.merge(entity); // no transaction begin/commit
    }

    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}