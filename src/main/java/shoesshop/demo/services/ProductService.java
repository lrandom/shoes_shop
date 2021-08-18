package shoesshop.demo.services;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoesshop.demo.entities.Product;
import shoesshop.demo.jpa.ProductJPA;

@Service
public class ProductService {
    @Autowired
    private ProductJPA productJPA;

    @Autowired
    private UploadService uploadService;

    public boolean save(Product product, MultipartFile uploadImage) {
        try {
            //upload ảnh
            if (uploadImage != null) {
                //tiến hành upload
                String uploadPath = uploadService.upload(uploadImage);
                if (uploadPath != null) {
                    product.setPicture(uploadPath);
                } else {
                    return false;
                }
            }
            productJPA.save(product);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            productJPA.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Product getProductById(long id) {
        return productJPA.findById(id).get();
    }

    public Iterable<Product> getLastProducts() {
        return productJPA.getLastProducts();
    }

    public Iterable<Product> getTopSaleProducts() {
        return productJPA.getSoleProducts();
    }

    public Iterable<Product> getSuggestProducts() {
        return productJPA.getSuggestProducts();
    }

    public Product getProduct(long id) {
        return productJPA.findById(id).get();
    }

/*
    public Iterable<Product> getSuggestProducts() {

    }
*/

    public Iterable<Product> searching(String query) {
        return productJPA.searching(query);
    }

    public ListResult getProductsByCategoryId(long categoryId, int page) {
        ListResult listResult = new ListResult();
        listResult.setListProduct(productJPA.getProductsByCategoryId(categoryId, PageRequest.of(page - 1, 10)));
        listResult.setActivePage(page);
        double totalPage = Math.ceil((double) productJPA.count() / 10);
        listResult.setTotalPage(totalPage);
        return listResult;
    }

    public ListResult getProductList(int page) {
        ListResult listResult = new ListResult();
        listResult.setListProduct(productJPA.findAll(PageRequest.of(page - 1, 10)));
        listResult.setActivePage(page);
        double totalPage = Math.ceil((double) productJPA.count() / 10);
        listResult.setTotalPage(totalPage);
        return listResult;
    }

    public ListResult searchProduct(String query,int page) {
        ListResult listResult = new ListResult();
        listResult.setListProduct(productJPA.searchProducts(query,PageRequest.of(page - 1, 10)));
        listResult.setActivePage(page);
        double totalPage = Math.ceil((double) productJPA.count() / 10);
        listResult.setTotalPage(totalPage);
        return listResult;
    }

    @Data
    public class ListResult {
        Iterable<Product> listProduct;
        int activePage;
        double totalPage;
    }

}
