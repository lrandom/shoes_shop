package shoesshop.demo.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import shoesshop.demo.entities.Categories;

public interface CategoryJPA extends PagingAndSortingRepository<Categories,Long> {
}
