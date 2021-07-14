package shoesshop.demo.jpa;

import org.springframework.data.repository.CrudRepository;
import shoesshop.demo.entities.OrderItems;

public interface OrderItemJPA extends CrudRepository<OrderItems,Long> {
}
