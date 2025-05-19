package az.edu.ada.wm2.backend.service;

import az.edu.ada.wm2.backend.config.RabbitMQConfig;
import az.edu.ada.wm2.backend.enums.OrderStatus;
import az.edu.ada.wm2.backend.messaging.OrderMessage;
import az.edu.ada.wm2.backend.model.Orders;
import az.edu.ada.wm2.backend.repo.OrdersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderMessageConsumerService {
    private final OrdersRepo ordersRepo;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void processOrder(OrderMessage orderMessage) {
        log.info("Received order for processing: {}", orderMessage);

        try {
            Orders order = ordersRepo.findById(String.valueOf(orderMessage.getOrderId()))
                    .orElseThrow(() -> new RuntimeException("Order not found: " + orderMessage.getOrderId()));

            order.setOrderStatus(OrderStatus.RECEIVED);
            ordersRepo.save(order);

            log.info("Order processed successfully: {}", orderMessage.getOrderId());
        } catch (Exception e) {
            log.error("Error processing order: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_STATUS_QUEUE)
    public void updateOrderStatus(OrderMessage statusMessage) {
        log.info("Received order status update: {} -> {}",
                statusMessage.getOrderId(), statusMessage.getStatus());

        try {
            Orders order = ordersRepo.findById(String.valueOf(statusMessage.getOrderId()))
                    .orElseThrow(() -> new RuntimeException("Order not found: " + statusMessage.getOrderId()));

            order.setOrderStatus(statusMessage.getStatus());
            ordersRepo.save(order);

            // Additional business logic based on the new status
            // For example, if status is READY, you might want to notify the waiter

            log.info("Order status updated successfully: {}", statusMessage.getOrderId());
        } catch (Exception e) {
            log.error("Error updating order status: {}", e.getMessage(), e);
        }

    }
}
