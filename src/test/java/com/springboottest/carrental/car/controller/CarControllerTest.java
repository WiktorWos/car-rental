package com.springboottest.carrental.car.controller;

import com.springboottest.carrental.car.entity.Car;
import com.springboottest.carrental.car.service.CarService;
import com.springboottest.carrental.initbinder.StringTrimmer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CarControllerTest {
    @MockBean
    CarService carService;

    @MockBean
    StringTrimmer stringTrimmer;

    @Autowired
    MockMvc mockMvc;


    @WithMockUser
    @Test
    public void findAll() throws Exception{
        List<Car> cars = createListOfOneCar();
        when(carService.findAll()).thenReturn(cars);

        List<Car> foundCars = carService.findAll();

        verify(carService).findAll();

        assertThat(foundCars).hasSize(1);

        this.mockMvc.perform(get("/cars/findAll"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cars"))
                .andExpect(view().name("car-list"));
    }

    private List<Car> createListOfOneCar() {
        Car car = new Car();
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        return cars;
    }

    @WithMockUser
    @Test
    public void addCar() throws Exception{
        this.mockMvc.perform(get("/cars/addCar"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("car"))
                .andExpect(view().name("car-form"));

    }

    @Test
    @WithMockUser
    public void saveCarSuccess() throws Exception{
        Car car = new Car("fasfa",12515,2015,false);
        this.mockMvc.perform(post("/cars/save")
                    .param("carName","fafsa")
                    .param("carMileage", "1245")
                    .param("carProduction","2015")
                    .param("isActive","false"))
                .andExpect(status().is3xxRedirection());
    }
}