package DAO;

import entities.ServiceRecord;
import jakarta.persistence.EntityManager;

public class ServiceRecordDao extends BaseDao<ServiceRecord, Integer> {
    public ServiceRecordDao(EntityManager entityManager) {
        super(entityManager, ServiceRecord.class);
    }
}


