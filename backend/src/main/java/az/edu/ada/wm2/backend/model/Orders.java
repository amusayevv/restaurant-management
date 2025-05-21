package az.edu.ada.wm2.backend.model;

import az.edu.ada.wm2.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int tableId;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime localDateTime;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        this.localDateTime = LocalDateTime.now();
    }

    public Orders(int tableId, List<OrderItem> orderItems) {
        this.tableId = tableId;
        this.orderItems = orderItems;
        this.calculateTotalPrice();
    }
    public Orders(int tableId, List<OrderItem> orderItems, OrderStatus orderStatus, double totalPrice, LocalDateTime localDateTime) {
        this.tableId = tableId;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.localDateTime = localDateTime;
    }

    public void calculateTotalPrice() {
        double rawTotal = orderItems.stream()
                .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
                .sum();

        this.totalPrice = Math.round(rawTotal * 100.0) / 100.0;
    }

}
