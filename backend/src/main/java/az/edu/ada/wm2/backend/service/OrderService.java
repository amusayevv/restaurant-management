package az.edu.ada.wm2.backend.service;

import az.edu.ada.wm2.backend.DTO.OrderStatusDTO;
import az.edu.ada.wm2.backend.model.Orders;
import az.edu.ada.wm2.backend.repo.OrderItemRepo;
import az.edu.ada.wm2.backend.repo.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderItemRepo orderItemRepo;
    private final OrdersRepo ordersRepo;

    @Autowired
    public OrderService(OrderItemRepo orderItemRepo, OrdersRepo ordersRepo) {
        this.orderItemRepo = orderItemRepo;
        this.ordersRepo = ordersRepo;
    }

    public void placeOrder(Orders orders) {
        ordersRepo.save(orders);
    }

    public List<Orders> getOrders() {
        return ordersRepo.findAll();
    }

    public void deleteOrder(String id) {
        ordersRepo.deleteById(id);
    }

    public void deleteOrder() {
        ordersRepo.deleteAll();
    }

    public void updateOrder(OrderStatusDTO orderStatusDTO, String id) {
        Orders orderToUpdate = ordersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderToUpdate.setId(id);
        orderToUpdate.setOrderStatus(orderStatusDTO.getOrderStatus());
        ordersRepo.save(orderToUpdate);
    }
}
