package az.edu.ada.wm2.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {

    @GetMapping
    public String showKitchen() {
        return "kitchen.html";
    }
}
