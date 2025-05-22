package az.edu.ada.wm2.backend.config;

import az.edu.ada.wm2.backend.enums.UserRole;
import az.edu.ada.wm2.backend.model.User;
import az.edu.ada.wm2.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initializeDefaultUsers(UserRepo userRepo) {
        return args -> {
            createUserIfNotExists(userRepo, "admin", passwordEncoder.encode("admin"), UserRole.MANAGER);
            createUserIfNotExists(userRepo, "kitchen", passwordEncoder.encode("kitchen"), UserRole.KITCHEN);
            createUserIfNotExists(userRepo, "waiter", passwordEncoder.encode("waiter"), UserRole.WAITER);

            System.out.println("Default users initialization completed");
        };
    }

    private void createUserIfNotExists(UserRepo userRepo, String username,
                                       String password, UserRole role) {
        if (userRepo.findByUsername(username) == null) {
            User user = new User(username, password, role);
            userRepo.save(user);
            System.out.println("Created default user: " + username);
        }
    }
}