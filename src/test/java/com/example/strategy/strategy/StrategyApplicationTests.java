package com.example.strategy.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.example.strategy.strategy.Discounter.*;
import static org.assertj.core.api.Assertions.assertThat;

class StrategyApplicationTests {
    @Test
    public void shouldDivideByTwo_WhenApplyingStaffDiscounter() {
        Discounter staffDiscounter = new EasterDiscounter();

        final BigDecimal discountedValue = staffDiscounter
                .apply(BigDecimal.valueOf(100));

        assertThat(discountedValue)
                .isEqualByComparingTo(BigDecimal.valueOf(50));
    }

    @Test
    public void shouldDivideByTwo_WhenApplyingStaffDiscounterWithAnonyousTypes() {
        Discounter staffDiscounter = new Discounter() {
            @Override
            public BigDecimal apply(BigDecimal amount) {
                return amount.multiply(BigDecimal.valueOf(0.5));
            }
        };

        final BigDecimal discountedValue = staffDiscounter
                .apply(BigDecimal.valueOf(100));

        assertThat(discountedValue)
                .isEqualByComparingTo(BigDecimal.valueOf(50));
    }

    @Test
    public void shouldDivideByTwo_WhenApplyingStaffDiscounterWithLamda() {
        Discounter staffDiscounter = amount -> amount.multiply(BigDecimal.valueOf(0.5));

        final BigDecimal discountedValue = staffDiscounter
                .apply(BigDecimal.valueOf(100));

        assertThat(discountedValue)
                .isEqualByComparingTo(BigDecimal.valueOf(50));
    }

    @Test
    public void shouldApplyAllDiscounts() {
        List<Discounter> discounters = Arrays.asList(christmasDiscounter(), newYearDiscounter(), easterDiscounter());

        BigDecimal amount = BigDecimal.valueOf(100);

        final Discounter combinedDiscounter = discounters
                .stream()
                .reduce(v -> v, Discounter::combine);

        combinedDiscounter.apply(amount);
    }

    @Test
    public void shouldChainDiscounters() {
        final Function<BigDecimal, BigDecimal> combinedDiscounters = Discounter
                .christmasDiscounter()
                .andThen(newYearDiscounter());

        BigDecimal apply = combinedDiscounters.apply(BigDecimal.valueOf(100));
    }

}
