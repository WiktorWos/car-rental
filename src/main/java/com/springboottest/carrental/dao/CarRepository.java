package com.springboottest.carrental.dao;

import com.springboottest.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}
