package az.edu.ada.wm2.backend.service;

import az.edu.ada.wm2.backend.enums.UserRole;
import az.edu.ada.wm2.backend.model.User;
import az.edu.ada.wm2.backend.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId("1");
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setRole(UserRole.WAITER);
    }

    @Test
    void saveUser_ShouldEncodePasswordAndSaveUser() {
        // Arrange
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        userService.saveUser(testUser);

        // Assert
        verify(passwordEncoder).encode("password");
        verify(userRepo).save(any(User.class));
        assertEquals("encodedPassword", testUser.getPassword());
    }

    @Test
    void deleteUser_ShouldDeleteUserById() {
        // Arrange
        String userId = "1";
        doNothing().when(userRepo).deleteById(anyString());

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepo).deleteById(userId);
    }
}
