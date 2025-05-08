import DAO.CarDao;
import entities.Car;
import entities.Rental;
import jakarta.persistence.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        // Query rentals for customer "Petr Svoboda" (id = 1)
        try {
            TypedQuery<Rental> query = em.createQuery(
                    "SELECT r FROM Rental r WHERE r.customer.id = :customerId", Rental.class);
            query.setParameter("customerId", 1L);
            List<Rental> rentals = query.getResultList();

            rentals.forEach(rental ->
                    System.out.println("Rental ID: " + rental.getId() +
                            ", Customer: " + rental.getCustomer().getName()));
        } finally {
            em.close();
        }

        EntityManager em1 = emf.createEntityManager();
        EntityManager em2 = emf.createEntityManager();

        CarDao carDao1 = new CarDao(em1); // No need to pass Car.class
        CarDao carDao2 = new CarDao(em2);

        // Transaction 1 - UPDATE car mileage
        em1.getTransaction().begin();
        Car car1 = carDao1.findById(1L);
        car1.setMileage(car1.getMileage() - 500);
        carDao1.update(car1); // update without internal transaction
        System.out.println("T1: After update (before commit): " + carDao1.findAll());

        // Transaction 2 - READ (simulate dirty read)
        em2.getTransaction().begin();
        Car car2 = carDao2.findById(1L);
        System.out.println("T2: Dirty read: " + car2);
        em2.getTransaction().commit();

        // Commit T1
        em1.getTransaction().commit();

        // Final state
        // car id = 1
        em1.getTransaction().begin();
        System.out.println("Final state: " + carDao1.findAll());
        em1.getTransaction().commit();

        em1.close();
        em2.close();
        emf.close();
    }
}
