package shoesshop.demo.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shoesshop.demo.entities.Product;
import shoesshop.demo.entities.User;
import shoesshop.demo.helpers.Helper;
import shoesshop.demo.jpa.UserJPA;

@Service
public class UserService {
    @Autowired
    private UserJPA userJPA;

    @Autowired
    private Helper helper;


    public boolean save(User user) {
        user.setPassword(helper.getMD5(user.getPassword()));
        try {
            userJPA.save(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }



    public boolean delete(long id) {
        try {
            userJPA.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public User login(String email, String password) {
        return this.userJPA.login(email, helper.getMD5(password)).isPresent() ?
                this.userJPA.login(email, helper.getMD5(password)).get(): null;
    }

    public User getUserById(long id) {
        return userJPA.findById(id).get();
    }


    public ListResult getUserList(int page) {
       ListResult listResult = new ListResult();
       listResult.setListUser(userJPA.findAll(PageRequest.of(page - 1, 10)));
       listResult.setActivePage(page);
       double totalPage =  Math.ceil((double) userJPA.count()/10);
       listResult.setTotalPage(totalPage);
       return listResult;
    }

    @Data
    public class ListResult{
        Iterable<User> listUser;
        int activePage;
        double totalPage;
    }
}
