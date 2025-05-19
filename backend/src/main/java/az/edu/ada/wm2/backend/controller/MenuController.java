package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    MenuService menuService;
    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuItem> showMenu() {
        return menuService.showMenu();
    }

    @PostMapping
    public void addMenuItem(@RequestBody MenuItem menuItem) {
        menuService.addMenuItem(menuItem);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable String id) {
        menuService.deleteMenuItem(id);
    }

    @PutMapping("/{id}")
    public void updateMenuItem(@PathVariable String id, @RequestBody MenuItem menuItem) {
        menuService.updateMenuItem(id, menuItem);
    }
}
