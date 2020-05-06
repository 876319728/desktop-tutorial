package top.baivip.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.baivip.domain.Category;
import top.baivip.domain.User;
import top.baivip.utils.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    //查询所有分类信息
    public List<Category> findAll();

    //通过id查找分类信息
    public Category findById(String cid);

    void save(Category category);

    void update(Category category);

    void del(String cid);

    int countByCid(String cid);
}
