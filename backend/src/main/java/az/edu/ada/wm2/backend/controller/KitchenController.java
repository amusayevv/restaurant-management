package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {
    OrderService orderService;

    @Autowired
    public KitchenController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showKitchen(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "kitchen";
    }

}
