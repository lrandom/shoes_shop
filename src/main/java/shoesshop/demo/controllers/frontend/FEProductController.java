package shoesshop.demo.controllers.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoesshop.demo.entities.CartItem;
import shoesshop.demo.entities.Product;
import shoesshop.demo.services.ProductService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
public class FEProductController {
    @Autowired
    private ProductService productService;

    public String category() {
        return null;
    }


    @RequestMapping("/product/detail")
    public String detail(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "frontend/product/detail";
    }

    @GetMapping("/product/get-total-cart-items")
    @ResponseBody
    public String getTotalCartItem(HttpSession session) {
        if (session.getAttribute("CART") != null) {
            return "{\"total_cart_items\":" + ((ArrayList<CartItem>) session.getAttribute("CART")).size() + "}";
        }
        return "{\"total_cart_items\":0}";
    }

    @PostMapping("/product/add-to-cart")
    @ResponseBody
    public String addToCart(@RequestParam(name = "id") Long id,
                            @RequestParam(name = "quantity") int quantity,
                            HttpSession session
    ) {


        ArrayList<CartItem> cartItems;
        if (session.getAttribute("CART") == null) {
            //chưa có giỏ hàng
            cartItems = new ArrayList<>();
            Product product = productService.getProduct(id);
            CartItem cartItem = new CartItem();
            cartItem.setId(id);
            cartItem.setName(product.getName());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(quantity);
            cartItems.add(cartItem);
            //lưu trữ vào giỏ hàng
            session.setAttribute("CART", cartItems);
        } else {
            //đã có giỏ hàng
            cartItems = (ArrayList<CartItem>) session.getAttribute("CART");
            boolean flag = false;
            for (CartItem cartItem : cartItems) {
                if (cartItem.getId() == id) {
                    //tìm thấy thì tăng lên
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                //sp này ko có trong giỏ hàng
                CartItem cartItem = new CartItem();
                Product product = productService.getProduct(id);
                cartItem.setId(id);
                cartItem.setName(product.getName());
                cartItem.setPrice(product.getPrice());
                cartItem.setQuantity(quantity);
                cartItems.add(cartItem);
            }
            session.setAttribute("CART", cartItems);
        }

        return "{\"total_cart_items\":" + cartItems.size() + "}";
    }

    @GetMapping("/product/get-cart")
    public ResponseEntity<?> getCart(HttpSession session) {
        if (session.getAttribute("CART") == null) {
            return ResponseEntity.ok(new ArrayList<CartItem>());
        }
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("CART");
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/product/change-quantity")
    public ResponseEntity<?> getCart(HttpSession session,
                                     @RequestParam(name = "step") int step,
                                     @RequestParam(name = "id") long id
                                     ) {
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("CART");
        for (int i = 0; i < cartItems.size(); i++) {
            if(cartItems.get(i).getId()==id){
                cartItems.get(i).setQuantity(cartItems.get(i).getQuantity()+step);
            }
        }
        session.setAttribute("CART", cartItems);
        return ResponseEntity.ok(cartItems);
    }


    @PostMapping("/product/delete-item")
    public ResponseEntity<?> getCart(HttpSession session,
                                     @RequestParam(name = "id") long id
    ) {
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("CART");
        ArrayList<CartItem> tmpCartItems = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            if(cartItems.get(i).getId()!=id){
                tmpCartItems.add(cartItems.get(i));
            }
        }
        session.setAttribute("CART", tmpCartItems);
        return ResponseEntity.ok(tmpCartItems);
    }

}
