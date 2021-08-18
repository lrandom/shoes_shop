package shoesshop.demo.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import shoesshop.demo.entities.Order;
import shoesshop.demo.entities.Product;

public interface OrderJPA extends PagingAndSortingRepository<Order,Long> {
    @Query("SELECT u FROM Order u where u.userId=:userId")
    public Page<Order> getOrderByUserId(@Param("userId") long userId, Pageable pageable);
}
