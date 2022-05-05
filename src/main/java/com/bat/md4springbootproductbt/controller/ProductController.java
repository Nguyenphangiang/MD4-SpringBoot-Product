package com.bat.md4springbootproductbt.controller;

import com.bat.md4springbootproductbt.model.Product;
import com.bat.md4springbootproductbt.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("products",productService.findAll());
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showFormCreate(){
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("product",new Product());
        return modelAndView;
    }
    @PostMapping("/save")
    public String createProduct(@ModelAttribute("product")Product product, Model model){
        productService.save(product);
        model.addAttribute("products",productService.findAll());
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/edit");
            modelAndView.addObject("product",product.get());
            return modelAndView;
        }
        else return new ModelAndView("/error404");
    }
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product,Model model){
        productService.remove(product.getId());
        productService.save(product);
        model.addAttribute("products",productService.findAll());
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/delete");
            modelAndView.addObject("product",product.get());
            return modelAndView;
        } else {
            return new ModelAndView("error404");
        }
    }
    @PostMapping("/delete")
    public ModelAndView deleteProduct(@ModelAttribute("product")Product product){
        productService.remove(product.getId());
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("products",productService.findAll());
        return modelAndView;
    }
}
