package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.enums.UserRole;
import az.edu.ada.wm2.backend.model.User;
import az.edu.ada.wm2.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private User secondUser;
    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        // Set up test users
        testUser = new User();
        testUser.setId("user1");
        testUser.setUsername("john.doe");
        testUser.setPassword("password123");
        testUser.setRole(UserRole.WAITER);

        secondUser = new User();
        secondUser.setId("user2");
        secondUser.setUsername("jane.smith");
        secondUser.setPassword("password456");
        secondUser.setRole(UserRole.MANAGER);

        testUsers = Arrays.asList(testUser, secondUser);
    }

    @Test
    void saveUser_ShouldCallUserServiceSaveUser() {
        // Arrange
        doNothing().when(userService).saveUser(any(User.class));

        // Act
        userController.saveUser(testUser);

        // Assert
        verify(userService).saveUser(testUser);
    }

    @Test
    void getUsers_ShouldReturnAllUsers() {
        // Arrange
        when(userService.getUsers()).thenReturn(testUsers);

        // Act
        List<User> result = userController.getUsers();

        // Assert
        assertEquals(testUsers, result);
        assertEquals(2, result.size());
        verify(userService).getUsers();
    }

    @Test
    void deleteUser_ShouldCallUserServiceDeleteUser() {
        // Arrange
        String userId = "user1";
        doNothing().when(userService).deleteUser(anyString());

        // Act
        userController.deleteUser(userId);

        // Assert
        verify(userService).deleteUser(userId);
    }
}