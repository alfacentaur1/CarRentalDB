package DAO;

import entities.Car;
import jakarta.persistence.EntityManager;

public class CarDao extends BaseDao<Car, Long> {
    public CarDao(EntityManager entityManager) {
        super(entityManager, Car.class);
    }

}
