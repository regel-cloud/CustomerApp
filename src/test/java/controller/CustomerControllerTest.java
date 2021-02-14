package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.JsonPathRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import regel.controller.CustomerController;
import regel.models.Address;
import regel.models.Customer;
import regel.service.CustomerService;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CustomerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerService userService;

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
}
