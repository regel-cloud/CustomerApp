package regel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import regel.dto.CustomerDTO;
import regel.models.Address;
import regel.models.Customer;
import regel.service.AddressService;
import regel.service.CustomerService;

import javax.validation.Valid;
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

    private final CustomerService customerService;

    private final AddressService addressService;

    @Autowired
    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(CUSTOMERS, customerService.showCustomerList());
        System.out.println(customerService.showCustomerList());
        return CUSTOMERS_INDEX;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id,
                       Model model) {
        Customer customer = customerService.showCustomerById(id);
        model.addAttribute(CUSTOMER, customer);
        model.addAttribute("actualAddress", customer.getActualAddress());
        model.addAttribute("registeredAddress", customer.getRegisteredAddress());
        return CUSTOMERS_CUSTOMER;
    }

    @GetMapping("/new")
    public String newCustomer(Model model) {
        Customer customer = new Customer();
        Address address = new Address();
        customer.setActualAddress(address);
        model.addAttribute(CUSTOMER, customer);
        return NEW_VIEW;
    }

    @GetMapping("/search")
    public String findClientByNameAndLastName(Model model) {
        Customer customer = new Customer();
        model.addAttribute(CUSTOMER, customer);
        return "customers/search";
    }

    @GetMapping("/search/results")
    public String showClientsByNameAndLastName(Model model, @ModelAttribute("customer") CustomerDTO customerDTO,
                                               BindingResult result) {
        Customer customer = mapDTOCustomerToPersistent(customerDTO);
        model.addAttribute(CUSTOMERS, customerService.findByNameAndLastName(customer.getFirstName(), customer.getLastName()));
        return CUSTOMERS_INDEX;
    }

    @PostMapping()
    public String createCustomer(
            @ModelAttribute(CUSTOMER) CustomerDTO customerDTO, BindingResult result) {
        Customer customer = mapDTOCustomerToPersistent(customerDTO);
        customer.getActualAddress().setCreated(LocalDateTime.now());
        customer.getRegisteredAddress().setCreated(LocalDateTime.now());
        addressService.saveAddress(customer.getActualAddress());
        addressService.saveAddress(customer.getRegisteredAddress());
        customerService.saveCustomer(customer);
        return REDIRECT_CLIENTS;
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") long id) {
        model.addAttribute(CUSTOMER, customerService.showCustomerById(id));
        return CUSTOMERS_EDIT;
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute(CUSTOMER) CustomerDTO customerDTO,
                         BindingResult result,
                         @PathVariable("id") long id) {
        Customer customer = mapDTOCustomerToPersistent(customerDTO);
        customer.getActualAddress().setModified(LocalDateTime.now());
        addressService.saveAddress(customer.getActualAddress());
        customerService.updateCustomer(customer, id);
        return REDIRECT_CLIENTS;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        customerService.deleteCustomer(id);
        return REDIRECT_CLIENTS;
    }

    private Customer mapDTOCustomerToPersistent(CustomerDTO customerDTO) {
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
