package az.edu.ada.wm2.backend.config;

import az.edu.ada.wm2.backend.enums.MenuCategories;
import az.edu.ada.wm2.backend.model.MenuItem;
import az.edu.ada.wm2.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MenuDataInitializer implements CommandLineRunner {

    private final MenuService menuService;

    @Autowired
    public MenuDataInitializer(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void run(String... args) {
        if (menuService.showMenu().isEmpty()) {
            menuService.addMenuItem(new MenuItem(
                "Bruschetta",
                "Grilled bread rubbed with garlic and topped with diced tomatoes, fresh basil, and olive oil",
                7.99,
                MenuCategories.APPETIZERS
            ));
            
            menuService.addMenuItem(new MenuItem(
                "Calamari Fritti",
                "Crispy fried squid served with marinara sauce",
                11.99,
                MenuCategories.APPETIZERS
            ));

            menuService.addMenuItem(new MenuItem(
                "Margherita Pizza",
                "Classic pizza with tomato sauce, fresh mozzarella, basil, and olive oil",
                14.99,
                MenuCategories.MAIN_COURSES
            ));

            menuService.addMenuItem(new MenuItem(
                "Pasta Carbonara",
                "Spaghetti with crispy pancetta, egg, pecorino romano, and black pepper",
                16.99,
                MenuCategories.MAIN_COURSES
            ));

            menuService.addMenuItem(new MenuItem(
                "Garlic Mashed Potatoes",
                "Creamy mashed potatoes with roasted garlic and butter",
                5.99,
                MenuCategories.SIDE_DISHES
            ));

            menuService.addMenuItem(new MenuItem(
                "Grilled Vegetables",
                "Seasonal vegetables grilled with olive oil and herbs",
                6.99,
                MenuCategories.SIDE_DISHES
            ));

            menuService.addMenuItem(new MenuItem(
                "Tiramisu",
                "Classic Italian dessert with layers of coffee-soaked ladyfingers and mascarpone cream",
                8.99,
                MenuCategories.DESSERTS
            ));

            menuService.addMenuItem(new MenuItem(
                "Chocolate Lava Cake",
                "Warm chocolate cake with a molten center, served with vanilla ice cream",
                9.99,
                MenuCategories.DESSERTS
            ));

            menuService.addMenuItem(new MenuItem(
                "Italian Soda",
                "Sparkling water with your choice of flavored syrup",
                3.99,
                MenuCategories.BEVERAGES
            ));

            menuService.addMenuItem(new MenuItem(
                "Espresso",
                "Traditional Italian espresso",
                2.99,
                MenuCategories.BEVERAGES
            ));
        }
    }
}
