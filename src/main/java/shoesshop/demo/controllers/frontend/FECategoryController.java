package shoesshop.demo.controllers.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shoesshop.demo.entities.Categories;
import shoesshop.demo.entities.Product;
import shoesshop.demo.services.CategoryService;
import shoesshop.demo.services.ProductService;

@Controller
public class FECategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/category")
    public String getCategory(Model model, @RequestParam(name = "id", defaultValue = "0") long categoryId,
                              @RequestParam(name = "page", defaultValue = "1") int page) {
        String nameCategory = "All";
        if (categoryId != 0) {
            //lấy tất cả các sản phẩm ra
            model.addAttribute("listResult", productService.getProductsByCategoryId(categoryId, page));
            Categories cate = categoryService.getCategoriesById(categoryId);
            nameCategory = cate.getName();
        } else {
            //lọc sp theo category
            model.addAttribute("listResult", productService.getProductList(page));
        }
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("categoryId", categoryId);

        model.addAttribute("categoryName", nameCategory);
        return "frontend/category";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name = "query", defaultValue = "null") String query,
                         @RequestParam(name = "page", defaultValue = "1") int page) {
        model.addAttribute("listResult", productService.searchProduct(query, page));
        model.addAttribute("query", query);
        return "frontend/search_result";
    }

    @GetMapping("/searching")
    public ResponseEntity<?> searching(@RequestParam(name = "query", defaultValue = "null") String query) {

        Iterable<Product> products = productService.searching(query);
        return ResponseEntity.ok(products);
    }
}
