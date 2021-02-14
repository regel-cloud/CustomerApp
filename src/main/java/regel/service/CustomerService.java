package regel.service;

import regel.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> showCustomerList();

    Customer showCustomerById(long id);

    void saveCustomer(Customer client);

    void updateCustomer(Customer clientForUpdating, long id);

    void deleteCustomer(long id);

    List<Customer> findByNameAndLastName(String firstName, String lastName);

}
