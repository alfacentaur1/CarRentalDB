package DAO;

import entities.Customer;
import jakarta.persistence.EntityManager;

public class CustomerDao extends BaseDao<Customer, Integer> {
    public CustomerDao(EntityManager entityManager) {
        super(entityManager, Customer.class);
    }
}

