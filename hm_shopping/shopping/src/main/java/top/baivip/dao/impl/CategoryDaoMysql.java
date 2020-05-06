package top.baivip.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.baivip.dao.CategoryDao;
import top.baivip.domain.Category;
import top.baivip.utils.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoMysql implements CategoryDao {

    //查询所有分类信息
    public List<Category> findAll() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from category";
        try {
            return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //通过id查找分类信息
    public Category findById(String cid) {
        System.out.println("这里是mysql实现");
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from category where cid = ?";
        try {
            return queryRunner.query(sql, new BeanHandler<Category>(Category.class), cid);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Category category) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "insert into category values(?,?)";
        try {
            queryRunner.update(sql, category.getCid(), category.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category category) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "update category set cname = ? where cid = ?";
        try {
            queryRunner.update(sql,category.getCname(),category.getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void del(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from category where cid = ?";
        try {
            queryRunner.update(sql,cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByCid(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select count(*) from product where cid = ?";
        try {
            return ((Long)queryRunner.query(sql,new ScalarHandler(),cid)).intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
