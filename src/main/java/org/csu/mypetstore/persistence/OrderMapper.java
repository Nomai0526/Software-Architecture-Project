package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    void insertOrder(Order order);
    Order getOrder(int orderId);
    List<Order> getOrdersByUsername(String username);
}
