package com.springboottest.carrental.transaction.price;

import org.springframework.stereotype.Component;

@Component
public class PriceBase {
    public double getPriceBase(int experience) {
        return 10 + 10./experience;
    }
}
