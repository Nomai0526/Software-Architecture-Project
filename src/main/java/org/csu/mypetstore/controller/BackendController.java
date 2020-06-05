package org.csu.mypetstore.controller;

import com.alibaba.fastjson.JSON;
import org.csu.mypetstore.domain.*;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/backend/")
public class BackendController {

    @Autowired
    CatalogService catalogService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;

    @GetMapping("categories")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getCategories() {
        List<Category> categoryList = catalogService.getCategoryList();
        String categoryJson = JSON.toJSONString(categoryList);
        return categoryJson;
    }
    //获得category列表

    @PostMapping("categories")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addCategories(Category category) {
        catalogService.addCategory(category);
    }
    //添加category

    @DeleteMapping("categories")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategories(Category category) {
        catalogService.deleteCategory(category);
    }
    //删除category

    @PatchMapping("categories")
    @ResponseStatus(value = HttpStatus.OK)
    public void patchCategories(Category category) {
        catalogService.updateCategory(category);
    }
    //修改categoty

    @GetMapping("products")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getProducts() {
        List<Product> productList = catalogService.getProductList();
        String productJson = JSON.toJSONString(productList);
        return productJson;
    }
    //获得Product列表

    @PostMapping("products")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addProduct(Product product) {
        catalogService.insertProduct(product);
    }
    //添加Product

    @DeleteMapping("products")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(Product product) {
        catalogService.deleteProduct(product);
    }
    //删除Product

    @PatchMapping("products")
    @ResponseStatus(value = HttpStatus.OK)
    public void patchProduct(Product product) {
        catalogService.updateProduct(product);
    }
    //修改Product

    @GetMapping("items")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getItems() {
        List<Product> itemList = catalogService.getProductList();
        String itemJson = JSON.toJSONString(itemList);
        return itemJson;
    }
    //获得item列表

    @PostMapping("items")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addItems(Item item) {
        catalogService.insertItem(item);
    }
    //添加item

    @DeleteMapping("items")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteItems(Item item) {
        catalogService.deleteItem(item);
    }
    //删除item

    @PatchMapping("items")
    @ResponseStatus(value = HttpStatus.OK)
    public void patchItems(Item item) {
        catalogService.updateItem(item);
    }
    //修改item

    @GetMapping("accounts")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getAccounts() {
        List<Account> accountList = accountService.getAccounts();
        String itemJson = JSON.toJSONString(accountList);
        return itemJson;
    }
    //获得item列表

    @PostMapping("accounts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addAccounts(Account account) { accountService.insertAccount(account);}
    //添加items

    @DeleteMapping("accounts")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAccounts(Account account) {
        accountService.deleteAccount(account);
    }
    //删除item

    @PatchMapping("accounts")
    @ResponseStatus(value = HttpStatus.OK)
    public void patchAccounts(Account account) {accountService.updateAccount(account);
    }
    //修改item

    @GetMapping("orders")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public String getOrders() {
        List<Order> orderList = orderService.getOrders();
        String itemJson = JSON.toJSONString(orderList);
        return itemJson;
    }
    //获得order列表

    @PostMapping("orders")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addOrders(Order order) { orderService.insertOrder(order);}
    //添加order

    @DeleteMapping("orders")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteOrders(Order order) {
        orderService.deleteOrder(order);
    }
    //删除order

    @PatchMapping("orders")
    @ResponseStatus(value = HttpStatus.OK)
    public void patchOrders(Order order) {orderService.updateOrder(order);
    }
    //修改order
}

