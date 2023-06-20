package com.guo.learn.controller;

import com.guo.learn.bean.SignInBean;
import com.guo.learn.bean.User;
import com.guo.learn.framework.GetMapping;
import com.guo.learn.framework.ModelAndView;
import com.guo.learn.framework.PostMapping;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
    private static final Map<String, User> userDateBase = new HashMap<>(){};
    static{
            List<User> users = List.of(new User("bob@example.com", "bob123", "Bob", "This is bob."), new User("tom@example.com", "tomcat", "Tom", "This is tom."));
            users.forEach(user -> userDateBase.put(user.email,user));
        }

    @GetMapping("/signin")
    public ModelAndView signin() {
        return new ModelAndView("/signin.html");
    }

    @PostMapping("/signin")
    public ModelAndView doSignIn(SignInBean bean, HttpServletResponse response, HttpSession session) throws IOException {
        User user = userDateBase.get(bean.email);
        if (user == null || !user.password.equals(bean.password)) {
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write("{\"error\":\"bad email or password\"}");
            pw.flush();
        }else{
            session.setAttribute("user", user);
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write("{\"result\":\"true\"}");
            pw.flush();
        }
        return null;
    }

    @GetMapping("/signout")
    public ModelAndView signOut(HttpSession session) {
        session.removeAttribute("user");
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/user/profile")
    public ModelAndView profile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/signin");
        }
        return new ModelAndView("/profile.html","user",user);
    }
}
