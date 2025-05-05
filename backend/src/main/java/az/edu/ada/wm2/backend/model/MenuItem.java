package az.edu.ada.wm2.backend.model;

import az.edu.ada.wm2.backend.enums.MenuCategories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private double price;
    @Enumerated(EnumType.STRING)
    private MenuCategories category;

    public MenuItem(String name, String description, double price, MenuCategories category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
