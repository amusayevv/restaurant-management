package az.edu.ada.wm2.backend.service;

import az.edu.ada.wm2.backend.DTO.OrderRequestDTO;
import az.edu.ada.wm2.backend.DTO.OrderStatusDTO;
import az.edu.ada.wm2.backend.config.RabbitMQConfig;
import az.edu.ada.wm2.backend.messaging.OrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderMessageProducerService {
    private final RabbitTemplate rabbitTemplate;

    public void sendOrderForProcessing(String orderId, OrderRequestDTO orderRequest) {
        OrderMessage orderMessage = new OrderMessage(
                orderId,
                orderRequest.getTableId(),
                orderRequest.getOrderItems(),
                null,
                LocalDateTime.now()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_ROUTING_KEY,
                orderMessage
        );
    }

    public void sendOrderStatusUpdate(String orderId, OrderStatusDTO statusUpdate) {
        OrderMessage statusMessage = new OrderMessage();
        statusMessage.setOrderId(orderId);
        statusMessage.setStatus(statusUpdate.getOrderStatus());
        statusMessage.setOrderTime(LocalDateTime.now());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_STATUS_ROUTING_KEY,
                statusMessage
        );
    }
}
