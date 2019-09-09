package com.springboottest.carrental.service;

import com.springboottest.carrental.dao.CarRepository;
import com.springboottest.carrental.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{
    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
