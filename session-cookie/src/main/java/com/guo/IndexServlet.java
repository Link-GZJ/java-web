package com.guo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = (String) req.getSession().getAttribute("user");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("X-Powered-By","JavaEE Servlet");
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Welcome," + (user == null ? "Guest" : user) + "</h1>");
        if (user == null) {
            if ("zh".equals(parseLanguageFromCookie(req))) {
                pw.write("<p> <a href=\"/signIn\">登 录</a></p>");
            } else {
                pw.write("<p> <a href=\"/signIn\">Sign In</a></p>");
            }

        }else{
            if ("zh".equals(parseLanguageFromCookie(req))) {
                pw.write("<p> <a href=\"/signOut\">登出</a></p>");
            } else {
                pw.write("<p> <a href=\"/signOut\">Sign Out</a></p>");
            }
        }
        pw.write("<p> <a href=\"/pref?lang=en\">English</a> | <a href=\"/pref?lang=zh\">中文</a></p>");
        pw.flush();
    }

    private String parseLanguageFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lang".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "en";
    }
}
