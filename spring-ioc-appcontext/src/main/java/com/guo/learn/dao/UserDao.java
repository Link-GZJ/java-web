package com.guo.learn.dao;

import com.guo.learn.service.User;
import org.springframework.asm.TypeReference;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private String url;
    private String userName;
    private String password;
    private String driver;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        try {
            //com.mysql.jdbc.Driver
            //加载驱动
            Class.forName(driver);
            //链接数据库
            Connection connection = DriverManager.getConnection(url, userName, password);
            //编写sql
            String sql="select * from user";
            //预编译
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"),resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("name"));
                userList.add(user);
            }
            //6.关闭连接，释放资源
            ps.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
