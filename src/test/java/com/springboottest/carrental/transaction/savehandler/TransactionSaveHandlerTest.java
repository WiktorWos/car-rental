package com.springboottest.carrental.transaction.savehandler;

        import com.springboottest.carrental.car.entity.Car;
        import com.springboottest.carrental.car.service.CarService;
        import com.springboottest.carrental.customer.entity.Customer;
        import com.springboottest.carrental.customer.jackson.CustomerJsonToPojo;
        import com.springboottest.carrental.customer.service.CustomerService;
        import com.springboottest.carrental.transaction.entity.Transaction;
        import com.springboottest.carrental.transaction.price.PriceBase;
        import com.springboottest.carrental.transaction.service.TransactionService;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.Mockito;
        import org.mockito.junit.MockitoJUnitRunner;

        import java.time.LocalDateTime;

        import static org.assertj.core.api.Assertions.assertThat;
        import static org.mockito.BDDMockito.given;
        import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class TransactionSaveHandlerTest {
    @Mock
    private TransactionService transactionService;
    @Mock
    private CarService carService;
    @Mock
    private CustomerJsonToPojo customerJsonToPojo;
    @Mock
    private CustomerService customerService;
    @Mock
    private PriceBase priceBase;
    @InjectMocks
    private TransactionSaveHandler transactionSaveHandler;



    @Test
    public void getTransactionWithCar() {
        Transaction transaction = new Transaction();
        Car car = new Car();
        car.setId(1L);
        transaction.setCar(car);
        TransactionSaveHandler transactionSaveHandlerSpy = Mockito.spy(transactionSaveHandler);

        given(transactionSaveHandlerSpy.getNewCarId(transaction)).willReturn(1L);
        Car carWithProperties = new Car("car", 1000,2018,false);
        carWithProperties.setId(1L);
        given(transactionSaveHandlerSpy.getNewCar(1L)).
                willReturn(carWithProperties);

        Transaction transactionWithCar = transactionSaveHandler.getTransactionWithCar(transaction);

        assertThat(transactionWithCar.getCar()).isEqualTo(carWithProperties);

    }

    @Test
    public void getTransactionIfIsOnUpdate_isNotOnUpdate() {
        Transaction transaction = new Transaction();
        Car car = new Car("car", 1000,2018,false);
        car.setId(1L);
        transaction.setCar(car);
        TransactionSaveHandler transactionSaveHandlerSpy = Mockito.spy(transactionSaveHandler);

        given(transactionSaveHandlerSpy.isTransactionOnUpdate(transaction)).willReturn(false);

        Transaction newTransaction = transactionSaveHandler.getTransactionIfIsOnUpdate(transaction);

        assertThat(newTransaction).isEqualTo(transaction);
    }

    @Test
    public void getTransactionIfIsOnUpdate_isOnUpdate() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        Car car = new Car("car", 1000,2018,false);
        car.setId(1L);
        transaction.setCar(car);
        TransactionSaveHandler transactionSaveHandlerSpy = Mockito.spy(transactionSaveHandler);

        Customer customer = new Customer("Jan", "Nowak", 2, "n@n.pl","53td/42/4222");
        Transaction updatedTransaction = transaction;
        updatedTransaction.setCustomer(customer);
        updatedTransaction.setPrice(10);
        updatedTransaction.setStartMileage(1000);
        updatedTransaction.setStartDate(LocalDateTime.now());

        given(transactionSaveHandlerSpy.isTransactionOnUpdate(transaction)).willReturn(true);
        given(transactionSaveHandlerSpy.getUpdatedTransaction(transaction)).willReturn(updatedTransaction);

        Transaction returnedTransaction = transactionSaveHandler.getTransactionIfIsOnUpdate(transaction);

        assertThat(returnedTransaction).isEqualTo(updatedTransaction);
    }





























}