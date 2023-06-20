package com.guo.learn.controller;

import com.guo.learn.bean.User;
import com.guo.learn.framework.GetMapping;
import com.guo.learn.framework.ModelAndView;
import jakarta.servlet.http.HttpSession;

public class IndexController {
    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new ModelAndView("/index.html", "user", user);
    }

    @GetMapping("/hello")
    public ModelAndView hello(String name) {
        if (name == null) {
            name = "World";
        }
        return new ModelAndView("/hello.html", "name", name);
    }
}
