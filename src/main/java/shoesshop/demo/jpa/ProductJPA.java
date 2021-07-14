package shoesshop.demo.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import shoesshop.demo.entities.Product;

public interface ProductJPA extends PagingAndSortingRepository<Product,Long> {
}
