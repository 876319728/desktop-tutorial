package top.baivip.web.vo;


import top.baivip.dao.OrderDao;
import top.baivip.dao.impl.OrderDaoImpl;
import top.baivip.domain.Order;
import top.baivip.utils.BeanFactory;

public class Test {
    private static OrderDao orderDao = BeanFactory.newInstance(OrderDao.class);
    public static void main(String[] args) {
        double b =11.11;
        long b1 = (long) b;
        System.out.println(b1);
    }
}
