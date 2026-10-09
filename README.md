# Spring Integration (EIP) Homework

## Domain: Coffee Order Processing System
This application simulates a coffee order pipeline where orders are validated, transformed, and routed based on customer status.

## Implemented EIP Patterns:
1. **Messaging Gateway**: `OrderGateway` provides a clean entry point to the integration flow.
2. **Message Filter**: Filters out orders that have an empty or null coffee type.
3. **Message Transformer**: Normalizes the coffee type to uppercase for consistency.
4. **Message Router**: Routes messages to different sub-flows based on the `isPremium` boolean flag.
5. **Service Activator**: Final processing is handled by the `OrderProcessingService`.

## Integration Flow:
`OrderGateway` -> `orderInputChannel` -> `Filter` -> `Transformer` -> `Router` -> (`PremiumFlow` / `RegularFlow`) -> `OrderProcessingService`

## How to Run:
1. Navigate to the project directory: `cd /Users/mixa/Desktop/hw15`
2. Run tests: `mvn clean test`
3. Build package: `mvn clean package`
