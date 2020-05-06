package top.baivip.dao;

import top.baivip.domain.Order;
import top.baivip.domain.OrderItem;
import top.baivip.domain.vo.OrderItemVo;

import java.util.List;

public interface OrderDao {
    void saveOrder(Order order);

    void saveOrderItem(OrderItem orderItem);

    Order findByOid(String oid);

    List<OrderItemVo> findItemVos(String oid);

    List<Order> findOrderByUid(String uid, int pageNumber, int pageSize);

    int countByUid(String uid);

    void updateSHR(Order order);


    void updateState(String oid, int orderStateYifukuan);

    Double findMoney(Order order);
}
