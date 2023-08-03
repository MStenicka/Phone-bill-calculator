package com.phonecompany.billing;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelephoneBillCalculatorTest {
    @Test
    public void testCalculateTotalCost() {
        String phoneLog = "420774577451,13-01-2020 18:10:15,13-01-2020 18:17:57\n"
                + "420774577452,18-01-2020 08:59:20,18-01-2020 09:10:00\n"
                + "420774577452,19-01-2020 07:58:20,19-01-2020 08:01:00\n"
                + "420774577454,19-01-2020 07:59:20,19-01-2020 08:10:00";

        TelephoneBillCalculator calculator = new TelephoneBillCalculatorImpl();
        BigDecimal totalCost = calculator.calculate(phoneLog);

        BigDecimal expectedTotalCost = new BigDecimal("8.80");

        assertEquals(expectedTotalCost, totalCost);
    }
}
