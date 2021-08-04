package shoesshop.demo.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import shoesshop.demo.entities.Product;
import shoesshop.demo.entities.User;

import java.util.Optional;

public interface UserJPA extends PagingAndSortingRepository<User,Long> {
    @Query("SELECT u FROM User u where u.email=:email AND u.password=:password")
    public Optional<User> login(@Param("email") String email
            , @Param("password") String password);
}
