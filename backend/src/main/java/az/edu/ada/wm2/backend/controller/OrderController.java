package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.DTO.OrderRequestDTO;
import az.edu.ada.wm2.backend.enums.OrderStatus;
import az.edu.ada.wm2.backend.model.OrderItem;
import az.edu.ada.wm2.backend.model.Orders;
import az.edu.ada.wm2.backend.service.MenuService;
import az.edu.ada.wm2.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    private final MenuService menuService;
    private final OrderService orderService;

    @Autowired
    public OrderController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public void placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        List<OrderItem> orderItems = new ArrayList<>();
        orderRequestDTO.getOrderItems().forEach(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuService.getMenuItemById(item.getId()));
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        });

        int tableId = orderRequestDTO.getTableId();

        Orders orders = new Orders(tableId, orderItems);
        orders.setOrderStatus(OrderStatus.IN_PREPARATION);
        orderService.placeOrder(orders);
        System.out.println(orders.toString());
    }

    @GetMapping("/order")
    public List<Orders> getOrders() {
        return orderService.getOrders();
    }

    @DeleteMapping("/order/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }

    @DeleteMapping("/order")
    public void deleteOrder() {
        orderService.deleteOrder();
    }
}
