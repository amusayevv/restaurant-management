package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MenuController {
    MenuService menuService;
    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu")
    public List<MenuItem> showMenu() {
        return menuService.showMenu();
    }

    @PostMapping("/menu")
    public void addMenuItem(@RequestBody MenuItem menuItem) {
        menuService.addMenuItem(menuItem);
    }

    @DeleteMapping("")
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/menu/{id}")
    public void deleteMenuItem(@PathVariable String id) {
        menuService.deleteMenuItem(id);
    }

    @PutMapping("/menu/{id}")
    public void updateMenuItem(@PathVariable String id, @RequestBody MenuItem menuItem) {
        menuService.updateMenuItem(id, menuItem);
    }
}
