package az.edu.ada.wm2.backend.repo;

import az.edu.ada.wm2.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, String> {
}
