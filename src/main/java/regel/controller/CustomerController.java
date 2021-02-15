package regel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import regel.dto.CustomerDTO;
import regel.models.Address;
import regel.models.Customer;
import regel.service.CustomerService;

import java.time.LocalDateTime;


@Controller
@RequestMapping("/customers")
public class CustomerController {
    private static final String REDIRECT_CLIENTS = "redirect:/customers";
    private static final String CUSTOMERS = "customers";
    private static final String CUSTOMERS_CUSTOMER = "customers/customer";
    private static final String CUSTOMER = "customer";
    private static final String NEW_VIEW = "customers/new";
    private static final String CUSTOMERS_INDEX = "customers/index";
    private static final String CUSTOMERS_EDIT = "customers/edit";
    private static final String CUSTOMERS_SEARCH = "customers/search";
    private static final String ACTUAL_ADDRESS = "actualAddress";
    private static final String REGISTERED_ADDRESS = "registeredAddress";

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Returns view with list of all customers
    @GetMapping()
    public String showAllCustomers(Model model) {
        model.addAttribute(CUSTOMERS, customerService.showCustomerList());
        return CUSTOMERS_INDEX;
    }

    //Returns view with customer
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id,
                       Model model) {
        Customer customer = customerService.showCustomerById(id);
        model.addAttribute(CUSTOMER, customer);
        model.addAttribute(ACTUAL_ADDRESS, customer.getActualAddress());
        model.addAttribute(REGISTERED_ADDRESS, customer.getRegisteredAddress());
        return CUSTOMERS_CUSTOMER;
    }

    //Returns page with customer form
    @GetMapping("/new")
    public String newCustomer(Model model) {
        Customer customer = new Customer();
        Address actualAddress = new Address();
        Address registeredAddress = new Address();
        customer.setActualAddress(actualAddress);
        customer.setRegisteredAddress(registeredAddress);
        model.addAttribute(CUSTOMER, customer);
        return NEW_VIEW;
    }

    //Returns page with customer search form
    @GetMapping("/search")
    public String findClientByNameAndLastName(Model model) {
        Customer customer = new Customer();
        model.addAttribute(CUSTOMER, customer);
        return CUSTOMERS_SEARCH;
    }

    //Returns page with customer search results
    @GetMapping("/search/results")
    public String showClientsByNameAndLastName(Model model, @ModelAttribute(CUSTOMER) CustomerDTO customerDTO) {
        Customer customer = mapDTOCustomerToPersistent(customerDTO);
        model.addAttribute(CUSTOMERS, customerService.findByNameAndLastName(customer.getFirstName(), customer.getLastName()));
        return CUSTOMERS_INDEX;
    }

    //Creates customer from form
    @PostMapping()
    public String createCustomer(
            @ModelAttribute(CUSTOMER) CustomerDTO customerDTO) {
        customerDTO.getActualAddress().setCreated(LocalDateTime.now());
        customerDTO.getRegisteredAddress().setCreated(LocalDateTime.now());
        Customer customer = mapDTOCustomerToPersistent(customerDTO);
        customerService.saveCustomer(customer);
        return REDIRECT_CLIENTS;
    }


    //Returns page with customer edit form
    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") long id) {
        model.addAttribute(CUSTOMER, customerService.showCustomerById(id));
        return CUSTOMERS_EDIT;
    }

    //Updates customer from edit form(assuming that registered address stays the same)
    @PutMapping("/{id}")
    public String update(@ModelAttribute(CUSTOMER) CustomerDTO customerDTO, @PathVariable("id") long id) {
        Customer customer = mapDTOCustomerToPersistent(customerDTO);
        customer.getActualAddress().setModified(LocalDateTime.now());
        customerService.updateCustomer(customer, id);
        return REDIRECT_CLIENTS;
    }


    //Deletes customer
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        customerService.deleteCustomer(id);
        return REDIRECT_CLIENTS;
    }

    public Customer mapDTOCustomerToPersistent(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setMiddleName(customerDTO.getMiddleName());
        customer.setSex(customerDTO.getSex());
        customer.setActualAddress(customerDTO.getActualAddress());
        customer.setRegisteredAddress(customerDTO.getRegisteredAddress());
        return customer;
    }

}
