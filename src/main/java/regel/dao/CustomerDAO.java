package regel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import regel.models.Customer;

import java.util.List;


public interface CustomerDAO extends JpaRepository<Customer, Long> {
    List<Customer> findAllByFirstNameAndLastName(String firstName, String lastName);
}
