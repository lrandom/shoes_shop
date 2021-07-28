package shoesshop.demo.controllers.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shoesshop.demo.entities.Product;
import shoesshop.demo.services.ProductService;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getHome(Model model) {
        Iterable<Product> lastProducts = productService.getLastProducts();
        Iterable<Product> topSaleProducts  = productService.getTopSaleProducts();
        Iterable<Product> suggestProducts  = productService.getSuggestProducts();
        model.addAttribute("lastProducts", lastProducts);
        model.addAttribute("topSaleProducts", topSaleProducts);
        model.addAttribute("suggestProducts", suggestProducts);
        return "frontend/home";
    }
}
