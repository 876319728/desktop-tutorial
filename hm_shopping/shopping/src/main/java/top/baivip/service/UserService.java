package top.baivip.service;


import top.baivip.dao.UserDao;
import top.baivip.domain.User;

public class UserService {

    public void save(User user){
        new UserDao().save(user);
    }

    public User findUserByUAP(String username, String password) {
        return new UserDao().findUserByUAP(username,password);
    }
}
