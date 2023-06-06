package com.guo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebServlet("/pref")
public class LanguageServlet extends HttpServlet {
    private static final Set<String> LANGUAGE = Set.of("en","zh");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        if (LANGUAGE.contains(lang)) {
            Cookie cookie = new Cookie("lang", lang);
            cookie.setPath("/");
            cookie.setMaxAge(8640000);
            resp.addCookie(cookie);
        }
        resp.sendRedirect("/");
    }
}
