package DAO;

import entities.Service;
import jakarta.persistence.EntityManager;

public class ServiceDao extends BaseDao<Service, Integer> {
    public ServiceDao(EntityManager entityManager) {
        super(entityManager, Service.class);
    }
}

