package org.csu.mypetstore.controller;

import com.sun.deploy.net.HttpResponse;
import org.csu.mypetstore.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("verify/")
@SessionAttributes("verificationCode")
public class VerificationCodeController {

    @Autowired
    VerificationCodeService verificationCodeService;

    @GetMapping("getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response,Model model)
    {
        verificationCodeService.getVerify(request,response);
        String verificationCode = (String)request.getSession().getAttribute("verificationCode");
        model.addAttribute("verificationCode",verificationCode);
        System.out.println("123321");
    }
    //验证码图片请求的url，图片直接写入response，验证码存储在session中
}
