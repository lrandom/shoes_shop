package shoesshop.demo.jpa;

import org.springframework.data.repository.CrudRepository;
import shoesshop.demo.entities.OrderItem;

public interface OrderItemJPA extends CrudRepository<OrderItem,Long> {
}
