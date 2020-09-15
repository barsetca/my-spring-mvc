package ru.cherniak.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.cherniak.spring.mvc.model.Product;
import ru.cherniak.spring.mvc.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String getAll(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String getProduct(@PathVariable Long id) {
        Product product = productService.getById(id);
        return String.format("Title %s, price = %d", product.getTitle(), product.getCost());
    }

    @PostMapping(value = "/add_product")
    public String addProduct(@RequestParam String title, @RequestParam int cost) {
        productService.save(title, cost);
        return "redirect:/products";
    }

    //Проверочный метод. Работа POST (JSON->JSON) проверяется в файле /checkPost.http
    @PostMapping("/my_product")
    @ResponseBody
    public Product returnMyProductWithoutSave(@RequestBody Product product) {
        product.setTitle("new");
        return product;
    }

    @GetMapping("/delete_product/{id}")
    public String delete(@PathVariable long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
