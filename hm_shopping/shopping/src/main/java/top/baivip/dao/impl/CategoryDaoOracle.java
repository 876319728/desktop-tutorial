package top.baivip.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.baivip.dao.CategoryDao;
import top.baivip.domain.Category;
import top.baivip.utils.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoOracle implements CategoryDao {

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
        System.out.println("这里是oracle实现");
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from category where cid = ?";
        try {
            return queryRunner.query(sql,new BeanHandler<Category>(Category.class),cid);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Category category) {

    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void del(String cid) {

    }

    @Override
    public int countByCid(String cid) {

        return 0;
    }
}
