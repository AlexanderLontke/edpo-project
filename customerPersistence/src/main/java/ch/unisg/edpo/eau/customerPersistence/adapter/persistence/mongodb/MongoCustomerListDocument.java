package ch.unisg.edpo.eau.customerPersistence.adapter.persistence.mongodb;

import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Value
@Document(collation = "customer_lists")
public class MongoCustomerListDocument {
    @Id
    String customerListID;

    List<MongoCustomerDocument> listOfCustomers;
}
