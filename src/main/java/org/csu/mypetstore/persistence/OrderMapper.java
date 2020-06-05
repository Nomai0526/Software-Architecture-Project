package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    void insertOrder(Order order);

    void insertOrderItems(@Param("list") List<CartItem> cartItemList, @Param("orderId") int orderId);//同时传入订单号和订单内容

    List<CartItem> getItemListById(int orderId);

    Order getOrder(int orderId);

    List<Order> getOrdersByUsername(String username);

    List<String> getItemIdByOrderId(int orderId);

    List<Order> getOrders();

    void updateOrder(Order order);

    void deleteOrder(Order order);

    void deleteOrderItems(int orderId);
}
