package shoesshop.demo.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import shoesshop.demo.entities.Product;

public interface ProductJPA extends PagingAndSortingRepository<Product,Long> {
    @Query(nativeQuery = true,
            value = "select * from products where status=1 order by id desc limit 10")
    public Iterable<Product> getLastProducts();

    @Query(nativeQuery = true,
            value = "select * from products where status=1  order by sole_quantity desc limit 10")
    public Iterable<Product> getSoleProducts();
}
