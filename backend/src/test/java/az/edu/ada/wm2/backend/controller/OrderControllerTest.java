package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.DTO.OrderItemDTO;
import az.edu.ada.wm2.backend.DTO.OrderRequestDTO;
import az.edu.ada.wm2.backend.DTO.OrderStatusDTO;
import az.edu.ada.wm2.backend.enums.MenuCategories;
import az.edu.ada.wm2.backend.enums.OrderStatus;
import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.model.OrderItem;
import az.edu.ada.wm2.backend.model.Orders;
import az.edu.ada.wm2.backend.service.MenuService;
import az.edu.ada.wm2.backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderControllerTest {

    @Mock
    private MenuService menuService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MenuItem testMenuItem;
    private OrderItem testOrderItem;
    private Orders testOrder;
    private OrderRequestDTO testOrderRequestDTO;
    private OrderItemDTO testOrderItemDTO;
    private OrderStatusDTO testOrderStatusDTO;
    private List<Orders> testOrders;

    @BeforeEach
    void setUp() {
        // Set up test menu item
        testMenuItem = new MenuItem();
        testMenuItem.setId("menu1");
        testMenuItem.setName("Pizza Margherita");
        testMenuItem.setDescription("Classic pizza with tomato sauce and mozzarella");
        testMenuItem.setPrice(12.99);
        testMenuItem.setCategory(MenuCategories.MAIN_COURSES);

        // Set up test order item
        testOrderItem = new OrderItem();
        testOrderItem.setId("orderItem1");
        testOrderItem.setMenuItem(testMenuItem);
        testOrderItem.setQuantity(2);

        // Set up test order
        testOrder = new Orders();
        testOrder.setId("order1");
        testOrder.setTableId(3);
        testOrder.setOrderStatus(OrderStatus.IN_PREPARATION);
        testOrder.setOrderItems(Arrays.asList(testOrderItem));
        testOrder.setTotalPrice(25.98); // 2 * 12.99
        testOrder.setLocalDateTime(LocalDateTime.now());

        // Set up a second order for list tests
        Orders secondOrder = new Orders();
        secondOrder.setId("order2");
        secondOrder.setTableId(5);
        secondOrder.setOrderStatus(OrderStatus.PENDING);
        secondOrder.setOrderItems(Arrays.asList(testOrderItem));
        secondOrder.setTotalPrice(25.98);
        secondOrder.setLocalDateTime(LocalDateTime.now());

        testOrders = Arrays.asList(testOrder, secondOrder);

        // Set up test DTOs
        testOrderItemDTO = new OrderItemDTO();
        testOrderItemDTO.setId("menu1");
        testOrderItemDTO.setQuantity(2);

        testOrderRequestDTO = new OrderRequestDTO();
        testOrderRequestDTO.setTableId(3);
        testOrderRequestDTO.setOrderItems(Arrays.asList(testOrderItemDTO));

        testOrderStatusDTO = new OrderStatusDTO();
        testOrderStatusDTO.setOrderStatus(OrderStatus.READY);
    }

    @Test
    void placeOrder_ShouldCreateAndSaveOrder() {
        // Arrange
        when(menuService.getMenuItemById(anyString())).thenReturn(testMenuItem);
        doNothing().when(orderService).placeOrder(any(Orders.class));

        // Act
        orderController.placeOrder(testOrderRequestDTO);

        // Assert
        ArgumentCaptor<Orders> ordersCaptor = ArgumentCaptor.forClass(Orders.class);
        verify(orderService).placeOrder(ordersCaptor.capture());

        Orders capturedOrder = ordersCaptor.getValue();

        assertEquals(3, capturedOrder.getTableId());
        assertEquals(OrderStatus.IN_PREPARATION, capturedOrder.getOrderStatus());
        assertEquals(1, capturedOrder.getOrderItems().size());
        assertEquals(testMenuItem, capturedOrder.getOrderItems().get(0).getMenuItem());
        assertEquals(2, capturedOrder.getOrderItems().get(0).getQuantity());

        // Verify the method calls
        verify(menuService).getMenuItemById("menu1");
    }

    @Test
    void getOrders_ShouldReturnAllOrders() {
        // Arrange
        when(orderService.getOrders()).thenReturn(testOrders);

        // Act
        List<Orders> result = orderController.getOrders();

        // Assert
        assertEquals(testOrders, result);
        assertEquals(2, result.size());
        verify(orderService).getOrders();
    }

    @Test
    void deleteOrderById_ShouldCallOrderServiceDeleteOrder() {
        // Arrange
        String orderId = "order1";
        doNothing().when(orderService).deleteOrder(anyString());

        // Act
        orderController.deleteOrder(orderId);

        // Assert
        verify(orderService).deleteOrder(orderId);
    }

    @Test
    void deleteAllOrders_ShouldCallOrderServiceDeleteOrder() {
        // Arrange
        doNothing().when(orderService).deleteOrder();

        // Act
        orderController.deleteOrder();

        // Assert
        verify(orderService).deleteOrder();
    }

    @Test
    void updateOrder_ShouldCallOrderServiceUpdateOrder() {
        // Arrange
        String orderId = "order1";
        doNothing().when(orderService).updateOrder(any(OrderStatusDTO.class), anyString());

        // Act
        orderController.updateOrder(testOrderStatusDTO, orderId);

        // Assert
        verify(orderService).updateOrder(testOrderStatusDTO, orderId);
    }
}