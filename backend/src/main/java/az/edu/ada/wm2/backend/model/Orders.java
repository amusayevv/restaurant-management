package az.edu.ada.wm2.backend.model;

import az.edu.ada.wm2.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime localDateTime;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        this.localDateTime = LocalDateTime.now();
    }

    public Orders(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
