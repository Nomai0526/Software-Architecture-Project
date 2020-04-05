package org.csu.mypetstore.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("order/")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("newOrder")
    public String newOrder(HttpServletRequest request,Model model)
    {
        ArrayList<String> cardTypeList = new ArrayList<String>();
        cardTypeList.add("Type 1");
        cardTypeList.add("Type 2");
        cardTypeList.add("Type 3");
        model.addAttribute("cardTypeList",cardTypeList);
        if(request.getSession().getAttribute("account")==null)
            return "account/signon";
        else return "order/new_order_form";
    }
    @PostMapping("shippingForm")
    public String shippingForm(HttpServletRequest request, String shipToDiff)
    {

        //用了比较古典的写法
        HttpSession session = request.getSession();
        Order order = new Order();
        order.setOrderId(23333);
        order.setUserId(((Account) session.getAttribute("account")).getUsername());
        order.setOrderDate(new Date());

        order.setBillAddr1((String) request.getParameter("billAddr1"));
        order.setBillAddr2((String) request.getParameter("billAddr2"));
        order.setBillCity((String) request.getParameter("billCity"));
        order.setBillState((String) request.getParameter("billState"));
        order.setBillZip((String) request.getParameter("billZip"));
        order.setBillCountry((String) request.getParameter("billCountry"));
        order.setTotalPrice(((Cart) session.getAttribute("cart")).getSubTotal());
//        order.setTotalPrice(new BigDecimal(1000));
        order.setBillToFirstName((String) request.getParameter("billFirstName"));
        order.setBillToLastname((String) request.getParameter("billLastName"));
        order.setCreditCard((String) request.getParameter("cardNumber"));
        order.setExprdate((String) request.getParameter("exprDate"));
        order.setCardType((String) request.getParameter("cardType"));
        order.setLocale("default");
        order.setCourier("default");
        order.setShipAddr1((String) request.getParameter("billAddr1"));
        order.setShipAddr2((String) request.getParameter("billAddr2"));
        order.setShipCity((String) request.getParameter("billCity"));
        order.setShipState((String) request.getParameter("billState"));
        order.setShipZip((String) request.getParameter("billZip"));
        order.setShipCountry((String) request.getParameter("billCountry"));
        order.setShipToFirstname((String) request.getParameter("billFirstName"));
        order.setShipToLastname((String) request.getParameter("billLastName"));
        session.setAttribute("order",order);
        if(shipToDiff==null)
        {
            orderService.insertOrder(order);
            return "order/view_order";
        }
        else
        {
            return "order/shipping_form";
        }
    }
    @PostMapping("confirmOrder")
    public String confirmOrder(HttpServletRequest request,Model model)
    {
        Order order = (Order)(request.getSession().getAttribute("order"));
        System.out.println(order);
        order.setShipAddr1((String)request.getParameter("shipAddr1"));
        order.setShipAddr2((String)request.getParameter("shipAddr2"));
        order.setShipCity((String)request.getParameter("shipCity"));
        order.setShipState((String)request.getParameter("shipState"));
        order.setShipZip((String)request.getParameter("shipZip"));
        order.setShipCountry((String)request.getParameter("shipCountry"));
        order.setShipToFirstname((String)request.getParameter("shipFirstName"));
        order.setShipToLastname((String)request.getParameter("shipLastName"));
        orderService.insertOrder(order);
        return "order/view_order";
    }
    @GetMapping("orderLists")
    public String myOrders(HttpServletRequest request,Model model)
    {

        List<Order> orderList = orderService.getOrdersByUsername(((Account)(request.getSession().getAttribute("account"))).getUsername());
        //从httpsession中获取用户名，并查询订单列表
        model.addAttribute("orderList",orderList);
        return "order/order_lists";
    }
    @GetMapping("viewOrder")
    public String viewOrder(HttpServletRequest request,Model model)
    {
        Order order = orderService.getOrder(Integer.parseInt(request.getParameter("orderId")));
        request.getSession().setAttribute("order",order);
//        model.addAttribute("order",order); //这里不知道为啥用model不行了
        System.out.println(((Order)(request.getSession().getAttribute("order"))).getOrderId());
        return "order/view_order";
    }
}
