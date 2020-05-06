package top.baivip.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import top.baivip.domain.User;
import top.baivip.utils.DataSourceUtil;

import javax.lang.model.element.QualifiedNameable;
import java.sql.SQLException;

public class UserDao {
    public void save(User user) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "insert into user(uid,username,password,name,email,birthday,gender,remark)values(?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getBirthday(),user.getGender(),user.getRemark()};
        try {
            queryRunner.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUserByUAP(String username, String password) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from user where username = ? and password = ?";
        Object[] params = {username,password};
        try {
            User user = queryRunner.query(sql, new BeanHandler<User>(User.class), params);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
