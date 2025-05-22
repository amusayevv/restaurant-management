package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.enums.MenuCategories;
import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.service.MenuService;
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
public class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    private MenuItem testMenuItem;
    private MenuItem secondMenuItem;
    private List<MenuItem> testMenuItems;

    @BeforeEach
    void setUp() {
        testMenuItem = new MenuItem();
        testMenuItem.setId("menu1");
        testMenuItem.setName("Pizza Margherita");
        testMenuItem.setDescription("Classic pizza with tomato sauce and mozzarella");
        testMenuItem.setPrice(12.99);
        testMenuItem.setCategory(MenuCategories.MAIN_COURSES);

        secondMenuItem = new MenuItem();
        secondMenuItem.setId("menu2");
        secondMenuItem.setName("Tiramisu");
        secondMenuItem.setDescription("Classic Italian dessert");
        secondMenuItem.setPrice(8.99);
        secondMenuItem.setCategory(MenuCategories.DESSERTS);

        testMenuItems = Arrays.asList(testMenuItem, secondMenuItem);
    }

    @Test
    void showMenu_ShouldReturnAllMenuItems() {
        // Arrange
        when(menuService.showMenu()).thenReturn(testMenuItems);

        // Act
        List<MenuItem> result = menuController.showMenu();

        // Assert
        assertEquals(testMenuItems, result);
        assertEquals(2, result.size());
        verify(menuService).showMenu();
    }

    @Test
    void addMenuItem_ShouldCallMenuServiceAddMenuItem() {
        // Arrange
        doNothing().when(menuService).addMenuItem(any(MenuItem.class));

        // Act
        menuController.addMenuItem(testMenuItem);

        // Assert
        verify(menuService).addMenuItem(testMenuItem);
    }

    @Test
    void deleteMenuItem_ShouldCallMenuServiceDeleteMenuItem() {
        // Arrange
        String menuItemId = "menu1";
        doNothing().when(menuService).deleteMenuItem(anyString());

        // Act
        menuController.deleteMenuItem(menuItemId);

        // Assert
        verify(menuService).deleteMenuItem(menuItemId);
    }

    @Test
    void updateMenuItem_ShouldCallMenuServiceUpdateMenuItem() {
        // Arrange
        String menuItemId = "menu1";
        MenuItem updatedMenuItem = new MenuItem();
        updatedMenuItem.setName("Updated Pizza");
        updatedMenuItem.setDescription("Updated description");
        updatedMenuItem.setPrice(14.99);
        updatedMenuItem.setCategory(MenuCategories.MAIN_COURSES);

        doNothing().when(menuService).updateMenuItem(anyString(), any(MenuItem.class));

        // Act
        menuController.updateMenuItem(menuItemId, updatedMenuItem);

        // Assert
        verify(menuService).updateMenuItem(menuItemId, updatedMenuItem);
    }
}