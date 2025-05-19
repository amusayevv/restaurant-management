package az.edu.ada.wm2.backend.messaging;

import az.edu.ada.wm2.backend.DTO.OrderItemDTO;
import az.edu.ada.wm2.backend.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {
    private String orderId;
    private Integer tableId;
    private List<OrderItemDTO> orderItems;
    private OrderStatus status;
    private LocalDateTime orderTime;
}
