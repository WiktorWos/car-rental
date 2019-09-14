package com.springboottest.carrental.car.service;

import com.springboottest.carrental.car.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> findAll();
    void save(Car car);
    void deleteById(Long id);
    Car getById(Long id);
}
