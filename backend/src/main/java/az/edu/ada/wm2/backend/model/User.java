package az.edu.ada.wm2.backend.model;

import az.edu.ada.wm2.backend.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.WAITER;
    }

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
