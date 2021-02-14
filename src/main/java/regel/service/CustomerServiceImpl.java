package regel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import regel.dao.CustomerDAO;
import regel.models.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO repository;

    @Autowired
    public CustomerServiceImpl(CustomerDAO repository) {
        this.repository = repository;
    }

    public List<Customer> showCustomerList() {
        return repository.findAll();
    }

    public Customer showCustomerById(long id) {
       return repository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public List<Customer> findByNameAndLastName(String firstName, String lastName) {
        List<Customer> customers = repository.findAllByFirstNameAndLastName(firstName, lastName);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customers;
    }

    public void saveCustomer(Customer client) {
        repository.save(client);
    }


    public void updateCustomer(Customer updatedCustomer, long id) {
        Customer customerForUpdate = repository.findById(id).orElseThrow();
        customerForUpdate.setFirstName(updatedCustomer.getFirstName());
        customerForUpdate.setLastName(updatedCustomer.getLastName());
        customerForUpdate.setMiddleName(updatedCustomer.getMiddleName());
        customerForUpdate.setSex(updatedCustomer.getSex());
        customerForUpdate.setActualAddress(updatedCustomer.getActualAddress());
        customerForUpdate.setRegisteredAddress(updatedCustomer.getRegisteredAddress());
        repository.save(customerForUpdate);
    }

    public void deleteCustomer(long id) {
        repository.deleteById(id);
    }

}
