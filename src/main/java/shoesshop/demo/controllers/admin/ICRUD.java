package shoesshop.demo.controllers.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shoesshop.demo.entities.User;

public interface ICRUD {
    public String add(Model model);

    public String doAdd(User user , RedirectAttributes flashSession);

    public String list(Model model, @RequestParam(name="page", defaultValue = "1") int page);

    public String edit();

    public String doEdit();

    public String delete();
}
