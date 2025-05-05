package az.edu.ada.wm2.backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class OrderItem {
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int quantity;

    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;
}
