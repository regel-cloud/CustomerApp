package controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import regel.controller.CustomerController;
import regel.dto.CustomerDTO;
import regel.models.Address;
import regel.models.Customer;
import regel.service.AddressService;
import regel.service.CustomerNotFoundException;
import regel.service.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CustomerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerService userService;
    @Mock
    private AddressService addressService;

    @InjectMocks
    private CustomerController userController;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void test_get_all_success() throws Exception {
        List<Customer> users = Arrays.asList(
                new Customer(),
                new Customer());
        when(userService.showCustomerList()).thenReturn(users);
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());
        verify(userService, times(1)).showCustomerList();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_get_by_id_success() throws Exception {
        Customer user = new Customer(1L, "name", "lastname", "middlename", "sex", new Address(), new Address());
        when(userService.showCustomerById(1L)).thenReturn(user);
        mockMvc.perform(get("/customers/{id}", 1))
                .andExpect(status().isOk());
        verify(userService, times(1)).showCustomerById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(userService.showCustomerById(1)).thenThrow(new CustomerNotFoundException());
        mockMvc.perform(get("/customers/{id}", 1))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).showCustomerById(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_create_user_success() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO(1L, "name", "lastname", "middlename", "sex", new Address(), new Address());
        mockMvc.perform(post("/customers").flashAttr("customer", customerDTO))
                .andExpect(model().attributeExists("customer"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void test_get_by_name_and_success() throws Exception {
        Customer user = new Customer(1L, "name", "lastname", "middlename", "sex", new Address(), new Address());
        Customer nextUser = new Customer(2L, "name", "lastname", "middlename", "sex", new Address(), new Address());
        CustomerDTO customerDTO = new CustomerDTO(1L, "name", "lastname", "middlename", "sex", new Address(), new Address());

        when(userService.findByNameAndLastName("name", "lastname")).thenReturn(Arrays.asList(user, nextUser));
        mockMvc.perform(get("/customers/search/results").flashAttr("customer", customerDTO))
                .andExpect(status().isOk());
        verify(userService, times(1)).findByNameAndLastName("name", "lastname");
        verifyNoMoreInteractions(userService);
    }


}
