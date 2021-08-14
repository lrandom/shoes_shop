package shoesshop.demo.jpa;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import shoesshop.demo.entities.Product;
import shoesshop.demo.entities.User;

import java.util.Optional;

public interface ProductJPA extends PagingAndSortingRepository<Product, Long> {
    @Query(nativeQuery = true,
            value = "select * from products where status=1 order by id desc limit 10")
    public Iterable<Product> getLastProducts();

    @Query(nativeQuery = true,
            value = "select * from products where status=1  order by sole_quantity desc limit 10")
    public Iterable<Product> getSoleProducts();

    @Query(nativeQuery = true,
            value = "select * from products where status=1 AND is_suggest=1  order by id desc limit 10")
    public Iterable<Product> getSuggestProducts();

    @Query("SELECT u FROM Product u where u.categoryId=:categoryId")
    public Page<Product> getProductsByCategoryId(@Param("categoryId") long categoryId, Pageable pageable);

    @Query("SELECT u FROM Product u where u.name LIKE %:query%")
    public Page<Product> searchProducts(@Param("query") String query, Pageable pageable);

    @Query("SELECT u.id, u.name,u.picture FROM Product u where u.name LIKE %:query%")
    public Iterable<Product> searching(@Param("query") String query);
}


