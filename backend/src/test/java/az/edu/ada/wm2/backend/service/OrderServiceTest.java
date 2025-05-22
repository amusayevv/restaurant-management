package az.edu.ada.wm2.backend.service;

import az.edu.ada.wm2.backend.DTO.OrderStatusDTO;
import az.edu.ada.wm2.backend.enums.OrderStatus;
import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.model.OrderItem;
import az.edu.ada.wm2.backend.model.Orders;
import az.edu.ada.wm2.backend.repo.OrderItemRepo;
import az.edu.ada.wm2.backend.repo.OrdersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrdersRepo ordersRepo;

    @Mock
    private OrderItemRepo orderItemRepo;

    @InjectMocks
    private OrderService orderService;

    private Orders testOrder;
    private List<Orders> testOrders;
    private MenuItem testMenuItem;
    private OrderItem testOrderItem;
    private OrderStatusDTO testOrderStatusDTO;

    @BeforeEach
    void setUp() {
        testMenuItem = new MenuItem();
        testMenuItem.setId("menu1");
        testMenuItem.setName("Margherita Pizza");
        testMenuItem.setPrice(10.99);

        testOrderItem = new OrderItem();
        testOrderItem.setId("orderItem1");
        testOrderItem.setMenuItem(testMenuItem);
        testOrderItem.setQuantity(2);

        testOrder = new Orders();
        testOrder.setId("order1");
        testOrder.setTableId(5);
        testOrder.setOrderStatus(OrderStatus.PENDING);
        testOrder.setOrderItems(Arrays.asList(testOrderItem));
        testOrder.setTotalPrice(21.98); // 2 * 10.99
        testOrder.setLocalDateTime(LocalDateTime.now());

        Orders secondOrder = new Orders();
        secondOrder.setId("order2");
        secondOrder.setTableId(7);
        secondOrder.setOrderStatus(OrderStatus.IN_PREPARATION);
        secondOrder.setOrderItems(Arrays.asList(testOrderItem));
        secondOrder.setTotalPrice(21.98);
        secondOrder.setLocalDateTime(LocalDateTime.now());

        testOrders = Arrays.asList(testOrder, secondOrder);

        testOrderStatusDTO = new OrderStatusDTO();
        testOrderStatusDTO.setOrderStatus(OrderStatus.READY);
    }

    @Test
    void placeOrder_ShouldSaveOrder() {
        // Arrange
        when(ordersRepo.save(any(Orders.class))).thenReturn(testOrder);

        // Act
        orderService.placeOrder(testOrder);

        // Assert
        verify(ordersRepo).save(testOrder);
    }

    @Test
    void getOrders_ShouldReturnAllOrders() {
        // Arrange
        when(ordersRepo.findAll()).thenReturn(testOrders);

        // Act
        List<Orders> result = orderService.getOrders();

        // Assert
        assertEquals(testOrders, result);
        assertEquals(2, result.size());
        verify(ordersRepo).findAll();
    }

    @Test
    void deleteOrderById_ShouldDeleteOrderById() {
        // Arrange
        String orderId = "order1";
        doNothing().when(ordersRepo).deleteById(anyString());

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(ordersRepo).deleteById(orderId);
    }

    @Test
    void deleteAllOrders_ShouldDeleteAllOrders() {
        // Arrange
        doNothing().when(ordersRepo).deleteAll();

        // Act
        orderService.deleteOrder();

        // Assert
        verify(ordersRepo).deleteAll();
    }

    @Test
    void updateOrder_ShouldUpdateOrderStatus() {
        // Arrange
        String orderId = "order1";
        when(ordersRepo.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(ordersRepo.save(any(Orders.class))).thenReturn(testOrder);

        // Act
        orderService.updateOrder(testOrderStatusDTO, orderId);

        // Assert
        verify(ordersRepo).findById(orderId);
        verify(ordersRepo).save(any(Orders.class));

        verify(ordersRepo).save(argThat(order ->
                order.getId().equals(orderId) &&
                        order.getOrderStatus() == OrderStatus.READY
        ));
    }

    @Test
    void updateOrder_ShouldThrowExceptionWhenOrderNotFound() {
        // Arrange
        String orderId = "nonexistent";
        when(ordersRepo.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(testOrderStatusDTO, orderId);
        });

        assertEquals("Order not found with id: " + orderId, exception.getMessage());
        verify(ordersRepo).findById(orderId);
        verify(ordersRepo, never()).save(any(Orders.class));
    }
}