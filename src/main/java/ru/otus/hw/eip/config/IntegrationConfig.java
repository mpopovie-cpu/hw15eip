package ru.otus.hw.eip.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import ru.otus.hw.eip.domain.CoffeeOrder;
import ru.otus.hw.eip.service.OrderProcessingService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final OrderProcessingService processingService;

    @Bean
    public MessageChannel orderInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow coffeeOrderFlow() {
        return IntegrationFlow.from("orderInputChannel")
                // 1. Filter: Drop orders with empty coffee type
                .filter(CoffeeOrder.class, order -> {
                    boolean isValid = order.getCoffeeType() != null && !order.getCoffeeType().isBlank();
                    if (!isValid) log.warn("Order {} filtered out: missing coffee type", order.getOrderId());
                    return isValid;
                })
                // 2. Transformer: Normalize coffee type to uppercase
                .<CoffeeOrder, CoffeeOrder>transform(order -> {
                    log.info("Transforming order {}", order.getOrderId());
                    order.setCoffeeType(order.getCoffeeType().toUpperCase());
                    return order;
                })
                // 3. Router: Route based on premium status
                .<CoffeeOrder, Boolean>route(CoffeeOrder::isPremium, mapping -> mapping
                        .subFlowMapping(true, sf -> sf.handle(order -> {
                            processingService.handlePremiumOrder((CoffeeOrder) order.getPayload());
                        }))
                        .subFlowMapping(false, sf -> sf.handle(order -> {
                            processingService.handleRegularOrder((CoffeeOrder) order.getPayload());
                        }))
                )
                .get();
    }
}
