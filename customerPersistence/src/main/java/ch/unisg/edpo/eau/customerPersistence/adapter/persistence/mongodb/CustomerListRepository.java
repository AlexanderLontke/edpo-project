package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerListRepository extends MongoRepository<MongoCustomerListDocument, String> {
    MongoCustomerListDocument findByCustomerListID(String id);
}
