package pl.javabegin.mySQL;

import pl.javabegin.entity.User;
import pl.javabegin.entity.UserDao;

import java.sql.Connection;
import java.sql.SQLException;

public class Main01 {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User("Renata", "rena@Ggmail.com", "t51ggg");
        user = userDao.create(user);

    }
}
