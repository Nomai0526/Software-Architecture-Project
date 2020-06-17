package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/account/")
@SessionAttributes({"account","myList","authenticated"})
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("signonForm")
    public String signonForm(){
        return "account/signon";
    }

    @PostMapping("signon")
    public String signon(String username, String password, Model model, HttpServletRequest request){

        if(username.equals("admin")&&password.equals("admin"))
            return "backend/index";

        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        Account loginAccount = accountService.getAccount(username,md5Password);

        if(!verificationCodeService.check(request.getSession(),request.getParameter("checkCode")))
        return "account/signon";
        //验证验证码，如果错误则返回带参数的登陆页面

        if(loginAccount == null){
            String msg = "Invalid username or password.  Signon failed.";
            model.addAttribute("msg",msg);
            return "account/signon";
        }else {
            loginAccount.setPassword(null);
            List<Product> myList =catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
            boolean authenticated = true;
            model.addAttribute("account", loginAccount);
            model.addAttribute("myList",myList);
            model.addAttribute("authenticated",authenticated);
            return "catalog/main";
        }
    }
    @GetMapping("registerPage")
    public String registerPage()
    {
        return "account/NewAccountForm";
    }
    @PostMapping("register")
    public String register(String userId,String password,String firstName,String lastName,String email,String phone,String addr1,String addr2,String city,String state,String zip,String country,Model model)
    {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Account account = new Account();
        account.setUsername(userId);
        account.setPassword(md5Password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setAddress1(addr1);
        account.setAddress2(addr2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
        accountService.insertAccount(account);
        boolean authenticated = true;
        List<Product> myList =catalogService.getProductListByCategory(account.getFavouriteCategoryId());
        model.addAttribute("account",account);
        model.addAttribute("myList",myList);
        model.addAttribute("authenticated",authenticated);

        return "catalog/main";
    }
    @GetMapping("signOut")
    public String signOut(Model model)
    {
        model.addAttribute("account",null);
        model.addAttribute("myList",null);
        model.addAttribute("authenticated",false);
        return "catalog/main";
    }
    @GetMapping("myAccountPage")
    public String myAccountPage()
    {
        return "account/EditAccountForm";
    }
    @PostMapping("editAccount")
    public String editAccountString (String userId,String password,String firstName,String lastName,String email,String phone,String addr1,String addr2,String city,String state,String zip,String country,Model model)
    {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Account account = new Account();
        account.setUsername(userId);
        account.setPassword(md5Password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setAddress1(addr1);
        account.setAddress2(addr2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
        accountService.updateAccount(account);
        boolean authenticated = true;
        List<Product> myList =catalogService.getProductListByCategory(account.getFavouriteCategoryId());
        model.addAttribute("account",account);
        model.addAttribute("myList",myList);
        model.addAttribute("authenticated",authenticated);
        return "catalog/main";
    }

}
