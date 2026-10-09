package ru.otus.hw.eip;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.eip.domain.CoffeeOrder;
import ru.otus.hw.eip.gateway.OrderGateway;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EipIntegrationTest {

    @Autowired
    private OrderGateway orderGateway;

    @Test
    void testPremiumOrderFlow() {
        CoffeeOrder order = CoffeeOrder.builder()
                .orderId("ORD-1")
                .customerName("Alice")
                .coffeeType("Latte")
                .isPremium(true)
                .build();

        orderGateway.processOrder(order);
        assertEquals("COMPLETED_PREMIUM", order.getStatus());
    }

    @Test
    void testRegularOrderFlow() {
        CoffeeOrder order = CoffeeOrder.builder()
                .orderId("ORD-2")
                .customerName("Bob")
                .coffeeType("Espresso")
                .isPremium(false)
                .build();

        orderGateway.processOrder(order);
        assertEquals("COMPLETED_REGULAR", order.getStatus());
    }

    @Test
    void testFilteredOrder() {
        CoffeeOrder order = CoffeeOrder.builder()
                .orderId("ORD-3")
                .customerName("Charlie")
                .coffeeType("") // Invalid
                .isPremium(false)
                .build();

        orderGateway.processOrder(order);
        assertNull(order.getStatus(), "Order should have been filtered out and not processed");
    }
}
