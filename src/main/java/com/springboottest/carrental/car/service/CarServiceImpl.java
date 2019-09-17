package com.springboottest.carrental.car.service;

import com.springboottest.carrental.car.dao.CarRepository;
import com.springboottest.carrental.car.entity.Car;
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

    @Override
    @Transactional
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Car getById(Long id) {
        return carRepository.getOne(id);
    }

    @Override
    @Transactional
    public List<Car> findAvailableCars() {
        return carRepository.findAvailableCars();
    }
}
