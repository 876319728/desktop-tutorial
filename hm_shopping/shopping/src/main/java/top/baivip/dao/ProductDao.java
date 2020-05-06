package top.baivip.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.baivip.constants.Global;
import top.baivip.domain.Product;
import top.baivip.utils.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProductDao {
    public List<Product> findNews() {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from product where pflag = ? order by pdate desc limit 9";
        try {
            return qr.query(sql,new BeanListHandler<>(Product.class),Global.PRODUCT_PFLAG_ON);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> findHots() {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from product where pflag = ? and is_hot = ? order by pdate desc limit 9";
        try {
            return qr.query(sql,new BeanListHandler<>(Product.class),Global.PRODUCT_PFLAG_ON,Global.PRODUCT_IS_HOT );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product findById(String pid) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from product where pid = ?";
        try {
            return qr.query(sql,new BeanHandler<>(Product.class),pid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 分页
     * @param cid
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Product> findByPageWithCid(String cid, int pageNumber, int pageSize) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from product where cid = ? limit ?,?";
        try {
            return qr.query(sql,new BeanListHandler<>(Product.class),cid,(pageNumber - 1)*pageSize,pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 商品总数量
     * @param cid
     * @return
     */
    public int count(String cid) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select count(*) from product where cid = ?";

        try {
            return ((Long)qr.query(sql,new ScalarHandler(),cid)).intValue();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Product> findByPage(int pageNumber, int pageSize) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="SELECT * FROM product  LIMIT ?,?";

        try {
            return qr.query(sql,new BeanListHandler<>(Product.class),(pageNumber-1)*pageSize,pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int count() {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="SELECT count(*) FROM product ";

        try {
            return ((Long)qr.query(sql,new ScalarHandler())).intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Product p) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="insert into product values(?,?,?,?,?,?,?,?,?,?) ";

        try {
            qr.update(sql,
                    p.getPid(),p.getPname(),p.getMarket_price(),
                    p.getShop_price(),p.getPimage(),p.getPdate(),
                    p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCid()

            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
