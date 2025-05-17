package az.edu.ada.wm2.backend.service;

import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    MenuItemRepo menuItemRepo;
    @Autowired
    public MenuService(MenuItemRepo menuItemRepo) {
        this.menuItemRepo = menuItemRepo;
    }

    public List<MenuItem> showMenu() {
        return menuItemRepo.findAll();
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItemRepo.save(menuItem);
    }

    public void deleteMenuItem(String id) {
        menuItemRepo.deleteById(id);
    }

    public void updateMenuItem(String id, MenuItem menuItem) {
        menuItem.setId(id);
        menuItemRepo.save(menuItem);
    }

    public MenuItem getMenuItemById(String id) {
        return menuItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
    }
}
