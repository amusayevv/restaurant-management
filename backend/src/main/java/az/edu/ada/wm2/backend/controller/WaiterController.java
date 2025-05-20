package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/waiter")
public class WaiterController {
    OrderService orderService;

    @Autowired
    public WaiterController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showKitchen(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "waiter";
    }

}

