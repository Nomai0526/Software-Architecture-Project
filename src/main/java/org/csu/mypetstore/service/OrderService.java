package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.persistence.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

//    OrderDaoImpl orderDao = new OrderDaoImpl();

    public void insertOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    public Order getOrder(int orderId) {
        return orderMapper.getOrder(orderId);
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }
//
//    public int getNextId(String name) {
//        return orderDao.getNextId(name);
//    }
}
