package com.guo.learn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        pw.write("<!doctype html><html>");
        pw.write("<head>");
        pw.write("<link href=\"/static/bootstrap.css\" rel=\"stylesheet\">");
        pw.write("</head>");
        pw.write("<body>");
        pw.write("<h1 class=\"border\">Index Page!</h1>");
        pw.write("</body>");
        pw.write("</html>");
        pw.flush();
    }
}
