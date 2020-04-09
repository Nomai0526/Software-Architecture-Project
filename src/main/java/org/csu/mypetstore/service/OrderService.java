package org.csu.mypetstore.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ItemMapper itemMapper;

    public void insertOrder(Order order) {
        Map<String,Object> map = new HashMap<String, Object>();
        for (CartItem cartItem:order.getCartItemList()
        ) {
            map.put("increment",cartItem.getQuantity());
            map.put("itemId",cartItem.getItem().getItemId());
            //对每个项目单独创建一个map，用于修改数据库
            itemMapper.decreaseInventoryQuantity(map);
            map.clear();
            //清空map
        }
        orderMapper.insertOrder(order); //向数据库中加入订单
        orderMapper.insertOrderItems(order.getCartItemList(),order.getOrderId());//向数据库的的另一张表中插入订单内容
    }

    public Order getOrder(int orderId) {
        Order order = orderMapper.getOrder(orderId);
        order.setCartItemList(getItemListById(orderId));
        List<String> itemIdList = orderMapper.getItemIdByOrderId(orderId);
        List<Item> itemList = new ArrayList<Item>();
        for(int i = 0;i<itemIdList.size();i++)
        {
            itemList.add(itemMapper.getItem(itemIdList.get(i)));
            order.getCartItemList().get(i).setItem(itemList.get(i));
        }
        return order;
        //这里的获取订单的写法是受数据库的设计的影响的
        //因为数据库中的订单项存储中只含有itemid，所以要手动查询出对应的item并进行同步
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }

    public List<CartItem> getItemListById(int id){
        return orderMapper.getItemListById(id);
    }

//    public void
//
//    public int getNextId(String name) {
//        return orderDao.getNextId(name);
//    }
}
