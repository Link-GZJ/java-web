package com.guo.mvc.controller;

import com.guo.mvc.mode.School;
import com.guo.mvc.mode.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        School school = new School("No.1 Middle School", "101 South Street");
        User tom = new User(124, "Tom", school);
        req.setAttribute("user",tom);
        req.getRequestDispatcher("/WEB-INF/user.jsp").forward(req,resp);
    }
}
