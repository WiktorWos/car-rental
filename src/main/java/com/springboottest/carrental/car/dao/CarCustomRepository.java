package com.springboottest.carrental.car.dao;

import com.springboottest.carrental.car.entity.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarCustomRepository {
    @Query("SELECT c FROM Car c WHERE c.active = false ")
    List<Car> findAvailableCars();
}
