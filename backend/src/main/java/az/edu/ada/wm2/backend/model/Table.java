package az.edu.ada.wm2.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int tableCode;
}
