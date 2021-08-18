package shoesshop.demo.controllers.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shoesshop.demo.entities.Order;
import shoesshop.demo.entities.User;
import shoesshop.demo.jpa.OrderJPA;
import shoesshop.demo.services.OrderService;
import shoesshop.demo.services.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("USER") != null) {
            session.removeAttribute("USER");
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(HttpSession httpSession) {

        if (httpSession.getAttribute("USER") != null) {
            return "redirect:/";
        }
        return "frontend/login";
    }

    @PostMapping("/do-login")
    public String doLogin(RedirectAttributes flashSession, HttpSession httpSession, @RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password) {

        User user = this.userService.login(email, password);
        if (user != null) {
            httpSession.setAttribute("USER", user);
            if (httpSession.getAttribute("REDIRECT_BACK") != null) {
                String redirectBack = (String) httpSession.getAttribute("REDIRECT_BACK");
                httpSession.removeAttribute("REDIRECT_BACK");
                return "redirect:" + redirectBack;
            }
        } else {
            flashSession.addFlashAttribute("failed", "Đăng nhập thất bại");
            return "redirect:/login";
        }
        return "redirect:/";
    }

    @PostMapping("/do-signup")
    public String doSignUp(RedirectAttributes flashSession, @RequestParam(name = "email") String email,
                           @RequestParam(name = "fullname") String fullName,
                           @RequestParam("password") String password) {
        User user = new User();
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPassword(password);
        user.setRole(1);
        try {
            userService.save(user);
        } catch (Exception e) {
            flashSession.addFlashAttribute("failed", "Tạo tài khoản thất bại");
            return "redirect:/signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "frontend/signup";
    }


    @GetMapping("/profile")
    public String profile() {
        return "frontend/authed/profile";
    }

    @GetMapping("/my-orders")
    public String myOrder(HttpSession session,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") int page) {
        User user = (User) session.getAttribute("USER");
        if (user != null) {
            //lấy thông tin user
            OrderService.ListResult listResult = orderService.getOrdersListByUserId(user.getId(), page);
            model.addAttribute("listResult", listResult);
        } else {
            return "redirect:/login";
        }
        return "frontend/authed/my-orders";
    }


    @GetMapping("/order-detail")
    public String orderDetail(@RequestParam(name="id") int id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "frontend/authed/order-detail";
    }
}
