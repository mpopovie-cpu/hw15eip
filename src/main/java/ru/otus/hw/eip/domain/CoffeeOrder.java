package ru.otus.hw.eip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeOrder {
    private String orderId;
    private String customerName;
    private String coffeeType; // Espresso, Latte, Cappuccino
    private String size;       // Small, Medium, Large
    private boolean isPremium;
    private String status;
}
