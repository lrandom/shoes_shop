package shoesshop.demo.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import shoesshop.demo.entities.Order;

public interface OrderJPA extends PagingAndSortingRepository<Order,Long> {
}
