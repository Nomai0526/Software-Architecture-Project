package org.csu.mypetstore.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("order/")
@SessionAttributes({"cart","order"})
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    CatalogService catalogService;

    @GetMapping("newOrder")
    public String newOrder(HttpServletRequest request,Model model)
    {
        Cart cart = (Cart) (request.getSession().getAttribute("cart"));
        Iterator<CartItem> cartItems = cart.getAllCartItems();
        boolean outStock = false;
        //用于标记是否出超出库存的临时变量
        while (cartItems.hasNext()){
            CartItem cartItem = cartItems.next();
            String itemId = cartItem.getItem().getItemId();
            System.out.println(itemId);
            int quantity = cartItem.getQuantity();
            if(quantity>catalogService.getInventoryById(cartItem.getItem().getItemId()))
            {
                cartItem.setInStock(false);
                outStock = true;
            }
        }
        if(outStock)
        {
            return "cart/updateCartQuantities";
            //若果下单时大于库存数量，则返回本页面
        }

        //在提交订单时对于商品的库存再做一次验证，以免有其他人在中间的时间下订单导致库存数量不足

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
    public String shippingForm(HttpServletRequest request, String shipToDiff,Model model)
    {
        //用了比较古典的写法
        HttpSession session = request.getSession();
        Order order = new Order();
        order.setOrderId(23333);
        order.setUserId(((Account) session.getAttribute("account")).getUsername());
        order.setOrderDate(new Date());
        order.setStatus(1);
        //订单状态1表示未完成，2表示正在运输，3表示已完成

        order.setBillAddr1((String) request.getParameter("billAddr1"));
        order.setBillAddr2((String) request.getParameter("billAddr2"));
        order.setBillCity((String) request.getParameter("billCity"));
        order.setBillState((String) request.getParameter("billState"));
        order.setBillZip((String) request.getParameter("billZip"));
        order.setBillCountry((String) request.getParameter("billCountry"));
        order.setTotalPrice(((Cart) session.getAttribute("cart")).getSubTotal());
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
            Cart cart = (Cart) (request.getSession().getAttribute("cart"));

            List<CartItem> cartItems = new ArrayList<CartItem>();
            for (CartItem cartItem:cart.getCartItemList()
                 ) {
                cartItems.add(cartItem);
            }
            //这里如果不深拷贝的话会导致清空购物车时将订单内容一并清空，因此手动实现一下深拷贝

            order.setCartItemList(cartItems);
            model.addAttribute("order",order);
            session.setAttribute("order",order);
            orderService.insertOrder(order);
            cart.clear();//清空购物车
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
        order.setShipAddr1((String)request.getParameter("shipAddr1"));
        order.setShipAddr2((String)request.getParameter("shipAddr2"));
        order.setShipCity((String)request.getParameter("shipCity"));
        order.setShipState((String)request.getParameter("shipState"));
        order.setShipZip((String)request.getParameter("shipZip"));
        order.setShipCountry((String)request.getParameter("shipCountry"));
        order.setShipToFirstname((String)request.getParameter("shipFirstName"));
        order.setShipToLastname((String)request.getParameter("shipLastName"));

        Cart cart = (Cart) (request.getSession().getAttribute("cart"));

        List<CartItem> cartItems = new ArrayList<CartItem>();
        for (CartItem cartItem:cart.getCartItemList()
        ) {
            cartItems.add(cartItem);
        }
        //这里如果不深拷贝的话会导致清空购物车时将订单内容一并清空，因此手动实现一下深拷贝

        order.setCartItemList(cartItems);
        model.addAttribute("order",order);
        orderService.insertOrder(order);
        cart.clear();//清空购物车
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
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getOrder(orderId);   //从数据库获得订单
        HttpSession session0 = request.getSession();
//        order.setCartItemList(orderService.getItemListById(orderId));   //从数据库获得订单内容
        model.addAttribute("order",order);
        HttpSession session = request.getSession();
        session.setAttribute("order",order);
        System.out.println(session.getAttribute("order"));
        return "order/view_order";
    }
}
