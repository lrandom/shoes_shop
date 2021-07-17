package shoesshop.demo.controllers.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface IWithImageCRUD<T> extends ICRUD {
    public String doAdd(T product , RedirectAttributes flashSession ,
                        @RequestParam(name="img")MultipartFile multipartFile);
}
