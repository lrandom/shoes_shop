package shoesshop.demo.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shoesshop.demo.entities.Categories;
import shoesshop.demo.jpa.CategoryJPA;

@Service
public class CategoryService {
    @Autowired
    private CategoryJPA categoryJPA;


    public boolean save(Categories category) {
        try {
            categoryJPA.save(category);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            categoryJPA.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Categories getCategoriesById(long id) {
        return categoryJPA.findById(id).get();
    }

    public Iterable<Categories> getAll() {
        return categoryJPA.findAll();
    }

    public ListResult getCategoriesList(int page) {
        ListResult listResult = new ListResult();
        listResult.setListCategories(categoryJPA.findAll(PageRequest.of(page - 1, 10)));
        listResult.setActivePage(page);
        double totalPage = Math.ceil((double) categoryJPA.count() / 10);
        listResult.setTotalPage(totalPage);
        return listResult;
    }

    @Data
    public class ListResult {
        Iterable<Categories> listCategories;
        int activePage;
        double totalPage;

    }
}

