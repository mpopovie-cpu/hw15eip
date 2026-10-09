package ru.otus.hw.eip.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.eip.domain.CoffeeOrder;

@MessagingGateway
public interface OrderGateway {
    @Gateway(requestChannel = "orderInputChannel")
    void processOrder(CoffeeOrder order);
}
