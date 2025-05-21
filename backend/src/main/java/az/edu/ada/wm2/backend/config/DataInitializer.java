package az.edu.ada.wm2.backend.config;

import az.edu.ada.wm2.backend.enums.UserRole;
import az.edu.ada.wm2.backend.model.User;
import az.edu.ada.wm2.backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private UserRepo userRepo;

    @Autowired
    public DataInitializer(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public CommandLineRunner initializeDefaultUsers(UserRepo userRepo) {
        return args -> {
            createUserIfNotExists(userRepo, "admin", "admin", UserRole.MANAGER);
            createUserIfNotExists(userRepo, "kitchen", "kitchen", UserRole.KITCHEN);
            createUserIfNotExists(userRepo, "waiter", "waiter", UserRole.WAITER);

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