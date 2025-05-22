package az.edu.ada.wm2.backend.service;

import az.edu.ada.wm2.backend.repo.MenuItemRepo;
import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.enums.MenuCategories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class MenuServiceTest {
    @Mock
    private MenuItemRepo menuItemRepo;

    @InjectMocks
    private MenuService menuService;

    private MenuItem testMenuItem;
    private List<MenuItem> testMenuItems;

    @BeforeEach
    void setUp() {
        testMenuItem = new MenuItem();
        testMenuItem.setId("1");
        testMenuItem.setName("Test Pizza");
        testMenuItem.setDescription("A delicious test pizza");
        testMenuItem.setPrice(12.99);
        testMenuItem.setCategory(MenuCategories.MAIN_COURSES);

        MenuItem secondMenuItem = new MenuItem();
        secondMenuItem.setId("2");
        secondMenuItem.setName("Test Pasta");
        secondMenuItem.setDescription("A tasty test pasta");
        secondMenuItem.setPrice(9.99);
        secondMenuItem.setCategory(MenuCategories.MAIN_COURSES);
        testMenuItems = Arrays.asList(testMenuItem, secondMenuItem);
    }


    @Test
    void showMenu_ShouldReturnAllMenuItems() {
        // Arrange
        when(menuItemRepo.findAll()).thenReturn(testMenuItems);

        // Act
        List<MenuItem> result = menuService.showMenu();

        // Assert
        assertEquals(testMenuItems, result);
        verify(menuItemRepo).findAll();
    }

    @Test
    void addMenuItem_ShouldSaveMenuItem() {
        // Arrange
        when(menuItemRepo.save(any(MenuItem.class))).thenReturn(testMenuItem);

        // Act
        menuService.addMenuItem(testMenuItem);

        // Assert
        verify(menuItemRepo).save(testMenuItem);
    }

    @Test
    void deleteMenuItem_ShouldDeleteMenuItemById() {
        // Arrange
        String menuItemId = "1";
        doNothing().when(menuItemRepo).deleteById(anyString());

        // Act
        menuService.deleteMenuItem(menuItemId);

        // Assert
        verify(menuItemRepo).deleteById(menuItemId);
    }

    @Test
    void updateMenuItem_ShouldUpdateExistingMenuItem() {
        // Arrange
        String menuItemId = "1";
        MenuItem updatedMenuItem = new MenuItem();
        updatedMenuItem.setName("Updated Pizza");
        updatedMenuItem.setDescription("An updated test pizza");
        updatedMenuItem.setPrice(14.99);
        updatedMenuItem.setCategory(MenuCategories.MAIN_COURSES);

        when(menuItemRepo.save(any(MenuItem.class))).thenReturn(updatedMenuItem);

        // Act
        menuService.updateMenuItem(menuItemId, updatedMenuItem);

        // Assert
        assertEquals(menuItemId, updatedMenuItem.getId());
        verify(menuItemRepo).save(updatedMenuItem);
    }

    @Test
    void getMenuItemById_ShouldReturnMenuItemWhenExists() {
        // Arrange
        String menuItemId = "1";
        when(menuItemRepo.findById(menuItemId)).thenReturn(Optional.of(testMenuItem));

        // Act
        MenuItem result = menuService.getMenuItemById(menuItemId);

        // Assert
        assertEquals(testMenuItem, result);
        verify(menuItemRepo).findById(menuItemId);
    }

    @Test
    void getMenuItemById_ShouldThrowExceptionWhenNotFound() {
        // Arrange
        String menuItemId = "999";
        when(menuItemRepo.findById(menuItemId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuService.getMenuItemById(menuItemId);
        });

        // Verify the exception message
        assertEquals("MenuItem not found with id: " + menuItemId, exception.getMessage());
        verify(menuItemRepo).findById(menuItemId);
    }

}
