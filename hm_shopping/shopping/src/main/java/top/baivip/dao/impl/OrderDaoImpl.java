package top.baivip.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.baivip.dao.OrderDao;
import top.baivip.domain.Order;
import top.baivip.domain.OrderItem;
import top.baivip.domain.vo.OrderItemVo;
import top.baivip.utils.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    //插入订单表
    @Override
    public void saveOrder(Order o) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into orders values(?,?,?,?,null,null,null,?)";
        Object[] params = {o.getOid(),o.getOrdertime(),o.getTotal(),o.getState(),o.getUid()};
        try {
            queryRunner.update(DataSourceUtil.getConnection(),sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //插入中间表
    @Override
    public void saveOrderItem(OrderItem oi) {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "insert into orderitem values(?,?,?,?)";
        try {
            Object[] params = {oi.getCount(),oi.getSubTotal(),oi.getPid(),oi.getOid()};
            queryRunner.update(DataSourceUtil.getConnection(),sql,params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findByOid(String oid) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="select * from orders where oid=?";

        try {
            return qr.query(sql,new BeanHandler<>(Order.class),oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderItemVo> findItemVos(String oid) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="\n" +
                "SELECT oi.count,oi.subtotal,oi.pid,p.pname,p.pimage,p.shop_price AS price FROM orderitem oi,product p WHERE oi.pid=p.pid AND oid=?";

        try {
            return qr.query(sql,new BeanListHandler<>(OrderItemVo.class),oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //分页查询
    @Override
    public List<Order> findOrderByUid(String uid, int pageNumber, int pageSize) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="SELECT * FROM orders  WHERE uid=? LIMIT ?,?";

        try {
            return qr.query(sql,new BeanListHandler<>(Order.class),uid,(pageNumber-1)*pageSize,pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUid(String uid) {

        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="select count(*) from orders where uid=?";

        try {
            return ((Long)qr.query(sql,new ScalarHandler(),uid)).intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void updateSHR(Order order) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql = "update orders set name = ?,address = ?,telephone = ? where oid = ?";
        Object[] params = {order.getName(),order.getAddress(),order.getTelephone(),order.getOid()};
        try {
             qr.update(sql,params);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateState(String oid, int state) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="update orders set state=? where oid=?";

        try {
            qr.update(sql,state,oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double findMoney(Order order) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="SELECT total FROM orders WHERE oid = ?";

        try {
            return ((Double)qr.query(sql,new ScalarHandler(),order.getOid()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Double findMoney1(Order order) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="SELECT total FROM orders WHERE oid = ?";

        try {
            return ((Double)qr.query(sql,new ScalarHandler(),order.getOid()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
