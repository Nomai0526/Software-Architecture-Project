package org.csu.mypetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("backend")
public class TestController {
    @GetMapping("backend")
//    @ResponseBody
    public String test()
    {

        System.out.println("123123");
        return "backend/index";
    }
}
