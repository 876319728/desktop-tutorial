package top.baivip.service.impl;

import top.baivip.domain.Order;
import top.baivip.domain.OrderItem;
import top.baivip.service.OrderService;
import top.baivip.utils.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImplWrapper implements OrderService {
    //再次说明 不是替代原来的对象 只是为了增强逻辑
    private OrderService originOrderService;

    public OrderServiceImplWrapper(OrderService originOrderService) {
        this.originOrderService = originOrderService;
    }

    @Override
    public void save(Order order, List<OrderItem> orderItems) {
        //开启事务
        try {
            DataSourceUtil.beginTransaction();
            //写业务
           originOrderService.save(order,orderItems);
            //业务结束
            //成功 提交
            DataSourceUtil.commitAndClose();
        } catch (SQLException e) {
            DataSourceUtil.rollbackAndClose();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findByOidWithItems(String oid) {
        return originOrderService.findByOidWithItems(oid);
    }

    @Override
    public List<Order> findOrderByUid(String uid, int pageNumber, int pageSize) {
        return originOrderService.findOrderByUid(uid,pageNumber,pageSize);
    }

    @Override
    public int countByUid(String uid) {
        return originOrderService.countByUid(uid);
    }

    @Override
    public void updateSHR(Order order) {
        originOrderService.updateSHR(order);
    }

    @Override
    public void updateState(String oid, int orderStateYifukuan) {
        originOrderService.updateState(oid,orderStateYifukuan);
    }

    @Override
    public Double findMoney(Order order) {
        return originOrderService.findMoney(order);
    }
}
