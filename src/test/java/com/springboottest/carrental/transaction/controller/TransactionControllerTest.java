package com.springboottest.carrental.transaction.controller;

import com.springboottest.carrental.car.entity.Car;
import com.springboottest.carrental.car.service.CarService;
import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.jackson.CustomerJsonToPojo;
import com.springboottest.carrental.customer.service.CustomerService;
import com.springboottest.carrental.initbinder.StringTrimmer;
import com.springboottest.carrental.transaction.entity.Transaction;
import com.springboottest.carrental.transaction.price.PriceBase;
import com.springboottest.carrental.transaction.savehandler.TransactionSaveHandler;
import com.springboottest.carrental.transaction.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;
    @MockBean
    private StringTrimmer stringTrimmer;
    @MockBean
    private CarService carService;
    @MockBean
    private CustomerJsonToPojo customerJsonToPojo;
    @MockBean
    private PriceBase priceBase;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private BindingResult bindingResult;
    @MockBean
    private TransactionSaveHandler transactionSaveHandler;
    @InjectMocks
    TransactionController transactionController;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    public void findAll() throws Exception{
        this.mockMvc.perform(get("/transaction/findAll"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("transactions"))
                .andExpect(view().name("transaction-list"));
    }

    @WithMockUser
    @Test
    public void saveNewTransactionWithNewCustomer() throws Exception{
        Transaction transaction = new Transaction();
        Car carWithOnlyId = new Car();
        carWithOnlyId.setId(1L);
        transaction.setCar(carWithOnlyId);

        this.mockMvc.perform(post("/transaction/save")
                .sessionAttr("transaction", transaction))
                .andExpect(status().is3xxRedirection());


//        Transaction newTransactionWithCar = new Transaction();
//        Car carWithProperties = new Car("Car",1000,2019,false);
//        carWithProperties.setId(1L);
//        newTransactionWithCar.setCar(carWithProperties);
//
//        given(transactionSaveHandler.getTransactionWithCar(transaction)).willReturn(newTransactionWithCar);
//        given(transactionSaveHandler.getTransactionIfIsOnUpdate(newTransactionWithCar)).
//                willReturn(newTransactionWithCar);
//
//        Transaction newTransactionWithSetProperties = newTransactionWithCar;
//        newTransactionWithSetProperties.setStartDate(LocalDateTime.now());
//        newTransactionWithSetProperties.setStartMileage(newTransactionWithCar.getCar().getCarMileage());
//        newTransactionWithSetProperties.getCar().setActive(true);
//
//        given(transactionSaveHandler.getTransactionWithSetProperties(newTransactionWithCar)).
//                willReturn(newTransactionWithSetProperties);
//
//        Customer customer = new Customer("Jan","Nowak",2,"jan@mail.com","15ys/33/5256");
//        Transaction transactionToSave = newTransactionWithSetProperties;
//        transactionToSave.setCustomer(customer);
//        transactionToSave.setPrice(10);
//
//        given(transactionSaveHandler.getTransactionWithSetProperties(newTransactionWithCar)).
//                willReturn(newTransactionWithSetProperties);
//
//
//        then(transactionService).should().save(transactionToSave);
    }
}