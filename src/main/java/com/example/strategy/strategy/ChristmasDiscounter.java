package com.example.strategy.strategy;

import java.math.BigDecimal;


public class ChristmasDiscounter implements Discounter {
    @Override
    public BigDecimal apply(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.9));
    }
}
