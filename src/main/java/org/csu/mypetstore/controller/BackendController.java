package org.csu.mypetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/backend/")
public class BackendController {
    @GetMapping("categories")
    @ResponseBody
    public String getCategories() {
        return "getCategories";
    }

    @PostMapping("categories")
    @ResponseBody
    public String addCategories() {
        return "postCategories";
    }

    @DeleteMapping("categories")
    public String deleteCategories() {
        return "Categories";
    }

    @PatchMapping("categories")
    public String patchCategories() {
        return "Categories";
    }

    @GetMapping("products")
    @ResponseBody
    public String getProducts() {
        return "getCategories";
    }

    @PostMapping("products")
    @ResponseBody
    public String addProducts() {
        return "postProducts";
    }

    @DeleteMapping("products")
    public String deleteProducts() {
        return "Products";
    }

    @PatchMapping("products")
    public String patchProducts() {
        return "Products";
    }
    @GetMapping("items")
    @ResponseBody
    public String getItems() {
        return "getCategories";
    }

    @PostMapping("items")
    @ResponseBody
    public String addItems() {
        return "postProducts";
    }

    @DeleteMapping("items")
    public String deleteItems() {
        return "Products";
    }

    @PatchMapping("items")
    public String patchItems() {
        return "Products";
    }
}

