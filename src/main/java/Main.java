import DAO.CarDao;
import DAO.CustomerDao;
import DAO.ServiceDao;
import DAO.ServiceRecordDao;
import entities.*;
import jakarta.persistence.*;
import java.time.LocalDate;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        ServiceRecordDao serviceRecordDao = new ServiceRecordDao(em);

        // === INSERT new service record ===
        ServiceRecord newRecord = new ServiceRecord();
        newRecord.setId(401); // set free ID
        newRecord.setDescription("Výmena brzdových destiček");

        // find a car which the service record will belong to
        Car existingCar = em.find(Car.class, 2); // suppose car with id = 1 exists
        if (existingCar != null) {
            newRecord.setCar(existingCar);

            em.getTransaction().begin();
            serviceRecordDao.save(newRecord);
            em.getTransaction().commit();
            System.out.println("New service record inserted for car ID: " + existingCar.getId());
        } else {
            System.out.println("Car not found – cannot insert service record.");
        }

        // === UPDATE service record ===
        ServiceRecord recordToUpdate = serviceRecordDao.findById(401);
        if (recordToUpdate != null) {
            recordToUpdate.setDescription("Kompletní servis podvozku");

            em.getTransaction().begin();
            serviceRecordDao.update(recordToUpdate);
            em.getTransaction().commit();
            System.out.println("Service record updated: " + recordToUpdate.getDescription());
        } else {
            System.out.println("Service record not found for update.");
        }


        // === INSERT new service ===
        ServiceDao serviceDao = new ServiceDao(em);
        Service newService = new Service();
        newService.setId(301);  // set free ID
        newService.setType("Oil Change");
        newService.setPrice(1500);
        newService.setServiceDate(LocalDate.of(2025, 5, 10));

        em.getTransaction().begin();
        serviceDao.save(newService);
        em.getTransaction().commit();
        System.out.println("New service inserted: " + newService.getType());

    // === UPDATE existing service ===
        Service serviceToUpdate = serviceDao.findById(301);
        if (serviceToUpdate != null) {
            serviceToUpdate.setPrice(1800);
            serviceToUpdate.setType("Full Service");

            em.getTransaction().begin();
            serviceDao.update(serviceToUpdate);
            em.getTransaction().commit();
            System.out.println("Service updated: " + serviceToUpdate.getType() + ", price: " + serviceToUpdate.getPrice());
        } else {
            System.out.println("Service not found for update.");
        }



        // === INSERT + UPDATE CUSTOMER
        CustomerDao customerDao = new CustomerDao(em);
        Customer newCustomer = new Customer();
        newCustomer.setId(102); // or free any id
        newCustomer.setName("Petr Novák");
        newCustomer.setStreet("Rovná");
        newCustomer.setCity("Praha");
        newCustomer.setPostalCode("11001");

        em.getTransaction().begin();
        customerDao.save(newCustomer);
        em.getTransaction().commit();
        System.out.println("New customer inserted: " + newCustomer.getName());

        Customer customerToUpdate = customerDao.findById(101);
        if (customerToUpdate != null) {
            customerToUpdate.setCity("Litvínov");
            em.getTransaction().begin();
            customerDao.update(customerToUpdate);
            em.getTransaction().commit();
            System.out.println("Customer updated: " + customerToUpdate.getName() + ", new city: " + customerToUpdate.getCity());
        } else {
            System.out.println("Customer not found");
        }


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
            // Em will be closed after all the transactions
        }

        // Create two EntityManagers for transaction simulation
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

        Car newCar = new Car();
        newCar.setType("Electric Car");
        newCar.setMileage(10000);
        newCar.setBrand("Tesla");  // Set the brand field
        newCar.setPlateNumber("1234567");
        newCar.setIsBorrowed(false);

        em.getTransaction().begin();
        em.persist(newCar);  // Persist the new car
        em.getTransaction().commit();
        System.out.println("New car inserted: " + newCar);

        // Update car mileage
        int newMileage = 1000;
        int carId = 1;
        Car car = em.find(Car.class, carId);
        if (car != null) {
            car.setMileage(newMileage);
            em.getTransaction().begin();
            em.merge(car);  // Using merge for updating an existing car entity
            em.getTransaction().commit();
            System.out.println("Car mileage updated: " + car);
        } else {
            System.out.println("Car not found!");
        }

        // Update car type
        Car carToUpdate = em.find(Car.class, carId);
        if (carToUpdate != null) {
            carToUpdate.setType("Motorcycle");
            em.getTransaction().begin();
            em.merge(carToUpdate);  // Using merge for updating car type
            em.getTransaction().commit();
            System.out.println("Car model updated: " + carToUpdate);
        } else {
            System.out.println("Car not found!");
        }

        // Cleanup
        em.close();
        em1.close();
        em2.close();
        emf.close();
    }
}
