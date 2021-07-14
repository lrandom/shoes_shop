package shoesshop.demo.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shoesshop.demo.entities.User;
import shoesshop.demo.helpers.Helper;
import shoesshop.demo.jpa.UserJPA;
import shoesshop.demo.services.UserService;

@Repository
@RequestMapping("/admin/users")
public class UserController implements ICRUD {


    @Autowired
    private UserService userService;

    @Override
    @GetMapping("/add")
    public String add(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/user/add";
    }

    @Override
    @PostMapping("/do-add")
    public String doAdd(User user, RedirectAttributes flashSession) {
       if(userService.save(user)){
            flashSession.addFlashAttribute("success", "Thêm thành công");
        }else{
            flashSession.addFlashAttribute("failed", "Thêm thất bại");
        }
        return "redirect:/admin/users/add";
    }

    @Override
    @GetMapping("/")
    public String list(Model model, @RequestParam(name="page", defaultValue = "1") int page) {
        UserService.ListResult  listResult=  userService.getUserList(page);
        model.addAttribute("listResult",listResult);
        return "admin/user/list";
    }

    @Override
    public String edit() {
        return null;
    }

    @Override
    public String doEdit() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
