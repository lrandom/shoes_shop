package shoesshop.demo.controllers.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shoesshop.demo.entities.CartItem;
import shoesshop.demo.entities.Product;
import shoesshop.demo.services.OrderService;
import shoesshop.demo.services.ProductService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String getHome(Model model) {
        Iterable<Product> lastProducts = productService.getLastProducts();
        Iterable<Product> topSaleProducts = productService.getTopSaleProducts();
        Iterable<Product> suggestProducts = productService.getSuggestProducts();
        model.addAttribute("lastProducts", lastProducts);
        model.addAttribute("topSaleProducts", topSaleProducts);
        model.addAttribute("suggestProducts", suggestProducts);
        return "frontend/home";
    }

    @GetMapping("/cart")
    public String cart() {
        return "frontend/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model,HttpSession httpSession) {
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) httpSession.getAttribute("CART");
        model.addAttribute("cartItems", cartItems);
        float subTotal = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            subTotal += cartItems.get(i).getPrice() * cartItems.get(i).getQuantity();
        }
        model.addAttribute("subTotal",subTotal );
        model.addAttribute("tax", subTotal*0.1);
        model.addAttribute("total", subTotal*0.1+subTotal);
        return "frontend/authed/checkout";
    }

    @PostMapping("/do-checkout")
    public String doCheckout(HttpSession httpSession, @RequestParam("ship_address") String shipAddress,
                             @RequestParam("total")Double total) {
        //CHƯA ĐĂNG NHẬP
      /*  if (httpSession.getAttribute("USER") == null) {
            httpSession.setAttribute("REDIRECT_BACK", "/checkout");
            return "redirect:/login";
        }*/


        //ĐÃ ĐĂNG NHẬP
        //TẠO ĐƠN HÀNG
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) httpSession.getAttribute("CART");
        orderService.makeOrder(shipAddress,1,total,cartItems);
        return "redirect:/checkout-success";
    }


    @GetMapping("/checkout-success")
    public String success(){
        return "frontend/checkout-success";
    }

}
