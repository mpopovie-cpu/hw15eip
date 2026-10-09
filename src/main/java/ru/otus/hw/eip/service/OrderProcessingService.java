package ru.otus.hw.eip.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.eip.domain.CoffeeOrder;

@Slf4j
@Service
public class OrderProcessingService {
    public void handleRegularOrder(CoffeeOrder order) {
        log.info("Processing Regular Order: {} for {}", order.getOrderId(), order.getCustomerName());
        order.setStatus("COMPLETED_REGULAR");
    }

    public void handlePremiumOrder(CoffeeOrder order) {
        log.info("Processing Premium Order: {} for {} with VIP treatment", order.getOrderId(), order.getCustomerName());
        order.setStatus("COMPLETED_PREMIUM");
    }
}
