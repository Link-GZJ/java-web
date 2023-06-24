package com.guo.learn.service;

import com.guo.learn.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private MailService mailService;
    private UserDao userDao;

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private final List<User> users = new ArrayList<>(List.of( // users:
            new User(1, "bob@example.com", "password", "Bob"), // bob
            new User(2, "alice@example.com", "password", "Alice"), // alice
            new User(3, "tom@example.com", "password", "Tom"))); // tom

    public User login(String email, String password) {
        List<User> userList = userDao.getUserList();
        for (User user : userList) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                mailService.sendLoginMail(user);
                return user;
            }
        }
        throw new RuntimeException("login failed.");
    }

    public User getUser(long id) {
        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
    }

    public User register(String email, String password, String name) {
        users.forEach((user) -> {
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new RuntimeException("email exist.");
            }
        });
        User user = new User(users.stream().mapToLong(User::getId).max().getAsLong() + 1, email, password, name);
        users.add(user);
        mailService.sendRegistrationMail(user);
        return user;
    }
}
