package top.baivip.service;

import top.baivip.domain.Order;
import top.baivip.domain.OrderItem;

import java.util.List;

public interface OrderService {

     void save(Order order, List<OrderItem> orderItems);

     Order findByOidWithItems(String oid);

    List<Order> findOrderByUid(String uid, int pageNumber, int pageSize);

    int countByUid(String uid);

    void updateSHR(Order order);

    void updateState(String oid, int orderStateYifukuan);

    Double findMoney(Order order);
}
