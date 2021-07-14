package shoesshop.demo.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import shoesshop.demo.entities.User;

public interface UserJPA extends PagingAndSortingRepository<User,Long> {

}
