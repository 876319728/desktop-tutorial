package top.baivip.service.impl;

import top.baivip.dao.OrderDao;
import top.baivip.domain.Order;
import top.baivip.domain.OrderItem;
import top.baivip.domain.vo.OrderItemVo;
import top.baivip.service.OrderService;
import top.baivip.transactional.Service;
import top.baivip.transactional.Transaction;
import top.baivip.utils.BeanFactory;
import top.baivip.utils.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = BeanFactory.newInstance(OrderDao.class);
    @Override
    @Transaction
    public void save(Order order, List<OrderItem> orderItems) {

//        //开启事务
//        try {
//            DataSourceUtil.beginTransaction();
//
//            //写业务
//            //先保存order
//            orderDao.saveOrder(order);
//            //int i = 5/0;
//            //再保存 orderItem
//            for (OrderItem orderItem : orderItems){
//                orderDao.saveOrderItem(orderItem);
//            }
//            //业务结束
//
//            //成功 提交
//            DataSourceUtil.commitAndClose();
//        } catch (SQLException e) {
//            DataSourceUtil.rollbackAndClose();
//            throw new RuntimeException(e);
//        }
        orderDao.saveOrder(order);
        for (OrderItem orderItem : orderItems){
               orderDao.saveOrderItem(orderItem);
          }
    }

    @Override
    public Order findByOidWithItems(String oid) {
        //假当 页面只需要订单数据
        Order order= orderDao.findByOid(oid);
        //假当 页面只需要订单数据
        //少了该订单关联的订单项显示信息
        //继续查询 该订单的订单项信息
        List<OrderItemVo> vos=orderDao.findItemVos(oid);

        order.setVos(vos);

        return order;
    }

    @Override
    public List<Order> findOrderByUid(String uid, int pageNumber, int pageSize) {

        List<Order> orders = orderDao.findOrderByUid(uid, pageNumber, pageSize);
        //再次查询 订单订单项
        for (Order order : orders) {
            List<OrderItemVo> itemVos = orderDao.findItemVos(order.getOid());
            order.setVos(itemVos);
        }
        return orders;
    }

    @Override
    public int countByUid(String uid) {
        return orderDao.countByUid(uid);
    }

    @Override
    public void updateSHR(Order order) {
        orderDao.updateSHR(order);
    }

    @Override
    public void updateState(String oid,int orderStateYifukuan) {
        orderDao.updateState(oid,orderStateYifukuan);
    }

    @Override
    public Double findMoney(Order order) {
        return orderDao.findMoney(order);
    }


}
