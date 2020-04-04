package org.csu.mypetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("order/")
public class OrderController {

    @GetMapping("newOrder")
    public String newOrder(Model model)
    {
        ArrayList<String> cardTypeList = new ArrayList<String>();
        cardTypeList.add("Type 1");
        cardTypeList.add("Type 2");
        cardTypeList.add("Type 3");
        model.addAttribute("cardTypeList",cardTypeList);
        return "order/new_order_form";
    }
    @PostMapping("shippingForm")
    public String shippingForm(String shipToDiff)
    {
        if(shipToDiff==null)
        {
            return "order/view_order";
        }
        else
        {
            return "order/shipping_form";
        }
    }
    @GetMapping("confirmOrder")
    public String confirmOrder()
    {
        return "order/view_order";
    }
}
