package shoesshop.demo.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shoesshop.demo.entities.Product;
import shoesshop.demo.services.CategoryService;
import shoesshop.demo.services.ProductService;

@Controller
@RequestMapping("/admin/products")
public class ProductController implements IWithImageCRUD<Product> {


    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Override
    @GetMapping("/add")
    public String add(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product/add";
    }

    @Override
    @PostMapping("/do-add")
    public String doAdd(Product product, RedirectAttributes flashSession,
                        @RequestParam(name = "img") MultipartFile multipartFile) {
        Product product1 = (Product) product;
        if (productService.save(product1, multipartFile)) {
            flashSession.addFlashAttribute("success", "Add successfully");
        } else {
            flashSession.addFlashAttribute("failed", "Add failed");
        }
        return "redirect:/admin/products/add";
    }

    @Override
    public String doAdd(Object user, RedirectAttributes flashSession) {
        return null;
    }

    @Override
    public String doEdit(Object user, RedirectAttributes flashSession) {
        return null;
    }

    @Override
    @RequestMapping("/")
    public String list(Model model, int page) {
        ProductService.ListResult listResult = productService.getProductList(page);
        model.addAttribute("listResult", listResult);
        return "admin/product/list";
    }

    @Override
    public String edit(Model model, long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/edit";
    }

    @Override
    public String delete(long id, RedirectAttributes flashSession) {
        if (productService.delete(id)) {
            flashSession.addFlashAttribute("success", "delete successfully");
        } else {
            flashSession.addFlashAttribute("failed", "delete failed");
        }
        return "redirect:/admin/product/";
    }
}
