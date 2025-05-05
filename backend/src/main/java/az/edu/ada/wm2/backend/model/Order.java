// package az.edu.ada.wm2.backend.model;

// import jakarta.persistence.*;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Data
// @NoArgsConstructor
// public class Order {
//     @Id
//     @GeneratedValue(strategy = GenerationType.UUID)
//     private String id;

//     @OneToMany
//     @JoinColumn(name = "order_item_id")
//     private OrderItem orderItem;
// }
