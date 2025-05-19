package az.edu.ada.wm2.backend.DTO;

import az.edu.ada.wm2.backend.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
}
